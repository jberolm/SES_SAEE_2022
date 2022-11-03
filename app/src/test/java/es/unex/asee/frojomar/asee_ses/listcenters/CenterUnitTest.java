package es.unex.asee.frojomar.asee_ses.listcenters;


import org.junit.Test;

import java.lang.reflect.Field;

import es.unex.asee.frojomar.asee_ses.model.Center;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CenterUnitTest {

    @Test
    public void setAddressTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "Avenida Salamanca";
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setAddress(value);
        final Field field = instance.getClass().getDeclaredField("address");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setB64ImageTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "b'dvrvsvfrtbevfe'";
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setB64Image(value);
        final Field field = instance.getClass().getDeclaredField("b64Image");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setCityIdTest() throws NoSuchFieldException, IllegalAccessException {
        Integer value = 1;
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setCityId(value);
        final Field field = instance.getClass().getDeclaredField("cityId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setIdTest() throws NoSuchFieldException, IllegalAccessException {
        Integer value = 1;
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setId(value);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setLatTest() throws NoSuchFieldException, IllegalAccessException {
        Double value = 6.012132;
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setLat(value);
        final Field field = instance.getClass().getDeclaredField("lat");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void set_longTest() throws NoSuchFieldException, IllegalAccessException {
        Double value = -39.43443534;
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setLong(value);
        final Field field = instance.getClass().getDeclaredField("_long");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setNameTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "Centro de salud de Caceres";
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setName(value);
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setTelephoneTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "927510000";
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setTelephone(value);
        final Field field = instance.getClass().getDeclaredField("telephone");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void setTelephoneUrgTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "927510001";
        es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        instance.setTelephoneUrg(value);
        final Field field = instance.getClass().getDeclaredField("telephoneUrg");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), value);
    }

    @Test
    public void getAddressTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("address");
        field.setAccessible(true);
        field.set(instance, "Avenida Salamanca");

        //when
        final String result = instance.getAddress();

        //then
        assertEquals("field wasn't retrieved properly", result, "Avenida Salamanca");
    }

    @Test
    public void getB64ImageTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("b64Image");
        field.setAccessible(true);
        field.set(instance, "b'asdsdfdfdsdsdfsd'");

        //when
        final String result = instance.getB64Image();

        //then
        assertEquals("field wasn't retrieved properly", result, "b'asdsdfdfdsdsdfsd'");
    }

    @Test
    public void getCityIdTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("cityId");
        field.setAccessible(true);
        field.set(instance, new Integer(1));

        //when
        final Integer result = instance.getCityId();

        //then
        assertEquals("field wasn't retrieved properly", result, new Integer(1));
    }

    @Test
    public void getIdTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(instance, new Integer(1));

        //when
        final Integer result = instance.getId();

        //then
        assertEquals("field wasn't retrieved properly", result, new Integer(1));
    }

    @Test
    public void getLatTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("lat");
        field.setAccessible(true);
        field.set(instance, new Double(39.23234343));

        //when
        final Double result = instance.getLat();

        //then
        assertEquals("field wasn't retrieved properly", result,  new Double(39.23234343));
    }

    @Test
    public void get_longTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("_long");
        field.setAccessible(true);
        field.set(instance, new Double(-1.121243));

        //when
        final Double result = instance.getLong();

        //then
        assertEquals("field wasn't retrieved properly", result, new Double(-1.121243));
    }

    @Test
    public void getNameTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(instance, "Centro de salud de Caceres");

        //when
        final String result = instance.getName();

        //then
        assertEquals("field wasn't retrieved properly", result, "Centro de salud de Caceres");
    }

    @Test
    public void getTelephoneTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("telephone");
        field.setAccessible(true);
        field.set(instance, "927510000");

        //when
        final String result = instance.getTelephone();

        //then
        assertEquals("field wasn't retrieved properly", result, "927510000");
    }

    @Test
    public void getTelephoneUrgTest() throws NoSuchFieldException, IllegalAccessException {
        final es.unex.asee.frojomar.asee_ses.model.Center instance = new es.unex.asee.frojomar.asee_ses.model.Center();
        final Field field = instance.getClass().getDeclaredField("telephoneUrg");
        field.setAccessible(true);
        field.set(instance, "927510001");

        //when
        final String result = instance.getTelephoneUrg();

        //then
        assertEquals("field wasn't retrieved properly", result, "927510001");
    }


    @Test
    public void equalsTest(){
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Nombre1");

        Center center2 = new Center();
        center2.setId(2);
        center2.setName("Nombre2");

        Center center3 = new Center();
        center3.setId(1);
        center3.setName("Nombre3");

        assertEquals(center1, center1);
        assertNotEquals(center1, center2);
        assertEquals(center1, center3);

    }

}
