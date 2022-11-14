package com.shiyiwan.plugin.common_utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
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

    public static boolean fileExist(String fileURI){
        return new File(fileURI).exists();
    }

    public static void createFile(String fileURI) throws IOException {
            new File(fileURI).createNewFile();
    }
}
