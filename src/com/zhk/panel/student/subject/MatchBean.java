package com.zhk.panel.student.subject;

import com.zhk.main.student.StudentBean;

/**
 * @author 赵洪苛
 * @date 2019/11/24 13:45
 * @description 学生选择课题的匹配实例
 */
public class MatchBean {

    private StudentBean studentBean;

    private SubjectBean subjectBean;

    // 学生选课题的状态
    private int state;

    // 事件唯一Id
    private int id;

    /**
     * 该课题选择成功
     */
    public static final int ACCEPTED = 1;

    /**
     * 正在等待管理员确认该课题是否选择成功
     */
    public static final int CONFIRMING = 2;

    /**
     * 未选择该课题或取消选择该课题
     */
    public static final int NONE = 0;

    public MatchBean() {}

    public MatchBean(int id, StudentBean studentBean, SubjectBean subjectBean, int state) {
        this.id = id;
        this.state = state;
        this.studentBean = studentBean;
        this.subjectBean = subjectBean;
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
