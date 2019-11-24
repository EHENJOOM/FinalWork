package com.zhk.login;

import com.zhk.main.student.StudentDialog;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

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

    private LoginPresenter loginPresenter = new LoginPresenter();

    public LoginDialog() {
        initView();
        loginPresenter.attachView(this);
    }

    private void initView() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    }

    @Override
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "警告", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void login(LoginBean loginBean) {
        // 退出消费者线程，使用事件分发者线程更新UI
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            StudentDialog studentDialog = new StudentDialog(loginBean);
            studentDialog.setSize(800, 600);
            studentDialog.setLocationRelativeTo(null);
            studentDialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        // 使用美化包进行界面美化
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginDialog dialog = new LoginDialog();
        dialog.setSize(350, 280);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("毕业论文课题管理系统");
        dialog.setVisible(true);
    }

}
