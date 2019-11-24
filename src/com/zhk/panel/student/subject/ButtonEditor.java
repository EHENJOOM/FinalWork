package com.zhk.panel.student.subject;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/23 20:28
 * @description 选课题按钮编辑器
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton selectButton;
    // 0代表还未选课题
    private int state = MatchBean.NONE;

    public ButtonEditor() {
        selectButton = new JButton("选择");
        selectButton.addActionListener(event -> {
            if (state == MatchBean.NONE) {
                // 用户按下确认键
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否确认选择该课题？", "请确认", JOptionPane.YES_NO_OPTION)) {
                    state = MatchBean.CONFIRMING;
                }
            } else {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否确认退选该课题？", "请确认", JOptionPane.YES_NO_OPTION)) {
                    state = MatchBean.NONE;
                }
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        state = (int) value;
        return selectButton;
    }

    @Override
    public Object getCellEditorValue() {
        return state;
    }

}
