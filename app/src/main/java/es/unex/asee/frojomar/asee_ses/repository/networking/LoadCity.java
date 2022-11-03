package es.unex.asee.frojomar.asee_ses.repository.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


import es.unex.asee.frojomar.asee_ses.model.City;

public class LoadCity extends AsyncTask<Void, Void, City> {

        private static final String BASE_URL=NetworkUtils.BASE_URL;
        private static final String RESOURCE_PATH_CITIES="cities";
        private static final String TAG="LoadCity";

        public interface LoadCityListener{
            public void onPostExecute(City city);
            public void onCancelled();
        }

        private final LoadCityListener callbacks;

        private Integer id_city;

        public LoadCity(Integer id_city, LoadCityListener callbacks){
            Log.i(TAG, "Definido un LoadCity para el id_city "+id_city);
            this.id_city=id_city;
            this.callbacks=callbacks;
        }


        @Override
        protected City doInBackground(Void... params) {

            URL queryURL;
            JSONObject result;

            //Load upcoming movies
            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{RESOURCE_PATH_CITIES,id_city.toString()});
            result = NetworkUtils.getJSONResponse(queryURL);

            Log.i(TAG, "Cargando la información de la ciudad de "+queryURL.toString());

            if(result != null) {
                Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
                return jsonToObject(result);
            }
            Log.i(TAG, "Resultado obtenido. Ciudad por defecto");
            City c= new City();
            c.setName("Ciudad desconocida");
            return c;
        }

        private City jsonToObject(JSONObject responseObject) {
            Log.i(TAG, "Convirtiendo el resultado a objeto");
            City city = new City();
            try {
                // Extract value of "" key -- a List
                Log.i(TAG,responseObject.toString());
                ObjectMapper mapper = new ObjectMapper();
                city = mapper.readValue(responseObject.toString(), City.class);
                Log.i(TAG, "Mapeado un la ciudad "+city.getName());

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
            return city;
        }

        @Override
        protected void onPostExecute(final City city) {
            callbacks.onPostExecute(city);
        }

        @Override
        protected void onCancelled() {
            callbacks.onCancelled();
        }

    }
