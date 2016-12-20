package com.linbingcheng.example.easemob.common.enumtype;

/**
 * Created by bingchenglin on 2016/12/15.
 */
public enum TargetType {

    users(0),
    chatgroups(1),
    chatrooms(3);

    private int value;
    private TargetType(int value){
            this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public static TargetType fromTo(int value){
        TargetType[] values = TargetType.values();
        for(int i = 0;i < values.length; i++){
            if(values[i].value == value){
                return values[i];
            }
        }
        throw new IllegalArgumentException("类型不正确,不能识别为[" + value + "]的类型.");
    }

}
