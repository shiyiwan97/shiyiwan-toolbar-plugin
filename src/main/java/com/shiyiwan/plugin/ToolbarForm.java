package com.shiyiwan.plugin;

import com.shiyiwan.plugin.util.ExcelUtils;
import com.shiyiwan.plugin.util.SystemUtils;

import javax.swing.*;

public class ToolbarForm {
    private JTable sourceTable1;
    private JTable sourceTable2;
    private JTable resultTable;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton paste1;
    private JButton paste2;
    private JButton calculate;
    private JPanel panel;

    private Object[][] storeDataArray1;
    private Object[][] storeDataArray2;


    public JPanel getPanel(){
        return panel;
    }

    public ToolbarForm() {
        paste1.addActionListener(e -> {
            String dataString = SystemUtils.getDataFromSystemClipBoard();
            Object[][] dataArray = ExcelUtils.getDataFromString(dataString);
            storeDataArray1 = dataArray;
            ExcelUtils.fillDataIntoTable(dataArray,sourceTable1);
        });
        paste2.addActionListener(e -> {
            String dataString = SystemUtils.getDataFromSystemClipBoard();
            Object[][] dataArray = ExcelUtils.getDataFromString(dataString);
            storeDataArray2 = dataArray;
            ExcelUtils.fillDataIntoTable(dataArray,sourceTable2);
        });
        calculate.addActionListener(e -> {
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
