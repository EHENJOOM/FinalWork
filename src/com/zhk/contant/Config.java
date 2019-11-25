package com.zhk.contant;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:33
 * @description 常量配置类
 */
public class Config {

    /**
     * 该课题处于未选状态
     */
    public static final int UNSELECTED_SUBJECT = 0;

    /**
     * 该课题已接收（已选上）
     */
    public static final int ACCEPTED_SUBJECT = 1;

    /**
     * 该课题正在等待指导老师确认
     */
    public static final int CONFIRMING_SUBJECT = 2;

    /**
     * 该条信息尚未修改
     */
    public static final int UNCHANGED_INFO = 3;

    /**
     * 该信息已经被修改
     */
    public static final int CHANGED_INFO = 4;

    /**
     * 该信息正在修改中
     */
    public static final int CHANGING_INFO = 5;
}
