package com.shiyiwan.plugin.common_utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class PowerShell {

//    private final Process process;

    private final BufferedReader bufferedReader;

    private final BufferedWriter bufferedWriter;

    public PowerShell(Process process) {
//        this.process = process;
        this.bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
}
