package es.unex.asee.frojomar.asee_ses.activities.appointments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.LoginActivity;
import es.unex.asee.frojomar.asee_ses.activities.navigationdrawer.NavDrawerActivity;
import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.model.AppointmentSpecialist;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadPerson;

public class AppointmentsActivity extends NavDrawerActivity implements ListAppointmentsFragment.AppointmentsInterface{
    private static final String TAG = "AppointmentsActivity";

    private ListAppointmentsFragment mListAppointmentsFragment;
    private DetailAppointmentsFragment mDetailAppointmentsFragment;
    private DetailAppointmentsSpecialistFragment mDetailAppointmentsSpecialistFragment;
    private LinearLayout mDetailAppointmentsSpecialistLayout;
    private LinearLayout mDetailAppointmentsLayout;
    private Person mPerson;
    private LoadPerson mLoadPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String dni=preferences.getString("dni", "");

        if(savedInstanceState!=null){
            mPerson= savedInstanceState.getParcelable("person");
        }
        if(mPerson!=null){
            Log.i(TAG, "No hace falta volver a consultar la persona registrada");
            this.loadFragments();
        }
        else{
            Log.i(TAG, "Consultando quien es la registrada");
            mLoadPerson = new LoadPerson(dni, new LoadPerson.LoadPersonListener() {
                @Override
                public void onPostExecute(Person person) {
                    mLoadPerson = null;

                    if (person!=null) {
                        mPerson=person;
                        loadFragments();
                    }
                    //TODO Añadir que fragment diciendo que no se ha podido comunicar con la BD
                }

                @Override
                public void onCancelled() {
                    mLoadPerson = null;
                    //TODO Añadir que fragment diciendo que no se ha podido comunicar con la BD
                }
            });
            mLoadPerson.execute((Void) null);
        }

    }

    public void loadFragments(){

        Fragment f= (Fragment) getSupportFragmentManager().findFragmentByTag("to_detail");
        if(f==null){
            f= (Fragment) getSupportFragmentManager().findFragmentByTag("to_list");
            if(f==null){
                mListAppointmentsFragment = new ListAppointmentsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

                Log.i(TAG, "Cargando el mListAppointmentsFragment");
                fragmentTransaction.add(R.id.appointments_fragments_container, mListAppointmentsFragment, "to_list");
                fragmentTransaction.commit();
            }
            else{
                Log.i(TAG, "YA existe un fragment de Listado");
            }
        }
        else{
            Log.i(TAG, "YA existe un fragment de Detalle");
        }

    }

    /**
     * This method, it's necessary in all the Activities that extends NavDrawerActivity, to link the
     * layout for this activity with the layouts for NavDrawerActivity class.
     */
    @Override
    protected void linkSpecificLayoutforActivity(){

        // We link the layout of NavDrawer activities with the layout of this specific activity
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.content_activities);
        ConstraintLayout childLayout = (ConstraintLayout) View.inflate(this, R.layout.activity_appointments, null);
        mainLayout.removeAllViews();
        mainLayout.addView(childLayout);
    }

    private boolean isInTwoPaneMode() {

        return findViewById(R.id.appointments_fragments_container) == null;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("person", mPerson);
    }

    // Display selected Appointment information
    @Override
    public void onAppointmentSelected(Appointment appointment) {
        Log.i(TAG, "Entered onAppointmentSelected(" + appointment.getId() + ")");

        // If there is no DetailCentersFragment instance, then create one

        if (mDetailAppointmentsFragment == null)
            mDetailAppointmentsFragment = new DetailAppointmentsFragment();

        // If in single-pane mode, replace single visible Fragment

        getSupportFragmentManager().beginTransaction().
                replace(R.id.appointments_fragments_container, mDetailAppointmentsFragment, "to_detail").addToBackStack(null).commit();

        // execute transaction now
        getSupportFragmentManager().executePendingTransactions();

        // Update the display of information of center
        mDetailAppointmentsFragment.updateAppointment(appointment);
    }

    // Display selected Appointment information
    @Override
    public void onAppointmentSpecialistSelected(AppointmentSpecialist appointment) {
        Log.i(TAG, "Entered onAppointmentSpecialistSelected(" + appointment.getId() + ")");

        // If there is no DetailCentersFragment instance, then create one

        if (mDetailAppointmentsSpecialistFragment == null)
            mDetailAppointmentsSpecialistFragment = new DetailAppointmentsSpecialistFragment();

        // If in single-pane mode, replace single visible Fragment

        getSupportFragmentManager().beginTransaction().
                replace(R.id.appointments_fragments_container, mDetailAppointmentsSpecialistFragment,
                        "to_detail").addToBackStack(null).commit();

        // execute transaction now
        getSupportFragmentManager().executePendingTransactions();

        // Update the display of information of center
        mDetailAppointmentsSpecialistFragment.updateAppointmentSpecialist(appointment);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.close_session) {
            SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            if(preferences.getString("login", "no").equals("yes")){
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Log.i("PRUEBA", "Closing session");
            }
            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else{
            return super.onNavigationItemSelected(item);
        }

    }
    public Person getPerson(){
        return mPerson;
    }
    

}
