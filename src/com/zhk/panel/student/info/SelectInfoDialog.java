package com.zhk.panel.student.info;

import com.zhk.login.LoginBean;
import com.zhk.main.student.StudentBean;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2019/11/24 10:59
 * @description 学生基本信息窗口
 */
public class SelectInfoDialog extends JFrame implements SelectInfoView {
    private JPanel contentPanel;
    private JLabel numberText;
    private JLabel nameText;
    private JLabel sexText;
    private JLabel majorText;
    private JLabel academyText;
    private JLabel clazzText;

    private SelectInfoPresenter presenter = new SelectInfoPresenter();

    public SelectInfoDialog(LoginBean loginBean) {
        setTitle("学生基本信息");
        setLocationRelativeTo(null);
        setContentPane(contentPanel);
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    @Override
    public void showInfo(StudentBean studentBean) {
        SwingUtilities.invokeLater(() -> {
            nameText.setText(studentBean.getName());
            numberText.setText(studentBean.getNumber());
            sexText.setText(studentBean.getSex());
            academyText.setText(studentBean.getAcademy());
            majorText.setText(studentBean.getMajor());
            clazzText.setText(String.format("%02d", studentBean.getGrade()) + String.format("%02d", studentBean.getClazz()));
            pack();
        });
    }
}
