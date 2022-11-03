package es.unex.asee.frojomar.asee_ses.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.activities.centers.ListCentersFragment;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.repository.CentersData;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;


/**
 * {@link ViewModel} for {@link ListCentersFragment}
 */
public class ListCentersViewModel extends AndroidViewModel{
    private final CentersData mRepositoryCenters;
    private final CitiesData mRepositoryCities;

    public final static String TAG="ListCentersViewModel";

    private MutableLiveData<List<Center>> mCenters;
    private final LiveData<List<City>> mCities;
    private String mCity;
    private Integer mCityId;
    private Application mApplication;

    public ListCentersViewModel(Application application, CentersData mRepositoryCenters, CitiesData mRepositoryCities, String city) {
        super(application);

        Log.i(TAG, "Definiendo ViewModel con ciudad "+city);

        this.mRepositoryCenters = mRepositoryCenters;
        this.mRepositoryCities = mRepositoryCities;
        this.mCity=city;
        this.mCityId=getCityId(city);
        this.mCenters=new MutableLiveData<List<Center>>();
        this.mApplication=application;

        this.mCities=this.mRepositoryCities.getAllCities(application.getApplicationContext());

        this.loadCenters();
    }

    public LiveData<List<Center>> getCenters() {
        return mCenters;
    }
    public LiveData<List<City>> getCities() {
        return mCities;
    }

    public void setCity(String city){
        Log.i(TAG, "set city to "+city);
        this.mCity=city;
        this.mCityId=getCityId(city);
        this.loadCenters();
    }

    private Integer getCityId(String name){
        if(mCities!=null){
            List<City> cities= mCities.getValue();
            for(int i=0; i<cities.size(); i++){
                if(cities.get(i).getName().equals(name)){
                    return cities.get(i).getId();
                }
            }
        }
        return -1;
    }

    private void loadCenters(){
        final androidx.lifecycle.Observer<List<Center>> centersObserver = new androidx.lifecycle.Observer<List<Center>>() {
            @Override
            public void onChanged(@Nullable final List<Center> centers) {
                mCenters.setValue(centers);
            }
        };

        if(mCity!=null && !mCity.equals("") && !mCity.equals("Todas")){
            Log.i(TAG,"Bucando los centros por ciudad "+mCity+" - "+mCityId);
            this.mRepositoryCenters.getCentersByCity(mApplication.getApplicationContext(), mCity, mCityId).observeForever(centersObserver);
        }
        else{
            Log.i(TAG,"Bucando todos los centros");
            Context appContext = mApplication.getApplicationContext();
            this.mRepositoryCenters.getAllCenters(appContext).observeForever(centersObserver);
        }
    }
}
