package com.zhk.panel.admin.student;

import com.zhk.main.StudentBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/21 18:09
 * @description 管理学生信息逻辑处理器
 */
public class ManageStudentPresenter extends BasePresenter<ManageStudentView> {

    private ManageStudentModel model = new ManageStudentModel();

    public void add(StudentBean studentBean) {
        model.add(studentBean, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage("增加成功！");
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

    public void delete(StudentBean studentBean) {
        model.delete(studentBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {
                    getView().showMessage("删除成功！");
                    getView().deleteApply(studentBean);
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

    public void change(StudentBean studentBean) {
        model.change(studentBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {
                    getView().showMessage("修改成功！");
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

    public void select() {
        model.select(new BaseCallBack<List<StudentBean>>() {
            @Override
            public void onSucceed(List<StudentBean> data) {
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
