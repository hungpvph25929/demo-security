package com.example.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class ConvertStringToDate {
    public static Date convert(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
