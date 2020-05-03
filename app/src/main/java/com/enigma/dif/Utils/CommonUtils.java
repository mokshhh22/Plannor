package com.enigma.dif.Utils;

import com.enigma.dif.Calendar.HolidayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    public static String getMonthTitleText(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM  yyyy");
        return sdf.format(date);
    }

    public static String getStringDateForDb(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatedSheetDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(date);
    }

    public static ArrayList<HolidayModel> filterHolidayByDay(ArrayList<HolidayModel> list, String day) {
        ArrayList<HolidayModel> hm = new ArrayList<>();
        for (HolidayModel obj: list) {
            if (obj.getIso().equals(day)) {
                hm.add(obj);
            }
        }
        return hm;
    }
}
