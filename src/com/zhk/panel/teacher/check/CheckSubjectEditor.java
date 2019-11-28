package com.zhk.panel.teacher.check;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/26 19:24
 * @description jtable的按钮editor
 */
public class CheckSubjectEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;

    private boolean isConfirmed = false;

    public CheckSubjectEditor(String title) {
        button = new JButton(title);

        button.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否" + title + "该学生的课题？", "确认", JOptionPane.YES_NO_OPTION)) {
                isConfirmed = true;
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return isConfirmed;
    }
}
