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

}
