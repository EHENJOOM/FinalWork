package com.zhk.panel.teacher.info.teacher;

import com.zhk.login.LoginBean;
import com.zhk.main.TeacherBean;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2019/12/8 21:07
 * @description 查询当前登录账号老师信息
 */
public class SelectTeacherDialog extends JFrame implements SelectTeacherView {
    private JPanel mainPanel;
    private JLabel numberLabel;
    private JLabel nameLabel;
    private JLabel sexLabel;
    private JLabel academyLabel;
    private JLabel jobLabel;

    private SelectTeacherPresenter presenter;

    public SelectTeacherDialog(LoginBean loginBean) {
        initView();
        presenter = new SelectTeacherPresenter();
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    private void initView() {
        setTitle("教师基本信息");
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        setSize(400, 250);
    }

    @Override
    public void show(TeacherBean teacherBean) {
        numberLabel.setText(teacherBean.getNumber());
        nameLabel.setText(teacherBean.getName());
        sexLabel.setText(teacherBean.getSex());
        academyLabel.setText(teacherBean.getOfAcademy());
        jobLabel.setText(teacherBean.getJobTitle());
    }
}
