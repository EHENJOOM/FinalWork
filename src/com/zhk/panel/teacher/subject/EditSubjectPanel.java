package com.zhk.panel.teacher.subject;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.panel.student.subject.SubjectBean;
import com.zhk.panel.teacher.info.student.ComboBoxEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 16:09
 * @description 编辑课题面板
 */
public class EditSubjectPanel extends JPanel implements EditSubjectView, EventListener {

    private List<SubjectBean> subjectBeans;
    private EditSubjectPresenter presenter  = new EditSubjectPresenter();

    private EditSubjectAdapter adapter;
    private JTable table;
    private JPopupMenu popupMenu;

    public EditSubjectPanel(LoginBean loginBean) {
        initView();
        createPopupMenu();
        presenter.attachView(this);
        presenter.select(loginBean);
        EventCenter.registerEventListener(this, Events.CHANGE_SUBJECT);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new EditSubjectAdapter();
        table = new JTable(adapter);
        String[] academy = new String[]{"经济管理学院", "信息科技与技术学院", "马克思主义学院", "文法学院"};
        table.getColumnModel().getColumn(1).setCellEditor(new ComboBoxEditor(academy));
        table.getColumnModel().getColumn(6).setCellEditor(new EditButtonEditor());
        table.getColumnModel().getColumn(6).setCellRenderer(new EditButtonRender());
        scrollPane.setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row == -1) {
                        return;
                    }

                    table.setRowSelectionInterval(row, row);
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "是否删除此课题？", "提示", JOptionPane.YES_NO_OPTION)) {
                int row = table.getSelectedRow();
                if (subjectBeans.get(row).getId() == 0) {
                    subjectBeans.remove(row);
                    table.updateUI();
                } else {
                    presenter.delete(row, subjectBeans.get(row));
                }
            }
        });
        JMenuItem addMenuItem = new JMenuItem("新建空行");
        addMenuItem.addActionListener(event -> {
            SubjectBean subjectBean = new SubjectBean();
            subjectBean.setId(0);
            subjectBean.setState(Config.UNCHANGED_INFO);
            subjectBeans.add(subjectBean);
            table.updateUI();
        });

        popupMenu.add(deleteMenuItem);
        popupMenu.add(addMenuItem);
    }

    @Override
    public void deleteApply(int row) {
        SwingUtilities.invokeLater(() -> {
            subjectBeans.remove(row);
            table.updateUI();
        });
    }

    @Override
    public void update(List<SubjectBean> subjectBeans) {
        SwingUtilities.invokeLater(() -> {
            this.subjectBeans = subjectBeans;
            adapter.setSubjectBeans(subjectBeans);
            table.updateUI();
        });
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.CHANGE_SUBJECT:
                if (((SubjectBean) object).getId() == 0) {
                    presenter.insert((SubjectBean) object);
                } else {
                    presenter.update((SubjectBean) object);
                }
                break;
            default:
                break;
        }
    }
}
