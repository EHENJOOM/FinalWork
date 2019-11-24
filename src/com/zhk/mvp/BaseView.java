package com.zhk.mvp;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2019/11/21 11:07
 * @description MVP设计模式，基础视图，窗口需实现此接口
 */
public interface BaseView {

    /**
     * 显示错误窗口
     * @param msg 错误信息
     */
    default void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "警告", JOptionPane.ERROR_MESSAGE);
    }

}
