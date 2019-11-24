package com.zhk.panel.student.score;

import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/24 16:07
 * @description 查询学生分数视图器
 */
public interface SelectScoreView extends BaseView {

    void update(List<ScoreBean> list);

}
