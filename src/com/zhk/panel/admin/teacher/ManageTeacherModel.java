package com.zhk.panel.admin.teacher;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
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
 * @date 2019/12/19 11:37
 * @description 管理老师信息数据处理器
 */
public class ManageTeacherModel {

    public void add(TeacherBean teacherBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "insert into teacher(number, name, sex, job, academy) values(?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, teacherBean.getNumber());
                statement.setString(2, teacherBean.getName());
                statement.setString(3, teacherBean.getSex());
                statement.setString(4, teacherBean.getJobTitle());
                statement.setString(5, teacherBean.getOfAcademy());

                if (1 == statement.executeUpdate()) {
                    getID(teacherBean, connection, baseCallBack);
                } else {
                    baseCallBack.onFailed("添加失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    private void getID(TeacherBean teacherBean,Connection connection, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            String sql = "select id from teacher where number = ? and name = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, teacherBean.getNumber());
                statement.setString(2, teacherBean.getName());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    teacherBean.setId(resultSet.getInt("id"));
                }
                resultSet.close();
                statement.close();
                baseCallBack.onSucceed("添加成功！");
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    public void delete(TeacherBean teacherBean, BaseCallBack<TeacherBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete from teacher where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, teacherBean.getId());

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed(teacherBean);
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

    public void change(TeacherBean teacherBean, BaseCallBack<TeacherBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update teacher set number = ?, name = ?, sex = ?, job = ?, academy = ? where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, teacherBean.getNumber());
                statement.setString(2, teacherBean.getName());
                statement.setString(3, teacherBean.getSex());
                statement.setString(4, teacherBean.getJobTitle());
                statement.setString(5, teacherBean.getOfAcademy());
                statement.setInt(6, teacherBean.getId());

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed(teacherBean);
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

    public void select(BaseCallBack<List<TeacherBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from teacher";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                List<TeacherBean> teacherBeans = new LinkedList<>();
                while (resultSet.next()) {
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setId(resultSet.getInt("id"));
                    teacherBean.setNumber(resultSet.getString("number"));
                    teacherBean.setName(resultSet.getString("name"));
                    teacherBean.setSex(resultSet.getString("sex"));
                    teacherBean.setJobTitle(resultSet.getString("job"));
                    teacherBean.setOfAcademy(resultSet.getString("academy"));
                    teacherBean.setState(Config.UNCHANGED_INFO);
                    teacherBeans.add(teacherBean);
                }

                resultSet.close();
                statement.close();
                baseCallBack.onSucceed(teacherBeans);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
