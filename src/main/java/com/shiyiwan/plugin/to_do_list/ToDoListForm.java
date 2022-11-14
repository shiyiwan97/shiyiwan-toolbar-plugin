package com.shiyiwan.plugin.to_do_list;

import com.shiyiwan.plugin.common_utils.SystemUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListForm {
    private JList<String> list;
    private JPanel panel;
    private JTextField textField;
    private JButton deleteButton;
    private JButton addAfterButton;
    private JButton addBeforeButton;
    private JButton doneButton;
    private final String localFileURI;

    public ToDoListForm() {

        localFileURI = System.getProperty("user.home") + "\\todoList.txt";
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        List<String> toDoList = getToDoListFromLocal();
        toDoList.stream().forEach(defaultListModel::addElement);
        list.setModel(defaultListModel);

        deleteButton.addActionListener(e -> {
            int[] selectedIndices = list.getSelectedIndices();
            if (selectedIndices.length != 0) {
                ((DefaultListModel<String>) list.getModel()).removeRange(selectedIndices[0], selectedIndices[selectedIndices.length - 1]);
                syncToLocal();
            }
        });
        deleteButton.registerKeyboardAction(deleteButton.getActionListeners()[0], KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        addAfterButton.addActionListener(e -> {
            String text = textField.getText();
            if (!"".equals(text) && text != null) {
                int[] selectedIndices = list.getSelectedIndices();
                int addIndex = selectedIndices.length == 0 ? defaultListModel.size() : selectedIndices[0];
                defaultListModel.add(addIndex, text);
                syncToLocal();
                textField.setText(null);
            }
        });

        addBeforeButton.addActionListener(e -> {
            String text = textField.getText();
            if (!"".equals(text) && text != null) {
                int[] selectedIndices = list.getSelectedIndices();
                int addIndex = selectedIndices.length == 0 ? defaultListModel.size() : selectedIndices[selectedIndices.length - 1] + 1;
                defaultListModel.add(addIndex, text);
                syncToLocal();
                textField.setText(null);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void syncToLocal() {
        Object[] toDoList = ((DefaultListModel<String>) list.getModel()).toArray();
        File file = new File(localFileURI);
        try {
            if (!file.exists()) {
                SystemUtils.createFile(localFileURI);
            } else {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                StringBuilder stringBuilder = new StringBuilder();
                for (Object data : toDoList) {
                    stringBuilder.append(data);
                    stringBuilder.append("\n");
                }
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getToDoListFromLocal() {
        List<String> todoList = new ArrayList<>();
        File file = new File(localFileURI);
        try {
            if (!file.exists()) {
                SystemUtils.createFile(localFileURI);
            } else {
                String data;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(localFileURI)));
                while ((data = bufferedReader.readLine()) != null) {
                    todoList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoList;
    }

}
