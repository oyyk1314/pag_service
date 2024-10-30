package com.pag.unit;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * 文件生成MD5
 */
public class MD5Generator {
    public static String generateMD5(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(filePath);
            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }

            byte[] mdBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdBytes.length; i++) {
                sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "d:\\data.pdf";
        String md5 = generateMD5(filePath);
        System.out.println("MD5 value: " + md5);
    }
}
