package com.zhk.panel.admin.login;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
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
 * @date 2019/12/18 18:27
 * @description 管理登录账号信息数据处理器
 */
public class ManageLoginModel {

    public void add(LoginBean loginBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "insert into login(account, password, type) values(?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                statement.setString(2, loginBean.getPassword());
                statement.setInt(3, loginBean.getType());

                if (1 == statement.executeUpdate()) {
                    getID(loginBean, connection, baseCallBack);
                } else {
                    baseCallBack.onFailed("添加失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    private void getID(LoginBean loginBean, Connection connection, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            String sql = "select id from login where account = ? and password = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                statement.setString(2, loginBean.getPassword());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    loginBean.setId(resultSet.getInt("id"));
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

    public void delete(LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete from login where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, loginBean.getId());

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed(loginBean);
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

    public void change(LoginBean loginBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update login set account = ?, password = ?, type = ? where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                statement.setString(2, loginBean.getPassword());
                statement.setInt(3, loginBean.getType());
                statement.setInt(4, loginBean.getId());

                if (1 == statement.executeUpdate()) {
                    baseCallBack.onSucceed("修改成功！");
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

    public void select(BaseCallBack<List<LoginBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from login";

            try{
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                List<LoginBean> list = new LinkedList<>();
                while (resultSet.next()) {
                    LoginBean loginBean = new LoginBean();
                    loginBean.setId(resultSet.getInt("id"));
                    loginBean.setAccount(resultSet.getString("account"));
                    loginBean.setPassword(resultSet.getString("password"));
                    loginBean.setType(resultSet.getInt("type"));
                    loginBean.setState(Config.UNCHANGED_INFO);
                    list.add(loginBean);
                }

                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(list);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
