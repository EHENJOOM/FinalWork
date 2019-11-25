package com.zhk.panel.student.exam;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:49
 * @description 考试信息jtable适配器
 */
public class ExamAdapter extends AbstractTableModel {

    private List<ExamBean> examBeans;

    private String[] headers = new String[]{"课程代码", "课程名称", "考试时间"};

    public void setExamBeans(List<ExamBean> examBeans) {
        this.examBeans = examBeans;
    }

    @Override
    public int getRowCount() {
        return examBeans == null ? 0 : examBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return examBeans.get(rowIndex).getCode();
            case 1:
                return examBeans.get(rowIndex).getName();
            case 2:
                return examBeans.get(rowIndex).getTime();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
