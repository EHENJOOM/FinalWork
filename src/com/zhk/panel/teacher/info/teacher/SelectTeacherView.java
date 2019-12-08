package com.zhk.panel.teacher.info.teacher;

import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseView;

/**
 * @author 赵洪苛
 * @date 2019/12/8 21:13
 * @description 查找老师信息视图器
 */
public interface SelectTeacherView extends BaseView {

    /**
     * 把老师的信息展示在窗口上
     * @param teacherBean 老师数据实体
     */
    void show(TeacherBean teacherBean);

}
