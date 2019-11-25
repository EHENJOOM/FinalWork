package com.zhk.panel.teacher.subject;

import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.panel.student.subject.SubjectBean;

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

    private LoginBean loginBean;
    private List<SubjectBean> subjectBeans;
    private EditSubjectPresenter presenter  = new EditSubjectPresenter();

    private EditSubjectAdapter adapter;
    private JTable table;
    private JPopupMenu popupMenu;

    public EditSubjectPanel(LoginBean loginBean) {
        this.loginBean = loginBean;
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
                presenter.delete(row, subjectBeans.get(row));
            }
        });
        JMenuItem addMenuItem = new JMenuItem("新建空行");
        addMenuItem.addActionListener(event -> {
            SubjectBean subjectBean = new SubjectBean();
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
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.YES_OPTION));
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.CHANGE_SUBJECT:
                presenter.update((SubjectBean) object);
                break;
            default:
                break;
        }
    }
}
