package com.mocentre.tehui.sub.model;

import java.util.List;

/**
 * 专题全部信息实体类.
 * 
 * Created by yukaiji on 2016/12/02.
 */
public class SubjectAllMsg {

	private Subject subject;
	
	private List<SubjectGoods> subjectGoodsList;

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<SubjectGoods> getSubjectGoodsList() {
		return subjectGoodsList;
	}

	public void setSubjectGoodsList(List<SubjectGoods> subjectGoodsList) {
		this.subjectGoodsList = subjectGoodsList;
	}

	

}
