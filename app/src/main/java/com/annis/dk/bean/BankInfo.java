package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 17:20
 * @Description
 */
public class BankInfo {
//    bankcard string 银行卡用户名称(对应银行卡名称)
//    idnumber bigint 身份证号
//    savingscard string 储蓄卡号
//    phone string 预留手机号
    /**
     * bankcard : 张三
     * idnumber : 5100000000000
     * savingscard : 535435553434668
     * phone : 153100000000
     */

    private String bankcard;
    private String idnumber;
    private String savingscard;
    private String phone;

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getSavingscard() {
        return savingscard;
    }

    public void setSavingscard(String savingscard) {
        this.savingscard = savingscard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
