package com.zhk.main.teacher;

import com.zhk.login.LoginBean;
import com.zhk.panel.teacher.check.CheckSubjectPanel;
import com.zhk.panel.teacher.subject.EditSubjectPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/25 12:56
 * @description 教师客户端主界面
 */
public class TeacherDialog extends JFrame {

    private LoginBean loginBean;

    private JPanel mainPanel;

    public TeacherDialog(LoginBean loginBean) {
        this.loginBean = loginBean;
        initView();
        initMenuBar();
    }

    private void initView() {
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setTitle("教师客户端");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu selectMenu = new JMenu("信息查询");
        JMenuItem showInfoMenuItem = new JMenuItem("基本信息查询");
        selectMenu.add(showInfoMenuItem);

        JMenu graduateMenu = new JMenu("毕业设计");
        JMenuItem editSubjectMenuItem = new JMenuItem("编辑课题");
        JMenuItem checkSubjectMenuItem = new JMenuItem("确认学生课题");
        editSubjectMenuItem.addActionListener(event -> showEditSubject());
        checkSubjectMenuItem.addActionListener(event -> showCheckSubject());
        graduateMenu.add(editSubjectMenuItem);
        graduateMenu.addSeparator();
        graduateMenu.add(checkSubjectMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        helpMenu.add(aboutMenuItem);
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(selectMenu);
        menuBar.add(graduateMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void showEditSubject() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new EditSubjectPanel(loginBean), BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    private void showCheckSubject() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new CheckSubjectPanel(loginBean), BorderLayout.CENTER);
        mainPanel.revalidate();
    }

}
