package com.shiyiwan.plugin;

import com.shiyiwan.plugin.common_utils.ExcelUtils;
import com.shiyiwan.plugin.common_utils.SystemUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPanel {
    private JTable table1;
    private JButton pasteButton1;
    private JPanel panel;
    private JTable table2;
    private JButton pasteButton2;
    private JTable table3;
    private JButton compareButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public ToolbarPanel(){

        pasteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = SystemUtils.getDataFromSystemClipBoard();
                Object[][] dataArray = ExcelUtils.getDataFromString(data);
                ExcelUtils.fillDataIntoTable(dataArray,table1);
            }
        });

    }

    public JPanel getPanel(){
        return panel;
    }

}
