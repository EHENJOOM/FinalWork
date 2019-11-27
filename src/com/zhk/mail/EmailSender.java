package com.zhk.mail;

import com.zhk.constant.Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author 赵洪苛
 * @date 2019/11/26
 * @description email发送器
 */
public class EmailSender implements BaseEmailSender {

    private Message message;

    private Session session;

    private boolean initial = false;

    private final Object lock = new Object();

    @Override
    public boolean isInitial() {
        return this.initial;
    }

    @Override
    public void init() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", Config.SEND_EMAIL_SMTP_HOST);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        // 根据配置创建会话对象, 用于和邮件服务器交互
        session = Session.getDefaultInstance(props);

        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        initial = true;
    }

    @Override
    public void setEmailContent(EmailBean emailBean) throws MessagingException {
        synchronized (lock) {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.SEND_EMAIL_ACCOUNT));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailBean.getReceiveAccount() + Config.MAIL_ACCOUNT_SUFFIX));
            message.setSubject(emailBean.getSubject());
            message.setText(emailBean.getContent());
            message.setSentDate(emailBean.getDate());
            message.saveChanges();
        }
    }

    @Override
    public void sendMessage() throws MessagingException {
        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
        transport.connect(Config.SEND_EMAIL_ACCOUNT, Config.SEND_EMAIL_PASSWORD);

        synchronized (lock) {
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());

            // 关闭连接
            transport.close();
            message = null;
        }
    }
}
