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
     * 超级管理员身份登录
     */
    public static final int ADMIN_LOGIN = 2;

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
     * 接收
     */
    public static final String ACCEPT_STRING = "接收";

    /**
     * 拒绝
     */
    public static final String REFUSE_STRING = "拒绝";

    /**
     * 关于作者
     */
    public static final String ABOUT_ME_STRING = "\t关于作者：北京化工大学 信管1701 赵洪苛\t";

    /**
     * 学院配置，其中null代表全部学院，这是用于筛选信息
     */
    public static final String[] ACADEMY_STRINGS = new String[]{null, "化学工程学院", "材料科学与工程学院", "机电工程学院", "信息科技与技术学院",
            "经济管理学院", "化学学院", "数理学院", "文法学院", "生命科学与技术学院"};

    /**
     * 教师职称
     */
    public static final String[] JOB_TITLE_STRINGS = new String[]{"教授", "副教授", "见习副教授", "教授助理", "讲师"};

    /**
     * 发件人的邮箱地址和密码
     */
    public static final String SEND_EMAIL_ACCOUNT = "884101977@qq.com";

    /**
     * 授权码
     */
    public static final String SEND_EMAIL_PASSWORD = "onmdbvtpkltrbdja";

    /**
     * 发件人邮箱的 SMTP 服务器地址
     */
    public static final String SEND_EMAIL_SMTP_HOST = "smtp.qq.com";

    /**
     * BUCT收件人邮箱后缀，默认邮件仅支持北化邮箱注册。可根据需要定制第三方邮件发送器
     */
    public static final String BUCT_MAIL_SUFFIX = "@mail.buct.edu.cn";

    /**
     * QQ邮箱后缀
     */
    public static final String QQ_MAIL_SUFFIX = "@qq.com";

    /**
     * 网易邮箱后缀
     */
    public static final String WANGYI_AIL_SUFFIX = "@163.com";
}
