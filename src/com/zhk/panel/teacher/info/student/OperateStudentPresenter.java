package com.zhk.panel.teacher.info.student;

import com.zhk.main.student.StudentBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/27 16:14
 * @description 操作学生信息逻辑处理器
 */
public class OperateStudentPresenter extends BasePresenter<OperateStudentView> {

    private OperateStudentModel model = new OperateStudentModel();

    /**
     * @see OperateStudentModel#select(BaseCallBack)
     */
    void select() {
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

    /**
     * @see OperateStudentModel#update(StudentBean, BaseCallBack)
     * @param studentBean 学生数据
     */
    void update(StudentBean studentBean) {
        model.update(studentBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {

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
     * @see OperateStudentModel#insert(StudentBean, BaseCallBack)
     * @param studentBean 学生数据
     */
    void insert(StudentBean studentBean) {
        model.insert(studentBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {

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
     * @see OperateStudentModel#delete(StudentBean, BaseCallBack)
     * @param studentBean 学生数据
     */
    void delete(StudentBean studentBean) {
        model.delete(studentBean, new BaseCallBack<StudentBean>() {
            @Override
            public void onSucceed(StudentBean data) {
                if (isViewAttached()) {

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
