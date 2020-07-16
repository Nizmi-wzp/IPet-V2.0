package com.wzp.pet.po;

import java.util.Date;

public class Post {
    private Integer postId;
    private User postAuthor;
    private String postTitle;
    private String postContent;
    private Date postTime;
    public String toString() {
        return "Post{" +
                "post_id=" + postId +
                ", post_author=" + postAuthor.getUserName() +
                ", post_title='" + postTitle + '\'' +
                ", post_content='" + postContent + '\'' +
                ", post_time='" + postTime + '\'' +
                '}';
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public User getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(User postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
