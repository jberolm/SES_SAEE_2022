package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class InsertCitiesLocal extends AsyncTask<Void,Void,Void> {

    public static final String TAG="InsertCitiesLocal";

    private final Context context;
    private final List<City> cities;

    public InsertCitiesLocal(Context context, List<City> cities){
        this.context=context;
        this.cities=cities;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db= AppDatabase.getDatabase(this.context);
        Log.i(TAG, "Insertando "+cities.size()+" en la BD local");
        for(int i=0; i<cities.size();i++){
            db.citiesDAO().insert(cities.get(i));
            Log.i(TAG, "Insertada una ciudad");
        }
        return null;
    }
}
