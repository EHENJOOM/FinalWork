package com.zhk.panel.teacher.check;

import com.zhk.constant.Config;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/26 19:37
 * @description 渲染器
 */
public class CheckSubjectRender extends JButton implements TableCellRenderer {

    public CheckSubjectRender(String title) {
        setText(title);
        if (Config.ACCEPT_STRING.equals(title)) {
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        } else if (Config.REFUSE_STRING.equals(title)) {
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (Config.ACCEPT_STRING.equals(value)) {
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        } else if (Config.REFUSE_STRING.equals(value)){
            setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
        }
        return this;
    }
}
