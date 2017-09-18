package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * Created by yukaiji on 2017/8/30.
 */
public class Supplier extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /** 供货商名称 */
    private String      title;
    /** 负责人 */
    private String      name;
    /** 产品数量 */
    private Integer     goodsCount;
    /** 账期（预付，月结，周结）*/
    private String      period;
    /** 客服电话 */
    private String      telephone;
    /** 邮箱 */
    private String      mail;
    /** 备注 */
    private String      note;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
