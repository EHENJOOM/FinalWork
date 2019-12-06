package com.zhk.panel.student.info;

import com.zhk.login.LoginBean;
import com.zhk.main.StudentBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

/**
 * @author 赵洪苛
 * @date 2019/11/23 16:23
 * @description 学生信息查询面板逻辑处理器
 */
public class SelectInfoPresenter extends BasePresenter<SelectInfoView> {

    private SelectInfoModel model = new SelectInfoModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {
                    getView().show(data);
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
