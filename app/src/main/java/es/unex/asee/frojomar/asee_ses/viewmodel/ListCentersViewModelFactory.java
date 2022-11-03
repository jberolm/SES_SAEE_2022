package es.unex.asee.frojomar.asee_ses.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.asee.frojomar.asee_ses.repository.CentersData;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link CentersData} and {@link CitiesData}
 */
public class ListCentersViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final CentersData mRepositoryCenters;
    private final CitiesData mRepositoryCities;
    private final Application mApplication;
    private final String mCity;

    public ListCentersViewModelFactory(Application application, CentersData repositoryCenters, CitiesData repositoryCities,
                                       String city) {
        this.mApplication= application;
        this.mRepositoryCenters = repositoryCenters;
        this.mRepositoryCities = repositoryCities;
        this.mCity=city;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ListCentersViewModel(mApplication, mRepositoryCenters, mRepositoryCities,
                mCity);
    }
}