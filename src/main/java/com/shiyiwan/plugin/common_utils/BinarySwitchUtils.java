package com.shiyiwan.plugin.common_utils;

import java.util.ArrayList;
import java.util.List;

public class BinarySwitchUtils {

    public static boolean getState(int data, int index) {
        return (data >> index & 1) > 0;
    }

    public static int calculateState(int data, int index, boolean state) {
        if (state) {
            return 1 << index | data;
        } else {
            return ~(1 << index) & data;
        }
    }

    public static List<Integer> getStateList(int input){
        List<Integer> output = new ArrayList<>();
        while(input != 0){
            output.add(input%2);
            input/=2;
        }
        return output;
    }

}
