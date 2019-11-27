package com.zhk.panel.teacher.check;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/26 19:37
 * @description
 */
public class CheckSubjectRender extends JButton implements TableCellRenderer {

    public CheckSubjectRender(String title) {
        setText(title);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
