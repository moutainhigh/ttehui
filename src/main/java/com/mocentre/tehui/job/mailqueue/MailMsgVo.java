/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.mailqueue;

import java.io.Serializable;

/**
 * 类MallMsgVo.java的实现描述：邮件内容对象
 * 
 * @author sz.gong 2017年8月9日 下午2:51:24
 */
public class MailMsgVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1428143192971251290L;

    private String            type;

    private String            title;

    private String            content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
