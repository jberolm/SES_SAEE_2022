package es.unex.asee.frojomar.asee_ses.listcenters.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.LoadCentersLocal;

@RunWith(MockitoJUnitRunner.class)
public class LoadCentersLocalUnitTest {

    @Mock
    LoadCentersLocal loadCentersLocal;

    // necessary to test the LiveData
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCenters () throws ExecutionException, InterruptedException {
        List<Center> centers = new ArrayList<>();
        Center center1 = createCenter(1);
        centers.add(center1);
        centers.add(createCenter(2));
        MutableLiveData<List<Center>> livecenters = new MutableLiveData<>();
        livecenters.postValue(centers);
        when(loadCentersLocal.getAllCenters()).thenReturn(livecenters);

        assertEquals(loadCentersLocal.getAllCenters().getValue(),centers);
        assertTrue(Objects.requireNonNull(loadCentersLocal.getAllCenters().getValue()).size() == 2);
        assertTrue(loadCentersLocal.getAllCenters().getValue().contains(center1));
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
