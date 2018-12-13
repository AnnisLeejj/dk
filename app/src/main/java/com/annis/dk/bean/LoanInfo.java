package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 17:55
 * @Description
 */
public class LoanInfo {
/*
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
     * loanAmount : 1000
     * loanTime : 1413131355555
     * repaymentTime : 1413131355556
     * serviceCharge : 50
     * isPayCost : 0
     * isPayLoan : 0
     * resultInfo : 审核通过
     * mloan : 0
     * isnew : 0
     * isPass : 0
     */

    private String loanAmount;
    private String loanTime;
    private String repaymentTime;
    private String serviceCharge;
    private int isPayCost;
    private int isPayLoan;
    private String resultInfo;
    private int mloan;
    private int isnew;
    private int isPass;

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

    public int getIsPayCost() {
        return isPayCost;
    }

    public void setIsPayCost(int isPayCost) {
        this.isPayCost = isPayCost;
    }

    public int getIsPayLoan() {
        return isPayLoan;
    }

    public void setIsPayLoan(int isPayLoan) {
        this.isPayLoan = isPayLoan;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getMloan() {
        return mloan;
    }

    public void setMloan(int mloan) {
        this.mloan = mloan;
    }

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }
}
