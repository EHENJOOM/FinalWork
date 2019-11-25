package com.zhk.panel.teacher.subject;

import com.zhk.mvp.BaseView;
import com.zhk.panel.student.subject.SubjectBean;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:09
 * @description 编辑课题视图器
 */
public interface EditSubjectView extends BaseView {

    /**
     * 回调删除成功后才将数据从table中移除，然后更新UI
     * @param row 删除数据所在行
     */
    void deleteApply(int row);

    /**
     * 从数据库获取数据后更新UI
     * @param subjectBeans 获取到的数据集
     */
    void update(List<SubjectBean> subjectBeans);

    /**
     * 显示提示信息
     * @param msg 提示内容
     */
    void showMessage(String msg);
}
