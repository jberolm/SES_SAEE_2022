package es.unex.asee.frojomar.asee_ses.listcenters.unittest;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.CentersData;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;
import es.unex.asee.frojomar.asee_ses.viewmodel.ListCentersViewModel;

@RunWith(MockitoJUnitRunner.class)
public class ListCentersViewModelUnitTest {

    private final String TAG = this.getClass().getSimpleName();

    @Mock
    CentersData centersData;
    @Mock
    CitiesData citiesData;
    private ListCentersViewModel viewModel;
    @Mock
    Observer<List<Center>> observer;
    @Mock
    Application app;

    //It will tell JUnit to force tests to be executed synchronously, especially when using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void getViewModel(){
        app = mock(Application.class);
        centersData = mock(CentersData.class);
        citiesData = mock(CitiesData.class);

    }

    @Test
    public void testNullCenters() throws InterruptedException {
        LiveData<List<Center>> response = new androidx.lifecycle.MutableLiveData<List<Center>>();
        when(centersData.getAllCenters(app.getApplicationContext())).thenReturn(response);
        viewModel = new ListCentersViewModel(app, centersData, citiesData, "Todas");
        viewModel.getCenters().observeForever(observer);
        assertNotNull(viewModel.getCenters());
        assertTrue(viewModel.getCenters().hasObservers());
        List<Center> returned =  LiveDataTestUtils.getValue(viewModel.getCenters());
        assertTrue(returned==null);
    }

    @Test
    public void testWithoutCenters() throws InterruptedException {
        List<Center> centros = new ArrayList<>();
        MutableLiveData<List<Center>> response = new androidx.lifecycle.MutableLiveData<List<Center>>();
        response.setValue(centros);
        when(centersData.getAllCenters(app.getApplicationContext())).thenReturn(response);
        viewModel = new ListCentersViewModel(app, centersData, citiesData, "Todas");
        viewModel.getCenters().observeForever(observer);
        assertNotNull(viewModel.getCenters());
        assertTrue(viewModel.getCenters().hasObservers());
        List<Center> returned =  LiveDataTestUtils.getValue(viewModel.getCenters());
        assertTrue(returned.size()==0);
    }

    @Test
    public void testWithCenters() throws InterruptedException {
        List<Center> centros = createCenters();
        MutableLiveData<List<Center>> response = new androidx.lifecycle.MutableLiveData<List<Center>>();
        response.setValue(centros);
        when(centersData.getAllCenters(app.getApplicationContext())).thenReturn(response);
        viewModel = new ListCentersViewModel(app, centersData, citiesData, "Todas");
        viewModel.getCenters().observeForever(observer);
        assertNotNull(viewModel.getCenters());
        assertTrue(viewModel.getCenters().hasObservers());
        List<Center> returned =  LiveDataTestUtils.getValue(viewModel.getCenters());
        assertTrue(returned.equals(centros));
    }

    @After
    public void tearDown() throws Exception {
        centersData = null;
        citiesData = null;
        viewModel = null;
    }


    private List<Center> createCenters(){
        List<Center> centros = new ArrayList<>();
        Center center1 = new Center();
        center1.setId(1);
        Center center2 = new Center();
        center2.setId(2);
        centros.add(center1);
        centros.add(center2);
        return centros;
    }
}
