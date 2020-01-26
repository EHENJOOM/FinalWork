package com.zhk.panel.admin.student;

import com.zhk.main.StudentBean;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/12/21 23:10
 * @description 学院专业关联的下拉组合框的渲染器
 */
public class MajorComboBoxRender extends DefaultTableCellRenderer {

    private JLabel label;

    public MajorComboBoxRender() {
        label = new JLabel();
        label.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        label.setText(((StudentBean) value).getMajor());
        fillColor(table, isSelected);
        return label;
    }

    /**
     *
     * @param t jtable
     * @param isSelected 当前行是否被选中
     */
    private void fillColor(JTable t, boolean isSelected){
        if (isSelected) {
            label.setBackground(t.getSelectionBackground());
            label.setForeground(t.getSelectionForeground());
        } else {
            label.setBackground(t.getBackground());
            label.setForeground(t.getForeground());
        }
    }
}
