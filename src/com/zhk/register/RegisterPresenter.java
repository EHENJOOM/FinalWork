package com.zhk.register;

import com.zhk.constant.Config;
import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2019/11/26 20:18
 * @description 注册或忘记密码逻辑处理器
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    private RegisterModel model = new RegisterModel();

    public void sendVerifyCode(int type, String account) {
        model.sendVerifyCode(type, account, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage("验证码已发送至您的BUCT邮箱，请注意查收！");
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    public void verify(int type, String verifyCode, LoginBean loginBean) {
        model.verify(loginBean.getAccount(), verifyCode, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    if (type == Config.REGISTER_DIALOG) {
                        getView().toRegister(loginBean);
                    } else if (type == Config.FORGET_PASSWORD_DIALOG) {
                        getView().toUpdatePassword(loginBean);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    public void updatePassword(LoginBean loginBean) {
        model.updatePassword(loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (isViewAttached()) {
                    if (getView().showConfirm("密码修改成功。\n快去登录吧！") == JOptionPane.YES_OPTION) {
                        getView().toLogin(data);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    public void register(LoginBean loginBean) {
        model.register(loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (isViewAttached()) {
                    if (getView().showConfirm("账号注册成功，\n快去登录吧！") == JOptionPane.YES_OPTION) {
                        getView().toLogin(loginBean);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

}
