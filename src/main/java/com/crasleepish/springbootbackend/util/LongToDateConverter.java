package com.crasleepish.springbootbackend.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Date;

public class LongToDateConverter extends StdConverter<Long, Date> {
    @Override
    public Date convert(Long aLong) {
        return new Date(1000 * aLong);
    }
}
