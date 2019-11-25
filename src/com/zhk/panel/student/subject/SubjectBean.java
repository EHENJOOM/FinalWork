package com.zhk.panel.student.subject;

import com.zhk.main.teacher.TeacherBean;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:00
 * @description 课题数据实例类
 */
public class SubjectBean {

    // 课题主管学院
    private String ofAcademy;

    // 题目名称
    private String name;

    // 课题代码
    private String code;

    private int id;

    // 当前学生对该课题的状态
    private int state;

    // 课题可接受总人数
    private int totalNum;

    // 课题已接收人数
    private int acceptedNum;

    // 课题待确认人数
    private int confirmingNum;

    // 指导教师信息
    private TeacherBean teacherBean;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAcceptedNum() {
        return acceptedNum;
    }

    public int getConfirmingNum() {
        return confirmingNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public String getOfAcademy() {
        return ofAcademy;
    }

    public TeacherBean getTeacherBean() {
        return teacherBean;
    }

    public void setAcceptedNum(int acceptedNum) {
        this.acceptedNum = acceptedNum;
    }

    public void setConfirmingNum(int confirmingNum) {
        this.confirmingNum = confirmingNum;
    }

    public void setOfAcademy(String ofAcademy) {
        this.ofAcademy = ofAcademy;
    }

    public void setTeacherBean(TeacherBean teacherBean) {
        this.teacherBean = teacherBean;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
