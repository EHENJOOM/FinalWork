package com.zhk.panel.student.exam;

import com.zhk.main.student.StudentBean;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:41
 * @description 考试信息数据实体
 */
public class ExamBean {

    // 考试信息唯一标识
    private int id;

    // 学生信息
    private StudentBean studentBean;

    // 课程代码
    private String code;

    // 课程名称
    private String name;

    // 考试时间
    private String time;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
