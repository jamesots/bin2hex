package com.jamesots.bin2hex;

import java.util.ArrayList;
import java.util.List;

public class Bin2Hex {
    private List<Integer> bytes = new ArrayList<>();
    private int address = 0;

    public void setAddress(int address) {
        this.address = address;
    }

    public String getRecord() {
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(':');
        sb.append(toHexString(bytes.size(), 2));
        sum += bytes.size();
        sb.append(toHexString(address, 4));
        sum += (address & 0xFF00) >> 8;
        sum += address & 0xFF;
        address += bytes.size();
        sb.append("00");
        for (Integer b : bytes) {
            sum += b;
            sb.append(toHexString(b, 2));
        }
        sb.append(toHexString((~(sum & 0xFF) + 1) & 0xFF, 2));

        bytes.clear();
        return sb.toString();
    }

    private String toHexString(int i, int len) {
        String hex = Long.toHexString(i).toUpperCase();
        if (len == 2) {
            if (hex.length() == 1) {
                return "0" + hex;
            } else {
                return hex;
            }
        } else {
            if (hex.length() == 1) {
                return "000" + hex;
            } else if (hex.length() == 2) {
                return "00" + hex;
            } else if (hex.length() == 3) {
                return "0" + hex;
            } else {
                return hex;
            }
        }
    }

    public void addByte(int b) throws Exception {
        if (bytes.size() == 0xFF) {
            throw new Exception("Too many bytes");
        }
        bytes.add(b);
    }

    public String endFile() {
        return ":00000001FF";
    }

    public int size() {
        return bytes.size();
    }
}
