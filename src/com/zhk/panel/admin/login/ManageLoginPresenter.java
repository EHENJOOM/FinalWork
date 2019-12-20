package com.zhk.panel.admin.login;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/18 18:27
 * @description 管理登录账号信息逻辑处理器
 */
public class ManageLoginPresenter extends BasePresenter<ManageLoginView> {

    private ManageLoginModel model = new ManageLoginModel();

    public void add(LoginBean loginBean) {
        model.add(loginBean, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage(data);
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

    public void delete(LoginBean loginBean) {
        model.delete(loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (isViewAttached()) {
                    getView().deleteApply(data);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError("删除失败，请重试！");
                }
            }
        });
    }

    public void change(LoginBean loginBean) {
        LoginBean temp = (LoginBean) loginBean.clone();
        model.change(loginBean, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage(data);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError("修改失败！");
                    getView().resetData(temp);
                }
            }
        });
    }

    public void select() {
        model.select(new BaseCallBack<List<LoginBean>>() {
            @Override
            public void onSucceed(List<LoginBean> data) {
                if (isViewAttached()) {
                    getView().update(data);
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
