package com.zhk.mail;

/**
 * @author 赵洪苛
 * @date 2019/11/26
 * @description 发送email的单例枚举类
 */
public enum  EmailSenderEnum {

    /**
     * 单例实例
     */
    INSTANCE;

    private BaseEmailSender sender = new EmailSender();

    /**
     * 获取当前单例实例
     * @return 单例实例
     */
    public static EmailSenderEnum getInstance() {
        return INSTANCE;
    }

    public EmailSenderEnum init() {
        if (!sender.isInitial()) {
            sender.init();
        }
        return this;
    }

    public EmailSenderEnum setEmail(EmailBean emailBean) throws Exception {
        sender.setEmailContent(emailBean);
        return this;
    }

    public void sendMessage() throws Exception {
        sender.sendMessage();
    }

}
