package fr.pandonia.hub.api.utils;

import java.text.DateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final Duration MONTH_DURATION = Duration.ofDays(30);

    public static String format(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(date);
    }
}
