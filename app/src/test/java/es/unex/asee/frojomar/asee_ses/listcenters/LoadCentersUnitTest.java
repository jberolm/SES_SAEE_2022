package es.unex.asee.frojomar.asee_ses.listcenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadCentersUnitTest {

    @Mock
    LoadCenters loadCenters;

    @Before
    public void setup () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCenters () throws ExecutionException, InterruptedException {
        //we created a list of centers
        List<Center> centers = new ArrayList<>();
        centers.add(createCenter(1));
        centers.add(createCenter(2));

        //mock the list of sites as a response to the get method
        when(loadCenters.get()).thenReturn(centers);

        /*we use get, instead of running a normal asynctask (LoadCenters is an asynctask), because
        we don't care if the entire asynctask is executed as a linear task, in the same thread*/
        assertEquals(loadCenters.get(),centers);
        assertNotEquals(loadCenters.get(), null);
        assertFalse(loadCenters.get().isEmpty());
        assertTrue(loadCenters.get().size()==2);
    }

    public static Center createCenter(Integer id){
        Center c = new Center();
        c.setId(id);
        c.setAddress("address");
        c.setName("name");
        c.setLat(-6.13);
        c.setLong(39.31);
        return c;
    }
}
