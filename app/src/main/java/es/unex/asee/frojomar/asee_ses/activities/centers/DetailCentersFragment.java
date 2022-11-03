package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;
import es.unex.asee.frojomar.asee_ses.repository.DoctorsData;
import es.unex.asee.frojomar.asee_ses.utils.ImageUtils;
import es.unex.asee.frojomar.asee_ses.viewmodel.DetailCentersViewModel;
import es.unex.asee.frojomar.asee_ses.viewmodel.DetailCentersViewModelFactory;

public class DetailCentersFragment extends Fragment {

    private static String CENTER_PARAM="center";
    private static String TAG="DetailCentersFragment";

//    private Center mCenter;
    private Activity mActivity;
    private View mLayout;

    private DoctorsData mLoadDoctors;
    private CitiesData mLoadCity;
    private DetailCentersViewModel mModel;

    private TextView mName;
    private TextView mAdress;
    private TextView mTlfno;
    private TextView mTlfno_urg;
    private CircleImageView mImage;
    private TextView mCity;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    public static DetailCentersFragment newInstance(Center center){
        DetailCentersFragment detailCentersFragment= new DetailCentersFragment();
        Bundle arg=new Bundle();
        arg.putParcelable(CENTER_PARAM,center);
        detailCentersFragment.setArguments(arg);

        return detailCentersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (Activity) context;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreateView");

        mLayout= inflater.inflate(R.layout.detail_center_fragment, container, false);

        mName = (TextView) mLayout.findViewById(R.id.name_center);
        mAdress = (TextView) mLayout.findViewById(R.id.adress_center);
        mTlfno = (TextView) mLayout.findViewById(R.id.tlfno_center);
        mTlfno_urg = (TextView) mLayout.findViewById(R.id.tlfno_urg_center);
        mImage = (CircleImageView) mLayout.findViewById(R.id.image_center);
        mCity = (TextView) mLayout.findViewById(R.id.city_center);

        mTlfno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTlfno.getText()));
                startActivity(intent);
            }
        });

        mTlfno_urg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTlfno_urg.getText()));
                startActivity(intent);
            }
        });

//        if(savedInstanceState!=null){
//            mCenter=savedInstanceState.getParcelable("center");
//            updateCenter(mCenter);
//        }
//        else{

        //}

        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DetailCentersViewModelFactory factory= new DetailCentersViewModelFactory(mActivity.getApplication(),
                CitiesData.getInstance(), DoctorsData.getInstance());
        mModel= ViewModelProviders.of(this,factory).get(DetailCentersViewModel.class);

        // Create the observer which updates the UI.
        final Observer<City> cityObserver = new Observer<City>() {
            @Override
            public void onChanged(@Nullable final City city) {
                // Update the UI, in this case, a TextView.
                mCity.setText(city.getName());
            }
        };

        // Create the observer which updates the UI.
        final Observer<List<Doctor>> doctorsObserver = new Observer<List<Doctor>>() {
            @Override
            public void onChanged(@Nullable final List<Doctor> doctors) {
                // Update the UI, in this case, a TextView.
                for (int i = 0; i < doctors.size(); i++) {
                    ((DoctorsAdapter) mAdapter).add(doctors.get(i));
                }
            }
        };

        mModel.getCity().observe(getViewLifecycleOwner(), cityObserver);
        mModel.getDoctors().observe(getViewLifecycleOwner(), doctorsObserver);

        if (getArguments() != null) {
            Center centerParam = getArguments().getParcelable(CENTER_PARAM);
            if (centerParam != null) {
                mModel.setCenter(centerParam);
                this.updateCenter(centerParam);
            } else {
                mModel.setCenter(new Center());
            }
        } else {
            mModel.setCenter(new Center());
        }

        this.loadMap();

        this.defineRecyclerView();
    }

    private void loadMap() {

        try {
            // don't recreate fragment everytime ensure last map location/state are maintained
            mapFragment =  SupportMapFragment.newInstance();

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap= googleMap;
                    Center mCenter= mModel.getCenter().getValue();
                    LatLng latLng = new LatLng(mCenter.getLat(), mCenter.getLong());
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title(mCenter.getName()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

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


    private void defineRecyclerView(){
        mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.doctors_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setNestedScrollingEnabled(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter= new DoctorsAdapter(new DoctorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor item) {
                Toast.makeText(mActivity, "Seleccionado "+item.getName(), Toast.LENGTH_SHORT).show();

            }
        }); //es lo que debe hacer si se clickea en un item
        mRecyclerView.setAdapter(mAdapter);


    }

    public void updateCenter(Center center){
        mModel.setCenter(center);
        this.updateView(center);
    }

    private void updateView(Center center){
        Center mCenter=center;
        mName.setText(center.getName());
        mAdress.setText(center.getAddress());
        mTlfno.setText(center.getTelephone());
        mTlfno_urg.setText(center.getTelephoneUrg());

        mActivity.setTitle(center.getName());

        Bitmap decodedByte= ImageUtils.b64toBitMap(mCenter.getB64Image());
        mImage.setImageBitmap(decodedByte);

        Log.i(TAG, "Center cambiado. Lanzando threads para leer Doctores y City");

        //TODO BORRAR COMENTARIOS SI FUNCIONA CORRECTAMENTE CITIESDATA Y DOCTORSDATA
//        mLoadCity = new LoadCity(mCenter.getCityId(), new LoadCity.LoadCityListener() {
//            @Override
//            public void onPostExecute(City city) {
//                mLoadCity= null;
//
//                mCity.setText(city.getName());
//                mCenter.setAdditionalProperty("city",city);
//            }
//
//            @Override
//            public void onCancelled() {
//                mLoadDoctors = null;
//            }
//        });
//        mLoadCity.execute((Void) null);
//
//        mLoadDoctors = new LoadDoctors(mCenter.getId(), new LoadDoctors.LoadDoctorsListener() {
//            @Override
//            public void onPostExecute(List<Doctor> doctors) {
//                mLoadDoctors= null;
//
//                for(int i=0; i<doctors.size(); i++){
//                    ((DoctorsAdapter)mAdapter).add(doctors.get(i));
//                }
//            }
//
//            @Override
//            public void onCancelled() {
//                mLoadDoctors = null;
//            }
//        });
//        mLoadDoctors.execute((Void) null);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable("center",mCenter);
    }


}
