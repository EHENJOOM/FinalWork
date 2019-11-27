package com.zhk.panel.student.subject;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.main.teacher.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:03
 * @description 选课题数据处理类
 */
public class SelectSubjectModel {

    /**
     * 从数据库中读取所有课题的数据
     * @param loginBean 该学生学号
     * @param baseCallBack 读取完毕的回调
     */
    public void select(LoginBean loginBean, BaseCallBack<List<SubjectBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from subject";

            List<SubjectBean> subjectBeans = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    SubjectBean subjectBean = new SubjectBean();
                    subjectBean.setId(resultSet.getInt("id"));
                    subjectBean.setCode(resultSet.getString("code"));
                    subjectBean.setName(resultSet.getString("name"));
                    subjectBean.setOfAcademy(resultSet.getString("academy"));
                    subjectBean.setTotalNum(resultSet.getInt("total"));
                    subjectBean.setAcceptedNum(resultSet.getInt("accepted"));
                    subjectBean.setConfirmingNum(resultSet.getInt("confirming"));
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setNumber(resultSet.getString("teacher"));
                    subjectBean.setTeacherBean(teacherBean);
                    subjectBeans.add(subjectBean);
                }

                statement.close();
                resultSet.close();
                selectTeacher(connection, loginBean, subjectBeans, baseCallBack);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }

        });
    }

    /**
     * 读取完课题数据后，读取指导老师的数据
     * @param connection 数据库连接
     * @param loginBean 学生账号信息
     * @param subjectBeans 查询到的课题数据
     * @param baseCallBack 查询完毕的回调
     */
    private void selectTeacher(Connection connection,LoginBean loginBean, List<SubjectBean> subjectBeans, BaseCallBack<List<SubjectBean>> baseCallBack) {
        String sql = "select * from teacher";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String num = resultSet.getString("number");
                subjectBeans.forEach(subjectBean -> {
                    if (num.equals(subjectBean.getTeacherBean().getNumber())) {
                        try {
                            subjectBean.getTeacherBean().setName(resultSet.getString("name"));
                            subjectBean.getTeacherBean().setSex(resultSet.getString("sex"));
                            subjectBean.getTeacherBean().setJobTitle(resultSet.getString("job"));
                        } catch (SQLException e) {
                            baseCallBack.onFailed("数据获取失败！");
                        }
                    }
                });
            }

            statement.close();
            resultSet.close();
            selectState(connection, loginBean, subjectBeans, baseCallBack);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        }
    }

    /**
     * 读取完指导教师信息后，读取该学生对该课题的状态
     * @param connection 数据库连接
     * @param loginBean 学生账号信息
     * @param subjectBeans 查询到的课题数据
     * @param baseCallBack 查询完毕的回调
     */
    private void selectState(Connection connection, LoginBean loginBean, List<SubjectBean> subjectBeans, BaseCallBack<List<SubjectBean>> baseCallBack) {
        String sql = "select code, state from stu_sub where number = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loginBean.getAccount());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                if (code != null) {
                    subjectBeans.forEach(subjectBean -> {
                        if (code.equals(subjectBean.getCode())) {
                            try {
                                subjectBean.setState(resultSet.getInt("state"));
                            } catch (SQLException e) {
                                baseCallBack.onFailed("数据获取失败！");
                            }
                        }
                    });
                }
            }

            ConnectionPoolEnum.getInstance().putBack(connection);
            statement.close();
            resultSet.close();
            baseCallBack.onSucceed(subjectBeans);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        }
    }

    /**
     * 向数据库中插入学生选课题的信息
     * @param matchBean 学生和课题的匹配数据
     * @param baseCallBack 插入数据回调
     */
    public void insertSelectedSubject(MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "insert into stu_sub(number, code, state) values(?,?,?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, matchBean.getStudentBean().getNumber());
                statement.setString(2, matchBean.getSubjectBean().getCode());
                statement.setInt(3, matchBean.getState());
                statement.executeUpdate();

                statement.close();
                baseCallBack.onSucceed(matchBean);
            } catch (SQLException e) {
                baseCallBack.onFailed("选择课题失败！");
            }
        });
    }

    /**
     * 把数据库中该学生已选上课题的事务删除
     * @param matchBean 学生和课题的匹配数据
     * @param baseCallBack 删除数据回调
     */
    public void cancelSelectedSubject(MatchBean matchBean, BaseCallBack<MatchBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete from stu_sub where number = ? and code = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, matchBean.getStudentBean().getNumber());
                statement.setString(2, matchBean.getSubjectBean().getCode());
                statement.executeUpdate();

                statement.close();
                baseCallBack.onSucceed(matchBean);
            } catch (SQLException e) {
                e.printStackTrace();
                baseCallBack.onFailed("退选课题失败！");
            }
        });
    }

}
