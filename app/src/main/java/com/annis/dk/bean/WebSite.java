package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 19:28
 * @Description
 */
public class WebSite {
/*
    CSTelephone string 客服电话
    CSWeChat string 客服微信
    receiptAddress string 收款二维码地址


    用户支付时根据额度选择的三种地址
    额度在用户表里面的mlimit(1,2,3档)
    服务费的二维码
    feeAddress1 string 费用支付二维码1地址
    feeAddress2 string 费用支付二维码2地址
    feeAddress3 string 费用支付二维码3地址
    website string 接口地址
    mykey string
*/


    /**
     * CSTelephone : 1413131355555
     * CSWeChat : 1413131355555
     * receiptAddress : 1413131355556
     * feeAddress1 : 1413131355555
     * feeAddress2 : 1413131355555
     * feeAddress3 : 1413131355555
     * website : http://xxx.com
     * mykey : 0
     */

    private String CSTelephone;
    private String CSWeChat;
    private String receiptAddress;
    private String feeAddress1;
    private String feeAddress2;
    private String feeAddress3;
    private String website;
    private String mykey;

    public String getCSTelephone() {
        return CSTelephone;
    }

    public void setCSTelephone(String CSTelephone) {
        this.CSTelephone = CSTelephone;
    }

    public String getCSWeChat() {
        return CSWeChat;
    }

    public void setCSWeChat(String CSWeChat) {
        this.CSWeChat = CSWeChat;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getFeeAddress1() {
        return feeAddress1;
    }

    public void setFeeAddress1(String feeAddress1) {
        this.feeAddress1 = feeAddress1;
    }

    public String getFeeAddress2() {
        return feeAddress2;
    }

    public void setFeeAddress2(String feeAddress2) {
        this.feeAddress2 = feeAddress2;
    }

    public String getFeeAddress3() {
        return feeAddress3;
    }

    public void setFeeAddress3(String feeAddress3) {
        this.feeAddress3 = feeAddress3;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMykey() {
        return mykey;
    }

    public void setMykey(String mykey) {
        this.mykey = mykey;
    }
}
