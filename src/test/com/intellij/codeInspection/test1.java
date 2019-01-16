package com.intellij.codeInspection;

import java.io.*;

public class test1 {
    public String readFile() throws IOException {
        File file = new File("C:\\Users\\pankaj\\Desktop\\test1.txt");
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            builder.append(st);
        }
        return builder.toString();
    }
}
