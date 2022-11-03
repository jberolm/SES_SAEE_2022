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

import es.unex.asee.frojomar.asee_ses.model.AppointmentSpecialist;

public class LoadAppointmentsSpecialist extends AsyncTask<Void, Void, List<AppointmentSpecialist>> {

        private static final String BASE_URL=NetworkUtils.BASE_URL;
        private static final String RESOURCE_PERSONS_PATH="persons";
        private static final String RESOURCE_APPOINTMENTS_PATH="appointmentsspecialist";
        private static final String TAG="LoadAppointSpecialist";


        public interface LoadAppointmentsSpecialistListener{
            public void onPostExecute(List<AppointmentSpecialist> appointments);
            public void onCancelled();
        }

        private LoadAppointmentsSpecialistListener callbacks;
        private Integer person_id;

        public LoadAppointmentsSpecialist(Integer id, LoadAppointmentsSpecialistListener callbacks){
            this.person_id=id;
            this.callbacks=callbacks;
        }

        @Override
        protected List<AppointmentSpecialist> doInBackground(Void... params) {

            URL queryURL;
            JSONObject result;

            //Load appointments filtering by person
            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{RESOURCE_PERSONS_PATH,person_id.toString(),RESOURCE_APPOINTMENTS_PATH});
            result = NetworkUtils.getJSONResponse(queryURL);


            Log.i(TAG, "Cargando las citas con especialistas de "+queryURL.toString());

            if(result != null) {
                Log.i(TAG, "Resultado obtenido de "+queryURL.toString());
                return jsonToList(result);
            }
            Log.i(TAG, "Resultado obtenido. Lista vacía");
            return new ArrayList<AppointmentSpecialist>();
        }

        private List<AppointmentSpecialist> jsonToList(JSONObject responseObject) {
            List<AppointmentSpecialist> result= new ArrayList<AppointmentSpecialist>();
            Log.i(TAG, "Convirtiendo el resultado a lista de objetos");

            try {
                // Extract value of "" key -- a List
                Log.i(TAG,responseObject.toString());
                JSONArray appointments = responseObject.getJSONArray("appointments");
                Log.i(TAG, "Longitud de la respuesta: "+appointments.length());

                // Iterate over earthquakes list
                for (int idx = 0; idx < appointments.length(); idx++) {

                    // Get single center data - a Map
                    JSONObject appointment_json = (JSONObject) appointments.get(idx);
                    Log.i(TAG, "AppointmentSpecialist json "+idx+": "+appointment_json);

                    ObjectMapper mapper = new ObjectMapper();
                    AppointmentSpecialist appointment= mapper.readValue(appointment_json.toString(), AppointmentSpecialist.class);
                    result.add(appointment);
                    Log.i(TAG, "Mapeada una cita y añadida: "+appointment.toString());

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
        protected void onPostExecute(final List<AppointmentSpecialist> appointments) {

            callbacks.onPostExecute(appointments);
        }

        @Override
        protected void onCancelled() {
            callbacks.onCancelled();
        }

    }
