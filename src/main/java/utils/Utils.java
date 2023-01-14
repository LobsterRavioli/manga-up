package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    /*
    public static void main(String[] args) throws Exception {
        System.out.println(MD5("password1"));
        System.out.println(MD5("password2"));
        System.out.println(MD5("password3"));
        System.out.println(MD5("password4"));
    }
     */

    public static String hash(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(string.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
