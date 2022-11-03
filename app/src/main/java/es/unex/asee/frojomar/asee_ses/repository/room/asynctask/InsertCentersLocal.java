package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class InsertCentersLocal extends AsyncTask<Void,Void,Void> {

    public static final String TAG="InsertCentersLocal";

    private final Context context;
    private final List<Center> centers;

    public InsertCentersLocal(Context context,List<Center> centers){
        this.context=context;
        this.centers=centers;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db= AppDatabase.getDatabase(this.context);
        Log.i(TAG, "Insertando "+centers.size()+" en la BD local");
        for(int i=0; i<centers.size();i++){
            db.centersDAO().insert(centers.get(i));
            Log.i(TAG, "Insertado un centro");
        }
        return null;
    }
}
