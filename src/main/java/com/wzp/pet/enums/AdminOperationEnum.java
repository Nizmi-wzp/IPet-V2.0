package com.wzp.pet.enums;

public enum AdminOperationEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"添加数小于1"),ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地帐号");
    private int state;
    private String stateInfo;

    private AdminOperationEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AdminOperationEnum stateOf(int index){
        for(AdminOperationEnum state:values()){
            if(state.getState()==index)
                return state;
        }
        return null;
    }
}
