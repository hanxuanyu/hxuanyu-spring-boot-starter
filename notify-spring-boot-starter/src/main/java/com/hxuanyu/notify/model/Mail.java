package com.hxuanyu.notify.model;

/**
 * 邮件
 *
 * @author hxuanyu
 */
public class Mail {
    private String from;
    private String to;
    private String cc;
    private String subject;
    private String content;

    public Mail() {
    }

    public Mail(String from, String to, String cc, String subject, String content) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.content = content;
    }

    /**
     * 不传入发送者时使用默认配置好的发件人
     *
     * @param to      收件人
     * @param cc      抄送人
     * @param subject 主题
     * @param content 邮件内容
     */
    public Mail(String to, String cc, String subject, String content) {
        this.from = null;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.content = content;
    }


    /**
     * 不传入发送者时使用默认配置好的发件人
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 邮件内容
     */
    public Mail(String to, String subject, String content) {
        this.from = null;
        this.to = to;
        this.cc = to;
        this.subject = subject;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailBean{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
