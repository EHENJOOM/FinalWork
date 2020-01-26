package com.zhk.panel.admin.student;

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
 * @date 2019/12/21 18:10
 * @description 管理学生信息数据处理器
 */
public class ManageStudentModel {

    public void add(StudentBean studentBean, BaseCallBack<String> baseCallBack) {
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

                if (1 == statement.executeUpdate()) {
                    getId(studentBean, connection, baseCallBack);
                } else {
                    baseCallBack.onFailed("添加失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    private void getId(StudentBean studentBean, Connection connection, BaseCallBack<String> baseCallBack) {
        String sql = "select id from student where number = ? and name = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentBean.getNumber());
            statement.setString(2, studentBean.getName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                studentBean.setId(resultSet.getInt("id"));
            }

            resultSet.close();
            statement.close();
            baseCallBack.onSucceed("添加成功！");
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        } finally {
            ConnectionPoolEnum.getInstance().putBack(connection);
        }
    }

    public void delete(StudentBean studentBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete from student where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, studentBean.getId());

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed(studentBean);
                } else {
                    baseCallBack.onFailed("删除失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    public void change(StudentBean studentBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update student set number = ?, name = ?, sex = ?, major = ?, academy = ?, grade = ?, clazz = ? where id = ?";

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

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed(studentBean);
                } else {
                    baseCallBack.onFailed("修改失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    public void select(BaseCallBack<List<StudentBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from student";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                List<StudentBean> studentBeans = new LinkedList<>();
                while (resultSet.next()) {
                    StudentBean studentBean = new StudentBean();
                    studentBean.setId(resultSet.getInt("id"));
                    studentBean.setNumber(resultSet.getString("number"));
                    studentBean.setName(resultSet.getString("name"));
                    studentBean.setSex(resultSet.getString("sex"));
                    studentBean.setMajor(resultSet.getString("major"));
                    studentBean.setAcademy(resultSet.getString("academy"));
                    studentBean.setGrade(resultSet.getInt("grade"));
                    studentBean.setClazz(resultSet.getInt("clazz"));
                    studentBean.setState(Config.UNCHANGED_INFO);
                    studentBeans.add(studentBean);
                }

                resultSet.close();
                statement.close();
                baseCallBack.onSucceed(studentBeans);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
