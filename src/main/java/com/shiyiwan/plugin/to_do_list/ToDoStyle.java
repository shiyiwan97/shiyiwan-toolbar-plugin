package com.shiyiwan.plugin.to_do_list;

import com.shiyiwan.plugin.common_utils.BitSwitchUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoStyle {

    private static final Map<Integer, String> styleMap;

    static {
        styleMap = new HashMap<>();
        styleMap.put(0,"text-decoration:line-through;");//删除线
        styleMap.put(1,"background:yellow;");//背景色
    }

    public static Map<Integer, String> getStyle(){
        return styleMap;
    };

    public static String generateCssStyleFromState(int state){
        List<Integer> stateList = BitSwitchUtils.getStateList(state);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stateList.size(); i++) {
            if(stateList.get(i) == 1){
                stringBuilder.append(styleMap.get(i));
            }
        }
        return stringBuilder.toString();
    }

}
