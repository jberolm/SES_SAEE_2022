package es.unex.asee.frojomar.asee_ses.repository.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


import es.unex.asee.frojomar.asee_ses.model.Person;

public class LoadPerson extends AsyncTask<Void, Void, Person> {

    private static final String BASE_URL = NetworkUtils.BASE_URL;
    private static final String RESOURCE_PATH_PERSONS = "persons";
    private static final String TAG = "LoadPerson";

    public interface LoadPersonListener {
        public void onPostExecute(Person person);

        public void onCancelled();
    }

    private final LoadPersonListener callbacks;
    private final String mDni;

    public LoadPerson(String mDni, LoadPersonListener callbacks) {
        Log.i(TAG, "Definido un LoadPerson con dni " + mDni);
        this.mDni = mDni;
        this.callbacks = callbacks;
    }

    @Override
    protected Person doInBackground(Void... params) {
        URL queryURL;
        JSONObject result;

        //Load upcoming movies
        queryURL = NetworkUtils.buildURL(BASE_URL,
                new String[]{RESOURCE_PATH_PERSONS, mDni});
        result = NetworkUtils.getJSONResponse(queryURL);

        Log.i(TAG, "Cargando información de la persona de " + queryURL.toString());

        if (result == null) {
            Log.e(TAG, "Resultado obtenido. Ninguna persona devuelta");
            return null;
        } else {
            Log.e(TAG, "Resultado obtenido. Convirtiendolo a Person");
            return jsonToObject(result);
        }
    }

    private Person jsonToObject(JSONObject responseObject) {
        Log.i(TAG, "Convirtiendo el resultado a objeto");
        Person person = new Person();
        try {
            // Extract value of "" key -- a List
            Log.i(TAG, responseObject.toString());
            ObjectMapper mapper = new ObjectMapper();
            person = mapper.readValue(responseObject.toString(), Person.class);
            Log.i(TAG, "Mapeada la persona " + person.getName());

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
        return person;
    }

    @Override
    protected void onPostExecute(final Person person) {
        callbacks.onPostExecute(person);
    }

    @Override
    protected void onCancelled() {
        callbacks.onCancelled();
    }
}
