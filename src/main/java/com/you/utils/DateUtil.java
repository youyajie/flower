package com.you.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by yyj on 2018/4/26.
 */
public class DateUtil {
    public static String format(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedDate = localDate.format(formatter);
        return formattedDate;
    }

}
