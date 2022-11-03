//package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.util.List;
//
//import es.unex.asee.frojomar.asee_ses.model.Doctor;
//import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
//
//public class LoadDoctorsLocal extends AsyncTask<Void,Void,List<Doctor>> {
//
//    public static final String TAG="LoadDoctorsLocal";
//
//    public interface LoadDoctorsLocalListener {
//        public void onPostExecute(List<Doctor> doctors);
//        public void onCancelled();
//    }
//    private final Context context;
//    private final LoadDoctorsLocalListener callbacks;
//    private final Integer centerId;
//
//    public LoadDoctorsLocal(Context context, LoadDoctorsLocalListener callbacks){
//        this.callbacks=callbacks;
//        this.context=context;
//        this.centerId=-1;
//    }
//
//    public LoadDoctorsLocal(Context context, Integer centerId, LoadDoctorsLocalListener callbacks){
//        this.callbacks=callbacks;
//        this.centerId=centerId;
//        this.context=context;
//    }
//
//    @Override
//    protected List<Doctor> doInBackground(Void... voids) {
//        Log.i(TAG, "Recuperando los doctores de la BD local");
//        AppDatabase db= AppDatabase.getDatabase(this.context);
//        if(centerId==-1) {
//            return db.doctorsDAO().getAll();
//        }
//        else{
//            return db.doctorsDAO().getByCenter(centerId);
//        }
//
//    }
//
//    @Override
//    protected void onPostExecute(List<Doctor> doctors) {
//        if(doctors==null){
//            Log.i(TAG, "No se han podido recuperar doctores de la BD local");
//        }
//        else{
//            Log.i(TAG, "Recuperados "+doctors.size()+"doctores de la BD local");
//
//        }
//        callbacks.onPostExecute(doctors);
//    }
//
//    @Override
//    protected void onCancelled() {
//        callbacks.onCancelled();
//    }
//}
package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class LoadDoctorsLocal {

    public static final String TAG = "LoadDoctorsLocal";

    private final Context context;

    public LoadDoctorsLocal(Context context) {
        this.context = context;
    }


    public LiveData<List<Doctor>> getAllDoctors() {
        Log.i(TAG, "Recuperando los doctores de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.doctorsDAO().getAll();
    }

    public LiveData<List<Doctor>> getByCenter(Integer centerId) {
        Log.i(TAG, "Recuperando los doctores del centro "+centerId+" de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.doctorsDAO().getByCenter(centerId);
    }

    public LiveData<Doctor> getById(Integer id) {
        Log.i(TAG, "Recuperando el doctor "+id+"de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.doctorsDAO().getById(id);
    }

    public boolean existDataId(Integer id) {
        Log.i(TAG, "Comprobando si existen datos en la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return !db.doctorsDAO().existDataId(id).isEmpty();
    }

    public boolean existDataCenter(Integer centerId) {
        Log.i(TAG, "Comprobando si existen datos en la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return !db.doctorsDAO().existDataCenter(centerId).isEmpty();
    }


}