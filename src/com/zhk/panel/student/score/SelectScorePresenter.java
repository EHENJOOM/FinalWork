package com.zhk.panel.student.score;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/24 16:49
 * @description 分数查询逻辑处理器
 */
public class SelectScorePresenter extends BasePresenter<SelectScoreView> {

    private SelectScoreModel model = new SelectScoreModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<ScoreBean>>() {
            @Override
            public void onSucceed(List<ScoreBean> data) {
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
