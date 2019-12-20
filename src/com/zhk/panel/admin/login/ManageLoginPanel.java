package com.zhk.panel.admin.login;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
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
 * @date 2019/12/18 18:25
 * @description 管理登录账号信息
 */
public class ManageLoginPanel extends JPanel implements ManageLoginView, EventListener {

    private JTable table;

    private JPopupMenu popupMenu;

    private ManageLoginAdapter adapter;

    private ManageLoginPresenter presenter;

    private List<LoginBean> loginBeans = new LinkedList<>();

    public ManageLoginPanel() {
        initView();
        createPopupMenu();
        presenter = new ManageLoginPresenter();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, Events.ADMIN_CHANGE_LOGIN);
    }

    private void initView() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        adapter = new ManageLoginAdapter();
        table = new JTable(adapter);

        table.getColumnModel().getColumn(2).setCellEditor(new ComboBoxEditor(new String[]{"学生", "教师", "管理员"}));
        table.getColumnModel().getColumn(3).setCellEditor(new EditButtonEditor());
        table.getColumnModel().getColumn(3).setCellRenderer(new EditButtonRender());
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
        JMenuItem addMenuItem = new JMenuItem("新建");
        addMenuItem.addActionListener(event -> {
            LoginBean loginBean = new LoginBean();
            loginBean.setType(Config.STUDENT_LOGIN);
            loginBean.setState(Config.UNCHANGED_INFO);
            loginBeans.add(loginBean);
            table.updateUI();
        });

        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "是否删除该账户？", "提示", JOptionPane.YES_NO_OPTION)) {
                int row = table.getSelectedRow();
                if (loginBeans.get(row).getId() == 0) {
                    loginBeans.remove(row);
                    table.updateUI();
                } else {
                    presenter.delete(loginBeans.get(row));
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
    public void update(List<LoginBean> data) {
        SwingUtilities.invokeLater(() -> {
            loginBeans.addAll(data);
            adapter.setLoginBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void deleteApply(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> {
            loginBeans.remove(loginBean);
            table.updateUI();
        });
    }

    @Override
    public void resetData(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> {
            loginBeans.forEach(temp -> {
                if (temp.getId() == loginBean.getId()) {
                    temp.setAccount(loginBean.getAccount());
                    temp.setPassword(loginBean.getPassword());
                    temp.setType(loginBean.getType());
                    temp.setState(loginBean.getState());
                }
            });
            table.updateUI();
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.ADMIN_CHANGE_LOGIN:
                LoginBean loginBean = (LoginBean) object;
                if (loginBean.getId() == 0) {
                    presenter.add(loginBean);
                } else {
                    presenter.change(loginBean);
                }
                break;
            default:
        }
    }
}
