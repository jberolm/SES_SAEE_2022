package es.unex.asee.frojomar.asee_ses.repository.room.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.City;

@Dao
public interface CitiesDAO {

    @Query("SELECT * FROM Cities")
    public LiveData<List<City>> getAll();

    @Query("SELECT * FROM Cities WHERE id=:id")
    public LiveData<City> getById(Integer id);

    @Query("SELECT * FROM Cities LIMIT 1")
    public List<City> existData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(City item);

    @Update
    public void update(City item);

    @Query("DELETE FROM Cities")
    public void deleteAll();
}
