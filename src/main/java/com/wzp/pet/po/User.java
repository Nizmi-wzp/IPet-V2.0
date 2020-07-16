package com.wzp.pet.po;

public class User {
    private Integer userId;
    private String userImage;
    private String userName;
    private String userPassword;
    private String telephone;
    private String lastLogin;
    private String lastSearch;
    private String maxTimesSearch;
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userImage='" + userImage + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", telephone='" + telephone + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(String lastSearch) {
        this.lastSearch = lastSearch;
    }

    public String getMaxTimesSearch() {
        return maxTimesSearch;
    }

    public void setMaxTimesSearch(String maxTimesSearch) {
        this.maxTimesSearch = maxTimesSearch;
    }
}
