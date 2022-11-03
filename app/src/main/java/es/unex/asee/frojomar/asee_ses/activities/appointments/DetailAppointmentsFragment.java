package es.unex.asee.frojomar.asee_ses.activities.appointments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.LoginActivity;
import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.model.Person;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCenter;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadCity;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadDoctor;
import es.unex.asee.frojomar.asee_ses.repository.networking.LoadPerson;

public class DetailAppointmentsFragment extends Fragment {


    private static String TAG="DetailAppointmentsFragment";

    private Person mPerson;
    private Center mCenter;
    private Doctor mDoctor;
    private Appointment mAppointment;

    private LoadDoctor mLoadDoctor;
    private LoadCity mLoadCity;
    private LoadCenter mLoadCenter;
    private LoadPerson mLoadPerson;

    private Activity mActivity;
    private View mLayout;

    private TextView mFechaView;
    private TextView mHoraView;
    private TextView mTlfnoContactoView;
    private TextView mDescriptionView;
    private TextView mNameCenterView;
    private TextView mCityView;
    private TextView mAdressView;
    private TextView mTlfnoView;
    private TextView mDoctorView;


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreateView");

        mLayout= inflater.inflate(R.layout.detail_appointments_fragment, container, false);

        mFechaView = (TextView) mLayout.findViewById(R.id.fecha_appointment);
        mHoraView = (TextView) mLayout.findViewById(R.id.hora_appointment);
        mTlfnoContactoView = (TextView) mLayout.findViewById(R.id.tlfno_appointment);
        mDescriptionView = (TextView) mLayout.findViewById(R.id.desc_appointment);
        mNameCenterView = (TextView) mLayout.findViewById(R.id.name_center);
        mCityView = (TextView) mLayout.findViewById(R.id.city_center);
        mAdressView = (TextView) mLayout.findViewById(R.id.adress_center);
        mTlfnoView = (TextView) mLayout.findViewById(R.id.tlfno_center);
        mDoctorView = (TextView) mLayout.findViewById(R.id.doctor_appointment);

        mTlfnoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTlfnoView.getText()));
                startActivity(intent);
            }
        });

        if(savedInstanceState!=null){
            mAppointment=savedInstanceState.getParcelable("appointment");
            updateAppointment(mAppointment);
        }
        else{
            mAppointment= new Appointment();
        }

        this.loadMap();

        final LinearLayout mWindowMap= mLayout.findViewById(R.id.window_map);
        final LinearLayout mWindowData= mLayout.findViewById(R.id.window_data);
        final Button buttonMap= (Button) mLayout.findViewById(R.id.button_mostrar_map);
        buttonMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(buttonMap.getText()==getResources().getString(R.string.button_mostrar_map)){
                            mWindowMap.startAnimation(AnimationUtils.loadAnimation(mActivity,
                                    R.anim.slide_up));
                            mWindowMap.setVisibility(View.VISIBLE);
                            buttonMap.setText(getResources().getString(R.string.button_ocultar_map));

                        }
                        else{
                            mWindowMap.startAnimation(AnimationUtils.loadAnimation(mActivity,
                                    R.anim.slide_down));
                            mWindowMap.setVisibility(View.GONE);
                            buttonMap.setText(getResources().getString(R.string.button_mostrar_map));

                        }
                    }
                });

        return mLayout;
    }

    private void loadMap() {
        try {
            // don't recreate fragment everytime ensure last map location/state are maintained
            mapFragment =  SupportMapFragment.newInstance();

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap= googleMap;
                }
            });
            // R.id.map is a FrameLayout, not a Fragment
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(TAG,"Excepción: "+e.toString());
        }
    }

    public void updateAppointment(Appointment appointment){

        mAppointment=appointment;

        mFechaView.setText(appointment.getDate());
        mHoraView.setText(appointment.getTime());
        mTlfnoContactoView.setText(appointment.getTelephone());
        mDescriptionView.setText(appointment.getDescription());


        Log.i(TAG, "Appointment cambiado. Lanzando threads para leer Person, Doctor, Center y City");

        //leer la información de la persona asociada al appointment
        SharedPreferences preferences = mActivity.getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String dni=preferences.getString("dni", "");

        this.loadPerson(dni);
    }

    private void loadPerson(String dni){
        mLoadPerson = new LoadPerson(dni, new LoadPerson.LoadPersonListener() {
            @Override
            public void onPostExecute(Person person) {
                mLoadPerson=null;
                mPerson=person;

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
                setMapMarket(mCenter.getLat(),mCenter.getLong());

                loadCity(mCenter.getCityId());
            }

            @Override
            public void onCancelled() {
                mLoadCenter=null;
            }
        });
        mLoadCenter.execute((Void) null);
    }


    public void loadCity(Integer id){
        //leer la información de la ciudad donde se encuentra el centro
        mLoadCity = new LoadCity(id, new LoadCity.LoadCityListener() {
            @Override
            public void onPostExecute(City city) {
                mLoadCity= null;
                mCityView.setText(city.getName());

            }

            @Override
            public void onCancelled() {
                mLoadCity= null;
            }
        });
        mLoadCity.execute((Void) null);
    }

    public void setMapMarket(Double lat, Double lng){
        LatLng latLng = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(latLng)
                .title(mCenter.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("appointment",mAppointment);
    }

}
