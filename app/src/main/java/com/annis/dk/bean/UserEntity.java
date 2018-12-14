package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 16:40
 * @Description
 */
public class UserEntity {

/*    /id int 用户编号 (返回ID为0时新增数据失败)
    phone string 账号
    isChecIdentity int 是否认证身份证（0:未认证 1:已认证）
    isChecOperator int 是否认证运营商(固定为1)
    isChecAlipay int 是否认证支付宝（0:未认证 1:已认证）
    isChecBankCard int 是否认证银行卡（0:未认证 1:已认证）
    userHead string 用户头像地址
    limit int 用户额度（分三档 1 2 3 默认1）*/
    private String id;
    private long phone;
    private int isChecIdentity;
    private int isChecOperator;
    private int isChecAlipay;
    private int isChecBankCard;
    private String userHead;
    private int limit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getIsChecIdentity() {
        return isChecIdentity;
    }

    public void setIsChecIdentity(int isChecIdentity) {
        this.isChecIdentity = isChecIdentity;
    }

    public int getIsChecOperator() {
        return isChecOperator;
    }

    public void setIsChecOperator(int isChecOperator) {
        this.isChecOperator = isChecOperator;
    }

    public int getIsChecAlipay() {
        return isChecAlipay;
    }

    public void setIsChecAlipay(int isChecAlipay) {
        this.isChecAlipay = isChecAlipay;
    }

    public int getIsChecBankCard() {
        return isChecBankCard;
    }

    public void setIsChecBankCard(int isChecBankCard) {
        this.isChecBankCard = isChecBankCard;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
