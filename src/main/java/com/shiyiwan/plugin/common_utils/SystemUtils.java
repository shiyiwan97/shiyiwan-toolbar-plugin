package com.shiyiwan.plugin.common_utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


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

    public static boolean fileExist(String fileURI) {
        return new File(fileURI).exists();
    }

    public static void createFile(String fileURI) throws IOException {
        new File(fileURI).createNewFile();
    }

    public static void writeListToFile(String URI, List<String> dataList) throws IOException {
        File file = new File(URI);
        if (!file.exists()) {
            createFile(URI);
        } else {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            StringBuilder stringBuilder = new StringBuilder();
            dataList.stream().forEach(data -> {
                stringBuilder.append(data);
                stringBuilder.append("\n");
            });
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        }
    }

    public static List<String> readLineFromFile(String URI) throws IOException {
        List<String> dataList = new ArrayList<>();
        File file = new File(URI);
        if (!file.exists()) {
            SystemUtils.createFile(URI);
        } else {
            String data;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(URI)));
            while ((data = bufferedReader.readLine()) != null) {
                dataList.add(data);
            }
        }
        return dataList;
    }
}
