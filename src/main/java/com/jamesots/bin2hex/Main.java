package com.jamesots.bin2hex;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("bin2hex <filename>");
            return;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(args[0]);
            try {
                final Bin2Hex bin2Hex = new Bin2Hex();
                bin2Hex.setAddress(0x100);
                int b;
                while ((b = fileInputStream.read()) != -1) {
                    bin2Hex.addByte(b);
                    if (bin2Hex.size() == 32) {
                        System.out.print(bin2Hex.getRecord());
                        System.out.print("\r\n");
                    }
                }
                if (bin2Hex.size() > 0) {
                    System.out.print(bin2Hex.getRecord());
                    System.out.print("\r\n");
                }
                System.out.print(bin2Hex.endFile());
                System.out.print("\r\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
