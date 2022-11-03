package es.unex.asee.frojomar.asee_ses.listcenters.unittest;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.CentersData;

public class CentersDataUnitTest {

    // necessary to test the LiveData
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    // create a variable to retrieve the CentersData instance
    //NOTE: CentersData encloses the origin of the data. We do not know if it is local or API REST data.
    private CentersData centersData;

    @Before
    public void getInstance() {
        //get an instance of the CentersData class
        centersData = CentersData.getInstance();
    }


    @Test
    public void writeCenterAndReadInDataBase() throws Exception {

        //we recover the centers (in a LiveData)
        LiveData<List<Center>> livecenters = centersData.getAllCenters(getInstrumentation().getTargetContext());
        //we retrieve LiveData information with getValue() from LiveDataTestUtils
        List<Center> centers = LiveDataTestUtils.getValue(livecenters);
        //check that centers is not null
        assertTrue(centers != null);
        /*we can not to check data, because we don't know their origin.
        We can not mock internal classes of CentersData in this test, without add dependency
            of other class (which must be tested in tests intended for themselves).*/
    }

    //auxiliar method to create more easily centers to the tests
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