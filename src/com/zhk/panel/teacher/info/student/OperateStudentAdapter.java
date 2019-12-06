package com.zhk.panel.teacher.info.student;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.main.StudentBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/27 16:28
 * @description 修改学生信息jtable适配器
 */
public class OperateStudentAdapter extends AbstractTableModel {

    private String[] headers = new String[]{"学号", "姓名", "性别", "专业", "学院", "班级", "修改"};

    private List<StudentBean> studentBeans;

    public void setStudentBeans(List<StudentBean> studentBeans) {
        this.studentBeans = studentBeans;
    }

    @Override
    public int getRowCount() {
        return studentBeans == null ? 0 : studentBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
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
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                studentBeans.get(rowIndex).setId(Integer.parseInt((String) object));
                break;
            case 1:
                studentBeans.get(rowIndex).setName((String) object);
                break;
            case 2:
                studentBeans.get(rowIndex).setSex((String) object);
                break;
            case 3:
                studentBeans.get(rowIndex).setMajor((String) object);
                break;
            case 4:
                studentBeans.get(rowIndex).setAcademy((String) object);
                break;
            case 5:
                String str = (String) object;
                studentBeans.get(rowIndex).setGrade(Integer.parseInt(str.substring(0, 1)));
                studentBeans.get(rowIndex).setClazz(Integer.parseInt(str.substring(2, 3)));
                break;
            case 6:
                studentBeans.get(rowIndex).setState((int) object);
                if ((int) object == Config.CHANGED_INFO) {
                    EventCenter.dispatchEvent(Events.CHANGE_STUDENT, 0, 0, studentBeans.get(rowIndex));
                }
                break;
            default:
                break;
        }
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
                return studentBeans.get(rowIndex).getMajor();
            case 4:
                return studentBeans.get(rowIndex).getAcademy();
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
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
