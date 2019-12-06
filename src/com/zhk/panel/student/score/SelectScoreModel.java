package com.zhk.panel.student.score;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.main.StudentBean;
import com.zhk.main.TeacherBean;
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
 * @date 2019/11/24 16:49
 * @description 分数查询数据处理器
 */
public class SelectScoreModel {

    /**
     * 从数据库中查询该学生所有的成绩
     * <p>由于{@code ScoreBean}是由{@code TeacherBean}和{@code StudentBean}构成的，因此查询完分数信息后，还需查找对应的老师信息和学生个人信息</p>
     * @param loginBean 账号信息
     * @param baseCallBack 查询完毕的回调
     */
    public void select(LoginBean loginBean, BaseCallBack<List<ScoreBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from score where num = ?";

            List<ScoreBean> list = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ScoreBean scoreBean = new ScoreBean();
                    scoreBean.setId(resultSet.getInt("id"));
                    scoreBean.setName(resultSet.getString("name"));
                    scoreBean.setCode(resultSet.getString("code"));
                    scoreBean.setProperty(resultSet.getString("property"));
                    scoreBean.setScore(resultSet.getInt("score"));
                    scoreBean.setPoint(resultSet.getFloat("point"));

                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setNumber(resultSet.getString("teacher"));
                    scoreBean.setTeacherBean(teacherBean);

                    StudentBean studentBean = new StudentBean();
                    studentBean.setNumber(resultSet.getString("num"));
                    scoreBean.setStudentBean(studentBean);
                    list.add(scoreBean);
                }

                statement.close();
                resultSet.close();
                selectTeacher(connection, list, baseCallBack);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据读取失败！");
            }
        });
    }

    /**
     * 查询老师信息
     * @param connection 数据库连接
     * @param list 数据集
     * @param baseCallBack 查询回调
     */
    private void selectTeacher(Connection connection, List<ScoreBean> list, BaseCallBack<List<ScoreBean>> baseCallBack) {
        String sql = "select * from teacher";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                list.forEach(scoreBean -> {
                    if (scoreBean.getTeacherBean().getNumber().equals(number)) {
                        try {
                            scoreBean.getTeacherBean().setName(resultSet.getString("name"));
                            scoreBean.getTeacherBean().setSex(resultSet.getString("sex"));
                            scoreBean.getTeacherBean().setJobTitle(resultSet.getString("job"));
                            scoreBean.getTeacherBean().setOfAcademy(resultSet.getString("academy"));
                        } catch (SQLException e) {
                            baseCallBack.onFailed("数据读取失败！");
                        }
                    }
                });
            }

            statement.close();
            resultSet.close();
            selectStudent(connection, list, baseCallBack);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败白！");
        }
    }

    /**
     * 查询学生个人信息
     * @param connection 数据库连接
     * @param list 数据集
     * @param baseCallBack 查询回调
     */
    private void selectStudent(Connection connection, List<ScoreBean> list, BaseCallBack<List<ScoreBean>> baseCallBack) {
        String sql = "select * from student where number = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, list.get(0).getStudentBean().getNumber());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                String major = resultSet.getString("major");
                String academy = resultSet.getString("academy");
                int grade = resultSet.getInt("grade");
                int clazz = resultSet.getInt("clazz");
                list.forEach(scoreBean -> {
                    scoreBean.getStudentBean().setName(name);
                    scoreBean.getStudentBean().setSex(sex);
                    scoreBean.getStudentBean().setMajor(major);
                    scoreBean.getStudentBean().setAcademy(academy);
                    scoreBean.getStudentBean().setGrade(grade);
                    scoreBean.getStudentBean().setClazz(clazz);
                });
            }

            statement.close();
            resultSet.close();
            baseCallBack.onSucceed(list);
        } catch (SQLException e) {
            baseCallBack.onFailed("数据读取失败！");
        } finally {
            ConnectionPoolEnum.getInstance().putBack(connection);
        }
    }

}
