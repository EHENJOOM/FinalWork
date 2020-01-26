package com.zhk.panel.admin.student;

import com.zhk.constant.Config;
import com.zhk.constant.MajorOfAcademy;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.main.StudentBean;
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
 * @date 2019/12/21 17:36
 * @description 管理学生信息面板
 */
public class ManageStudentPanel extends JPanel implements ManageStudentView, EventListener {

    private JTable table;

    private JPopupMenu popupMenu;

    private ManageStudentAdapter adapter;

    private ManageStudentPresenter presenter;

    private List<StudentBean> studentBeans = new LinkedList<>();

    public ManageStudentPanel() {
        initView();
        createPopupMenu();
        presenter = new ManageStudentPresenter();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, Events.ADMIN_CHANGE_STUDENT);
    }

    public void initView() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        adapter = new ManageStudentAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(2).setCellEditor(new ComboBoxEditor(new String[]{"男", "女"}));
        table.getColumnModel().getColumn(3).setCellEditor(new ComboBoxEditor(Config.ACADEMY_STRINGS));
        table.getColumnModel().getColumn(4).setCellEditor(new MajorComboBoxEditor());
        table.getColumnModel().getColumn(4).setCellRenderer(new MajorComboBoxRender());
        table.getColumnModel().getColumn(6).setCellEditor(new EditButtonEditor());
        table.getColumnModel().getColumn(6).setCellRenderer(new EditButtonRender());
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

    public void createPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem addMenuItem = new JMenuItem("新建");
        addMenuItem.addActionListener(event -> {
            StudentBean studentBean = new StudentBean();
            studentBean.setState(Config.UNCHANGED_INFO);
            studentBeans.add(studentBean);
        });

        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "是否删除该学生信息？", "提示", JOptionPane.YES_NO_OPTION)) {
                int row = table.getSelectedRow();
                if (studentBeans.get(row).getId() == 0) {
                    studentBeans.remove(row);
                } else {
                    presenter.delete(studentBeans.get(row));
                }
            }
        });

        popupMenu.add(addMenuItem);
        popupMenu.add(deleteMenuItem);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.ADMIN_CHANGE_STUDENT:
                StudentBean studentBean = (StudentBean) object;
                if (!MajorOfAcademy.isMatched(studentBean.getAcademy(), studentBean.getMajor())) {
                    showError("该学生当前专业与学院不匹配，请重新确认信息！");
                    return;
                }
                if (studentBean.getId() == 0) {
                    presenter.add(studentBean);
                } else {
                    presenter.change(studentBean);
                }
                break;
            default:
        }
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void update(List<StudentBean> data) {
        SwingUtilities.invokeLater(() -> {
            studentBeans.addAll(data);
            adapter.setStudentBeans(studentBeans);
            table.updateUI();
        });
    }

    @Override
    public void deleteApply(StudentBean studentBean) {
        SwingUtilities.invokeLater(() -> {
            studentBeans.remove(studentBean);
            table.updateUI();
        });
    }

    @Override
    public void resetData(StudentBean studentBean) {

    }
}
