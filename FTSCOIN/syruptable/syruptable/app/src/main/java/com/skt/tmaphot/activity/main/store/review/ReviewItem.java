package com.skt.tmaphot.activity.main.store.review;

public class ReviewItem {
    private String loginImageUrl;
    private String nickname;
    private String content;
    private String sympathyImage;
    private String sympathy;

    public ReviewItem(String loginImageUrl, String nickname, String content, String sympathyImage, String sympathy) {
        this.loginImageUrl = loginImageUrl;
        this.nickname = nickname;
        this.content = content;
        this.sympathyImage = sympathyImage;
        this.sympathy = sympathy;
    }

    public String getLoginImageUrl() {
        return loginImageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public String getSympathyImage() {
        return sympathyImage;
    }

    public String getSympathy() {
        return sympathy;
    }
}
