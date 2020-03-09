package com.jeesite.tio.utils;

import java.nio.ByteBuffer;

/**
 * 通讯工具类
 *
 * @author Administrator
 */
public final class MessageDataUtils {
    private MessageDataUtils() {
    }

    /**
     * 输入字符串根据hex编码转换数组并将数组编译成BCC加密码
     */
    public static final String get_BBC(String string) {
        byte hex[] = hexStr2Byte(string);
        return getBCC(hex);
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static final String convertStringToHex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * hex16进制转字符
     */
    public static final String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * hex10进制转16进制解码
     */
    public static final String bytesToHexString(byte[] body) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < body.length; i++) {
            int v = body[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 获取BBC异或校验码
     */
    public static final String getBCC(byte[] data) {
        //System.out.println("长度:"+data.length);
        String ret = "";
        byte BCC[] = new byte[1];
//        System.out.println();
        for (int i = 0; i < data.length; i++) {
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        String hex = Integer.toHexString(BCC[0] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }

    /**
     * 正确转换为HEX进行串口通讯函数
     */
    public static final byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

    /**
     * 获取补位16进制数据
     *
     * @param data
     * @return String
     */
    public static final String getCoverDataHex(String data) {

        String i = Integer.toHexString(Integer.valueOf(data));
        if (i.length() < 1)
            return "0" + i;
        else
            return i;
    }

    /**
     * 获取补位10进制数据
     *
     * @param data
     * @return String
     */
    public static final String getCoverDataString(String data) {
        if (data.length() < 2)
            return "0" + data;
        else
            return data;
    }

}
