package es.unex.asee.frojomar.asee_ses.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiManager;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadPerson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeTelephoneActivity extends AppCompatActivity {

    public static final String TELEPHONE="telephone";
    private EditText mTelephoneView;
    private RadioButton mDefaultView;
    private Button mButtonCancel;
    private Button mButtonSubmit;

    private String telephoneDefault;

    private Person personInstance;
    private LoadPerson loadPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_telephone);

        telephoneDefault =
                savedInstanceState == null ? null : savedInstanceState.getString(TELEPHONE);

        // If not, check to see if the extras bundle has these values passed in
        if (telephoneDefault == null) {
            Bundle extras = getIntent().getExtras();
            telephoneDefault =
                    extras == null ? null : extras
                            .getString(TELEPHONE);
        }
        if(telephoneDefault== null){
            telephoneDefault="";
        }

        mTelephoneView = findViewById(R.id.tlfno_appointment);
        mDefaultView = findViewById(R.id.set_default_button);
        mButtonCancel= findViewById(R.id.cancelButton);
        mButtonSubmit= findViewById(R.id.submitButton);

        mTelephoneView.setText(telephoneDefault);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setResult(RESULT_CANCELED);
                 finish();
             }
         });

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mDefaultView.isChecked()){
                    setAsDefaultTelephone(mTelephoneView.getText().toString());
                }
                Intent data = new Intent();
                data.putExtra(ChangeTelephoneActivity.TELEPHONE,mTelephoneView.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TELEPHONE,mTelephoneView.getText().toString());
    }


    private void setAsDefaultTelephone(final String telephone){
        //leer la información de la persona asociada al appointment
        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String dni=preferences.getString("dni", "");


        loadPerson= new LoadPerson(dni, new LoadPerson.LoadPersonListener() {
            @Override
            public void onPostExecute(Person person) {
                personInstance=person;
                loadPerson=null;
                personInstance.setTelephone(telephone);
                updatePerson(personInstance);
            }

            @Override
            public void onCancelled() {
                loadPerson=null;
            }
        });
        loadPerson.execute((Void) null);

    }

    private void updatePerson(Person person){
        if(person==null){
            Toast.makeText(this,"Error estableciendo por defecto",Toast.LENGTH_LONG).show();
        }
        else{
            ApiService myAPIService=ApiManager.getAPIService();
            myAPIService.updateUser(person.getId(),person)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ChangeTelephoneActivity.this,
                                        "Tlfno por defecto modificado",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(ChangeTelephoneActivity.this,
                                        "Error estableciendo por defecto",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(ChangeTelephoneActivity.this,
                                    "Error en llamada a API",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
