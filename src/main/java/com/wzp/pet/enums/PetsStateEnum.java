package com.wzp.pet.enums;

public enum PetsStateEnum {
    OFFLINE(-1, "非法操作"),  SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(-1002, "宠物为空");

    private int state;

    private String stateInfo;

    private PetsStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PetsStateEnum stateOf(int index) {
        for (PetsStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
