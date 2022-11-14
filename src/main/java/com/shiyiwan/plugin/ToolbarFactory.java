package com.shiyiwan.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.shiyiwan.plugin.excel_utils.ExcelDataCompareForm;
import com.shiyiwan.plugin.to_do_list.ToDoListForm;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ToolbarFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ExcelDataCompareForm excelDataCompareForm = new ExcelDataCompareForm();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JBScrollPane jbScrollPane1 = new JBScrollPane(excelDataCompareForm.getPanel());
        jbScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        Content excelDataCompareContent = contentFactory.createContent(jbScrollPane1, "ExcelUtil", false);
//        content.setPreferredFocusableComponent(jbScrollPane1);
        toolWindow.getContentManager().addContent(excelDataCompareContent);

        ToDoListForm toDoListForm = new ToDoListForm();
        JBScrollPane jbScrollPane2 = new JBScrollPane(toDoListForm.getPanel());
        jbScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        Content toDoListContent = contentFactory.createContent(jbScrollPane2, "ToDoList", false);
        toDoListContent.setPreferredFocusableComponent(jbScrollPane2);
        toolWindow.getContentManager().addContent(toDoListContent);

    }

}
