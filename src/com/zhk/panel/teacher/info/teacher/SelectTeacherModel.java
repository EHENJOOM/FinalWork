package com.zhk.panel.teacher.info.teacher;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.main.TeacherBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 赵洪苛
 * @date 2019/12/8 21:17
 * @description 查找老师信息数据处理器
 */
public class SelectTeacherModel {

    /**
     * 从数据库中获取对应的老师信息
     * @param loginBean 账户信息
     * @param baseCallBack 查询回调
     */
    public void select(LoginBean loginBean, BaseCallBack<TeacherBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from teacher where number = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                TeacherBean teacherBean = new TeacherBean();
                while (resultSet.next()) {
                    teacherBean.setNumber(loginBean.getAccount());
                    teacherBean.setId(resultSet.getInt("id"));
                    teacherBean.setName(resultSet.getString("name"));
                    teacherBean.setSex(resultSet.getString("sex"));
                    teacherBean.setOfAcademy(resultSet.getString("academy"));
                    teacherBean.setJobTitle(resultSet.getString("job"));
                }

                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(teacherBean);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
