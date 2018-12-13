package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 16:51
 * @Description
 */
public class IDCardEntity {
    /*id int 身份信息id
    uid int 用户账号id
    positive string 正面照 （url编码utf-8）
    back string 背面照 （url编码utf-8）
    hold string 手持照 （url编码utf-8）*/
    /**
     * id : 5
     * uid : 5
     * positive : %2fImgs%2f20181213%2fbPUanq.jpg
     * back : %2fImgs%2f20181213%2fbPUanq.jpg
     * hold : %2fImgs%2f20181213%2fbPUanq.jpg
     */

    private String id;
    private String uid;
    private String positive;
    private String back;
    private String hold;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }
}
