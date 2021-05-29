package com.crasleepish.springbootbackend.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Date;

public class DateToLongConverter extends StdConverter<Date, Long> {
    @Override
    public Long convert(Date date) {
        return date.getTime() / 1000;
    }
}
