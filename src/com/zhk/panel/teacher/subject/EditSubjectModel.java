package com.zhk.panel.teacher.subject;

import com.zhk.constant.Config;
import com.zhk.db.ConnectionPoolEnum;
import com.zhk.login.LoginBean;
import com.zhk.mvp.BaseCallBack;
import com.zhk.panel.student.subject.SubjectBean;
import com.zhk.thread.ThreadPoolEnum;
import com.zhk.util.SerializeUtil;

import java.io.File;
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

    /**
     * 加入一条数据
     * @param subjectBean 要加入的数据
     * @param baseCallBack 加入回调
     */
    public void insert(SubjectBean subjectBean, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Connection connection = ConnectionPoolEnum.getInstance().getConnection();
            String sql = "insert into subject(code, name, academy, teacher, total, accepted, confirming) values(?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, subjectBean.getCode());
                statement.setString(2, subjectBean.getName());
                statement.setString(3, subjectBean.getOfAcademy());
                statement.setString(4, subjectBean.getTeacherBean().getNumber());
                statement.setInt(5, subjectBean.getTotalNum());
                statement.setInt(6, subjectBean.getAcceptedNum());
                statement.setInt(7, subjectBean.getConfirmingNum());

                if (statement.executeUpdate() == 1) {
                    getId(subjectBean, connection, baseCallBack);
                } else {
                    baseCallBack.onFailed("操作失败！");
                }
                statement.close();
            } catch (SQLException e) {
                baseCallBack.onFailed("数据库连接失败！");
            }
        });
    }

    /**
     * 向数据库中插入数据成功后，获取该课题对应的id
     * @param subjectBean 课题数据实体
     * @param connection 数据库连接
     * @param baseCallBack 回调
     */
    private void getId(SubjectBean subjectBean, Connection connection, BaseCallBack<String> baseCallBack) {
        String sql = "select id from subject where code = ? and name = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subjectBean.getCode());
            statement.setString(2, subjectBean.getName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subjectBean.setId(resultSet.getInt("id"));
            }

            statement.close();
            resultSet.close();
            baseCallBack.onSucceed("操作成功！");
        } catch (SQLException e) {
            baseCallBack.onFailed("数据库连接失败！");
        } finally {
            ConnectionPoolEnum.getInstance().putBack(connection);
        }
    }

    /**
     * 序列化对象到指定位置
     * @param subjectBeans 要序列化的数据
     * @param file 文件保存位置
     * @param baseCallBack 序列化回调
     */
    public void exportSubject(List<SubjectBean> subjectBeans, File file, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            try {
                SerializeUtil.write(subjectBeans, file);
                baseCallBack.onSucceed("导出成功！");
            } catch (Exception e) {
                baseCallBack.onFailed("导出失败！");
            }
        });
    }

    /**
     *
     * @param file 文件读取位置
     * @param baseCallBack 反序列化回调
     */
    public void importSubject(File file, BaseCallBack<List<SubjectBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            try {
                List list = SerializeUtil.read(file);
                List<SubjectBean> subjectBeans = new LinkedList<>();
                list.forEach(object -> subjectBeans.add((SubjectBean) object));
                baseCallBack.onSucceed(subjectBeans);
            } catch (Exception e) {
                baseCallBack.onFailed("导入失败！");
            }
        });
    }

}
