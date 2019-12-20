package com.zhk.panel.admin.login;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/18 18:53
 * @description 管理登录信息jtable适配器
 */
public class ManageLoginAdapter extends AbstractTableModel {

    private List<LoginBean> loginBeans;

    private String[] heads = new String[]{"工号/学号", "密码", "账号类型", "修改"};

    public void setLoginBeans(List<LoginBean> loginBeans) {
        this.loginBeans = loginBeans;
    }

    @Override
    public int getRowCount() {
        return loginBeans == null ? 0 : loginBeans.size();
    }

    @Override
    public int getColumnCount() {
        return heads.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return loginBeans.get(rowIndex).getAccount();
            case 1:
                return loginBeans.get(rowIndex).getPassword();
            case 2:
                switch (loginBeans.get(rowIndex).getType()) {
                    case Config.STUDENT_LOGIN:
                        return "学生";
                    case Config.TEACHER_LOGIN:
                        return "教师";
                    case Config.ADMIN_LOGIN:
                        return "管理员";
                    default:
                }
            case 3:
                return loginBeans.get(rowIndex).getState();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                loginBeans.get(rowIndex).setAccount((String) aValue);
                break;
            case 1:
                loginBeans.get(rowIndex).setPassword((String) aValue);
                break;
            case 2:
                switch ((String) aValue) {
                    case "学生":
                        loginBeans.get(rowIndex).setType(Config.STUDENT_LOGIN);
                        break;
                    case "教师":
                        loginBeans.get(rowIndex).setType(Config.TEACHER_LOGIN);
                        break;
                    case "管理员":
                        loginBeans.get(rowIndex).setType(Config.ADMIN_LOGIN);
                        break;
                    default:
                }
                break;
            case 3:
                loginBeans.get(rowIndex).setState((int) aValue);
                if ((int) aValue == Config.CHANGED_INFO) {
                    EventCenter.dispatchEvent(Events.ADMIN_CHANGE_LOGIN, 0, 0, loginBeans.get(rowIndex));
                }
                break;
            default:
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
                return loginBeans.get(rowIndex).getState() == Config.CHANGING_INFO;
            case 3:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return heads[columnIndex];
    }
}
