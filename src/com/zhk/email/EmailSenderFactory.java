package com.zhk.email;

/**
 * @author 赵洪苛
 * @date 2019/11/28 17:27
 * @description 工厂模式，eamil发送器工厂。可根据自己情况，实现{@link EmailSender}接口，完成一个邮件发送器对象，然后创建静态工厂方法获取对象。
 */
public class EmailSenderFactory {

    /**
     * 静态工厂，创建{@code DefaultEmailSender}对象
     * @return Email发送器对象
     */
    public static EmailSender getDefaultEmailSender() {
        return new DefaultEmailSender();
    }

}
