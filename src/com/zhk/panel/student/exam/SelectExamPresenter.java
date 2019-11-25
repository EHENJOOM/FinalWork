package com.zhk.panel.student.exam;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:40
 * @description 考试信息查询逻辑处理器
 */
public class SelectExamPresenter extends BasePresenter<SelectExamView> {

    private SelectExamModel model = new SelectExamModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<ExamBean>>() {
            @Override
            public void onSucceed(List<ExamBean> data) {
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
