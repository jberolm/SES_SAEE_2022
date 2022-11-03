package es.unex.asee.frojomar.asee_ses.addappointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import java.util.Date;

import es.unex.asee.frojomar.asee_ses.utils.DateUtils;

public class DateUtilsUnitTest {


    @Test
    public void time2Date_isCorrect(){
        Long time = new Long(1586304000);
        String date = DateUtils.time2Date(time);
        assertEquals(date, "09:38:24:000");
    }

    @Test
    public void DateToString_isCorrect() {
        Date date = new Date(1586304000);
        String string = DateUtils.DateToString(date);
        assertEquals(string, "19-ene.-1970,09:38:24 a. m.");
        assertNotEquals(string, "31-ene.-1970,09:38:24 a. m.");
    }

    @Test
    public void StringToDate_isCorrect() {
        String string = "19-ene.-1970,09:38:24 a. m.";
        String string2 = "31-ene.-1970,09:38:24 a. m.";
        java.util.Date date = DateUtils.StringToDate(string);
        java.util.Date date2 = DateUtils.StringToDate(string2);
        assertEquals(date.getTime(), 1586304000);
        assertNotEquals(date2.getTime(), 1586304000);
    }

    @Test
    public void DateToString2_isCorrect() {
        Date date = new Date(1586304000);
        String string = DateUtils.DateToString2(date);
        assertEquals(string, "1970-01-19T09:38");
        assertNotEquals(string, "1970-01-31T09:38");
    }

    @Test
    public void StringToDate2_isCorrect() {
        String string = "1970-01-19T09:38";
        String string2 = "1970-01-31T09:38";
        Date date = DateUtils.StringToDate2(string);
        Date date2 = DateUtils.StringToDate2(string2);
        assertEquals(date.getTime(), 1586280000);
        assertNotEquals(date2.getTime(), 1586280000);
    }

}
