package com.zhk.panel.student.subject;

import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.main.student.StudentBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:06
 * @description JTable课题表适配器
 */
public class SubjectAdapter extends AbstractTableModel {

    private String[] columnNames = new String[]{"课题代码", "课题主管学院", "课题名称", "指导教师工号", "指导教师姓名", "指导教师职称", "该课题可接收总人数", "该课题已接收人数", "该课题待确认人数", "操作"};
    private List<SubjectBean> subjectBeans;
    private StudentBean studentBean = new StudentBean();

    private int selectRowIndex = -1;
    private static final int OPERATE_COLUMN = 9;

    public void setSubjectBeans(List<SubjectBean> subjectBeans) {
        this.subjectBeans = subjectBeans;
    }

    public void setStudentBean(LoginBean loginBean) {
        this.studentBean.setNumber(loginBean.getAccount());
    }

    @Override
    public int getRowCount() {
        return subjectBeans == null ? 0 : subjectBeans.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 当前为操作列
        if (columnIndex == OPERATE_COLUMN) {
            // 当前列表一个课题都未选或当前是已选课题行，即可对按钮进行操作
            return selectRowIndex < 0 || rowIndex == selectRowIndex;
        }
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == OPERATE_COLUMN) {
            int state = (int) value;
            MatchBean matchBean = new MatchBean();
            matchBean.setSubjectBean(subjectBeans.get(rowIndex));
            matchBean.setStudentBean(studentBean);
            if (state == MatchBean.ACCEPTED || state == MatchBean.CONFIRMING) {
                // 选择该课题
                selectRowIndex = rowIndex;
                matchBean.setState(MatchBean.CONFIRMING);
                EventCenter.dispatchEvent(Events.INSERT_MATCH, 0, 0, matchBean);
            } else {
                // 退选该课题
                selectRowIndex = -1;
                matchBean.setState(MatchBean.NONE);
                EventCenter.dispatchEvent(Events.CANCEL_MATCH, 0, 0, matchBean);
            }
        }

        // 在jtable表上应用数据更改
        this.fireTableCellUpdated(rowIndex, columnIndex);
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
                return subjectBeans.get(rowIndex).getTeacherBean().getNumber();
            case 4:
                return subjectBeans.get(rowIndex).getTeacherBean().getName();
            case 5:
                return subjectBeans.get(rowIndex).getTeacherBean().getJobTitle();
            case 6:
                return subjectBeans.get(rowIndex).getTotalNum();
            case 7:
                return subjectBeans.get(rowIndex).getAcceptedNum();
            case 8:
                return subjectBeans.get(rowIndex).getConfirmingNum();
            case 9:
                return subjectBeans.get(rowIndex).getState();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
}
