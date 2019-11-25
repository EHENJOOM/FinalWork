package com.zhk.panel.student.subject;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/23 20:33
 * @description 选课题按钮渲染器
 */
public class ButtonRender extends JButton implements TableCellRenderer {

    public ButtonRender() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (MatchBean.NONE == (int)value) {
            setText("选择");
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        } else {
            setText("退选");
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        }
        return this;
    }
}
