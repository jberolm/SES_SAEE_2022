package es.unex.asee.frojomar.asee_ses.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiManager;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadPerson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends PreferenceActivity {

    public static final String KEY_PREF_TELEPHONE = "pref_changetelephone";
    public static final String KEY_PREF_CERRARSESION = "pref_cerrarsesion";

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private SharedPreferences prefs;
    private Person mPerson;
    private Boolean sessionInit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);


        sessionInit=false;
        mPerson=new Person();
        this.LoadPerson();

        //listener on changed sort order preference:
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Preference button = findPreference(KEY_PREF_CERRARSESION);
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(sessionInit){
                    closeSession();
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SettingsActivity.this,
                            getResources().getText(R.string.init_session_first),
                            Toast.LENGTH_LONG)
                            .show();
                }
                return true;
            }
        });

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                Log.i("SETTINGS","Settings key changed: " + key);

                if(key.equals(KEY_PREF_TELEPHONE)){
                    if(sessionInit){
                        updatePerson();
                    }
                    else{
                        Toast.makeText(SettingsActivity.this,
                                getResources().getText(R.string.init_session_first),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    private void LoadPerson(){

        //leer la información de la persona asociada al appointment
        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String dni=preferences.getString("dni", "");
        if(!dni.equals("")) {
            final LoadPerson mLoadPerson = new LoadPerson(dni, new LoadPerson.LoadPersonListener() {
                @Override
                public void onPostExecute(Person person) {
                    mPerson = person;
                }

                @Override
                public void onCancelled() {

                }
            });
            mLoadPerson.execute((Void) null);
            sessionInit=true;
        }
        else{
            sessionInit=false;
        }


    }


    private void closeSession(){
        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        if(preferences.getString("login", "no").equals("yes")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Log.i("PRUEBA", "Closing session");
            Toast.makeText(SettingsActivity.this,
                    getResources().getText(R.string.closing_session),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void updatePerson(){

        String telephone=prefs.getString(KEY_PREF_TELEPHONE, mPerson.getTelephone());
        mPerson.setTelephone(telephone);

        ApiService myAPIService=ApiManager.getAPIService();
        myAPIService.updateUser(mPerson.getId(),mPerson)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SettingsActivity.this,
                                    "Tlfno por defecto modificado",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this,
                                    "Error estableciendo por defecto",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this,
                                "Error en llamada a API",Toast.LENGTH_LONG).show();
                    }
                });

    }

}
