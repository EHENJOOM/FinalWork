package com.zhk.panel.admin.teacher;

import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/19 11:36
 * @description 管理老师信息视图器
 */
public interface ManageTeacherView extends BaseView {

    /**
     * 显示提示信息
     * @param msg 提示信息
     */
    void showMessage(String msg);

    /**
     * 更新UI
     * @param data 数据
     */
    void update(List<TeacherBean> data);

    /**
     * 删除成功，在临时数据中也删除该数据
     * @param teacherBean 要删除的数据
     */
    void deleteApply(TeacherBean teacherBean);

    /**
     * 恢复用户数据。防止数据库数据修改失败，造成临时数据与数据库数据不匹配。
     * @param teacherBean 恢复的数据
     */
    void resetData(TeacherBean teacherBean);

}
