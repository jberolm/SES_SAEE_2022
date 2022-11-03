package es.unex.asee.frojomar.asee_ses.addappointment;

import org.junit.Test;

import java.lang.reflect.Field;

import es.unex.asee.frojomar.asee_ses.model.Appointment;

import static org.junit.Assert.assertEquals;

public class AppointmentUnitTest {

    @Test
    public void setDateTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "12/12/2019";
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setDate(value);
        final Field field = instance.getClass().getDeclaredField("date");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setDescriptionTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "Dolor de cabeza";
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setDescription(value);
        final Field field = instance.getClass().getDeclaredField("description");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setIdTest() throws NoSuchFieldException, IllegalAccessException {
        Integer value = 1;
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setId(value);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setPersonIdTest() throws NoSuchFieldException, IllegalAccessException {
        Integer value = 2;
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setPersonId(value);
        final Field field = instance.getClass().getDeclaredField("personId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }


    @Test
    public void setTelephoneTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "666666666";
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setTelephone(value);
        final Field field = instance.getClass().getDeclaredField("telephone");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setTimeTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "12:00:00";
        es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        instance.setDate(value);
        final Field field = instance.getClass().getDeclaredField("date");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }


    @Test
    public void getDateTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("date");
        field.setAccessible(true);
        field.set(instance, "12/12/2019");

        //when
        final String result = instance.getDate();

        //then
        assertEquals("field wasn't retrieved properly", result, "12/12/2019");
    }

    @Test
    public void getDescriptionTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("description");
        field.setAccessible(true);
        field.set(instance, "Dolor de cabeza");

        //when
        final String result = instance.getDescription();

        //then
        assertEquals("field wasn't retrieved properly", result, "Dolor de cabeza");
    }

    @Test
    public void getIdTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(instance, new Integer(1));

        //when
        final Integer result = instance.getId();

        //then
        assertEquals("field wasn't retrieved properly", result, new Integer(1));
    }

    @Test
    public void getPersonIdTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("personId");
        field.setAccessible(true);
        field.set(instance, new Integer(1));

        //when
        final Integer result = instance.getPersonId();

        //then
        assertEquals("field wasn't retrieved properly", result, new Integer(1));
    }

    @Test
    public void getTelephoneTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("telephone");
        field.setAccessible(true);
        field.set(instance, "666666666");

        //when
        final String result = instance.getTelephone();

        //then
        assertEquals("field wasn't retrieved properly", result, "666666666");
    }

    @Test
    public void getTimeTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Appointment instance = new es.unex.asee.frojomar.asee_ses.model.Appointment();
        final Field field = instance.getClass().getDeclaredField("time");
        field.setAccessible(true);
        field.set(instance, "12:00:00");

        //when
        final String result = instance.getTime();

        //then
        assertEquals("field wasn't retrieved properly", result, "12:00:00");
    }
}
