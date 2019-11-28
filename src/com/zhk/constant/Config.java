package com.zhk.constant;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:33
 * @description 常量配置类
 */
public class Config {

    /**
     * 学生身份登录
     */
    public static final int STUDENT_LOGIN = 0;

    /**
     * 老师身份登录
     */
    public static final int TEACHER_LOGIN = 1;

    /**
     * 该课题处于未选状态
     */
    public static final int UNSELECTED_SUBJECT = 2;

    /**
     * 该课题已接收（已选上）
     */
    public static final int ACCEPTED_SUBJECT = 3;

    /**
     * 该课题正在等待指导老师确认
     */
    public static final int CONFIRMING_SUBJECT = 4;

    /**
     * 该条信息尚未修改
     */
    public static final int UNCHANGED_INFO = 5;

    /**
     * 该信息已经被修改
     */
    public static final int CHANGED_INFO = 6;

    /**
     * 该信息正在修改中
     */
    public static final int CHANGING_INFO = 7;

    /**
     * 注册窗口
     */
    public static final int REGISTER_DIALOG = 9;

    /**
     * 忘记密码窗口
     */
    public static final int FORGET_PASSWORD_DIALOG = 10;

    /**
     * 发件人的邮箱地址和密码
     */
    public static String SEND_EMAIL_ACCOUNT = "884101977@qq.com";

    /**
     * 授权码
     */
    public static String SEND_EMAIL_PASSWORD = "onmdbvtpkltrbdja";

    /**
     * 发件人邮箱的 SMTP 服务器地址
     */
    public static String SEND_EMAIL_SMTP_HOST = "smtp.qq.com";

    /**
     * BUCT收件人邮箱后缀，仅支持BUCTers注册
     */
    public static String BUCT_MAIL_SUFFIX = "@mail.buct.edu.cn";

    /**
     * QQ邮箱后缀
     */
    public static String QQ_MAIL_SUFFIX = "@qq.com";

    /**
     * 网易邮箱后缀
     */
    public static String WANGYI_AIL_SUFFIX = "@163.com";
}
