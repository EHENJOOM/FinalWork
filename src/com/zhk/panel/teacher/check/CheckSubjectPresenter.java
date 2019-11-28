package com.zhk.panel.teacher.check;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;
import com.zhk.panel.student.subject.MatchBean;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:48
 * @description 确认学生课题逻辑处理器
 */
public class CheckSubjectPresenter extends BasePresenter<CheckSubjectView> {

    private CheckSubjectModel model = new CheckSubjectModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<MatchBean>>() {
            @Override
            public void onSucceed(List<MatchBean> data) {
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

    public void check(String topic, MatchBean matchBean) {
        model.check(topic, matchBean, new BaseCallBack<MatchBean>() {
            @Override
            public void onSucceed(MatchBean data) {
                if (isViewAttached()) {
                    getView().showMessage("操作成功！", data);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                    getView().resetState(matchBean);
                }
            }
        });
    }

}
