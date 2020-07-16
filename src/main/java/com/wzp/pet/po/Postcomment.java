package com.wzp.pet.po;

public class Postcomment {
    private Integer PostcommentId;
    private Integer postId;
    private Integer UserId;
    private String Content;
    private String Time;

    public Integer getPostcommentId() {
        return PostcommentId;
    }

    public void setPostcommentId(Integer postcommentId) {
        PostcommentId = postcommentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
