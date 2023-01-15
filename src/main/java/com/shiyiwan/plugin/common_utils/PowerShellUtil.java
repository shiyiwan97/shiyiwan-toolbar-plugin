package com.shiyiwan.plugin.common_utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PowerShellUtil {

    private static final Map<Integer,PowerShell> context = new HashMap<>();

    private static final Set<Integer> contextIndexSet = context.keySet();

    public static int creatAPowerShell(){
        Process powerShell;
        try {
            powerShell = Runtime.getRuntime().exec("PowerShell.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer index = getCurrentUnUsedIndex();

        return index;
    }

    private static Integer getCurrentUnUsedIndex() {
        int size = contextIndexSet.size();
        List<Integer> contextIndexList = contextIndexSet.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            if (contextIndexList.get(i) != i) {
                return i;
            }
        }
        return size;
    }

}
