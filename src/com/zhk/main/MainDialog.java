package com.zhk.main;

import com.zhk.constant.Config;
import com.zhk.login.LoginBean;
import com.zhk.login.LoginDialog;
import com.zhk.panel.admin.login.ManageLoginPanel;
import com.zhk.panel.admin.teacher.ManageTeacherPanel;
import com.zhk.panel.student.exam.SelectExamPanel;
import com.zhk.panel.student.info.SelectInfoDialog;
import com.zhk.panel.student.score.SelectScorePanel;
import com.zhk.panel.student.subject.SelectSubjectPanel;
import com.zhk.panel.teacher.check.CheckSubjectPanel;
import com.zhk.panel.teacher.info.student.OperateStudentPanel;
import com.zhk.panel.teacher.info.teacher.SelectTeacherDialog;
import com.zhk.panel.teacher.subject.EditSubjectPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/25 12:56
 * @description 教师客户端主界面
 */
public class MainDialog extends JFrame {

    private LoginBean loginBean;

    private JPanel mainPanel;

    public MainDialog(LoginBean loginBean) {
        this.loginBean = loginBean;
        initView();
        addAbout();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (loginBean.getType() == Config.TEACHER_LOGIN) {
            setTitle("教师客户端");
            initTeacherMenuBar();
        } else if (loginBean.getType() == Config.STUDENT_LOGIN) {
            setTitle("学生客户端");
            initStudentMenuBar();
        } else if (loginBean.getType() == Config.ADMIN_LOGIN) {
            setTitle("超级管理员客户端");
            initAdminMenuBar();
        }
    }

    private void addAbout() {
        JLabel about = new JLabel(Config.ABOUT_ME_STRING);
        mainPanel.add(about, BorderLayout.SOUTH);
    }

    /**
     * 初始化用于老师的MenuBar
     */
    private void initTeacherMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu selectMenu = new JMenu("信息查询");
        JMenuItem showInfoMenuItem = new JMenuItem("教师基本信息查询");
        JMenuItem showStudentInfoMenuItem = new JMenuItem("学生基本信息查询");
        showInfoMenuItem.addActionListener(event -> showTeacherInfo());
        showStudentInfoMenuItem.addActionListener(event -> showStudentInfo());
        selectMenu.add(showInfoMenuItem);
        selectMenu.add(showStudentInfoMenuItem);

        JMenu graduateMenu = new JMenu("毕业设计");
        JMenuItem editSubjectMenuItem = new JMenuItem("编辑课题");
        JMenuItem checkSubjectMenuItem = new JMenuItem("确认学生课题");
        editSubjectMenuItem.addActionListener(event -> showEditSubject());
        checkSubjectMenuItem.addActionListener(event -> showCheckSubject());
        graduateMenu.add(editSubjectMenuItem);
        graduateMenu.addSeparator();
        graduateMenu.add(checkSubjectMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(selectMenu);
        menuBar.add(graduateMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
     * 初始化用于学生的MenuBar
     */
    private void initStudentMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu selectMenu = new JMenu("信息查询");
        JMenuItem showInfoMenuItem = new JMenuItem("基本信息查询");
        JMenuItem showScoreMenuItem = new JMenuItem("学生成绩查询");
        JMenuItem showExamMenuItem = new JMenuItem("考试信息查询");
        showInfoMenuItem.addActionListener(event -> showBaseInfo());
        showScoreMenuItem.addActionListener(event -> showStudentScore());
        showExamMenuItem.addActionListener(event -> showStudentExam());
        selectMenu.add(showInfoMenuItem);
        selectMenu.add(showScoreMenuItem);
        selectMenu.add(showExamMenuItem);

        JMenu graduateMenu = new JMenu("毕业设计");
        JMenuItem selectSubjectMenuItem = new JMenuItem("学生选题");
        selectSubjectMenuItem.addActionListener(event -> showSelectSubject());
        graduateMenu.add(selectSubjectMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(selectMenu);
        menuBar.add(graduateMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
     * 初始化用于管理员的MenuBar
     */
    private void initAdminMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu peopleMenu = new JMenu("人员管理");
        JMenuItem showLoginMenuItem = new JMenuItem("登录信息管理");
        JMenuItem showStudentItem = new JMenuItem("学生信息管理");
        JMenuItem showTeacherMenuItem = new JMenuItem("教师信息管理");
        showLoginMenuItem.addActionListener(event -> showLoginInfo());
        showStudentItem.addActionListener(event -> showStudent());
        showTeacherMenuItem.addActionListener(event -> showTeacher());
        peopleMenu.add(showLoginMenuItem);
        peopleMenu.add(showStudentItem);
        peopleMenu.add(showTeacherMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(peopleMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /***************************************
     * 公共界面显示方法
     ***************************************
     */
    private void exitLogin() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "确认退出当前界面？", "退出登录", JOptionPane.YES_NO_OPTION)) {
            this.dispose();
            LoginDialog dialog = new LoginDialog();
            dialog.setVisible(true);
        }
    }

    /***************************************
     * Student相关的界面显示方法
     * *************************************
     */
    private void showStudentExam() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new SelectExamPanel(loginBean), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void showStudentScore() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new SelectScorePanel(loginBean), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void showBaseInfo() {
        SelectInfoDialog dialog = new SelectInfoDialog(loginBean);
        dialog.setSize(400, 300);
        dialog.setVisible(true);
    }

    private void showSelectSubject() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new SelectSubjectPanel(loginBean), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    /************************************
     * Teacher相关的界面显示方法
     * **********************************
     */
    private void showTeacherInfo() {
        SelectTeacherDialog dialog = new SelectTeacherDialog(loginBean);
        dialog.setVisible(true);
    }

    private void showStudentInfo() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new OperateStudentPanel(), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void showEditSubject() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new EditSubjectPanel(loginBean), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void showCheckSubject() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new CheckSubjectPanel(loginBean), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    /**************************************
     * 超级管理员相关的界面显示方法
     * ************************************
     */
    private void showLoginInfo() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new ManageLoginPanel(), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void showStudent() {
        mainPanel.removeAll();
        mainPanel.repaint();

        addAbout();
        mainPanel.revalidate();
    }

    private void showTeacher() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new ManageTeacherPanel(), BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

}
