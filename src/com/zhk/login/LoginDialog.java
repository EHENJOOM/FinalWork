package com.zhk.login;

import com.zhk.constant.Config;
import com.zhk.main.student.StudentDialog;
import com.zhk.main.teacher.TeacherDialog;
import com.zhk.register.RegisterDialog;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 赵洪苛
 * @date 2019//11/21 17:57
 * @description 登录窗口
 */
public class LoginDialog extends JFrame implements LoginView {
    private JPanel contentPane;
    private JTextField accountTxtField;
    private JTextField passwordTxtField;
    private JButton okButton;
    private JLabel registerLabel;
    private JLabel forgetLabel;

    private LoginPresenter loginPresenter = new LoginPresenter();

    public LoginDialog() {
        initView();
        loginPresenter.attachView(this);
    }

    private void initView() {
        setContentPane(contentPane);
        setSize(350, 280);
        setLocationRelativeTo(null);
        setTitle("毕业论文课题管理系统");
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okButton.setForeground(Color.WHITE);
        okButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        okButton.addActionListener(event -> {
            String account = accountTxtField.getText();
            String password = passwordTxtField.getText();

            if (account.isEmpty()) {
                showError("账号不能为空！");
                return;
            }
            if (password.isEmpty()) {
                showError("密码不能为空！");
                return;
            }

            loginPresenter.login(new LoginBean(account, password));
        });

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                RegisterDialog dialog = new RegisterDialog(Config.REGISTER_DIALOG);
                dialog.setVisible(true);
            }
        });
        forgetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                RegisterDialog dialog = new RegisterDialog(Config.FORGET_PASSWORD_DIALOG);
                dialog.setVisible(true);
            }
        });
    }

    @Override
    public void showError(String msg) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, msg, "警告", JOptionPane.ERROR_MESSAGE);
        });
    }

    @Override
    public void login(LoginBean loginBean) {
        // 退出消费者线程，使用事件分发者线程更新UI
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            if (loginBean.getType() == LoginBean.STUDENT) {
                StudentDialog studentDialog = new StudentDialog(loginBean);
                studentDialog.setVisible(true);
            } else {
                TeacherDialog teacherDialog = new TeacherDialog(loginBean);
                teacherDialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // 使用美化包进行界面美化
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible",false);
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginDialog dialog = new LoginDialog();
        dialog.setVisible(true);
    }

}
