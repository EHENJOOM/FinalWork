package com.zhk.panel.student.subject;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.main.StudentBean;
import com.zhk.main.TeacherBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/23 18:06
 * @description JTable课题表适配器
 */
public class SelectSubjectAdapter extends AbstractTableModel {

    private List<SubjectBean> subjectBeans;
    private StudentBean studentBean = new StudentBean();

    /**
     * 列名
     */
    private String[] columnNames = new String[]{"课题代码", "课题主管学院", "课题名称", "指导教师工号", "指导教师姓名", "指导教师职称", "可接收总人数", "已接收人数", "待确认人数", "状态", "操作"};

    private int selectedId = -1;

    // 操作按钮对应的列数
    private static final int OPERATE_COLUMN = 10;

    public void setSubjectBeans(List<SubjectBean> subjectBeans) {
        this.subjectBeans = subjectBeans;
        // 查找所有记录，如果该学生已经选了课题，那么标志id则不为0，该学生也不可选其他课题
        subjectBeans.forEach(subjectBean -> {
            if (subjectBean.getState() != Config.UNSELECTED_SUBJECT) {
                selectedId = subjectBean.getId();
            }
        });
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
            return selectedId == -1 || selectedId == subjectBeans.get(rowIndex).getId();
        }
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == OPERATE_COLUMN) {
            int state = (int) value;
            if (state == subjectBeans.get(rowIndex).getState()) {
                return;
            }
            MatchBean matchBean = new MatchBean();
            matchBean.setSubjectBean(subjectBeans.get(rowIndex));
            matchBean.setStudentBean(studentBean);
            matchBean.setTeacherBean(subjectBeans.get(rowIndex).getTeacherBean());
            if (state == Config.ACCEPTED_SUBJECT || state == Config.CONFIRMING_SUBJECT) {
                // 选择该课题
                selectedId = subjectBeans.get(rowIndex).getId();
                matchBean.setState(Config.CONFIRMING_SUBJECT);
                // 使用事件分发器通知Panel调用Presenter进行数据库操作
                EventCenter.dispatchEvent(Events.INSERT_MATCH, 0, 0, matchBean);
            } else {
                // 退选该课题
                selectedId = -1;
                matchBean.setState(Config.UNSELECTED_SUBJECT);
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
                if (subjectBeans.get(rowIndex).getState() == Config.CONFIRMING_SUBJECT) {
                    return "待确认";
                } else if (subjectBeans.get(rowIndex).getState() == Config.ACCEPTED_SUBJECT) {
                    return "已确认";
                } else {
                    return "未选";
                }
            case 10:
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
