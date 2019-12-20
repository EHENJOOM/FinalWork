package com.zhk.panel.admin.login;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/18 18:26
 * @description 管理登录账号信息视图器
 */
public interface ManageLoginView extends BaseView {

    /**
     * 显示提示信息
     * @param msg 提示信息
     */
    void showMessage(String msg);

    /**
     * 更新UI
     * @param data 数据
     */
    void update(List<LoginBean> data);

    /**
     * 删除成功，在临时数据中也删除该数据
     * @param loginBean 要删除的数据
     */
    void deleteApply(LoginBean loginBean);

    /**
     * 恢复用户数据。防止数据库数据修改失败，造成临时数据与数据库数据不匹配。
     * @param loginBean 恢复的数据
     */
    void resetData(LoginBean loginBean);

}
