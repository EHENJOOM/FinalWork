package com.zhk.main.student;

import com.zhk.login.LoginBean;
import com.zhk.panel.student.exam.SelectExamPanel;
import com.zhk.panel.student.info.SelectInfoDialog;
import com.zhk.panel.student.score.SelectScorePanel;
import com.zhk.panel.student.subject.SelectSubjectPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2019/11/23 14:36
 * @description 学生客户端主界面
 */
public class StudentDialog extends JFrame implements StudentView {
    // 全局界面容器
    private JPanel mainPanel;

    private LoginBean loginBean;

    public StudentDialog(LoginBean loginBean) {
        this.loginBean = loginBean;

        initView();
        initMenuBar();
    }

    private void initView() {
        setTitle("学生客户端");
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu selectMenu = new JMenu("信息查询");
        JMenuItem showInfoMenuItem = new JMenuItem("基本信息查询");
        JMenuItem showScoreMenuItem = new JMenuItem("学生成绩查询");
        JMenuItem showExamMenuItem = new JMenuItem("考试信息查询");
        showInfoMenuItem.addActionListener(event -> showBaseInfo());
        showScoreMenuItem.addActionListener(event -> showScore());
        showExamMenuItem.addActionListener(event -> showExam());
        selectMenu.add(showInfoMenuItem);
        selectMenu.add(showScoreMenuItem);
        selectMenu.add(showExamMenuItem);

        JMenu graduateMenu = new JMenu("毕业设计");
        JMenuItem selectSubjectMenuItem = new JMenuItem("学生选题");
        selectSubjectMenuItem.addActionListener(event -> showSelectSubject());
        graduateMenu.add(selectSubjectMenuItem);

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

    private void showExam() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new SelectExamPanel(loginBean), BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    private void showScore() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(new SelectScorePanel(loginBean), BorderLayout.CENTER);
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
        mainPanel.revalidate();
    }

}
