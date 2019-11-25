package com.zhk.login;

/**
 * @author 赵洪苛
 * @date 2019/11/21 17:35
 * @description 登录账户数据实体类
 */
public class LoginBean {

    // 用户账户名
    private String account;

    // 用户密码
    private String password;

    // 用户类型
    private int type;

    public static final int STUDENT = 0;

    public static final int TEACHER = 1;

    public LoginBean() {}

    public LoginBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginBean(String account, String password, int type) {
        this.account = account;
        this.password = password;
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object obj) {
        LoginBean loginBean = (LoginBean) obj;
        if (this == obj) {
            return true;
        }

        return this.account.equals(loginBean.account) && this.password.equals(loginBean.password);
    }
}
