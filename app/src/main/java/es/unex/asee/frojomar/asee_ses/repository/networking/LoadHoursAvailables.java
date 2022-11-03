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


import es.unex.asee.frojomar.asee_ses.model.Appointment;

public class LoadHoursAvailables extends AsyncTask<Void, Void, List<String>> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_DOCTORS_PATH="doctors";
    private static final String RESOURCE_DATE_PATH="date";
    private static final String RESOURCE_AVAILABLES_PATH="availables";
    private static final String TAG="LoadHoursAvailables";

    public interface LoadHoursListener{
        public void onPostExecute(List<String> hours);
        public void onCancelled();
    }

    private Integer doctor_id;
    private String date;

    private final LoadHoursListener callbacks;

    public LoadHoursAvailables(Integer id, String date, LoadHoursListener callbacks){
        this.doctor_id=id;
        this.date=date;
        this.callbacks=callbacks;
    }

    @Override
    protected List<String> doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        //Load appointments filtering by person
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_DOCTORS_PATH,doctor_id.toString(),
                        RESOURCE_DATE_PATH, date, RESOURCE_AVAILABLES_PATH});
        result = NetworkUtils.getJSONResponse(queryURL);


        Log.i(TAG, "Cargando las horas disponibles de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToList(result);
        }
        Log.i(TAG, "Resultado obtenido. Lista vacía");
        return new ArrayList<String>();
    }

    private List<String> jsonToList(JSONObject responseObject) {
        List<String> result= new ArrayList<String>();
        Log.i(TAG, "Convirtiendo el resultado a lista de objetos");

        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            JSONArray hours = responseObject.getJSONArray("availables");
            Log.i(TAG, "Longitud de la respuesta: "+hours.length());

            // Iterate over earthquakes list
            for (int idx = 0; idx < hours.length(); idx++) {

                // Get single center data - a Map
                String hour_json = (String) hours.get(idx);
                Log.i(TAG, "Hora json "+idx+": "+hour_json);

                result.add(hour_json);
                Log.i(TAG, "Mapeada una cita y añadida: "+hour_json);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Error por JSONEXception");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(final List<String> hours) {
        callbacks.onPostExecute(hours);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}
