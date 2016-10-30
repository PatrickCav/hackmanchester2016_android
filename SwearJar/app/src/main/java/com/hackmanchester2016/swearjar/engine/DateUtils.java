package com.hackmanchester2016.swearjar.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by patrickc on 30/10/2016
 */
public class DateUtils {

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    }

    public static String formatDate(Date date){
        return dateFormat.format(date);
    }

}
