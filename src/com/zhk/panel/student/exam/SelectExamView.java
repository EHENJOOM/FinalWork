package com.zhk.panel.student.exam;

import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:39
 * @description 查询考试信息是凸起
 */
public interface SelectExamView extends BaseView {

    /**
     * 得到数据后，更新UI
     * @param examBeans 数据集
     */
    void update(List<ExamBean> examBeans);

}
