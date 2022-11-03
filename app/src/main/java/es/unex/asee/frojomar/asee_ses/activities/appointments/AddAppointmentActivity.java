package es.unex.asee.frojomar.asee_ses.activities.appointments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.ChangeTelephoneActivity;
import es.unex.asee.frojomar.asee_ses.activities.LoginActivity;
import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiManager;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCenter;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadDoctor;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadHoursAvailables;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadPerson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Test 1
public class AddAppointmentActivity extends AppCompatActivity {

    private static final String TAG = "AddAppointmentActivity";
    private static final Integer CHANGE_TELEPHONE_REQUEST = 0;
    private static final Integer ONE_DAY_MILLIS = 1000*60*60*24;

    private static String timeString;
    private static String dateString;

    private static TextView dateView;
    private static TextView timeView;

    private static Boolean dateSelected;
    private static Boolean hourSelected;

    private static List<String> mHoursAvailables;
    private static LoadHoursAvailables mLoadHours;

    private Date mDate;
    private EditText mDescriptionText;
    private TextView mTelephone;

    private TextView mDoctorView;
    private TextView mNameCenterView;
    private TextView mAdressView;
    private TextView mTlfnoView;

    private Person mPerson;
    private Center mCenter;
    private static Doctor mDoctor;
    private LoadPerson mLoadPerson;
    private LoadDoctor mLoadDoctor;
    private LoadCenter mLoadCenter;

    private Appointment mAppointmenttoEdit;
    private boolean isEdit;
    private Intent mIntent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_appointment_activity);

        mDescriptionText = (EditText) findViewById(R.id.desc_appointment);
        mTelephone= (TextView) findViewById(R.id.telephone_appointment);
        dateView = (TextView) findViewById(R.id.date);
        timeView = (TextView) findViewById(R.id.time);

        mDoctorView = (TextView) findViewById(R.id.doctor_appointment);
        mNameCenterView = (TextView) findViewById(R.id.name_center);
        mAdressView = (TextView) findViewById(R.id.adress_center);
        mTlfnoView = (TextView) findViewById(R.id.tlfno_center);

        mHoursAvailables= new ArrayList<String>();

        mIntent= getIntent();
        Bundle dataToEdit=mIntent.getExtras();
        if(dataToEdit!=null){
            setValuesOlder(mIntent);
        }
        else{
            // Set the default date and time
            setDefaultDateTime();
        }


        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog

        final Button timePickerButton = (Button) findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(dateSelected){
                    showTimePickerDialog();
                }
                else{
                    Toast.makeText(AddAppointmentActivity.this,
                            getResources().getText(R.string.select_date_first),Toast.LENGTH_LONG).show();
                }
            }
        });


        final Button changeTelephone= (Button) findViewById(R.id.change_tlfno_button);
        changeTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAppointmentActivity.this,ChangeTelephoneActivity.class);
                intent.putExtra(ChangeTelephoneActivity.TELEPHONE, mTelephone.getText().toString());
                startActivityForResult(intent, CHANGE_TELEPHONE_REQUEST);
            }
        });

        // OnClickListener for the Cancel Button,

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                // - Implement onClick().
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();

            }
        });

        //OnClickListener for the Reset Button

        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");
                if(isEdit){
                    setValuesOlder(mIntent);
                }
                else{
                    // - Reset data fields to default values
                    mDescriptionText.setText("");
                    setDefaultDateTime();
                }
            }
        });

        // OnClickListener for the Submit Button
        // Implement onClick().

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hourSelected) {
                    log("Entered submitButton.OnClickListener.onClick()");

                    // Gather Appointment data
                    Appointment newAppointment = new Appointment();

                    // -  Title
                    String descriptionString = mDescriptionText.getText().toString();
                    String telephoneString = mTelephone.getText().toString();


                    newAppointment.setPersonId(mPerson.getId());
                    newAppointment.setDate(dateString);
                    newAppointment.setTime(timeString);
                    newAppointment.setDescription(descriptionString);
                    newAppointment.setTelephone(telephoneString);


                    Log.i(TAG, "Terminando la activity");
                    Intent data = new Intent();

                    if(isEdit){
                        newAppointment.setId(mAppointmenttoEdit.getId());
                        Appointment.packageIntent(data, mPerson.getId(), mAppointmenttoEdit.getId(), dateString, descriptionString,
                                telephoneString, timeString);
                        data.putExtra("position", mIntent.getIntExtra("position", 0));

                        sendPutForAPI(newAppointment,data);
                    }
                    else{
                        newAppointment.setId(1000);
                        Appointment.packageIntent(data, mPerson.getId(), 1000, dateString, descriptionString,
                                telephoneString, timeString);
                        sendPostForAPI(newAppointment,data);
                    }

                }
                else{
                    Toast.makeText(AddAppointmentActivity.this,
                            getResources().getText(R.string.select_hour_first),Toast.LENGTH_LONG).show();
                }

            }
        });


        //leer la información de la persona asociada al appointment
        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String dni=preferences.getString("dni", "");

        this.loadPerson(dni);
    }

    private void sendPutForAPI(Appointment newAppointment, final Intent data) {
        Log.i(TAG, "En sendPutForAPI");
        ApiService mAPIService= ApiManager.getAPIService();
        mAPIService.editAppointment(newAppointment.getPersonId(),newAppointment.getId(),newAppointment)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.i(TAG, "put submitted to API.");
                            Toast.makeText(AddAppointmentActivity.this, "Solicitada edición", Toast.LENGTH_LONG).show();
                            // - return data Intent and finish
                            setResult(RESULT_OK, data);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "put failed to API.");

                        Toast.makeText(AddAppointmentActivity.this, "Petición fallida", Toast.LENGTH_LONG).show();
                        // - return data Intent and finish
                        setResult(RESULT_CANCELED, data);
                        finish();
                    }
                });
    }

    private void sendPostForAPI(Appointment newAppointment, final Intent data) {
        Log.i(TAG, "En sendPostForAPI");
        ApiService mAPIService= ApiManager.getAPIService();
        mAPIService.createAppointment(newAppointment.getPersonId(),newAppointment)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.i(TAG, "post submitted to API." + response.body().toString());
                            Toast.makeText(AddAppointmentActivity.this, "Solicitado post", Toast.LENGTH_LONG).show();
                            // - return data Intent and finish
                            setResult(RESULT_OK, data);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(AddAppointmentActivity.this, "Petición fallida", Toast.LENGTH_LONG).show();
                        // - return data Intent and finish
                        setResult(RESULT_CANCELED, data);
                        finish();
                    }
                });
    }

    private void setValuesOlder(Intent data){
        mAppointmenttoEdit=new Appointment(data);

        mTelephone.setText(mAppointmenttoEdit.getTelephone());
        mDescriptionText.setText(mAppointmenttoEdit.getDescription());
        dateString=mAppointmenttoEdit.getDate();
        timeString=mAppointmenttoEdit.getTime();
        dateView.setText(dateString);
        timeView.setText(timeString);

        dateSelected=true;
        hourSelected=true;

        if(mDoctor!=null) {
            Log.i(TAG, "Cargando horas disponibles para doctor "+mDoctor.getId()+" para" +
                    "el día "+dateString);
            mLoadHours = new LoadHoursAvailables(mDoctor.getId(), dateString, new LoadHoursAvailables.LoadHoursListener() {
                @Override
                public void onPostExecute(List<String> hours) {
                    mLoadHours = null;
                    mHoursAvailables = hours;
                    Log.i(TAG, "Horas: "+hours.toString());
                }

                @Override
                public void onCancelled() {
                    mLoadHours = null;
                }
            });
            mLoadHours.execute((Void) null);
        }
        else{
            Log.e(TAG, "mDoctor es null, no podemos cargar las horas disponibles");
        }

        setTitle(R.string.title_activity_edit_appointments);
        isEdit=true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHANGE_TELEPHONE_REQUEST){
            if(resultCode==RESULT_OK){
                Bundle extras=data.getExtras();
                String telephone=extras.getString(ChangeTelephoneActivity.TELEPHONE);
                mTelephone.setText(telephone);
            }
        }
    }

    private void loadPerson(String dni){
        mLoadPerson = new LoadPerson(dni, new LoadPerson.LoadPersonListener() {
            @Override
            public void onPostExecute(Person person) {
                mLoadPerson=null;
                mPerson=person;

                if(isEdit==false){
                    mTelephone.setText(mPerson.getTelephone());
                }
                loadDoctor(mPerson.getDoctorId());
            }

            @Override
            public void onCancelled() {
                mLoadPerson=null;
            }
        });
        mLoadPerson.execute((Void) null);
    }

    private void loadDoctor(Integer id){
        //leer la información del médico de familia para la persona obtenida
        mLoadDoctor = new LoadDoctor(id, new LoadDoctor.LoadDoctorListener() {
            @Override
            public void onPostExecute(Doctor doctor) {
                mLoadDoctor=null;
                mDoctor=doctor;
                mDoctorView.setText(mDoctor.getName());

                loadCenter(mDoctor.getCenterId());
            }

            @Override
            public void onCancelled() {
                mLoadDoctor=null;
            }
        });
        mLoadDoctor.execute((Void) null);
    }

    public void loadCenter(Integer id){
        //leer la información del centro donde trabaja el doctor
        mLoadCenter= new LoadCenter(id, new LoadCenter.LoadCenterListener() {
            @Override
            public void onPostExecute(Center center) {
                mLoadCenter=null;

                mCenter=center;
                mNameCenterView.setText(mCenter.getName());
                mAdressView.setText(mCenter.getAddress());
                mTlfnoView.setText(mCenter.getTelephone());
            }

            @Override
            public void onCancelled() {
                mLoadCenter=null;
            }
        });
        mLoadCenter.execute((Void) null);
    }

    // Do not modify below here

    // Use this method to set the default date and time

    private void setDefaultDateTime() {

        // Default is current time
        mDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);

        setTimeString(9, 0,
                0);

        timeView.setText(timeString);

        dateSelected=false;
        hourSelected=false;
    }

    private String getMinDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        return dateCalendarToString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

    }

    private String getMaxDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        return dateCalendarToString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)+2);

    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting

        dateString = dateCalendarToString(year,monthOfYear,dayOfMonth);

        if(mDoctor!=null) {
            Log.i(TAG, "Cargando horas disponibles para doctor "+mDoctor.getId()+" para" +
                    "el día "+dateString);
            mLoadHours = new LoadHoursAvailables(mDoctor.getId(), dateString, new LoadHoursAvailables.LoadHoursListener() {
                @Override
                public void onPostExecute(List<String> hours) {
                    mLoadHours = null;
                    mHoursAvailables = hours;
                    Log.i(TAG, "Horas: "+hours.toString());
                }

                @Override
                public void onCancelled() {
                    mLoadHours = null;
                }
            });
            mLoadHours.execute((Void) null);
        }
        else{
            Log.e(TAG, "mDoctor es null, no podemos cargar las horas disponibles");
        }
    }

    private static String dateCalendarToString(int year, int monthOfYear, int dayOfMonth){
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        return year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
    }


    // DialogFragment used to pick a ToDoItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            Dialog dialog= new DatePickerDialog(getActivity(), this, year, month, day);

            Long minDate=c.getTimeInMillis();
            Long maxDate=c.getTimeInMillis()+(2*ONE_DAY_MILLIS);

            ((DatePickerDialog)dialog).getDatePicker().setMinDate(minDate);
            ((DatePickerDialog)dialog).getDatePicker().setMaxDate(maxDate);


            return dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateView.setText(dateString);

            dateSelected=true;
            hourSelected=false;

        }

    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle(getResources().getString(R.string.select_hour_string));
        alt_bld.setSingleChoiceItems(toVector(mHoursAvailables), -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String hour = mHoursAvailables.get(item);
                timeString=hour;
                timeView.setText(timeString);
                dialog.dismiss();// dismiss the alertbox after chose option
                hourSelected=true;
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    private static String[] toVector(List<String> list){
        String[] array = new String[list.size()];

        for(int i=0; i<list.size(); i++){
            array[i]=list.get(i);
        }

        return array;
    }
    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
