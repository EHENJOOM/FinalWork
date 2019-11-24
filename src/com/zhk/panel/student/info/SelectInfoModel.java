package com.zhk.panel.student.info;

import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.main.student.StudentBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.*;

/**
 * @author 赵洪苛
 * @date 2019/11/23 16:23
 * @description 学生信息查询数据处理器
 */
public class SelectInfoModel {

    public void select(LoginBean loginBean, BaseCallBack<StudentBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from student where number = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();
                StudentBean studentBean = new StudentBean();
                while (resultSet.next()) {
                    studentBean.setNumber(resultSet.getString("number"));
                    studentBean.setName(resultSet.getString("name"));
                    studentBean.setSex(resultSet.getString("sex"));
                    studentBean.setMajor(resultSet.getString("major"));
                    studentBean.setAcademy(resultSet.getString("academy"));
                    studentBean.setGrade(resultSet.getInt("grade"));
                    studentBean.setClazz(resultSet.getInt("clazz"));
                    studentBean.setId(resultSet.getInt("id"));
                }

                ConnectionPoolEnum.getInstance().putBack(connection);
                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(studentBean);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

}
