package es.unex.asee.frojomar.asee_ses.repository.room.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;

@Dao
public interface CentersDAO {

    @Query("SELECT * FROM Centers")
    public LiveData<List<Center>> getAll();

    @Query("SELECT * FROM Centers WHERE cityId=:cityId")
    LiveData<List<Center>> getByCity(Integer cityId);

    @Query("SELECT * FROM Centers WHERE id=:id")
    public LiveData<Center> getById(Integer id);

    @Query("SELECT * FROM Centers LIMIT 1")
    public List<Center> existData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Center item);

    @Update
    public void update(Center item);

    @Query("DELETE FROM Centers")
    public void deleteAll();




}
