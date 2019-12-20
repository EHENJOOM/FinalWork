package com.zhk.panel.admin.teacher;

import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/19 11:37
 * @description 管理老师信息逻辑处理器
 */
public class ManageTeacherPresenter extends BasePresenter<ManageTeacherView> {

    private ManageTeacherModel model = new ManageTeacherModel();

    public void add(TeacherBean teacherBean) {
        model.add(teacherBean, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage("添加成功！");
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

    public void delete(TeacherBean teacherBean) {
        model.delete(teacherBean, new BaseCallBack<TeacherBean>() {
            @Override
            public void onSucceed(TeacherBean data) {
                if (isViewAttached()) {
                    getView().showMessage("删除成功！");
                    getView().deleteApply(teacherBean);
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

    public void change(TeacherBean teacherBean) {
        TeacherBean temp =(TeacherBean) teacherBean.clone();
        model.change(teacherBean, new BaseCallBack<TeacherBean>() {
            @Override
            public void onSucceed(TeacherBean data) {
                if (isViewAttached()) {
                    getView().showMessage("修改成功！");
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().resetData(temp);
                    getView().showError(msg);
                }
            }
        });
    }

    public void select() {
        model.select(new BaseCallBack<List<TeacherBean>>() {
            @Override
            public void onSucceed(List<TeacherBean> data) {
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
