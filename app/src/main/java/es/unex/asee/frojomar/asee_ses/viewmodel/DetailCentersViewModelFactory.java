package es.unex.asee.frojomar.asee_ses.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.asee.frojomar.asee_ses.repository.CentersData;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;
import es.unex.asee.frojomar.asee_ses.repository.DoctorsData;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link CentersData} and {@link CitiesData}
 */
public class DetailCentersViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DoctorsData mRepositoryDoctors;
    private final CitiesData mRepositoryCities;
    private final Application mApplication;

    public DetailCentersViewModelFactory(Application application, CitiesData repositoryCities, DoctorsData repositoryDoctors) {
        this.mApplication= application;
        this.mRepositoryCities = repositoryCities;
        this.mRepositoryDoctors = repositoryDoctors;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailCentersViewModel(mApplication, mRepositoryCities, mRepositoryDoctors);
    }
}