package es.unex.asee.frojomar.asee_ses.repository.room.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.repository.room.roomdb.AppDatabase;

public class InsertDoctorsLocal extends AsyncTask<Void,Void,Void> {

    public static final String TAG="InsertDoctorsLocal";

    private final Context context;
    private final List<Doctor> doctors;

    public InsertDoctorsLocal(Context context,List<Doctor> doctors){
        this.context=context;
        this.doctors=doctors;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db= AppDatabase.getDatabase(this.context);
        Log.i(TAG, "Insertando "+doctors.size()+" en la BD local");
        for(int i=0; i<doctors.size();i++){
            db.doctorsDAO().insert(doctors.get(i));
            Log.i(TAG, "Insertado un doctor");
        }
        return null;
    }
}
