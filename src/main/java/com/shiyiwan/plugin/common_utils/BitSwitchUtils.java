package com.shiyiwan.plugin.common_utils;

import java.util.ArrayList;
import java.util.List;

public class BitSwitchUtils {

    //todo 最高位位0的问题，最高位前再加一个1

    /**
     * 获取某一个开关的值（开关是从右开始数起的，左起第一个是第一个开关，第一个index=0）
     * @param data 开关十进制数字
     * @param index 第几个开关
     * @return
     */
    public static boolean getState(int data, int index) {
        return (data >> index & 1) > 0;
    }

    /**
     * 计算某一位变成开或关之后的十进制数字
     * @param data 初始的开关十进制数字
     * @param index 第几个开关
     * @param state 要变成什么状态
     * @return
     */
    public static int calculateState(int data, int index, boolean state) {
        if (state) {
            return 1 << index | data;
        } else {
            return ~(1 << index) & data;
        }
    }

    /**
     * 获取每一开关位
     * @param input
     * @return
     */
    public static List<Integer> getStateList(int input){
        List<Integer> output = new ArrayList<>();
        while(input != 0){
            output.add(input%2);
            input/=2;
        }
        return output;
    }

}
