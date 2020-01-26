package com.zhk.panel.admin.student;

import com.zhk.main.StudentBean;
import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/21 17:36
 * @description 管理学生信息视图器
 */
public interface ManageStudentView extends BaseView {

    /**
     * 显示提示信息
     * @param msg 提示信息
     */
    void showMessage(String msg);

    /**
     * 更新UI
     * @param data 数据
     */
    void update(List<StudentBean> data);

    /**
     * 删除成功，在临时数据中也删除该数据
     * @param studentBean 要删除的数据
     */
    void deleteApply(StudentBean studentBean);

    /**
     * 恢复用户数据。防止数据库数据修改失败，造成临时数据与数据库数据不匹配。
     * @param studentBean 恢复的数据
     */
    void resetData(StudentBean studentBean);

}
