package com.zhk.panel.admin.teacher;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.main.TeacherBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/20 11:18
 * @description 管理老师信息jtale数据适配器
 */
public class ManageTeacherAdapter extends AbstractTableModel {

    private List<TeacherBean> teacherBeans;

    private String[] items = new String[]{"工号", "姓名", "性别", "学院", "职称", "修改"};

    public void setTeacherBeans(List<TeacherBean> teacherBeans) {
        this.teacherBeans = teacherBeans;
    }

    @Override
    public int getRowCount() {
        return teacherBeans == null ? 0 : teacherBeans.size();
    }

    @Override
    public int getColumnCount() {
        return items.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return teacherBeans.get(rowIndex).getState() == Config.CHANGING_INFO;
            case 5:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                teacherBeans.get(rowIndex).setNumber((String) aValue);
                break;
            case 1:
                teacherBeans.get(rowIndex).setName((String) aValue);
                break;
            case 2:
                teacherBeans.get(rowIndex).setSex((String) aValue);
                break;
            case 3:
                teacherBeans.get(rowIndex).setOfAcademy((String) aValue);
                break;
            case 4:
                teacherBeans.get(rowIndex).setJobTitle((String) aValue);
                break;
            case 5:
                teacherBeans.get(rowIndex).setState((int) aValue);
                if ((int) aValue == Config.CHANGED_INFO) {
                    EventCenter.dispatchEvent(Events.ADMIN_CHANGE_TEACHER, 0, 0, teacherBeans.get(rowIndex));
                }
                break;
            default:
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return teacherBeans.get(rowIndex).getNumber();
            case 1:
                return teacherBeans.get(rowIndex).getName();
            case 2:
                return teacherBeans.get(rowIndex).getSex();
            case 3:
                return teacherBeans.get(rowIndex).getOfAcademy();
            case 4:
                return teacherBeans.get(rowIndex).getJobTitle();
            case 5:
                return teacherBeans.get(rowIndex).getState();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return items[columnIndex];
    }
}
