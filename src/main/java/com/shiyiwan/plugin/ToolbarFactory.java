package com.shiyiwan.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ToolbarFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ToolbarForm toolbarPanel = new ToolbarForm();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JBScrollPane jbScrollPane = new JBScrollPane(toolbarPanel.getPanel());
        jbScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        Content content = contentFactory.createContent(jbScrollPane, "ExcelUtils", false);
        content.setPreferredFocusableComponent(jbScrollPane);
        toolWindow.getContentManager().addContent(content);

    }

}
