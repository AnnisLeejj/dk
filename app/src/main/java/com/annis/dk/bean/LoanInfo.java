package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 17:55
 * @Description
 */
public class LoanInfo {
/**
 loanAmount double 贷款金额
 loanTime bigint 贷款时间（时间戳）
 repaymentTime bigint 还款时间（时间戳）
 serviceCharge double 服务费
 isPayCost int 是否支付
 isPayLoan int 是否还款
 resultInfo text 贷款审核备注
 mloan int 是否已发放贷款(0:未发放 1:已发放)
 isnew int 是否审请了贷款（0：未申请 1：已申请）
 isPass int 审核状态（0：未审核 1：已审核未通过 2：已审核已通过）
 */
    /**
     * GetLoan接口
     * isnew = 1,ispass==0,ispaycost=0,mloan=0  用户申请贷款
     *  
     * isnew = 1,ispass==1,ispaycost=0,mloan=0  用户未通过贷款申请
     *  
     * isnew = 1,ispass==2,ispaycost=0,mloan=0  用户通过贷款申请 未确认已支付
     *  
     * isnew = 1,ispass==2,ispaycost=2,mloan=0  用户通过贷款申请 已确认已支付
     *  
     * isnew = 1,ispass==2,ispaycost=1,mloan=0  用户已支付费用
     *  
     * isnew = 1,ispass==2,ispaycost=1,mloan=1  后台已发放贷款
     *  
     * isnew = 0,ispass==0,ispayloan=1,mloan=0  用户已还款
     *  
     * isnew = 0,ispass==0,ispaycost=0,mloan=0  用户未申请贷款
     */
    private int id;
    private int uid;
    private String loanAmount;
    private String loanTime;
    private String repaymentTime;
    private String serviceCharge;
    private String isPayCost;
    private String isPayLoan;
    private String resultInfo;
    private String mloan;
    private String isNew;
    private String isPass;
    private String CSTelephone;
    private String CSWeChat;
    private String receiptAddress;
    private String feeAddress1;
    private String feeAddress2;
    private String feeAddress3;
    private String website;
    private String mykey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getIsPayCost() {
        return isPayCost;
    }

    public void setIsPayCost(String isPayCost) {
        this.isPayCost = isPayCost;
    }

    public String getIsPayLoan() {
        return isPayLoan;
    }

    public void setIsPayLoan(String isPayLoan) {
        this.isPayLoan = isPayLoan;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getMloan() {
        return mloan;
    }

    public void setMloan(String mloan) {
        this.mloan = mloan;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

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