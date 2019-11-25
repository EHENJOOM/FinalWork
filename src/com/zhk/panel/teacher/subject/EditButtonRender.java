package com.zhk.panel.teacher.subject;

import com.zhk.contant.Config;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/25 17:20
 * @description 修改信息渲染器
 */
public class EditButtonRender extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (Config.UNCHANGED_INFO == (int) value || Config.CHANGED_INFO == (int) value) {
            setText("修改");
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        } else if (Config.CHANGING_INFO == (int) value) {
            setText("保存");
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        }
        return this;
    }

}
