package com.chk.wx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用于验证自设token对接微信平台
 * @author chk
 * @version 1.0
 * @date 2021-10-13 15:53
 */
public class SignUtil {
    /** 16进制数组 */
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    /**
     * 验证签名
     * @author chk
     * @param wxToken 服务器令牌
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     **/
    public static boolean checkSignature(String wxToken,String signature, String timestamp, String nonce) {
        String content = Stream.of(wxToken, timestamp, nonce).sorted().collect(Collectors.joining());
        MessageDigest md;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行 sha1 加密
            byte[] digest = md.digest(content.getBytes());
            tmpStr = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将 sha1 加密后的字符串可与 signature 对比，标识该请求来源于微信
        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }
    /**
     * 将字节数组转换为十六进制字符串
     * @author chk
     * @date 2021/10/13 15:21
     * @param bytes 字节数组
     **/
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
