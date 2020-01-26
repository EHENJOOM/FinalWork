package com.zhk.panel.admin.student;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.main.StudentBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/21 18:19
 * @description 管理学生信息jtable适配器
 */
public class ManageStudentAdapter extends AbstractTableModel {

    private List<StudentBean> studentBeans;

    private String[] items = new String[]{"学号", "姓名", "性别", "学院", "专业", "班级", "修改"};

    public void setStudentBeans(List<StudentBean> studentBeans) {
        this.studentBeans = studentBeans;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return studentBeans.get(rowIndex).getNumber();
            case 1:
                return studentBeans.get(rowIndex).getName();
            case 2:
                return studentBeans.get(rowIndex).getSex();
            case 3:
                return studentBeans.get(rowIndex).getAcademy();
            case 4:
                return studentBeans.get(rowIndex);
            case 5:
                return String.format("%02d", studentBeans.get(rowIndex).getGrade())
                    + String.format("%02d", studentBeans.get(rowIndex).getClazz());
            case 6:
                return studentBeans.get(rowIndex).getState();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return studentBeans.get(rowIndex).getState() == Config.CHANGING_INFO;
            case 6:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                studentBeans.get(rowIndex).setNumber((String) aValue);
                break;
            case 1:
                studentBeans.get(rowIndex).setName((String) aValue);
                break;
            case 2:
                studentBeans.get(rowIndex).setSex((String) aValue);
                break;
            case 3:
                studentBeans.get(rowIndex).setAcademy((String) aValue);
                break;
            case 4:
                studentBeans.get(rowIndex).setMajor((String) aValue);
                break;
            case 5:
                studentBeans.get(rowIndex).setGrade(Integer.parseInt(((String) aValue).substring(0, 2)));
                studentBeans.get(rowIndex).setClazz(Integer.parseInt(((String) aValue).substring(2, 4)));
                break;
            case 6:
                studentBeans.get(rowIndex).setState((int) aValue);
                if ((int) aValue == Config.CHANGED_INFO) {
                    EventCenter.dispatchEvent(Events.ADMIN_CHANGE_STUDENT, 0, 0, studentBeans.get(rowIndex));
                }
                break;
            default:
        }
    }

    @Override
    public String getColumnName(int column) {
        return items[column];
    }

    @Override
    public int getRowCount() {
        return studentBeans == null ? 0 : studentBeans.size();
    }

    @Override
    public int getColumnCount() {
        return items.length;
    }
}
