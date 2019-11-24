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

}
