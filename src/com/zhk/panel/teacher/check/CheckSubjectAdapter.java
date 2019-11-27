package com.zhk.panel.teacher.check;

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
            "待确认人数", "学生学号", "姓名", "性别", "学院", "专业", "班级", "接收", "拒绝"};

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
            case 12:
            case 13:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 12:
                break;
            case 13:
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
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return items[columnIndex];
    }
}
