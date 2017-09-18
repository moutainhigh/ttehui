package com.mocentre.tehui.common.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件
 * Created by 王雪莹 on 2017/8/9.
 */
public class SendMailUtil {

    // 发件人的账号
    private final static String MAIL_USER = "yukaiji@mocentre.com";
    // 访问SMTP服务时需要提供的密码
    private final static String MAIL_PASSWORD = "Maniykj0703";

    /**
     * 群发邮件
     * @param addressList 发送地址列表
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public static void sendmail(List<String> addressList,String subject,String content)  {
        try {
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.exmail.qq.com");
            props.put("mail.user", MAIL_USER);
            props.put("mail.password", MAIL_PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            final int num = addressList.size();
            InternetAddress[] addressArr = new InternetAddress[num];
            for (int i = 0; i <num; i++) {
                addressArr[i] = new InternetAddress(addressList.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, addressArr);
            // 设置邮件标题
            message.setSubject(subject);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * @param address 邮件地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public static void sendmail(String address,String subject,String content)  {
        try {
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.exmail.qq.com");
            // 发件人的账号
            props.put("mail.user", MAIL_USER);
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", MAIL_PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            // 设置邮件标题
            message.setSubject(subject);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
