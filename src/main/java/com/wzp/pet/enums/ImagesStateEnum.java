package com.wzp.pet.enums;

public enum ImagesStateEnum {
    OFFLINE(-1,"非法操作"),SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),EMPTY(-1002,"图片为空");
    private int state;
    private String stateiInfo;

    ImagesStateEnum(int state, String stateiInfo) {
        this.state = state;
        this.stateiInfo = stateiInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateiInfo() {
        return stateiInfo;
    }

    public static ImagesStateEnum stateOf(int index){
        for(ImagesStateEnum state:values()){
            if(state.getState()==index){
                return state;
            }
        }
        return null;
    }
}
