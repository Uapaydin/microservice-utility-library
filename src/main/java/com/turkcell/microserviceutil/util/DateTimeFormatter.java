package com.turkcell.microserviceutil.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 13:24
 */
public class DateTimeFormatter {
    private DateTimeFormatter(){

    }
    public static final DateFormat longDateTimeStampFormatter;
    public static final DateFormat shortDateTimeStampFormatter;
    public static final DateFormat shortDateStampFormatter;

    static {
        longDateTimeStampFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
        shortDateTimeStampFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        shortDateStampFormatter = new SimpleDateFormat("yyyy.MM.dd");
    }
}
