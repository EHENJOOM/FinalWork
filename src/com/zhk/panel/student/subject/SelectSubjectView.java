package com.zhk.panel.student.subject;

import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/23 17:57
 * @description 选课题视图器
 */
public interface SelectSubjectView extends BaseView {

    /**
     * 将从数据库中读取到课题数据更新到窗口中
     * @param subjectBeans 课题数据
     */
    void update(List<SubjectBean> subjectBeans);

    /**
     * 显示提示窗口
     * @param msg 提示信息
     * @param matchBean 数据
     */
    void showMessage(String msg, MatchBean matchBean);

}
