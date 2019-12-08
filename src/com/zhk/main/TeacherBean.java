package com.zhk.main;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:15
 * @description 老师数据实体类
 */
public class TeacherBean {

    // 教师信息唯一标识
    private int id;

    // 教师姓名
    private String name;

    // 教师工号
    private String number;

    // 教师性别
    private String sex;

    // 教师职称
    private String jobTitle;

    // 教师所属学院
    private String ofAcademy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setOfAcademy(String ofAcademy) {
        this.ofAcademy = ofAcademy;
    }

    public String getOfAcademy() {
        return ofAcademy;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
