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
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(string);
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
