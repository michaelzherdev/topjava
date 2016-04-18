package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Mikhail on 18.04.2016.
 */
public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return TimeUtil.parseLocalTime(s);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
