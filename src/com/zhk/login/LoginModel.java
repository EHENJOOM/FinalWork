package com.zhk.login;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 赵洪苛
 * @date 2019/11/21 18:09
 * @description 登录的数据处理器
 */
public class LoginModel {

    /**
     * 采用线程池、数据库连接池技术从数据库中读出账户数据
     * @param loginBean 界面获取的账户数据
     * @param baseCallBack 数据读取的回调
     */
    public void getData(LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            // 从数据库连接池获取连接对象
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from login where account = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                LoginBean bean = new LoginBean();
                while (resultSet.next()) {
                    bean.setAccount(resultSet.getString("account"));
                    bean.setPassword(resultSet.getString("password"));
                    bean.setType(resultSet.getInt("type"));
                }

                ConnectionPoolEnum.getInstance().putBack(connection);
                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(bean);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接错误！");
            }
        });
    }

}
