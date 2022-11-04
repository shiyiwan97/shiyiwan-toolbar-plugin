package com.shiyiwan.plugin.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExcelUtils {

        public static Object[][] getDataFromString(String input){
            String[] rows = input.split("\n");
            int rowCount = rows.length;
            int columnCount = rows[1].split("\n").length;
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

        public static void fillDataIntoTable(Object[][] data, JTable table){

            Object[] column = {"ac", "bc", "cc"};
            DefaultTableModel defaultTableModel = new DefaultTableModel(data, column);
            table.setModel(defaultTableModel);
        }

}
