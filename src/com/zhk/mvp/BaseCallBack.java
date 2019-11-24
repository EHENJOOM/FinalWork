package com.zhk.mvp;

/**
 * @author 赵洪苛
 * @date 2019/11/21 11:13
 * @description 默认消息回调，可根据需要继承此接口
 */
public interface BaseCallBack<T> {

    /**
     * 事物处理成功回调
     * @param data 处理成功的返回数据
     */
    void onSucceed(T data);

    /**
     * 事物处理失败回调
     * @param msg 处理失败的原因
     */
    void onFailed(String msg);

}
