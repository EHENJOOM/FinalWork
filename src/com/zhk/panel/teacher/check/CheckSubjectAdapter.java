package com.zhk.panel.teacher.check;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.panel.student.subject.MatchBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:45
 * @description 确认学生选课题jtable适配器
 */
public class CheckSubjectAdapter extends AbstractTableModel {

    private List<MatchBean> matchBeans;

    private String[] items = new String[]{"课题代码", "课题名称", "所属学院", "总接收人数", "已接收人数",
            "待确认人数", "学生学号", "姓名", "性别", "学院", "专业", "班级", "状态", "接收", "拒绝"};

    public void setMatchBeans(List<MatchBean> matchBeans) {
        this.matchBeans = matchBeans;
    }

    @Override
    public int getRowCount() {
        return matchBeans == null ? 0 : matchBeans.size();
    }

    @Override
    public int getColumnCount() {
        return items.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 13:
            case 14:
                return matchBeans.get(rowIndex).getState() == Config.CONFIRMING_SUBJECT;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 13:
                if ((boolean) object) {
                    matchBeans.get(rowIndex).setState(Config.ACCEPTED_SUBJECT);
                    EventCenter.dispatchEvent(Events.ACCEPT_STUDENT, 0, 0, matchBeans.get(rowIndex));
                }
                break;
            case 14:
                if ((boolean) object) {
                    matchBeans.get(rowIndex).setState(Config.UNSELECTED_SUBJECT);
                    EventCenter.dispatchEvent(Events.REFUSE_STUDENT, 0, 0, matchBeans.get(rowIndex));
                }
                break;
            default:
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return matchBeans.get(rowIndex).getSubjectBean().getCode();
            case 1:
                return matchBeans.get(rowIndex).getSubjectBean().getName();
            case 2:
                return matchBeans.get(rowIndex).getSubjectBean().getOfAcademy();
            case 3:
                return matchBeans.get(rowIndex).getSubjectBean().getTotalNum();
            case 4:
                return matchBeans.get(rowIndex).getSubjectBean().getAcceptedNum();
            case 5:
                return matchBeans.get(rowIndex).getSubjectBean().getConfirmingNum();
            case 6:
                return matchBeans.get(rowIndex).getStudentBean().getNumber();
            case 7:
                return matchBeans.get(rowIndex).getStudentBean().getName();
            case 8:
                return matchBeans.get(rowIndex).getStudentBean().getSex();
            case 9:
                return matchBeans.get(rowIndex).getStudentBean().getAcademy();
            case 10:
                return matchBeans.get(rowIndex).getStudentBean().getMajor();
            case 11:
                return String.format("%02d", matchBeans.get(rowIndex).getStudentBean().getGrade())
                        + String.format("%02d", matchBeans.get(rowIndex).getStudentBean().getClazz());
            case 12:
                if (matchBeans.get(rowIndex).getState() == Config.CONFIRMING_SUBJECT) {
                    return "待确认";
                } else if (matchBeans.get(rowIndex).getState() == Config.ACCEPTED_SUBJECT) {
                    return "已接收";
                } else if (matchBeans.get(rowIndex).getState() == Config.UNSELECTED_SUBJECT) {
                    return "未选";
                }
            case 13:
                return Config.ACCEPT_STRING;
            case 14:
                return Config.REFUSE_STRING;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return items[columnIndex];
    }
}
