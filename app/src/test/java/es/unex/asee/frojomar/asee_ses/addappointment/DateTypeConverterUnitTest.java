package es.unex.asee.frojomar.asee_ses.addappointment;

import org.junit.Test;


import java.util.Date;

import es.unex.asee.frojomar.asee_ses.utils.DateTypeConverter;

import static org.junit.Assert.*;


public class DateTypeConverterUnitTest {

    @Test
    public void fromTimestamp_isCorrect() {
        Long value = new Long(1586304000);
        Long value2 = new Long(1586389000);
        Date date = new Date(value);

        assertEquals(date, DateTypeConverter.fromTimestamp(value));
        assertNotEquals(date, DateTypeConverter.fromTimestamp(value2));
    }

    @Test
    public void dateToTimestamp_isCorrect() {
        Long value = new Long(1586304000);
        Long value2 = new Long(1586389000);
        Date date = new Date(value);

        assertEquals(value, DateTypeConverter.dateToTimestamp(date));
        assertNotEquals(value2, DateTypeConverter.dateToTimestamp(date));
    }
}