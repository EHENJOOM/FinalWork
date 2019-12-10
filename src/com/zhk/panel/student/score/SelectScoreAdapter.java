package com.zhk.panel.student.score;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/24 16:52
 * @description 学生分数表适配器
 */
public class SelectScoreAdapter extends AbstractTableModel {

    private List<ScoreBean> scoreBeans;

    private String[] items = new String[]{"课程代码", "课程名称", "课程性质", "学分", "绩点", "开课学院", "任课教师", "学号", "姓名", "性别", "学院", "专业", "班级"};

    public void setScoreBeans(List<ScoreBean> scoreBeans) {
        this.scoreBeans = scoreBeans;
    }

    @Override
    public int getRowCount() {
        return scoreBeans == null ? 0 : scoreBeans.size();
    }

    @Override
    public int getColumnCount() {
        return items.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return scoreBeans.get(rowIndex).getCode();
            case 1:
                return scoreBeans.get(rowIndex).getName();
            case 2:
                return scoreBeans.get(rowIndex).getProperty();
            case 3:
                return scoreBeans.get(rowIndex).getScore();
            case 4:
                return scoreBeans.get(rowIndex).getPoint();
            case 5:
                return scoreBeans.get(rowIndex).getTeacherBean().getOfAcademy();
            case 6:
                return scoreBeans.get(rowIndex).getTeacherBean().getName();
            case 7:
                return scoreBeans.get(rowIndex).getStudentBean().getNumber();
            case 8:
                return scoreBeans.get(rowIndex).getStudentBean().getName();
            case 9:
                return scoreBeans.get(rowIndex).getStudentBean().getSex();
            case 10:
                return scoreBeans.get(rowIndex).getStudentBean().getAcademy();
            case 11:
                return scoreBeans.get(rowIndex).getStudentBean().getMajor();
            case 12:
                return String.format("%02d", scoreBeans.get(rowIndex).getStudentBean().getGrade())
                        + String.format("%02d", scoreBeans.get(rowIndex).getStudentBean().getClazz());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return items[columnIndex];
    }
}
