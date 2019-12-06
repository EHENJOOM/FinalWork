package com.zhk.email;

import com.zhk.constant.Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author 赵洪苛
 * @date 2019/11/26 18:58
 * @description email发送器
 */
public class DefaultEmailSender implements EmailSender {

    private Message message;

    private Session session;

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
    }

    @Override
    public <T> void setEmailContent(T emailBean) throws MessagingException {
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Config.SEND_EMAIL_ACCOUNT));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(((EmailBean)emailBean).getReceiveAccount() + ((EmailBean)emailBean).getSuffix()));
        message.setSubject(((EmailBean)emailBean).getSubject());
        message.setText(((EmailBean)emailBean).getContent());
        message.setSentDate(((EmailBean)emailBean).getDate());
        message.saveChanges();
    }

    @Override
    public void sendMessage() throws MessagingException {
        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
        transport.connect(Config.SEND_EMAIL_ACCOUNT, Config.SEND_EMAIL_PASSWORD);
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
        message = null;
    }
}
