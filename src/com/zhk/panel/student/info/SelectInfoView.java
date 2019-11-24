package com.zhk.panel.student.info;

import com.zhk.main.student.StudentBean;
import com.zhk.mvp.BaseView;

/**
 * @author 赵洪苛
 * @date 2019/11/23 16:19
 * @description 查询学生信息视图接口
 */
public interface SelectInfoView extends BaseView {

    /**
     * 显示学生信息
     * @param studentBean 学生数据实体
     */
    void showInfo(StudentBean studentBean);

}
