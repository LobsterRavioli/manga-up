package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static void main(String[] args) throws Exception {
        System.out.println(hash("password1"));

    }


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



    public static Date parseDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(date);
            return startDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
