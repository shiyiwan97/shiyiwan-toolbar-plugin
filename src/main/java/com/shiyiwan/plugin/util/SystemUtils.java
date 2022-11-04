package com.shiyiwan.plugin.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SystemUtils {

    public static String getDataFromSystemClipBoard() {
        String data = "";
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = systemClipboard.getContents(null);
        try {
            data = (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException unsupportedFlavorException) {
            unsupportedFlavorException.printStackTrace();
        }
        return data;
    }
}
