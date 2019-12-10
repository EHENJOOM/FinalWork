package com.zhk.panel.teacher.check;

import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.mvp.BasePresenter;
import com.zhk.panel.student.subject.MatchBean;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:48
 * @description 确认学生课题逻辑处理器
 */
public class CheckSubjectPresenter extends BasePresenter<CheckSubjectView> {

    private CheckSubjectModel model = new CheckSubjectModel();

    /**
     * 查询选了该导师课题的所有数据
     * @param loginBean 账户信息
     */
    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<MatchBean>>() {
            @Override
            public void onSucceed(List<MatchBean> data) {
                if (isViewAttached()) {
                    getView().update(data);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    /**
     * 更新导师确认的数据
     * @param topic 更新的类型。ACCEPTED_SUBJECT或REFUSE_SUBJECT
     * @param matchBean 数据实体
     */
    public void check(String topic, MatchBean matchBean) {
        // 临时存储数据，若操作失败，则恢复数据
        int accepted = matchBean.getSubjectBean().getAcceptedNum();
        int confirming = matchBean.getSubjectBean().getConfirmingNum();

        model.check(topic, matchBean, new BaseCallBack<MatchBean>() {
            @Override
            public void onSucceed(MatchBean data) {
                if (isViewAttached()) {
                    getView().showMessage("操作成功！", data);
                    System.out.println("Total:"+matchBean.getSubjectBean().getTotalNum()+"\tAccepted:"+matchBean.getSubjectBean().getAcceptedNum()+"\tConfirming:"+matchBean.getSubjectBean().getConfirmingNum());
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                    // 如果操作失败，则恢复操作之前的数据状态
                    matchBean.getSubjectBean().setAcceptedNum(accepted);
                    matchBean.getSubjectBean().setConfirmingNum(confirming);
                    getView().resetState(matchBean);
                }
            }
        });
    }

}
