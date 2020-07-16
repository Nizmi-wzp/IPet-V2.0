package com.wzp.pet.po;

public class Adoptpets {

    private Integer adopt_id;
    private int user_id;
    private int pet_id;
    private String adopt_reason;
    private String state;
    public int getAdopt_id() {
        return adopt_id;
    }

    public void setAdopt_id(int adopt_id) {
        this.adopt_id = adopt_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getAdopt_reason() {
        return adopt_reason;
    }

    public void setAdopt_reason(String adopt_reason) {
        this.adopt_reason = adopt_reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
