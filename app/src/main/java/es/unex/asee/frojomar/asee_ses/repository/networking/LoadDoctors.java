package es.unex.asee.frojomar.asee_ses.repository.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Doctor;

public class LoadDoctors extends AsyncTask<Void, Void, List<Doctor>> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_PATH_CENTERS="centers";
    private static final String RESOURCE_PATH_DOCTORS="doctors";
    private static final String TAG = "LoadDoctors";

     public interface LoadDoctorsListener{
        public void onPostExecute(List<Doctor> doctors);
        public void onCancelled();
    }

    private final LoadDoctorsListener callbacks;
    private final Integer id_center;

    public LoadDoctors(Integer id_center, LoadDoctorsListener callbacks){
        Log.i(TAG, "Definido un LoadDoctors para el centro "+id_center);
        this.id_center=id_center;
        this.callbacks=callbacks;
    }

    @Override
    protected List<Doctor> doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        //Load upcoming movies
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_PATH_CENTERS,id_center.toString(),RESOURCE_PATH_DOCTORS});
        result = NetworkUtils.getJSONResponse(queryURL);

        Log.i(TAG, "Cargando los doctores de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToList(result);
        }
        Log.i(TAG, "Resultado obtenido. Lista vacía");
        return new ArrayList<Doctor>();
    }



    private List<Doctor> jsonToList(JSONObject responseObject) {
        List<Doctor> result= new ArrayList<Doctor>();
        Log.i(TAG, "Convirtiendo el resultado a lista de objetos");

        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            JSONArray doctors = responseObject.getJSONArray("doctors");
            Log.i(TAG, "Longitud de la respuesta: "+doctors.length());

            // Iterate over doctors list
            for (int idx = 0; idx < doctors.length(); idx++) {

                // Get single center data - a Map
                JSONObject doctor_json = (JSONObject) doctors.get(idx);

                // Summarize doctors data as a string and add it to
                // result
                ObjectMapper mapper = new ObjectMapper();
                Doctor doctor = mapper.readValue(doctor_json.toString(), Doctor.class);
                result.add(doctor);
                Log.i(TAG, "Mapeado un doctor y añadido: "+doctor.toString());

            }
        } catch (JSONException e) {
            Log.e(TAG, "Error por JSONEXception");
            e.printStackTrace();
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
        return result;
    }

    @Override
    protected void onPostExecute(final List<Doctor> doctors) {
        callbacks.onPostExecute(doctors);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}
