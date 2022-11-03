package es.unex.asee.frojomar.asee_ses.activities.appointments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.model.AppointmentSpecialist;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiManager;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadAppointments;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadAppointmentsSpecialist;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListAppointmentsFragment extends Fragment {

    private LoadAppointments mLoadAppointments;
    private LoadAppointmentsSpecialist mLoadAppointmentsSpecialist;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewSpecialist;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterSpecialist;
    private List<Appointment> mAppointments;
    private List<AppointmentSpecialist> mAppointmentsSpecialist;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManagerSpecialist;
    private LoadAppointmentsSpecialist.LoadAppointmentsSpecialistListener listenerLoadAppSpecialist;
    private LoadAppointments.LoadAppointmentsListener listenerLoadAppointments;
    private Person mPerson;

    private static final int ADD_APPOINTMENT_REQUEST = 0;
    private static final int EDIT_APPOINTMENT_REQUEST = 1;
    public static final String TAG="ListAppointFragment";

    public interface AppointmentsInterface{
        public void onAppointmentSelected(Appointment appointment);
        public void onAppointmentSpecialistSelected(AppointmentSpecialist appointment);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (Activity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPerson=((AppointmentsActivity)mActivity).getPerson();

        final View mLayout = inflater.inflate(R.layout.list_appointments_fragment, container, false);

        this.onCreateRecyclerViewAppointments(mLayout, savedInstanceState);
        this.onCreateRecyclerViewSpecialist(mLayout, savedInstanceState);
        this.onCreateButtonAdd(mLayout);
        return mLayout;
    }

    private void onCreateRecyclerViewAppointments(View mLayout, Bundle savedInstanceState){
        mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(mActivity);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AppointmentsAdapter(
                new AppointmentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Appointment item) {
                        ((AppointmentsActivity) mActivity).onAppointmentSelected(item);
                    }
                },
                new AppointmentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Appointment item) {
                        Toast.makeText(mActivity,"Editar "+item.getDate()+" "+item.getTime(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mActivity,AddAppointmentActivity.class);
                        Appointment.packageIntent(intent,item.getPersonId(),item.getId(),
                                item.getDate(),item.getDescription(),item.getTelephone(),
                                item.getTime());
                        intent.getExtras().putInt("position",((AppointmentsAdapter)mAdapter).getIndexof(item));
                        startActivityForResult(intent, EDIT_APPOINTMENT_REQUEST);
                    }
                },
                new AppointmentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Appointment item) {
                        createDialogDelete(item);
                    }
                }
        ); //es lo que debe hacer si se clickea en un item
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState!=null){
            mAppointments=savedInstanceState.getParcelableArrayList("appointments");
        }


        listenerLoadAppointments= new LoadAppointments.LoadAppointmentsListener() {
            @Override
            public void onPostExecute(List<Appointment> appointments) {
                mLoadAppointments= null;
                mAppointments=appointments;
                ((AppointmentsAdapter)mAdapter).clear();
                for(int i=0; i<appointments.size(); i++){
                    ((AppointmentsAdapter)mAdapter).add(appointments.get(i));
                }
            }

            @Override
            public void onCancelled() {
                mLoadAppointments = null;
            }
        };

        mLoadAppointments = new LoadAppointments(mPerson.getId(),listenerLoadAppointments);
        if(mAppointments!=null){
            Log.i(TAG, "Existen citas, no hay que volver a solicitarlas");
            listenerLoadAppointments.onPostExecute(mAppointments);
        }else{
            Log.i(TAG, "No existen citas, hay que volver a solicitarlas");
            mLoadAppointments.execute((Void) null);
        }

    }

    private void onCreateRecyclerViewSpecialist(View mLayout, Bundle savedInstanceState){
        mRecyclerViewSpecialist = (RecyclerView) mLayout.findViewById(R.id.my_recycler_view_specialist);
        mLayoutManagerSpecialist = new LinearLayoutManager(mActivity);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerViewSpecialist.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerViewSpecialist.setLayoutManager(mLayoutManagerSpecialist);

        // specify an adapter (see also next example)
        mAdapterSpecialist = new AppointmentsSpecialistAdapter(
                new AppointmentsSpecialistAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AppointmentSpecialist item) {
                        ((AppointmentsActivity) mActivity).onAppointmentSpecialistSelected(item);
                    }
                },
                new AppointmentsSpecialistAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AppointmentSpecialist item) {
                        createDialogAplazar(item);
                    }
                },getContext()); //es lo que debe hacer si se clickea en un item o en aplazar
        mRecyclerViewSpecialist.setAdapter(mAdapterSpecialist);

        if(savedInstanceState!=null){
            mAppointmentsSpecialist=savedInstanceState.getParcelableArrayList("appointmentsspecialist");
        }

        listenerLoadAppSpecialist=
                new LoadAppointmentsSpecialist.LoadAppointmentsSpecialistListener() {
                    @Override
                    public void onPostExecute(List<AppointmentSpecialist> appointments) {
                        mLoadAppointmentsSpecialist= null;
                        mAppointmentsSpecialist=appointments;
                        ((AppointmentsSpecialistAdapter)mAdapterSpecialist).clear();
                        for(int i=0; i<appointments.size(); i++){
                            ((AppointmentsSpecialistAdapter)mAdapterSpecialist).add(appointments.get(i));
                        }
                    }

                    @Override
                    public void onCancelled() {
                        mLoadAppointmentsSpecialist = null;
                    }
                };
        mLoadAppointmentsSpecialist = new LoadAppointmentsSpecialist(mPerson.getId(),listenerLoadAppSpecialist);
        if(mAppointmentsSpecialist!=null){
            Log.i(TAG, "Existen citas con especialistas, no hay que volver a solicitarlas");
            listenerLoadAppSpecialist.onPostExecute(mAppointmentsSpecialist);
        }else{
            Log.i(TAG, "No existen citas, hay que volver a solicitarlas");
            mLoadAppointmentsSpecialist.execute((Void) null);
        }

    }

    private void createDialogAplazar(final AppointmentSpecialist item){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mActivity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mActivity);
        }
        builder.setTitle(R.string.aplazar_alert)
                .setMessage(R.string.aplazar_alert_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        aplazarSpecialist(item);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void aplazarSpecialist(AppointmentSpecialist item){
        item.setSolAplazamiento("true");
        Log.i(TAG, "En aplazarSpecialist");
        ApiService mAPIService= ApiManager.getAPIService();
        mAPIService.aplazarAppointment(item.getPersonId(),item.getId(),item)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.i(TAG, "post submitted to API." + response.body().toString());
                            Toast.makeText(mActivity, "Solicitado aplazamiento", Toast.LENGTH_LONG).show();
                            reloadAppointmentsSpecialist();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mActivity, "Petición fallida", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void reloadAppointments(){
        mLoadAppointments = new LoadAppointments(mPerson.getId(), listenerLoadAppointments);
        mLoadAppointments.execute((Void) null);
    }
    private void reloadAppointmentsSpecialist(){
        mLoadAppointmentsSpecialist= new LoadAppointmentsSpecialist(mPerson.getId(),listenerLoadAppSpecialist);
        mLoadAppointmentsSpecialist.execute((Void) null);
    }

    private void createDialogDelete(final Appointment item){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mActivity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mActivity);
        }
        builder.setTitle(R.string.delete_alert)
                .setMessage(R.string.delete_alert_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAppointment(item);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteAppointment(Appointment item){
        Log.i(TAG, "En aplazarSpecialist");
        ApiService mAPIService= ApiManager.getAPIService();
        mAPIService.deleteAppointment(item.getPersonId(),item.getId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.i(TAG, "post submitted to API.");
                            Toast.makeText(mActivity, "Eliminada cita", Toast.LENGTH_LONG).show();
                            reloadAppointments();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mActivity, "Petición fallida", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void onCreateButtonAdd(View mLayout){
        FloatingActionButton addButton= mLayout.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddAppointmentActivity.class);
                startActivityForResult(intent,ADD_APPOINTMENT_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        Log.i(TAG, "En onActivityResult");
        if (requestCode == ADD_APPOINTMENT_REQUEST){
            Log.i(TAG, "En onActivityResult por ADD_APPOINTMENT_REQUEST");
            if (resultCode == RESULT_OK){
                Log.i(TAG, "Añadiendo appointment");
                Appointment item = new Appointment(data);

                //insert into DB
                int id=1000;

                //update item ID
                item.setId(id);

                //insert into adapter list
                ((AppointmentsAdapter)mAdapter).add(item);
                reloadAppointments();
            }
        }
        if (requestCode == EDIT_APPOINTMENT_REQUEST){
            Log.i(TAG, "En onActivityResult por EDIT_APPOINTMENT_REQUEST");
            if (resultCode == RESULT_OK){
                Log.i(TAG, "Editando appointment");
                Appointment item = new Appointment(data);
                Integer position = data.getIntExtra("position", 0);

                reloadAppointments();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("appointments", (ArrayList<Appointment>) mAppointments);
        outState.putParcelableArrayList("appointmentsSpecialist", (ArrayList<AppointmentSpecialist>) mAppointmentsSpecialist);
    }

}
