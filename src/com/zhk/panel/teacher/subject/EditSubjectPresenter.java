package com.zhk.panel.teacher.subject;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;
import com.zhk.panel.student.subject.SubjectBean;

import java.io.File;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:10
 * @description 编辑课题逻辑处理器
 */
public class EditSubjectPresenter extends BasePresenter<EditSubjectView> {

    private EditSubjectModel model = new EditSubjectModel();

    /**
     * @see EditSubjectModel#select(LoginBean, BaseCallBack)
     * @param loginBean 账户信息
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
     * @see EditSubjectModel#update(SubjectBean, BaseCallBack)
     * @param subjectBean 需要更新的数据
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
     * @see EditSubjectModel#delete(SubjectBean, BaseCallBack)
     * @param row 删除的行
     * @param subjectBean 需要删除的数据
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

    /**
     * @see EditSubjectModel#insert(SubjectBean, BaseCallBack)
     * @param subjectBean 需要加入的数据
     */
    public void insert(SubjectBean subjectBean) {
        model.insert(subjectBean, new BaseCallBack<String>() {
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

    public void exportSubject(List<SubjectBean> subjectBeans, File file) {
        model.exportSubject(subjectBeans, file, new BaseCallBack<String>() {
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

    public void importSubject(File file) {
        model.importSubject(file, new BaseCallBack<List<SubjectBean>>() {
            @Override
            public void onSucceed(List<SubjectBean> data) {
                if (isViewAttached()) {
                    getView().update(data);
                    getView().showMessage("导入成功！");
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
