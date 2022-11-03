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

import es.unex.asee.frojomar.asee_ses.model.City;

public class LoadCities extends AsyncTask<Void, Void, List<City>> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_PATH="cities";
    private static final String TAG="LoadCities";

    public interface LoadCitiesListener{
        public void onPostExecute(List<City> cities);
        public void onCancelled();
    }

    private final LoadCitiesListener callbacks;

    public LoadCities(LoadCitiesListener callbacks){
        this.callbacks=callbacks;
    }

    @Override
    protected List<City> doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        //Load upcoming movies
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_PATH});
        result = NetworkUtils.getJSONResponse(queryURL);

        Log.i(TAG, "Cargando las ciudades de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToList(result);
        }
        Log.i(TAG, "Resultado obtenido. Lista vacía");
        return new ArrayList<City>();
    }

    private List<City> jsonToList(JSONObject responseObject) {
        List<City> result= new ArrayList<City>();
        Log.i(TAG, "Convirtiendo el resultado a lista de objetos");

        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            JSONArray cities = responseObject.getJSONArray("cities");
            Log.i(TAG, "Longitud de la respuesta: "+cities.length());

            // Iterate over earthquakes list
            for (int idx = 0; idx < cities.length(); idx++) {

                // Get single center data - a Map
                JSONObject city_json = (JSONObject) cities.get(idx);

                // Summarize center data as a string and add it to
                // result
                ObjectMapper mapper = new ObjectMapper();
                City city = mapper.readValue(city_json.toString(), City.class);
                result.add(city);
                Log.i(TAG, "Mapeada una ciudad y añadida: "+city.getName());

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
    protected void onPostExecute(final List<City> cities) {
        callbacks.onPostExecute(cities);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}