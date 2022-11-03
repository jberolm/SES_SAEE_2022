package es.unex.asee.frojomar.asee_ses.listcenters.unittest;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.CentersDAO;


@RunWith(AndroidJUnit4.class)
public class CentersDAOUnitTest {

    // created instances for the DAO to test and our implementation of RoomDatabase
    private CentersDAO centersDAO;
    private AppDatabase db;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        //create a mock database, using our RoomDatabase implementation (AppDatabase.class)
        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        //take the dao from the created database
        centersDAO = db.centersDAO();
    }

    @After
    public void closeDb() throws IOException {
        //closing database
        db.close();
    }

    @Test
    public void writeCenterAndReadInDataBase() throws Exception {
        //create two centers
        Center center = createCenter(1);
        Center center2 = createCenter(2);
        //to insert the centers in the dao
        centersDAO.insert(center);
        centersDAO.insert(center2);

        //we recover the centers (in a LiveData)
        LiveData<List<Center>> livecenters = centersDAO.getAll();
        //we retrieve LiveData information with getValue() from LiveDataTestUtils
        List<Center> centers = LiveDataTestUtils.getValue(livecenters);
        //check that one of the elements is the first center
        assertTrue(centers.get(0).getId().equals(1) || centers.get(0).getId().equals(2));
        //check that one of the elements is the second center
        assertTrue(centers.get(1).getId().equals(1) || centers.get(1).getId().equals(2));
        //check that the same center does not return us twice
        assertFalse(centers.get(0).getId().equals(centers.get(1).getId()));
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