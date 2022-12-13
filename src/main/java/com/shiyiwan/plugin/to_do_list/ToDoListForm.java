package com.shiyiwan.plugin.to_do_list;

import com.shiyiwan.plugin.common_utils.BitSwitchUtils;
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
    private JButton importantButton;
    private JButton refreshButton;
    private JButton editButton;
    private final String localFileURI;
    private List<String> toDoList;
    private boolean isSelect = false;
    private boolean isDone = true;
    private boolean isImportant = true;

    public ToDoListForm() {

        localFileURI = System.getProperty("user.home") + "\\todoList.txt";
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        toDoList = getToDoListFromLocal();
        toDoList.stream().forEach(data -> defaultListModel.addElement(renderToHtml(data)));
        list.setModel(defaultListModel);

        deleteButton.addActionListener(e -> {
            int[] selectedIndices = list.getSelectedIndices();
            if (selectedIndices.length != 0) {
                ((DefaultListModel<String>) list.getModel()).removeRange(selectedIndices[0], selectedIndices[selectedIndices.length - 1]);
                for (int selectedIndex : selectedIndices) {
                    toDoList.remove(selectedIndex);
                }
                syncToLocal();
            }
        });
        deleteButton.registerKeyboardAction(deleteButton.getActionListeners()[0], KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        addAfterButton.addActionListener(e -> {
            String text = textField.getText();
            if (!"".equals(text) && text != null) {
                int[] selectedIndices = list.getSelectedIndices();
                int addIndex = selectedIndices.length == 0 ? 0 : selectedIndices[0];
                defaultListModel.add(addIndex, text);
                toDoList.add(addIndex, text + "$0");
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
                toDoList.add(addIndex, text + "$0");
                syncToLocal();
                textField.setText(null);
            }
        });

        list.addListSelectionListener(e -> {
            if (!isSelect) {
                addAfterButton.setText("Add After");
                addBeforeButton.setText("Add Before");
            }
            int[] selectedIndices = list.getSelectedIndices();
            if (selectedIndices.length == 0) {
                isSelect = false;
                addAfterButton.setText("Add First");
                addBeforeButton.setText("Add Last");
                return;
            }
            int firstSelectedIndex = selectedIndices[0];
            String firstSelectedData = toDoList.get(firstSelectedIndex);
            boolean doneState = getState(firstSelectedData, 0);
            doneButton.setText(doneState ? "Undone" : "Done");
            isDone = !doneState;

            boolean importState = getState(firstSelectedData, 1);
            importantButton.setText(importState ? "Unimportant" : "Important");
            isImportant = !importState;

        });

        doneButton.addActionListener(e -> {
            int[] selectedIndices = list.getSelectedIndices();
            int length = selectedIndices.length;
            if (length > 1) {
                int firstIndex = selectedIndices[0];
                int lastIndex = selectedIndices[length - 1];
                for (int i = firstIndex; i < lastIndex + 1; i++) {
                    toDoList.set(i, changeState(toDoList.get(i), 0, isDone));
                    defaultListModel.set(i, renderToHtml(toDoList.get(i)));
                }
            } else if (length == 1) {
                int index = selectedIndices[0];
                toDoList.set(index, changeState(toDoList.get(index), 0, isDone));
                defaultListModel.set(index, renderToHtml(toDoList.get(index)));
            }
            doneButton.setText(isDone ? "Undone" : "Done");
            isDone = !isDone;
            syncToLocal();
        });

        importantButton.addActionListener(e -> {
            int[] selectedIndices = list.getSelectedIndices();
            int length = selectedIndices.length;
            if (length > 1) {
                int firstIndex = selectedIndices[0];
                int lastIndex = selectedIndices[length - 1];
                for (int i = firstIndex; i < lastIndex + 1; i++) {
                    toDoList.set(i, changeState(toDoList.get(i), 1, isImportant));
                    defaultListModel.set(i, renderToHtml(toDoList.get(i)));
                }
            } else if (length == 1) {
                int index = selectedIndices[0];
                toDoList.set(index, changeState(toDoList.get(index), 1, isImportant));
                defaultListModel.set(index, renderToHtml(toDoList.get(index)));
            }
            importantButton.setText(isImportant ? "Unimportant" : "Important");
            isImportant = !isImportant;
            syncToLocal();
        });

        refreshButton.addActionListener(e -> {
            toDoList = getToDoListFromLocal();
            defaultListModel.clear();
            toDoList.stream().forEach(data -> defaultListModel.addElement(renderToHtml(data)));
            list.setModel(defaultListModel);
        });

        editButton.addActionListener(e -> {

            int firstSelectedIndex;
            int[] selectedIndices = list.getSelectedIndices();
            if (selectedIndices.length >= 1) {
                firstSelectedIndex = selectedIndices[0];
                String firstSelectedText = toDoList.get(firstSelectedIndex);
                String oldText = firstSelectedText.substring(0, firstSelectedText.lastIndexOf('$'));
                //parentComponent:以那个窗口为基础弹窗;message:输入框上方提示信息;title:弹框标题;messageType:消息类型;icon:图标;
                //selectionValues:多选框及内容;initialSelectionValue:输入框初始内容
                Object newTextObj = JOptionPane.showInputDialog(null, "", "Edit", JOptionPane.PLAIN_MESSAGE, null, null, oldText);
                String newText = newTextObj.toString();
                if (!newText.equals("") && !newText.equals(oldText)) {
                    defaultListModel.set(firstSelectedIndex, newText);
                    toDoList.set(firstSelectedIndex, newText + "$0");
                    syncToLocal();
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void syncToLocal() {
        try {
            SystemUtils.writeListToFile(localFileURI, toDoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getToDoListFromLocal() {
        List<String> todoList = new ArrayList<>();
        try {
            todoList = SystemUtils.readLineFromFile(localFileURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    public String renderToHtml(String input) {
        int index = input.lastIndexOf("$");
        String data = input.substring(0, index);
        int state = Integer.parseInt(input.substring(index + 1));
        String css = ToDoStyle.generateCssStyleFromState(state);
        return "<html><font style=\"" + css + "\">" + data + "</font><html>";
    }

    public String changeState(String input, int needChangedIndex, boolean needChangedState) {
        int index = input.lastIndexOf("$");
        String data = input.substring(0, index);
        int state = Integer.parseInt(input.substring(index + 1));
        int changedState = BitSwitchUtils.calculateState(state, needChangedIndex, needChangedState);
        return data + "$" + changedState;
    }

    public boolean getState(String input, int stateIndex) {
        int index = input.lastIndexOf("$");
        int state = Integer.parseInt(input.substring(index + 1));
        return BitSwitchUtils.getState(state, stateIndex);
    }


}
