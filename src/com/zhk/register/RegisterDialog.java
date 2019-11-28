package com.zhk.register;

import com.zhk.constant.Config;
import com.zhk.login.LoginBean;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2019/11/26 20:18
 * @description 注册或忘记密码窗口
 */
public class RegisterDialog extends JFrame implements RegisterView {
    private JTextField accountTextField;
    private JTextField verifyCodeTextField;
    private JButton sendVerifyButton;
    private JTextField passwordTextField;
    private JButton okButton;
    private JLabel titleLabel;
    private JPanel contentPanel;
    private JRadioButton studentRadio;
    private JRadioButton teacherRadio;

    private RegisterPresenter presenter = new RegisterPresenter();

    private int status = Config.STUDENT_LOGIN;

    public RegisterDialog(int type) {
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 300);
        initView(type);
        presenter.attachView(this);
    }

    private void initView(int type) {
        if (type == Config.REGISTER_DIALOG) {
            setTitle("注册");
            titleLabel.setText("注册");
            studentRadio.setVisible(true);
            teacherRadio.setVisible(true);
        } else if (type == Config.FORGET_PASSWORD_DIALOG) {
            setTitle("忘记密码");
            titleLabel.setText("忘记密码");
            studentRadio.setVisible(false);
            teacherRadio.setVisible(false);
        }

        ButtonGroup buttonGroup = new ButtonGroup();
        studentRadio.addActionListener(event -> status = Config.STUDENT_LOGIN);
        teacherRadio.addActionListener(event -> status = Config.TEACHER_LOGIN);
        buttonGroup.add(studentRadio);
        buttonGroup.add(teacherRadio);

        sendVerifyButton.addActionListener(event -> {
            String account = accountTextField.getText();
            if (account.isEmpty()) {
                showMessage("账号不能为空！");
                return;
            }
            presenter.sendVerifyCode(type, account);
        });
        okButton.addActionListener(event -> {
            String account = accountTextField.getText();
            String verify = verifyCodeTextField.getText();
            String password = passwordTextField.getText();

            if (account.isEmpty()) {
                showMessage("账号不能为空！");
            }
            if (verify.isEmpty()) {
                showMessage("验证码不能为空！");
            }
            if (password.isEmpty()) {
                showMessage("密码不能为空！");
            }

            presenter.verify(type, verify, new LoginBean(account, password, status));
        });
    }

    @Override
    public int showConfirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "提示", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void toRegister(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> presenter.register(loginBean));
    }

    @Override
    public void toUpdatePassword(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> presenter.updatePassword(loginBean));
    }

    @Override
    public void toLogin(LoginBean loginBean) {

    }

}
