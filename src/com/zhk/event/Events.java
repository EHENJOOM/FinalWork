package com.zhk.event;

/**
 * @author 赵洪苛
 * @date 2019/11/24 13:59
 * @description 事件主题集合
 */
public class Events {
    /**
     * 向数据库中插入学生选择课题
     */
    public static final String INSERT_MATCH = "insert_match";

    /**
     * 删除数据库中该学生已选择的课题
     */
    public static final String CANCEL_MATCH = "cancel_match";

    /**
     * 修改课题信息
     */
    public static final String CHANGE_SUBJECT = "change_subject";

    /**
     * 修改学生信息
     */
    public static final String CHANGE_STUDENT = "change_student";

    /**
     * 接收该学生的课题请求
     */
    public static final String ACCEPT_STUDENT = "accept_student";

    /**
     * 拒绝该学生的课题请求
     */
    public static final String REFUSE_STUDENT = "refuse_student";

    /**
     * 管理员修改登录账号信息
     */
    public static final String ADMIN_CHANGE_LOGIN = "admin_change_login";

    /**
     * 管理员修改教师信息
     */
    public static final String ADMIN_CHANGE_TEACHER = "admin_change_teacher";

    /**
     * 管理员修改学生信息
     */
    public static final String ADMIN_CHANGE_STUDENT = "admin_change_student";
}
