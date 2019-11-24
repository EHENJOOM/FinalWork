package com.zhk.panel.student.subject;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:02
 * @description 选课题逻辑处理器
 */
public class SelectSubjectPresenter extends BasePresenter<SelectSubjectView> {

    private SelectSubjectModel model = new SelectSubjectModel();

    /**
     * @see SelectSubjectModel#select(LoginBean, BaseCallBack)
     */
    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<SubjectBean>>() {
            @Override
            public void onSucceed(List<SubjectBean> data) {
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

    /**
     * @see SelectSubjectModel#insertSelectedSubject(MatchBean, BaseCallBack)
     * @param matchBean 数据集
     */
    public void insert(MatchBean matchBean) {
        model.insertSelectedSubject(matchBean, new BaseCallBack<MatchBean>() {
            @Override
            public void onSucceed(MatchBean data) {
                if (isViewAttached()) {
                    getView().showMessage("选课题成功，正在等待指导老师确认", data);
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

    /**
     * @see SelectSubjectModel#cancelSelectedSubject(MatchBean, BaseCallBack)
     * @param matchBean 数据集
     */
    public void cancel(MatchBean matchBean) {
        model.cancelSelectedSubject(matchBean, new BaseCallBack<MatchBean>() {
            @Override
            public void onSucceed(MatchBean data) {
                if (isViewAttached()) {
                    getView().showMessage("退选课题成功！", data);
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
