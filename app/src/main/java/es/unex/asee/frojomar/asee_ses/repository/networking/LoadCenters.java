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

import es.unex.asee.frojomar.asee_ses.model.Center;

public class LoadCenters extends AsyncTask<Void, Void, List<Center>> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_CENTERS_PATH="centers";
    private static final String RESOURCE_CITIES_PATH="cities";
    private static final String TAG="LoadCenters";

    public interface LoadCentersListener{
        public void onPostExecute(List<Center> centers);
        public void onCancelled();
    }

    private final String filter;
    private final int idfilter;
    private final LoadCentersListener callbacks;

    public LoadCenters(LoadCentersListener callbacks){
        this.filter="";
        this.idfilter=-1;
        this.callbacks=callbacks;
    }
    public LoadCenters(String filter,int idfilter, LoadCentersListener callbacks){
        this.filter=filter;
        this.idfilter=idfilter;
        this.callbacks=callbacks;
    }

    @Override
    protected List<Center> doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        Integer id=this.idfilter;

        if(filter.equals("") || filter.equals("Todas") || id==-1){
            //Load all centers
            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{RESOURCE_CENTERS_PATH});
            result = NetworkUtils.getJSONResponse(queryURL);
        }
        else{
            //Load centers filtering by city
            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{RESOURCE_CITIES_PATH,id.toString(),RESOURCE_CENTERS_PATH});
            result = NetworkUtils.getJSONResponse(queryURL);
        }
;

        Log.i(TAG, "Cargando los centros de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToList(result);
        }
        Log.i(TAG, "Resultado obtenido. Lista vacía");
        return new ArrayList<Center>();
    }

    private List<Center> jsonToList(JSONObject responseObject) {
        List<Center> result= new ArrayList<Center>();
        Log.i(TAG, "Convirtiendo el resultado a lista de objetos");

        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            JSONArray centers = responseObject.getJSONArray("centers");
            Log.i(TAG, "Longitud de la respuesta: "+centers.length());

            // Iterate over earthquakes list
            for (int idx = 0; idx < centers.length(); idx++) {

                // Get single center data - a Map
                JSONObject center_json = (JSONObject) centers.get(idx);

                // Summarize center data as a string and add it to
                // result
                ObjectMapper mapper = new ObjectMapper();
                Center center = mapper.readValue(center_json.toString(), Center.class);
                result.add(center);
                Log.i(TAG, "Mapeado un centro y añadido: "+center.toString());

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
    protected void onPostExecute(final List<Center> centers) {
        callbacks.onPostExecute(centers);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}