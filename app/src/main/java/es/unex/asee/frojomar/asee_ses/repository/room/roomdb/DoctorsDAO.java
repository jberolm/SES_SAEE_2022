package es.unex.asee.frojomar.asee_ses.repository.room.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Doctor;

@Dao
public interface DoctorsDAO {

    @Query("SELECT * FROM Doctors")
    public LiveData<List<Doctor>> getAll();

    @Query("SELECT * FROM Doctors WHERE centerId=:centerId")
    LiveData<List<Doctor>> getByCenter(Integer centerId);

    @Query("SELECT * FROM Doctors WHERE id=:id")
    public LiveData<Doctor> getById(Integer id);

    @Query("SELECT * FROM Doctors WHERE id=:id LIMIT 1")
    public List<Doctor> existDataId(Integer id);

    @Query("SELECT * FROM Doctors WHERE centerId=:centerId LIMIT 1")
    public List<Doctor> existDataCenter(Integer centerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Doctor item);

    @Update
    public void update(Doctor item);

    @Query("DELETE FROM Doctors")
    public void deleteAll();

}
