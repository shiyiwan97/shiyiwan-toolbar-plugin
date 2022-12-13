package com.shiyiwan.plugin.excel_utils;

import com.shiyiwan.plugin.common_utils.ExcelUtils;
import com.shiyiwan.plugin.common_utils.SystemUtils;

import javax.swing.*;

public class ExcelDataCompareForm {
    private JTable sourceTable1;
    private JTable sourceTable2;
    private JTable resultTable;
    private JTextField textField1;
    private JTextField textField2;
    private JButton pasteButton1;
    private JButton pasteButton2;
    private JButton calculateButton;
    private JPanel panel;

    private Object[][] storeDataArray1;
    private Object[][] storeDataArray2;


    public JPanel getPanel(){
        return panel;
    }

    public ExcelDataCompareForm() {
        pasteButton1.addActionListener(e -> {
            String dataString = SystemUtils.getDataFromSystemClipBoard();
            Object[][] dataArray = ExcelUtils.getDataFromString(dataString);
            storeDataArray1 = dataArray;
            ExcelUtils.fillDataIntoTable(dataArray,sourceTable1);
        });
        pasteButton2.addActionListener(e -> {
            String dataString = SystemUtils.getDataFromSystemClipBoard();
            Object[][] dataArray = ExcelUtils.getDataFromString(dataString);
            storeDataArray2 = dataArray;
            ExcelUtils.fillDataIntoTable(dataArray,sourceTable2);
        });
        calculateButton.addActionListener(e -> {
            Object[][] differentData = ExcelUtils.calculateDifferentData(storeDataArray1, storeDataArray2, 1, 1, 2, 2);
            ExcelUtils.fillDataIntoTable(differentData,resultTable);
        });
    }



    public void pasteClipBoardToTable(JTable target,Object[][] storeDataArray){
        String dataString = SystemUtils.getDataFromSystemClipBoard();
        Object[][] dataArray = ExcelUtils.getDataFromString(dataString);
        storeDataArray = dataArray;
        ExcelUtils.fillDataIntoTable(dataArray,target);
    }
}
