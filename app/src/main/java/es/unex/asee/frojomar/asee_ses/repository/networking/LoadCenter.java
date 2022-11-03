package es.unex.asee.frojomar.asee_ses.repository.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


import es.unex.asee.frojomar.asee_ses.model.Center;

public class LoadCenter extends AsyncTask<Void, Void, Center> {

    private static final String BASE_URL=NetworkUtils.BASE_URL;
    private static final String RESOURCE_PATH_CENTERS="centers";
    private static final String TAG="LoadCenter";

    public interface LoadCenterListener{
        public void onPostExecute(Center center);
        public void onCancelled();
    }

    private final LoadCenterListener callbacks;

    private Integer id_center;

    public LoadCenter(Integer id_center, LoadCenterListener callbacks){
        Log.i(TAG, "Definido un LoadCenter para el id_center "+id_center);
        this.id_center=id_center;
        this.callbacks=callbacks;
    }


    @Override
    protected Center doInBackground(Void... params) {

        URL queryURL;
        JSONObject result;

        //Load upcoming movies
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_PATH_CENTERS,id_center.toString()});
        result = NetworkUtils.getJSONResponse(queryURL);

        Log.i(TAG, "Cargando la información del centro de "+queryURL.toString());

        if(result != null) {
            Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
            return jsonToObject(result);
        }
        Log.i(TAG, "Resultado obtenido. Centro por defecto");
        Center c= new Center();
        c.setName("Centro desconocido");
        return c;
    }

    private Center jsonToObject(JSONObject responseObject) {
        Log.i(TAG, "Convirtiendo el resultado a objeto");
        Center center = new Center();
        try {
            // Extract value of "" key -- a List
            Log.i(TAG,responseObject.toString());
            ObjectMapper mapper = new ObjectMapper();
            center = mapper.readValue(responseObject.toString(), Center.class);
            Log.i(TAG, "Mapeado un el centro "+center.getName());

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
        return center;
    }

    @Override
    protected void onPostExecute(final Center center) {
        callbacks.onPostExecute(center);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }

}
