package com.zhk.panel.student.exam;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:40
 * @description 查询考试信息数据处理器
 */
public class SelectExamModel {

    /**
     * 查找考试信息
     * @param loginBean 账号信息
     * @param baseCallBack 查找回调
     */
    public void select(LoginBean loginBean, BaseCallBack<List<ExamBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from exam where number = ?";

            List<ExamBean> examBeans = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ExamBean examBean = new ExamBean();
                    examBean.setCode(resultSet.getString("code"));
                    examBean.setName(resultSet.getString("name"));
                    examBean.setTime(resultSet.getString("time"));
                    examBeans.add(examBean);
                }

                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(examBeans);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
