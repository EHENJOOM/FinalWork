package com.zhk.panel.teacher.check;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.main.StudentBean;
import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.panel.student.subject.MatchBean;
import com.zhk.panel.student.subject.SubjectBean;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:49
 * @description 确认学生课题数据处理器
 */
public class CheckSubjectModel {

    /**
     * 先查找该老师的课题，然后再查找课题详细信息，最后查找选该课题的学生信息
     * @param loginBean 账号信息
     * @param baseCallBack 查找回调
     */
    public void select(LoginBean loginBean, BaseCallBack<List<MatchBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from stu_sub where teacher = ?";

            List<MatchBean> matchBeans = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    MatchBean matchBean = new MatchBean();
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setNumber(loginBean.getAccount());
                    matchBean.setTeacherBean(teacherBean);
                    matchBean.setState(resultSet.getInt("state"));
                    matchBean.setId(resultSet.getInt("id"));

                    StudentBean studentBean = new StudentBean();
                    studentBean.setNumber(resultSet.getString("number"));
                    matchBean.setStudentBean(studentBean);

                    SubjectBean subjectBean = new SubjectBean();
                    subjectBean.setId(resultSet.getInt("subject"));
                    matchBean.setSubjectBean(subjectBean);
                    matchBeans.add(matchBean);
                }

                statement.close();
                resultSet.close();
                selectSubject(connection, matchBeans, baseCallBack);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    /**
     * 从数据库查找课题详细信息
     * @param connection 数据库连接
     * @param matchBeans 学生选课题的匹配信息
     * @param baseCallBack 查找回调
     */
    private void selectSubject(Connection connection, List<MatchBean> matchBeans, BaseCallBack<List<MatchBean>> baseCallBack) {
        String sql = "select * from subject";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                matchBeans.forEach(matchBean -> {
                    if (id == matchBean.getSubjectBean().getId()) {
                        try {
                            matchBean.getSubjectBean().setCode(resultSet.getString("code"));
                            matchBean.getSubjectBean().setName(resultSet.getString("name"));
                            matchBean.getSubjectBean().setOfAcademy(resultSet.getString("academy"));
                            matchBean.getSubjectBean().setTotalNum(resultSet.getInt("total"));
                            matchBean.getSubjectBean().setAcceptedNum(resultSet.getInt("accepted"));
                            matchBean.getSubjectBean().setConfirmingNum(resultSet.getInt("confirming"));
                        } catch (SQLException e) {
                            baseCallBack.onFailed("数据获取失败！");
                        }
                    }
                });
            }

            statement.close();
            resultSet.close();
            selectStudent(connection, matchBeans, baseCallBack);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        }
    }

    /**
     * 查找学生详细信息
     * @param connection 数据库连接
     * @param matchBeans 数据集
     * @param baseCallBack 查找回调
     */
    private void selectStudent(Connection connection, List<MatchBean> matchBeans, BaseCallBack<List<MatchBean>> baseCallBack) {
        String sql = "select * from student";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                matchBeans.forEach(matchBean -> {
                    if (number.equals(matchBean.getStudentBean().getNumber())) {
                        try {
                            matchBean.getStudentBean().setName(resultSet.getString("name"));
                            matchBean.getStudentBean().setSex(resultSet.getString("sex"));
                            matchBean.getStudentBean().setMajor(resultSet.getString("major"));
                            matchBean.getStudentBean().setAcademy(resultSet.getString("academy"));
                            matchBean.getStudentBean().setGrade(resultSet.getInt("grade"));
                            matchBean.getStudentBean().setClazz(resultSet.getInt("clazz"));
                        } catch (SQLException e) {
                            baseCallBack.onFailed("数据获取失败！");
                        }
                    }
                });
            }

            baseCallBack.onSucceed(matchBeans);
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        } finally {
            ConnectionPoolEnum.getInstance().putBack(connection);
        }
    }

    /**
     * 向数据库中更新该学生选课题的状态
     * @param topic 更新类型
     * @param matchBean 该学生选课题的数据集
     * @param baseCallBack 操作回调
     */
    public void check(String topic, MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        if (Events.ACCEPT_STUDENT.equals(topic)) {
            if (matchBean.getSubjectBean().getAcceptedNum() + 1 > matchBean.getSubjectBean().getTotalNum()) {
                baseCallBack.onFailed("该课题总可接收人数已到达最大值！");
                return;
            }
            matchBean.getSubjectBean().setAcceptedNum(matchBean.getSubjectBean().getAcceptedNum() + 1);
            accept(matchBean, baseCallBack);
        } else if (Events.REFUSE_STUDENT.equals(topic)) {
            matchBean.getSubjectBean().setConfirmingNum(matchBean.getSubjectBean().getConfirmingNum() - 1);
            refuse(matchBean, baseCallBack);
        }
    }

    /**
     * 拒绝该学生的课题请求，只更新stu_sub表中的内容，之后再调用updateSubject()方法更新subject表中的数据
     * @param matchBean 更新数据
     * @param baseCallBack 更新回调
     */
    private void refuse(MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete stu_sub where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, matchBean.getId());

                if (statement.executeUpdate() != 1) {
                    baseCallBack.onFailed("操作失败！");
                }
                statement.close();

                updateSubject(Events.REFUSE_STUDENT, connection, matchBean, baseCallBack);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    /**
     * 接收该学生的课题请求，只更新stu_sub表中的数据，之后再调用updateSubject()更新subject表中的数据
     * @param matchBean 更新的数据
     * @param baseCallBack 更新回调
     */
    private void accept(MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update stu_sub set state = ? where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, matchBean.getState());
                statement.setInt(2, matchBean.getId());

                if (statement.executeUpdate() != 1) {
                    baseCallBack.onFailed("操作失败！");
                    return;
                }

                statement.close();
                updateSubject(Events.ACCEPT_STUDENT, connection, matchBean, baseCallBack);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    /**
     * 更新subject表中的内容
     * @param topic 更改的主题
     * @param connection 数据库连接
     * @param matchBean 更新的数据
     * @param baseCallBack 更新回调
     */
    private void updateSubject(String topic, Connection connection, MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        String sql = "update subject set total = ?, confirming = ?, accepted = ? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, matchBean.getSubjectBean().getTotalNum());
            statement.setInt(2, matchBean.getSubjectBean().getConfirmingNum());
            statement.setInt(3, matchBean.getSubjectBean().getAcceptedNum());
            statement.setInt(4, matchBean.getSubjectBean().getId());

            if (statement.executeUpdate() == 1) {
                if (Events.REFUSE_STUDENT.equals(topic)) {
                    matchBean.setState(Config.UNSELECTED_SUBJECT);
                } else if (Events.ACCEPT_STUDENT.equals(topic)){
                    matchBean.setState(Config.ACCEPTED_SUBJECT);
                }
                baseCallBack.onSucceed(matchBean);
            } else {
                baseCallBack.onFailed("操作失败！");
            }
            statement.close();
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        } finally {
            ConnectionPoolEnum.getInstance().putBack(connection);
        }
    }
}
