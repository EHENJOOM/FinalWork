package com.zhk.panel.teacher.info.student;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.main.StudentBean;
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
 * @date 2019/11/27 16:18
 * @description 操作学生信息数据处理器
 */
public class OperateStudentModel {

    /**
     * 从数据库中查找所有学生信息
     * @param baseCallBack 查找回调
     */
    public void select(BaseCallBack<List<StudentBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from student";

            List<StudentBean> studentBeans = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    StudentBean studentBean = new StudentBean();
                    studentBean.setId(resultSet.getInt("id"));
                    studentBean.setNumber(resultSet.getString("number"));
                    studentBean.setAcademy(resultSet.getString("academy"));
                    studentBean.setName(resultSet.getString("name"));
                    studentBean.setSex(resultSet.getString("sex"));
                    studentBean.setMajor(resultSet.getString("major"));
                    studentBean.setGrade(resultSet.getInt("grade"));
                    studentBean.setClazz(resultSet.getInt("clazz"));
                    studentBean.setState(Config.UNCHANGED_INFO);
                    studentBeans.add(studentBean);
                }

                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(studentBeans);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 更新学生基本信息
     * @param studentBean 学生信息
     * @param baseCallBack 更新回调
     */
    public void update(StudentBean studentBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update student set number = ?, name = ?, sex = ?, major =?, academy = ?, grade = ?, clazz = ? where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, studentBean.getNumber());
                statement.setString(2, studentBean.getName());
                statement.setString(3, studentBean.getSex());
                statement.setString(4, studentBean.getMajor());
                statement.setString(5, studentBean.getAcademy());
                statement.setInt(6, studentBean.getGrade());
                statement.setInt(7, studentBean.getClazz());
                statement.setInt(8, studentBean.getId());

                if (statement.executeUpdate() == 1) {
                    baseCallBack.onSucceed(studentBean);
                } else {
                    baseCallBack.onFailed("数据更新失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 删除学生信息
     * @param studentBean 学生信息
     * @param baseCallBack 删除回调
     */
    public void delete(StudentBean studentBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete from student where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, studentBean.getId());
                if (statement.executeUpdate() == 1) {
                    baseCallBack.onSucceed(studentBean);
                } else {
                    baseCallBack.onFailed("数据删除失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 插入学生信息
     * @param studentBean 学生信息
     * @param baseCallBack 插入回调
     */
    public void insert(StudentBean studentBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "insert into student(number, name, sex, major, academy, grade, clazz) values(?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, studentBean.getNumber());
                statement.setString(2, studentBean.getName());
                statement.setString(3, studentBean.getSex());
                statement.setString(4, studentBean.getMajor());
                statement.setString(5, studentBean.getAcademy());
                statement.setInt(6, studentBean.getGrade());
                statement.setInt(7, studentBean.getClazz());

                if (statement.executeUpdate() == 1) {
                    baseCallBack.onSucceed(studentBean);
                } else {
                    baseCallBack.onFailed("添加数据失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }
}
