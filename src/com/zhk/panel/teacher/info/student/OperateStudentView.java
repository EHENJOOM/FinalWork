package com.zhk.panel.teacher.info.student;

import com.zhk.main.StudentBean;
import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/27 16:12
 * @description 操作学生信息视图器
 */
public interface OperateStudentView extends BaseView {

    /**
     * 向jtable中写入数据，并更新UI
     * @param studentBeans 学生数据集
     */
    void update(List<StudentBean> studentBeans);

    /**
     * 显示提示信息
     * @param msg 提示信息
     */
    void showMessage(String msg);

    /**
     * 删除临时数据，确保数据库中数据与临时数据一致
     * @param data 数据
     */
    void deleteApply(StudentBean data);

}
