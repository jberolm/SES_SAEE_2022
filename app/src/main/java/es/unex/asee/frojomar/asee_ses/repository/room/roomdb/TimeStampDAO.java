package es.unex.asee.frojomar.asee_ses.repository.room.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.TimeStamp;

@Dao
public interface TimeStampDAO {

    @Query("SELECT * FROM TimeStamp")
    public List<TimeStamp> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(TimeStamp item);

    @Update
    public void update(TimeStamp item);

    @Query("DELETE FROM TimeStamp")
    public void deleteAll();

}
