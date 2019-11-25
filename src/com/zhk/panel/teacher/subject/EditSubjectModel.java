package com.zhk.panel.teacher.subject;

import com.zhk.contant.Config;
import com.zhk.db.ConnectionPool;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.panel.student.subject.SubjectBean;
import com.zhk.thread.ThreadPoolEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:11
 * @description 编辑课题数据处理器
 */
public class EditSubjectModel {

    /**
     * 查询数据库中的课题数据
     * @param loginBean 账号信息
     * @param baseCallBack 查询回调
     */
    public void select(LoginBean loginBean, BaseCallBack<List<SubjectBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "select * from subject where teacher = ?";

            List<SubjectBean> subjectBeans = new LinkedList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, loginBean.getAccount());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    SubjectBean subjectBean = new SubjectBean();
                    subjectBean.setState(Config.UNCHANGED_INFO);
                    subjectBean.setId(resultSet.getInt("id"));
                    subjectBean.setCode(resultSet.getString("code"));
                    subjectBean.setName(resultSet.getString("name"));
                    subjectBean.setOfAcademy(resultSet.getString("academy"));
                    subjectBean.setTotalNum(resultSet.getInt("total"));
                    subjectBean.setAcceptedNum(resultSet.getInt("accepted"));
                    subjectBean.setConfirmingNum(resultSet.getInt("confirming"));
                    subjectBeans.add(subjectBean);
                }

                statement.close();
                resultSet.close();
                baseCallBack.onSucceed(subjectBeans);
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 更新数据库中的信息
     * @param subjectBean 要更新的数据
     * @param baseCallBack 更新回调
     */
    public void update(SubjectBean subjectBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "update subject set code = ?, name = ?, academy = ?, total = ? where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, subjectBean.getCode());
                statement.setString(2, subjectBean.getName());
                statement.setString(3, subjectBean.getOfAcademy());
                statement.setInt(4, subjectBean.getTotalNum());
                statement.setInt(5, subjectBean.getId());

                if (statement.executeUpdate() == 1) {
                    baseCallBack.onSucceed("数据保存成功！");
                } else {
                    baseCallBack.onFailed("数据保存失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

    /**
     * 删除一条数据
     * @param subjectBean 要删除的数据
     * @param baseCallBack 删除回调
     */
    public void delete(SubjectBean subjectBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "delete subject where id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, subjectBean.getId());

                if (statement.executeUpdate() == 1) {
                    baseCallBack.onSucceed("删除成功！");
                } else {
                    baseCallBack.onFailed("删除失败！");
                }
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            } finally {
                ConnectionPoolEnum.getInstance().putBack(connection);
            }
        });
    }

}
