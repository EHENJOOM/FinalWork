package com.zhk.panel.teacher.check;

import com.zhk.mvp.BaseView;
import com.zhk.panel.student.subject.MatchBean;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:47
 * @description 确认学生课题视图器
 */
public interface CheckSubjectView extends BaseView {

    /**
     * 从数据库获取学生课题，并更新UI
     * @param matchBeans 数据集
     */
    void update(List<MatchBean> matchBeans);

    /**
     * 显示操作之后的提示信息
     * @param msg 提示信息
     * @param data 操作后的数据
     */
    void showMessage(String msg, MatchBean data);

    /**
     * 重置状态。防止数据库更新失败导致数据库数据和临时数据不一致
     * @param matchBean 数据
     */
    void resetState(MatchBean matchBean);

}
