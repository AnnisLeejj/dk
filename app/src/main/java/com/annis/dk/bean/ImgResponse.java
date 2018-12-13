package com.annis.dk.bean;

/**
 * @author Lee
 * @date 2018/12/13 19:31
 * @Description
 */
public class ImgResponse {
//    state 状态
//    img 图片地址（相对路径经过url编码）

    /**
     * state : 200
     * img : %2fImgs%2f20181211%2fHhueCS.jpg
     */

    private String state;
    private String img;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
