package es.unex.asee.frojomar.asee_ses.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.model.TimeStamp;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadDoctor;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadDoctors;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.InsertDoctorsLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.asynctask.LoadDoctorsLocal;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.TimeStampDAO;

public class DoctorsData {

    private static DoctorsData instance;

    private DoctorsData() {
    }

    public static DoctorsData getInstance() {
        if (instance == null) {
            return new DoctorsData();
        } else {
            return instance;
        }
    }
    private LoadDoctors doctorsAPI;
    private LoadDoctor doctorAPI;




    public LiveData<Doctor> getDoctorById(final Integer id,final Context context){

        LiveData<Doctor> data=new LoadDoctorsLocal(context).getById(id);

        Executor executor= new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                new Thread(command).start();
            }
        };
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(existDbById(context,id) || dbOlder(context)){
                    cargarDatosById(id,context);
                }
            }
        });

        return data;
    }

    public LiveData<List<Doctor>> getDoctorsByCenter(final Context context,
                                                   final Integer centerId){

        LiveData<List<Doctor>> data=new LoadDoctorsLocal(context).getByCenter(centerId);

        Executor executor= new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                new Thread(command).start();
            }
        };
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(existDbByCenter(context,centerId) || dbOlder(context)){
                    cargarDatosByCenter(centerId,context);
                }
            }
        });

        return data;
    }


    private boolean existDbById(Context context, Integer id){
        return !new LoadDoctorsLocal(context).existDataId(id);
    }

    private boolean existDbByCenter(Context context, Integer centerId){
        return !new LoadDoctorsLocal(context).existDataCenter(centerId);
    }



    public void cargarDatosByCenter(final Integer centerId, final Context context) {
        doctorsAPI = new LoadDoctors(centerId, new LoadDoctors.LoadDoctorsListener() {
            @Override
            public void onPostExecute(List<Doctor> doctors) {
                InsertDoctorsLocal insertDoctors = new InsertDoctorsLocal(context, doctors);
                insertDoctors.execute((Void) null);
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
                doctorsAPI = null;
            }

            @Override
            public void onCancelled() {
                doctorsAPI = null;
            }
        });
        doctorsAPI.execute((Void) null);
    }

    public void cargarDatosById(final Integer id, final Context context) {
        doctorAPI = new LoadDoctor(id, new LoadDoctor.LoadDoctorListener() {
            @Override
            public void onPostExecute(Doctor doctor) {
                List<Doctor> doctors = new ArrayList<Doctor>();
                doctors.add(doctor);
                InsertDoctorsLocal insertDoctors = new InsertDoctorsLocal(context, doctors);
                insertDoctors.execute((Void) null);
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
                doctorAPI = null;
            }

            @Override
            public void onCancelled() {
                doctorAPI = null;
            }
        });
        doctorAPI.execute((Void) null);
    }

    private boolean dbOlder(Context context){
        TimeStampDAO tsDao= AppDatabase.getDatabase(context).timestampDAO();

        List<TimeStamp> tsList= tsDao.getAll();
        if(tsList!=null){
            if(tsList.size()>0){
                long diffInMillies=Math.abs(new Date().getTime()-tsList.get(0).getTimestampDoctors().getTime());
                long diff= TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff<1){
                    Log.i("DoctorsData", "Se actualizaron los datos hace menos de un día");
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
                tsList.get(0).setTimestampDoctors(new Date());
                tsDao.insert(tsList.get(0));
            }
        }
    }
}
