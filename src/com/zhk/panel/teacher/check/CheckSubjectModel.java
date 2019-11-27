package com.zhk.panel.teacher.check;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.main.student.StudentBean;
import com.zhk.main.teacher.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BaseModel;
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
     *
     * @param loginBean
     * @param baseCallBack
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
     *
     * @param connection
     * @param matchBeans
     * @param baseCallBack
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
     *
     * @param connection
     * @param matchBeans
     * @param baseCallBack
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
            ConnectionPoolEnum.getInstance().putBack(connection);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        }
    }
}
