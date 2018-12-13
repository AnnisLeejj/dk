package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 17:17
 * @Description
 */
public class AlipayInfo {
  /*  alipay string 支付宝账号
    alipaycipher string 支付宝密码
    zmImg string 芝麻信用截图地址(url编码utf-8)*/
    /**
     * alipay : abcde
     * alipaycipher : 987654321
     * zmImg : %2fImgs%2f20181213%2fbPUanq.jpg
     */

    private String alipay;
    private String alipaycipher;
    private String zmImg;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getAlipaycipher() {
        return alipaycipher;
    }

    public void setAlipaycipher(String alipaycipher) {
        this.alipaycipher = alipaycipher;
    }

    public String getZmImg() {
        return zmImg;
    }

    public void setZmImg(String zmImg) {
        this.zmImg = zmImg;
    }
}
