package es.unex.asee.frojomar.asee_ses.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;
import es.unex.asee.frojomar.asee_ses.repository.DoctorsData;

public class DetailCentersViewModel extends AndroidViewModel {
    public final static String TAG="DetailCentersViewModel";


    private final DoctorsData mRepositoryDoctors;
    private final CitiesData mRepositoryCities;

    private final MutableLiveData<Center> mCenter;
    private MutableLiveData<City> mCity;
    private MutableLiveData<List<Doctor>> mDoctors;

    private Application mApplication;

    public DetailCentersViewModel(Application application, CitiesData mRepositoryCities, DoctorsData mRepositoryDoctors) {
        super(application);

        Log.i(TAG, "Definiendo ViewModel");

        this.mRepositoryDoctors = mRepositoryDoctors;
        this.mRepositoryCities = mRepositoryCities;
        this.mCenter=new MutableLiveData<Center>();
        this.mCity=new MutableLiveData<City>();
        this.mDoctors=new MutableLiveData<List<Doctor>>();
        this.mApplication=application;
    }

    public LiveData<Center> getCenter() {
        return mCenter;
    }

    public LiveData<City> getCity() {
        return mCity;
    }

    public LiveData<List<Doctor>> getDoctors() {
        return mDoctors;
    }

    public void setCenter(Center center){
        this.mCenter.setValue(center);
        this.loadCity();
        this.loadDoctors();
    }

    private void loadCity(){

        final androidx.lifecycle.Observer<City> observer = new androidx.lifecycle.Observer<City>() {
            @Override
            public void onChanged(@Nullable final City city) {
                mCity.setValue(city);
            }
        };

        if(this.mCenter!=null && this.mCenter.getValue()!=null){
            Center center= this.mCenter.getValue();
            this.mRepositoryCities.getCityById(center.getCityId(),mApplication.getApplicationContext()).observeForever(observer);
        }
    }

    private void loadDoctors(){

        final androidx.lifecycle.Observer<List<Doctor>> observer = new androidx.lifecycle.Observer<List<Doctor>>() {
            @Override
            public void onChanged(@Nullable final List<Doctor> doctors) {
                mDoctors.setValue(doctors);
            }
        };

        if(this.mCenter!=null && this.mCenter.getValue()!=null){
            Center center= this.mCenter.getValue();
            this.mRepositoryDoctors.getDoctorsByCenter(mApplication.getApplicationContext(),center.getId()).observeForever(observer);
        }
    }

}
