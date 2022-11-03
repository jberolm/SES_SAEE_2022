//package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.util.List;
//
//import es.unex.asee.frojomar.asee_ses.model.City;
//import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;
//
//public class LoadCitiesLocal extends AsyncTask<Void,Void,List<City>> {
//
//    public static final String TAG="LoadCitiesLocal";
//
//    public interface LoadCitiesLocalListener {
//        public void onPostExecute(List<City> cities);
//        public void onCancelled();
//    }
//    private final Context context;
//    private final LoadCitiesLocalListener callbacks;
//
//    public LoadCitiesLocal(Context context, LoadCitiesLocalListener callbacks){
//        this.callbacks=callbacks;
//        this.context=context;
//    }
//
//
//    @Override
//    protected List<City> doInBackground(Void... voids) {
//        Log.i(TAG, "Recuperando las ciudades de la BD local");
//        AppDatabase db= AppDatabase.getDatabase(this.context);
//        return db.citiesDAO().getAll();
//    }
//
//    @Override
//    protected void onPostExecute(List<City> cities) {
//        if(cities==null){
//            Log.i(TAG, "No se han podido recuperar ciudades de la BD local");
//        }
//        else{
//            Log.i(TAG, "Recuperadas "+cities.size()+" ciudades de la BD local");
//
//        }
//        callbacks.onPostExecute(cities);
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

import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class LoadCitiesLocal {

    public static final String TAG = "LoadCitiesLocal";

    private final Context context;

    public LoadCitiesLocal(Context context) {
        this.context = context;
    }


    public LiveData<List<City>> getAllCities() {
        Log.i(TAG, "Recuperando las ciudades de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.citiesDAO().getAll();
    }


    public LiveData<City> getById(Integer id) {
        Log.i(TAG, "Recuperando la ciudad "+id+"de la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return db.citiesDAO().getById(id);
    }

    public boolean existData() {
        Log.i(TAG, "Comprobando si existen datos en la BD local");
        AppDatabase db = AppDatabase.getDatabase(this.context);
        return !db.citiesDAO().existData().isEmpty();
    }
}