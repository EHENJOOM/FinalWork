package com.zhk.register;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.mail.EmailBean;
import com.zhk.mail.EmailSenderEnum;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 赵洪苛
 * @date 2019/11/26
 * @description 注册或忘记密码数据处理器
 */
public class RegisterModel {

    private Map<String, String> verifyMap = new HashMap<>();

    /**
     * 向指定账号发送验证码
     * @param type 验证码类型
     * @param account 邮箱账号
     * @param baseCallBack 发送邮件回调
     */
    public void sendVerifyCode(int type, String account, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            String verifyCode = UUID.randomUUID().toString();
            verifyCode = verifyCode.substring(2, 8);
            verifyMap.put(account, verifyCode);
            EmailBean emailBean = new EmailBean();
            if (type == Config.REGISTER_DIALOG) {
                emailBean.setSubject("注册账号验证码");
                emailBean.setContent("用户" + account + "：\n\t您好！\n\t您正在使用“毕业课题管理系统”，您正在注册账号，现在是验证您的身份是否合法。\n\t您的验证码是 "
                        + verifyCode + " ，请不要将此验证码告知他人。\n\t此邮件为系统发送，请不要回复！\n谢谢！");
                emailBean.setReceiveAccount(account);
                emailBean.setDate(new Date());
            } else if (type == Config.FORGET_PASSWORD_DIALOG) {
                emailBean.setSubject("忘记密码验证码");
                emailBean.setContent("用户" + account + "\n\t您好！\n\t您正在使用“毕业课题管理系统”，您正在更改密码，现在是验证您的身份是否合法。\n\t您的验证码是 "
                        + verifyCode + " ，请不要将此验证码告知他人。\n\t此邮件为系统发送，请不要回复！\n谢谢！");
                emailBean.setReceiveAccount(account);
                emailBean.setDate(new Date());
            }

            try {
                EmailSenderEnum.getInstance().init().setEmail(emailBean).sendMessage();
                baseCallBack.onSucceed("验证码已发送，请前往您的邮箱查看！");
            } catch (Exception e) {
                baseCallBack.onFailed("验证码发送失败，请稍后重试！");
            }
        });
    }

    /**
     * 验证验证码是否合法
     * @param verifyCode 验证码
     * @param baseCallBack 验证回调
     */
    public void verify(String account, String verifyCode, BaseCallBack<String> baseCallBack) {
        if (verifyMap.isEmpty()) {
            baseCallBack.onFailed("验证码已过期，请重新发送验证码！");
            return;
        }

        if (verifyMap.get(account).equals(verifyCode)) {
            baseCallBack.onSucceed("验证码正确");
        } else {
            baseCallBack.onFailed("验证码错误，请重新输入验证码！");
        }
    }

    /**
     * 更新数据库中的登录密码
     * @param loginBean 账户信息
     * @param baseCallBack 更新回调
     */
    public void updatePassword(LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from login where account = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                String account = null;
                while (resultSet.next()) {
                    account = resultSet.getString("account");
                }
                if (account == null || account.isEmpty()) {
                    baseCallBack.onFailed("该账号尚未注册，请先前往注册！");
                    return;
                }

                String update = "update login set password = ? where account = ?";
                PreparedStatement ps = connection.prepareStatement(update);
                ps.setString(1, loginBean.getPassword());
                ps.setString(2, loginBean.getAccount());
                if (ps.executeUpdate() == 1) {
                    baseCallBack.onSucceed(loginBean);
                } else {
                    baseCallBack.onFailed("密码修改失败，请重试！");
                }

                statement.close();
                ps.close();
                resultSet.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 向数据库中写入新账户信息
     * @param loginBean
     * @param baseCallBack
     */
    public void register(LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from login where account = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                String account = null;
                while (resultSet.next()) {
                    account = resultSet.getString("account");
                }

                if (account == null || account.isEmpty()) {
                    String register = "insert into login(account, password, type) values(?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(register);
                    ps.setString(1, loginBean.getAccount());
                    ps.setString(2, loginBean.getPassword());
                    ps.setInt(3, loginBean.getType());
                    if (ps.executeUpdate() == 1) {
                        baseCallBack.onSucceed(loginBean);
                    } else {
                        baseCallBack.onFailed("注册账号失败，请重试！");
                    }
                } else {
                    baseCallBack.onFailed("该账号已注册！");
                }
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
