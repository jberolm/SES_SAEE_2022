package es.unex.asee.frojomar.asee_ses.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.model.TimeStamp;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCities;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.InsertCitiesLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.LoadCitiesLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.TimeStampDAO;

public class CitiesData {

    private static CitiesData instance;

    private CitiesData() {
    }

    public static CitiesData getInstance() {
        if (instance == null) {
            return new CitiesData();
        } else {
            return instance;
        }
    }
    public LiveData<List<City>> getAllCities(final Context context){

        LiveData<List<City>> data=new LoadCitiesLocal(context).getAllCities();

        Executor executor= new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                new Thread(command).start();
            }
        };
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(dbEmpty(context) || dbOlder(context)){
                    cargarDatos(context);
                }
            }
        });

        return data;
    }

    public LiveData<City> getCityById(final Integer id,final Context context){

        LiveData<City> data=new LoadCitiesLocal(context).getById(id);

        Executor executor= new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                new Thread(command).start();
            }
        };
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(dbEmpty(context) || dbOlder(context)){
                    cargarDatos(context);
                }
            }
        });

        return data;
    }

    private void cargarDatos(final Context context){
        final LoadCities loadCities= new LoadCities(new LoadCities.LoadCitiesListener() {
            @Override
            public void onPostExecute(List<City> cities) {
                InsertCitiesLocal insertCities= new InsertCitiesLocal(context, cities);
                insertCities.execute((Void) null);
                Executor executor= new Executor() {
                    @Override
                    public void execute(@NonNull Runnable command) {
                        new Thread(command).start();
                    }
                };
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                            actualizarTimeStamp(context);
                    }
                });
            }

            @Override
            public void onCancelled() {

            }
        });
        loadCities.execute((Void) null);
    }


    private boolean dbEmpty(Context context){
        return !new LoadCitiesLocal(context).existData();
    }

    private boolean dbOlder(Context context){
        TimeStampDAO tsDao= AppDatabase.getDatabase(context).timestampDAO();

        List<TimeStamp> tsList= tsDao.getAll();
        if(tsList!=null){
            if(tsList.size()>0){
                long diffInMillies=Math.abs(new Date().getTime()-tsList.get(0).getTimestampCities().getTime());
                long diff= TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff<1){
                    Log.i("CitiesData", "Se actualizaron los datos hace menos de un día");
                    return false;
                }
            }
        }
        return true;
    }

    private void actualizarTimeStamp(Context context){
        TimeStampDAO tsDao= AppDatabase.getDatabase(context).timestampDAO();

        List<TimeStamp> tsList= tsDao.getAll();
        if(tsList!=null){
            if(tsList.size()>0){
                tsList.get(0).setTimestampCities(new Date());
                tsDao.insert(tsList.get(0));
            }
        }
    }
}