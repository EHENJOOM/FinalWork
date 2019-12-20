package com.zhk.panel.admin.teacher;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.main.TeacherBean;
import com.zhk.panel.teacher.info.student.ComboBoxEditor;
import com.zhk.panel.teacher.subject.EditButtonEditor;
import com.zhk.panel.teacher.subject.EditButtonRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/19 11:35
 * @description 管理老师信息面板
 */
public class ManageTeacherPanel extends JPanel implements ManageTeacherView, EventListener {

    private JTable table;

    private JPopupMenu popupMenu;

    private List<TeacherBean> teacherBeans = new LinkedList<>();

    private ManageTeacherAdapter adapter;

    private ManageTeacherPresenter presenter;

    public ManageTeacherPanel() {
        initView();
        createPopupMenu();
        presenter = new ManageTeacherPresenter();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, Events.ADMIN_CHANGE_TEACHER);
    }

    private void initView() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        adapter = new ManageTeacherAdapter();
        table = new JTable(adapter);

        table.getColumnModel().getColumn(2).setCellEditor(new ComboBoxEditor(new String[]{"男", "女"}));
        table.getColumnModel().getColumn(3).setCellEditor(new ComboBoxEditor(Config.ACADEMY_STRINGS));
        table.getColumnModel().getColumn(4).setCellEditor(new ComboBoxEditor(Config.JOB_TITLE_STRINGS));
        table.getColumnModel().getColumn(5).setCellEditor(new EditButtonEditor());
        table.getColumnModel().getColumn(5).setCellRenderer(new EditButtonRender());
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

        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem addMenuItem = new JMenuItem("新建");
        addMenuItem.addActionListener(event -> {
            TeacherBean teacherBean = new TeacherBean();
            teacherBean.setState(Config.UNCHANGED_INFO);
            teacherBeans.add(teacherBean);
            table.updateUI();
        });

        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "是否删除该教师？", "提示", JOptionPane.YES_NO_OPTION)) {
                int row = table.getSelectedRow();
                if (teacherBeans.get(row).getId() == 0) {
                    teacherBeans.remove(row);
                    table.updateUI();
                } else {
                    presenter.delete(teacherBeans.get(row));
                }
            }
        });

        popupMenu.add(addMenuItem);
        popupMenu.add(deleteMenuItem);
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void update(List<TeacherBean> data) {
        SwingUtilities.invokeLater(() -> {
            teacherBeans.addAll(data);
            adapter.setTeacherBeans(teacherBeans);
            table.updateUI();
        });
    }

    @Override
    public void deleteApply(TeacherBean teacherBean) {
        SwingUtilities.invokeLater(() -> {
            teacherBeans.remove(teacherBean);
            table.updateUI();
        });
    }

    @Override
    public void resetData(TeacherBean teacherBean) {
        SwingUtilities.invokeLater(() -> {
            teacherBeans.forEach(temp -> {
                if (temp.getId() == teacherBean.getId()) {
                    temp.setNumber(teacherBean.getNumber());
                    temp.setName(teacherBean.getName());
                    temp.setSex(teacherBean.getSex());
                    temp.setOfAcademy(teacherBean.getOfAcademy());
                    temp.setJobTitle(teacherBean.getJobTitle());
                    temp.setState(teacherBean.getState());
                }
            });
            table.updateUI();
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.ADMIN_CHANGE_TEACHER:
                TeacherBean teacherBean = (TeacherBean) object;
                if (teacherBean.getId() == 0) {
                    presenter.add(teacherBean);
                } else {
                    presenter.change(teacherBean);
                }
                break;
            default:
        }
    }
}
