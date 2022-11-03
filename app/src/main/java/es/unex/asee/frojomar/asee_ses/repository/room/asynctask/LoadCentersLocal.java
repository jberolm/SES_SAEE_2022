//package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.util.List;
//
//import es.unex.asee.frojomar.asee_ses.model.Center;
//import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
//
//public class LoadCentersLocal extends AsyncTask<Void,Void,List<Center>> {
//
//    public static final String TAG="LoadCentersLocal";
//
//    public interface LoadCentersLocalListener {
//        public void onPostExecute(List<Center> centers);
//        public void onCancelled();
//    }
//    private final Context context;
//    private final LoadCentersLocalListener callbacks;
//    private final Integer cityId;
//
//    public LoadCentersLocal(Context context, LoadCentersLocalListener callbacks){
//        this.callbacks=callbacks;
//        this.context=context;
//        this.cityId=-1;
//    }
//
//    public LoadCentersLocal(Context context, Integer cityId, LoadCentersLocalListener callbacks){
//        this.callbacks=callbacks;
//        this.cityId=cityId;
//        this.context=context;
//    }
//
//    @Override
//    protected List<Center> doInBackground(Void... voids) {
//        Log.i(TAG, "Recuperando el centros de la BD local");
//        AppDatabase db= AppDatabase.getDatabase(this.context);
//        if(cityId==-1) {
//            return db.centersDAO().getAll();
//        }
//        else{
//            return db.centersDAO().getByCity(cityId);
//        }
//
//    }
//
//    @Override
//    protected void onPostExecute(List<Center> centers) {
//        if(centers==null){
//            Log.i(TAG, "No se han podido recuperar centros de la BD local");
//        }
//        else{
//            Log.i(TAG, "Recuperados "+centers.size()+" de la BD local");
//
//        }
//        callbacks.onPostExecute(centers);
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

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class LoadCentersLocal {

    public static final String TAG = "LoadCentersLocal";

    private final Context context;

    public LoadCentersLocal(Context context) {
        this.context = context;
    }


    public LiveData<List<Center>> getAllCenters() {
        Log.i(TAG, "Recuperando el centros de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.centersDAO().getAll();
    }

    public LiveData<List<Center>> getByCity(Integer cityId) {
        Log.i(TAG, "Recuperando el centros de la ciudad "+cityId+" de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.centersDAO().getByCity(cityId);
    }

    public LiveData<Center> getById(Integer id) {
        Log.i(TAG, "Recuperando el centro "+id+"de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.centersDAO().getById(id);
    }

    public boolean existData() {
        Log.i(TAG, "Comprobando si existen datos en la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return !db.centersDAO().existData().isEmpty();
    }
}