package com.zhk.panel.teacher.info.teacher;

import com.zhk.login.LoginBean;
import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

/**
 * @author 赵洪苛
 * @date 2019/12/8 21:16
 * @description 查找老师信息逻辑处理器
 */
public class SelectTeacherPresenter extends BasePresenter<SelectTeacherView> {

    SelectTeacherModel model = new SelectTeacherModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<TeacherBean>() {
            @Override
            public void onSucceed(TeacherBean data) {
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
