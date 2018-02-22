package com.nexer.nexerbluetooth.main.aux;

import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.CursorAdapter;

import java.util.Formatter;

/**
 * Created by Paulo on 13/12/2016.
 */

public class HexUtils {

    public static String toHexString(int number) {
        String ret = Integer.toHexString(number).toUpperCase();
        switch (ret.length()) {
            case CursorAdapter.FLAG_AUTO_REQUERY /*1*/:
                ret = "000" + ret;
                break;
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                ret = "00" + ret;
                break;
            case NotificationCompat.WearableExtender.SIZE_MEDIUM /*3*/:
                ret = new StringBuilder("0").append(ret).toString();
                break;
        }
        return ret.toUpperCase();
    }

    //checks if it is empty
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static int hexToDecimal(String number){

        Integer outputDecimal = Integer.parseInt(number, 16);
        return  outputDecimal;

    }

    public static byte[] hexToBytes(String hexString) {

        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return sb.toString();
    }
}
