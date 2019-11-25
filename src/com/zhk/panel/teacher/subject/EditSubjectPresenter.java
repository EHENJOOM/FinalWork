package com.zhk.panel.teacher.subject;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;
import com.zhk.panel.student.subject.SubjectBean;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:10
 * @description 编辑课题逻辑处理器
 */
public class EditSubjectPresenter extends BasePresenter<EditSubjectView> {

    private EditSubjectModel model = new EditSubjectModel();

    /**
     *
     * @param loginBean
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
     *
     * @param subjectBean
     */
    public void update(SubjectBean subjectBean) {
        model.update(subjectBean, new BaseCallBack<String>() {
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

    /**
     *
     * @param subjectBean
     */
    public void delete(int row, SubjectBean subjectBean) {
        model.delete(subjectBean, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage(data);
                    getView().deleteApply(row);
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
