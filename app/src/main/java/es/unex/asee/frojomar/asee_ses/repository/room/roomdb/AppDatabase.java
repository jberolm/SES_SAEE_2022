package es.unex.asee.frojomar.asee_ses.repository.room.roomdb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.model.TimeStamp;

@Database(entities = {Center.class, Doctor.class, City.class, TimeStamp.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "ASEE_SES.db").allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3).build();
        }
        return instance;
    }

    static final Migration MIGRATION_2_3= new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'TimeStamp' ('id' INTEGER," +
                    " 'timestampCenters' INTEGER,'timestampCities' INTEGER,'timestampDoctors' INTEGER," +
                    " PRIMARY KEY ('id'))");
        }
    };

    public abstract CentersDAO centersDAO();

    public abstract DoctorsDAO doctorsDAO();

    public abstract CitiesDAO citiesDAO();

    public abstract TimeStampDAO timestampDAO();

}
