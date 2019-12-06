package com.zhk.panel.student.subject;

import com.zhk.main.StudentBean;
import com.zhk.main.TeacherBean;

/**
 * @author 赵洪苛
 * @date 2019/11/24 13:45
 * @description 学生与课题的匹配实例
 */
public class MatchBean {

    private StudentBean studentBean;

    private SubjectBean subjectBean;

    private TeacherBean teacherBean;

    // 学生选课题的状态
    private int state;

    // 事件唯一Id
    private int id;

    public MatchBean() {}

    public MatchBean(int id, StudentBean studentBean, SubjectBean subjectBean, int state) {
        this.id = id;
        this.state = state;
        this.studentBean = studentBean;
        this.subjectBean = subjectBean;
    }

    public TeacherBean getTeacherBean() {
        return teacherBean;
    }

    public void setTeacherBean(TeacherBean teacherBean) {
        this.teacherBean = teacherBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public SubjectBean getSubjectBean() {
        return subjectBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public void setSubjectBean(SubjectBean subjectBean) {
        this.subjectBean = subjectBean;
    }
}
