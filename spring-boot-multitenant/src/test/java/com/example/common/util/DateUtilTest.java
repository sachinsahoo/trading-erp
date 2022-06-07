package com.example.common.util;

import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DateUtilTest {

    @Test
    public void toLocalDateTimeTest() {
        Long timestamp = 1611730800l;
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(timestamp);
        LocalDateTime expected = LocalDateTime.of(2021, Month.JANUARY, 27, 1, 0);
        Assert.assertEquals(localDateTime, expected);


    }

    @Test
    public void getEpochTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.JANUARY, 27, 1, 0);
        Long timestamp = 1611730800l;
        Long epochTime = DateUtil.getEpochTime(localDateTime);
        Assert.assertEquals(epochTime, timestamp);
    }

    @Test
    public void getLongTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.JANUARY, 27, 0, 0);
        Long expected = DateUtil.getEpochTime(localDateTime);
        Long timestamp = 1611727200l;
        Long epochTime = DateUtil.getLong(Month.JANUARY, 27, 2021);
        Assert.assertEquals(epochTime, timestamp);
    }

    @Test
    public void getLDTTest() {
        LocalDateTime expected = LocalDateTime.of(2021, Month.JANUARY, 27, 00, 00);
        LocalDateTime actual = DateUtil.getLDT(Month.JANUARY, 27, 2021);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getLDTMMYYYTest() {
        LocalDateTime actual = DateUtil.getLDTMMYYYY(1, 2021);
        LocalDateTime expected = LocalDateTime.of(2021, Month.JANUARY, 01, 0, 0);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getYearMonthsTest() {
        List<String> actual = DateUtil.getYearMonths(1601528400l, 1611727200l);
        List<String> expected = new ArrayList<String>();
        expected.add("2020_10");
        expected.add("2020_11");
        expected.add("2020_12");
        expected.add("2021_1");
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getDaysTest() {
        List<LocalDateTime> actual = DateUtil.getDays(LocalDateTime.of(2021, Month.JANUARY, 25, 0, 0),
                LocalDateTime.of(2021, Month.JANUARY, 27, 0, 0));
        List<LocalDateTime> expected = new ArrayList<LocalDateTime>();
        expected.add(LocalDateTime.of(2021, Month.JANUARY, 25, 0, 0));
        expected.add(LocalDateTime.of(2021, Month.JANUARY, 26, 0, 0));
        Assert.assertEquals(expected, actual);


    }

    @Test
    public void getLabelfromYMTest(){
        String actual = DateUtil.getLabelfromYM("2021_01");
        String expected = "Jan 2021";
        Assert.assertEquals(expected, actual);
    }

}






