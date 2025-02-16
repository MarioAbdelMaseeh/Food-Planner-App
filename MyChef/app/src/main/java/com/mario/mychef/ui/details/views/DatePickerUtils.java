package com.mario.mychef.ui.details.views;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;

import java.util.Calendar;

public class DatePickerUtils {
    public static CalendarConstraints.Builder getConstraintsFromToday() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long today = calendar.getTimeInMillis();
        return new CalendarConstraints.Builder()
                .setStart(today)
                .setValidator(DateValidatorPointForward.from(today));
    }
}
