package com.zhk.login;

import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

/**
 * @author 赵洪苛
 * @date 2019/11/21 18:08
 * @description 登录事务处理器
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel model = new LoginModel();

    public void login(LoginBean loginBean) {
        model.getData(loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (loginBean.equals(data)) {
                    if (isViewAttached()) {
                        getView().login(data);
                    }
                } else {
                    if (isViewAttached()) {
                        getView().showError("用户名或密码错误！");
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                getView().showError(msg);
            }
        });
    }

}
