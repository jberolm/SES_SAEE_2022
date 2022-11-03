package es.unex.asee.frojomar.asee_ses.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.TimeStamp;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCenters;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.InsertCentersLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.LoadCentersLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.TimeStampDAO;

public class CentersData {

    private static CentersData instance;

    private CentersData(){}

    public static CentersData getInstance(){
        if(instance==null){
            return new CentersData();
        }
        else{
            return instance;
        }
    }

    public LiveData<List<Center>> getAllCenters(final Context context){

        LiveData<List<Center>> data=new LoadCentersLocal(context).getAllCenters();

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

    public LiveData<Center> getCenterById(final Integer id,final Context context){

        LiveData<Center> data=new LoadCentersLocal(context).getById(id);

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

    public LiveData<List<Center>> getCentersByCity(final Context context,final String city,
                                 final Integer cityId){

        LiveData<List<Center>> data=new LoadCentersLocal(context).getByCity(cityId);

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
        final LoadCenters loadCenters= new LoadCenters(new LoadCenters.LoadCentersListener() {
            @Override
            public void onPostExecute(List<Center> centers) {
                InsertCentersLocal insertCenters= new InsertCentersLocal(context, centers);
                insertCenters.execute((Void) null);
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
        loadCenters.execute((Void) null);
    }


    private boolean dbEmpty(Context context){
        return !new LoadCentersLocal(context).existData();
    }

    private boolean dbOlder(Context context){
        TimeStampDAO tsDao= AppDatabase.getDatabase(context).timestampDAO();

        List<TimeStamp> tsList= tsDao.getAll();
        if(tsList!=null){
            if(tsList.size()>0){
                long diffInMillies=Math.abs(new Date().getTime()-tsList.get(0).getTimestampCenters().getTime());
                long diff= TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff<1){
                    Log.i("CentersData", "Se actualizaron los datos hace menos de un día");
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
                tsList.get(0).setTimestampCenters(new Date());
                tsDao.insert(tsList.get(0));
            }
        }
    }
}
