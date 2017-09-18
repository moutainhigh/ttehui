package com.mocentre.tehui.ntc.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 通知邮件实体
 *
 * Created by wangxueying on 17/08/08
 */
public class NtcMail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 通知类型（tx:退款通知）**/
	String noticeType;

	/** 邮件地址 **/
	String mail;

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
