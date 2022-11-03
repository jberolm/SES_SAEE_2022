package es.unex.asee.frojomar.asee_ses.repository.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


import es.unex.asee.frojomar.asee_ses.model.Doctor;

public class LoadDoctor extends AsyncTask<Void, Void, Doctor> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_PATH_DOCTORS="doctors";
    private static final String TAG = "LoadDoctor";

    public interface LoadDoctorListener{
        public void onPostExecute(Doctor doctor);
        public void onCancelled();
    }

    private final LoadDoctorListener callbacks;
    private final Integer id_doctor;

    public LoadDoctor(Integer id_doctor, LoadDoctorListener callbacks){
        Log.i(TAG, "Definido un LoadDoctor para el id "+id_doctor);
        this.id_doctor=id_doctor;
        this.callbacks=callbacks;
    }

    @Override
    protected Doctor doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        //Load upcoming movies
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_PATH_DOCTORS,id_doctor.toString()});
        result = NetworkUtils.getJSONResponse(queryURL);

        Log.i(TAG, "Cargando el doctor de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToObject(result);
        }
        Log.i(TAG, "Resultado obtenido. Lista vacía");
        return new Doctor();
    }



    private Doctor jsonToObject(JSONObject responseObject) {
        Log.i(TAG, "Convirtiendo el resultado a objeto");
        Doctor doctor= new Doctor();
        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            ObjectMapper mapper = new ObjectMapper();
            doctor = mapper.readValue(responseObject.toString(), Doctor.class);
            Log.i(TAG, "Mapeado el doctor "+doctor.getName());

        } catch (JsonParseException e) {
            Log.e(TAG, "Error por parseando el JSON");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            Log.e(TAG, "Error por Mapeando el JSON");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Error por IOException");
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    protected void onPostExecute(final Doctor doctor) {
        callbacks.onPostExecute(doctor);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}