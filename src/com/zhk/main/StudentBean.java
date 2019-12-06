package com.zhk.main;

/**
 * @author 赵洪苛
 * @date 2019/11/21 22:47
 * @description 学生信息实体类
 */
public class StudentBean {

    // 学生唯一标识
    private int id;

    // 学号
    private String number;

    // 姓名
    private String name;

    // 性别
    private String sex;

    // 专业
    private String major;

    // 学院
    private String academy;

    // 用于信息修改时判断状态，不存入数据库
    private int state;

    // 年级
    private int grade;

    // 班级
    private int clazz;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getAcademy() {
        return academy;
    }

    public int getClazz() {
        return clazz;
    }

    public int getGrade() {
        return grade;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
