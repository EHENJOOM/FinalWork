package com.zhk.panel.admin.student;

import com.zhk.constant.MajorOfAcademy;
import com.zhk.main.StudentBean;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * @author 赵洪苛
 * @date 2019/12/21 21:42
 * @description 和学院关联的专业下拉组合框
 */
public class MajorComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    private String str;

    private StudentBean studentBean;

    private JComboBox<String> comboBox;

    private static final int CLICK_COUNT_TO_START = 1;

    public MajorComboBoxEditor() {
        comboBox = new JComboBox<>();
        comboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                str =(String) comboBox.getSelectedItem();
                fireEditingStopped();
            }
        });
    }

    /**
     * 为下拉列表框设置其中的数据
     */
    private void setComboBoxItem() {
        MajorOfAcademy.getMajors(studentBean.getAcademy()).forEach(item -> comboBox.addItem(item));
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= CLICK_COUNT_TO_START;
        }
        return false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        studentBean = (StudentBean) value;
        comboBox.removeAllItems();
        setComboBoxItem();
        comboBox.setSelectedItem(str);
        return comboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return str;
    }
}
