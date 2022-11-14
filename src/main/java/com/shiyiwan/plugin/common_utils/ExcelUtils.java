package com.shiyiwan.plugin.common_utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class ExcelUtils {

    public static Object[][] getDataFromString(String input) {
        String[] rows = input.split("\n");
        int rowCount = rows.length;
        int columnCount = rows[0].split("\n").length;
        Object[][] output = new Object[rowCount][columnCount];
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            String[] cells = row.split("\t");
            Object[] rowData = transferStringArrayToObjectArray(cells);
            output[i] = rowData;
        }
        return output;
    }

    private static Object[] transferStringArrayToObjectArray(String[] input) {
        int length = input.length;
        Object[] output = new Object[length];
        System.arraycopy(input, 0, output, 0, input.length);
        return output;
    }

    public static void fillDataIntoTable(Object[][] data, JTable table) {

        Object[] column = generateColumnName(data);
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, column);
        table.setModel(defaultTableModel);
    }

    private static Object[] generateColumnName(Object[][] data) {
        int length = data[0].length;
        return new Object[length];
    }

    public static Object[][] calculateDifferentData(Object[][] source1, Object[][] source2, int keyColumnNum1, int keyColumnNum2
            , int valueColumnNum1, int valueColumnNum2) {
        Object[][] exceptionNotice = new Object[1][1];
        //判断两个数据源size一致
        if (source1[0].length != source2[0].length) {
            exceptionNotice[0][0] = "size of two dataArrays must same";
            return exceptionNotice;
        }
        int rowCount1 = source1.length;
        int rowCount2 = source2.length;

        DataMapAndKeyIndex dataMapAndKeyIndex1 = transferDataToMap(source1, keyColumnNum1, valueColumnNum1);
        DataMapAndKeyIndex dataMapAndKeyIndex2 = transferDataToMap(source2, keyColumnNum2, valueColumnNum2);
        Map<String, String> dataMap1 = dataMapAndKeyIndex1.getDataMap();
        Map<String, String> dataMap2 = dataMapAndKeyIndex2.getDataMap();
        Map<String, Integer> keyIndexMap1 = dataMapAndKeyIndex1.getKeyIndexMap();
        Map<String, Integer> keyIndexMap2 = dataMapAndKeyIndex2.getKeyIndexMap();

        //判断是否有key重复的行
        Set<String> source1KeySet = dataMap1.keySet();
        Set<String> source2KeySet = dataMap2.keySet();
        if (source1KeySet.size() != rowCount1 || source2KeySet.size() != rowCount2) {
            exceptionNotice[0][0] = "key row have same data";
            return exceptionNotice;
        }
        HashSet<String> retainSet = new HashSet<>(source1KeySet);
        retainSet.retainAll(source2KeySet);
        int columnCount = source1[0].length;
        Object[][] calculateResult = new Object[rowCount1 + rowCount2 + 3][columnCount];

        List<Integer> source1OnlyRowNumList = new ArrayList<>();
        List<Integer> source2OnlyRowNumList = new ArrayList<>();
        List<Integer> differentRowNumList = new ArrayList<>();


        source1KeySet.stream().forEach(key -> {
            String value1 = dataMap1.get(key);
            String value2 = dataMap2.get(key);
            if (value2 == null) {
                source1OnlyRowNumList.add(keyIndexMap1.get(key));
            }else if(!value1.equals(value2)){
                differentRowNumList.add(keyIndexMap1.get(key));
            }
        });

        source2KeySet.stream().forEach(key -> {
            String value1 = dataMap1.get(key);
            if (value1 == null) {
                source2OnlyRowNumList.add(keyIndexMap2.get(key));
            }
        });

        int point = 0;
        if(source1OnlyRowNumList.size() != 0){
            calculateResult[point++][0] = "table1 only";
            for (Integer key : source1OnlyRowNumList) {
                calculateResult[point++] = source1[key];
            }
        }
        if(source2OnlyRowNumList.size() != 0){
            calculateResult[point++][0] = "table2 only";
            for (Integer key : source2OnlyRowNumList) {
                calculateResult[point++] = source2[key];
            }
        }
        if(differentRowNumList.size() != 0){
            calculateResult[point++][0] = "different ( display table1 data )";
            for (Integer key : differentRowNumList) {
                calculateResult[point++] = source1[key];
            }
        }
        return calculateResult;
    }

    private static DataMapAndKeyIndex transferDataToMap(Object[][] input, int keyColumnNum, int valueColumnNum) {
        DataMapAndKeyIndex dataMapAndKeyIndex = new DataMapAndKeyIndex();
        Map<String, String> dataMap = dataMapAndKeyIndex.getDataMap();
        Map<String, Integer> keyIndexMap = dataMapAndKeyIndex.getKeyIndexMap();

        int rowCount = input.length;
        for (int i = 0; i < rowCount; i++) {
            dataMap.put(input[i][keyColumnNum - 1].toString(), input[i][valueColumnNum - 1].toString());
            keyIndexMap.put(input[i][keyColumnNum - 1].toString(), i);
        }
        return dataMapAndKeyIndex;
    }

    static class DataMapAndKeyIndex {
        private Map<String, String> dataMap = new HashMap<>();
        private Map<String, Integer> keyIndexMap = new HashMap<>();

        public Map<String, String> getDataMap() {
            return dataMap;
        }

        public Map<String, Integer> getKeyIndexMap() {
            return keyIndexMap;
        }
    }

}
