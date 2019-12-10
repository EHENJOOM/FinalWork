package com.zhk.panel.student.score;

import com.zhk.main.StudentBean;
import com.zhk.main.TeacherBean;

/**
 * @author 赵洪苛
 * @date 2019/11/24 16:08
 * @description 学生分数实体
 */
public class ScoreBean {

    /**
     * 唯一标识
     */
    private int id;

    /**
     * 学生信息
     */
    private StudentBean studentBean;

    /**
     * 课程代码
     */
    private String code;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程性质
     */
    private String property;

    /**
     * 该课程分数
     */
    private int score;

    /**
     * 该课程绩点
     */
    private float point;

    /**
     * 课程状态
     */
    private int state;

    private TeacherBean teacherBean;

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setTeacherBean(TeacherBean teacherBean) {
        this.teacherBean = teacherBean;
    }

    public TeacherBean getTeacherBean() {
        return teacherBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public int getScore() {
        return score;
    }

    public String getProperty() {
        return property;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
