package com.zhk.panel.teacher.subject;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.panel.student.subject.SubjectBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:16
 * @description 编辑课题信息jatable适配器
 */
public class EditSubjectAdapter extends AbstractTableModel {

    private List<SubjectBean> subjectBeans;

    private String[] headers = new String[] {"课题代码", "课题主管学院", "课题名称", "可接收总人数", "已接收人数", "待确认人数", "修改"};

    public void setSubjectBeans(List<SubjectBean> subjectBeans) {
        this.subjectBeans = subjectBeans;
    }

    @Override
    public int getRowCount() {
        return subjectBeans == null ? 0 : subjectBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                subjectBeans.get(rowIndex).setCode((String) value);
                break;
            case 1:
                subjectBeans.get(rowIndex).setOfAcademy((String) value);
                break;
            case 2:
                subjectBeans.get(rowIndex).setName((String) value);
                break;
            case 3:
                subjectBeans.get(rowIndex).setTotalNum(Integer.parseInt((String) value));
                break;
            case 6:
                subjectBeans.get(rowIndex).setState((int) value);
                if ((int) value == Config.CHANGED_INFO) {
                    EventCenter.dispatchEvent(Events.CHANGE_SUBJECT, 0, 0, subjectBeans.get(rowIndex));
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
                return subjectBeans.get(rowIndex).getCode();
            case 1:
                return subjectBeans.get(rowIndex).getOfAcademy();
            case 2:
                return subjectBeans.get(rowIndex).getName();
            case 3:
                return subjectBeans.get(rowIndex).getTotalNum();
            case 4:
                return subjectBeans.get(rowIndex).getAcceptedNum();
            case 5:
                return subjectBeans.get(rowIndex).getConfirmingNum();
            case 6:
                return subjectBeans.get(rowIndex).getState();
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
                if (subjectBeans.get(rowIndex).getState() == Config.CHANGING_INFO) {
                    return true;
                } else {
                    return false;
                }
            case 6:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
