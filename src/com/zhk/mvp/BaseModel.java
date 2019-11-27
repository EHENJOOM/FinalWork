package com.zhk.mvp;

import com.zhk.login.LoginBean;

/**
 * @author 赵洪苛
 * @date 2019/11/26
 * @description MVP设计模式，数据库连接处理器需实现此类
 */
public interface BaseModel {

    <T> void select(LoginBean loginBean, BaseCallBack<T> baseCallBack);

    <T, U> void update(T data, BaseCallBack<U> baseCallBack);

    <T, U> void delete(T data, BaseCallBack<U> baseCallBack);

    <T, U> void insert(T data, BaseCallBack<U> baseCallBack);
}
