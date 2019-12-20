package com.zhk.login;

/**
 * @author 赵洪苛
 * @date 2019/11/21 17:35
 * @description 登录账户数据实体类
 */
public class LoginBean implements Cloneable {

    // 用户唯一标识
    private int id;

    // 用户账户名
    private String account;

    // 用户密码
    private String password;

    // 用户类型
    private int type;

    // 当前数据状态
    private int state;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public Object clone() {
        LoginBean loginBean = new LoginBean(this.account, this.password, this.type);
        loginBean.setId(this.id);
        return loginBean;
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
