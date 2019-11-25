package com.zhk.panel.teacher.subject;

import com.zhk.contant.Config;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/25 17:21
 * @description 修改信息按钮
 */
public class EditButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton editButton;

    private int state = Config.UNCHANGED_INFO;

    public EditButtonEditor() {
        editButton = new JButton("修改");
        editButton.addActionListener(event -> {
            if (state == Config.UNCHANGED_INFO || state == Config.CHANGED_INFO) {
                state = Config.CHANGING_INFO;
            } else if (state == Config.CHANGING_INFO) {
                editButton.setText("保存");
                state = Config.CHANGED_INFO;
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        state = (int) value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return state;
    }
}
