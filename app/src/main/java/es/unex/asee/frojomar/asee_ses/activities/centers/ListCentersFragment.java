package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.City;
import es.unex.asee.frojomar.asee_ses.repository.CentersData;
import es.unex.asee.frojomar.asee_ses.repository.CitiesData;
import es.unex.asee.frojomar.asee_ses.viewmodel.ListCentersViewModel;
import es.unex.asee.frojomar.asee_ses.viewmodel.ListCentersViewModelFactory;

public class ListCentersFragment extends Fragment {

    //private CentersData mLoadCenters;
    //private CitiesData mLoadCities;
    private ListCentersViewModel mModel;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private String citySelected;
    private Spinner mSpinner;
    private RecyclerView.Adapter mAdapter;
    //private List<Center> mCenters;
    private RecyclerView.LayoutManager mLayoutManager;
    //private List<City> mCities;

    public static final String TAG="ListCentersFragment";

    public interface CentersInterface{
        public void onCenterSelected(Integer pos,Center center, List<Center> centers);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity.setTitle(R.string.title_activity_centers);

        View mLayout = inflater.inflate(R.layout.list_centers_fragment, container, false);

        mSpinner = (Spinner) mLayout.findViewById(R.id.cities_spinner);

        if(mLayout.findViewById(R.id.my_recycler_view)==null){
            mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.my_recycler_view_grid);
            mLayoutManager = new GridLayoutManager(mActivity,2);
        }
        else{
            mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.my_recycler_view);
            mLayoutManager = new LinearLayoutManager(mActivity);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CentersAdapter(new CentersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Center item) {
                int index=((CentersAdapter)mAdapter).indexOf(item);
                ((CentersActivity) mActivity).onCenterSelected(index,item, mModel.getCenters().getValue()); //TODO COMPROBAR
            }
        }); //es lo que debe hacer si se clickea en un item
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState!=null){
            citySelected=savedInstanceState.getString("cityspinner");
        }
        if(citySelected==null || citySelected.equals("")){
            citySelected="Todas";
        }


        //TODO BORRAR SI FUNCIONA VIEWMODEL
//        mLoadCenters = new LoadCenters(listenerLoadCenters);
//        if(mCenters!=null){
//            Log.i(TAG, "Existen centros, no hay que volver a solicitarlos");
//            listenerLoadCenters.onPostExecute(mCenters);
//        }else{
//            Log.i(TAG, "No existen centros, hay que volver a solicitarlos");
//            mLoadCenters.execute((Void) null);
//        }

        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListCentersViewModelFactory factory= new ListCentersViewModelFactory(mActivity.getApplication(),
                CentersData.getInstance(), CitiesData.getInstance(),citySelected);
        mModel= ViewModelProviders.of(this,factory).get(ListCentersViewModel.class);

        // Create the observer which updates the UI.
        final Observer<List<Center>> centersObserver = new Observer<List<Center> >() {
            @Override
            public void onChanged(@Nullable final List<Center> centers) {
                // Update the UI, in this case, a TextView.
                ((CentersAdapter)mAdapter).clear();
                for(int i=0; i<centers.size(); i++){
                    ((CentersAdapter)mAdapter).add(centers.get(i));
                }
            }
        };

        // Create the observer which updates the UI.
        final Observer<List<City>> citiesObserver = new Observer<List<City> >() {
            @Override
            public void onChanged(@Nullable final List<City> cities) {
                List<String> cities_names_list = new ArrayList<String>();

                cities_names_list.add("Todas");
                for(int i=0; i<cities.size(); i++){
                    cities_names_list.add(cities.get(i).getName());
                }

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(mActivity,
                        android.R.layout.simple_spinner_item, cities_names_list);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                mSpinner.setAdapter(adapter);


                if(citySelected!=null && !citySelected.equals("")){
                    Integer pos=adapter.getPosition(citySelected);
                    mSpinner.setSelection(pos);
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getCenters().observe(getViewLifecycleOwner(), centersObserver);

        mModel.getCities().observe(getViewLifecycleOwner(), citiesObserver);

        this.loadSpinner();

    }

    private void loadSpinner(){
//        mCities= new ArrayList<City>();
//
//        mLoadCities = CitiesData.getInstance();
//        mLoadCities.getAllCities(getContext(),new CitiesData.getAllCitiesListener() {
//            @Override
//            public void getAllCities(List<City> cities) {
//                mLoadCities= null;
//                List<String> cities_names_list = new ArrayList<String>();
//                mCities= cities;
//
//                cities_names_list.add("Todas");
//                for(int i=0; i<cities.size(); i++){
//                    cities_names_list.add(cities.get(i).getName());
//                }
//
//                // Create an ArrayAdapter using the string array and a default spinner layout
//                ArrayAdapter<String> adapter=new ArrayAdapter<String>(mActivity,
//                        android.R.layout.simple_spinner_item, cities_names_list);
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                // Apply the adapter to the spinner
//                mSpinner.setAdapter(adapter);
//
//
//                if(citySelected!=null && !citySelected.equals("")){
//                    Integer pos=adapter.getPosition(citySelected);
//                    mSpinner.setSelection(pos);
//                }
//
//                loadCenters();
//            }
//        });

        final Fragment fragment= this;
        mSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String label = parent.getItemAtPosition(position).toString();
                if(label!=null){
                    if(!citySelected.equals(label)){
                        citySelected = label;
                        // Showing selected spinner item
                        Toast.makeText(parent.getContext(), "You selected: " + label,
                                Toast.LENGTH_LONG).show();
                        //TODO borrar comentado si funciona ViewModel
////                        mLoadCenters = new LoadCenters(label, getCityId(label), listenerLoadCenters);
////                        mLoadCenters.execute((Void) null);
//                        mLoadCenters= CentersData.getInstance();
//                        mLoadCenters.getCentersByCity(getContext(),label, getCityId(label), new CentersData.getAllCentersListener() {
//                            @Override
//                            public void getAllCenters(List<Center> centers) {
//                                listenerLoadCenters.onPostExecute(centers);
//                            }
//                        });

                        mModel.setCity(citySelected);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

//    private void loadCenters(){
//        if(mCenters!=null){
//            Log.i(TAG, "Existen centros, no hay que volver a solicitarlos");
//            listenerLoadCenters.onPostExecute(mCenters);
//        }else {
//            Log.i(TAG, "No existen centros, hay que volver a solicitarlos");
//            mLoadCenters= CentersData.getInstance();
//            if(citySelected==null || citySelected.equals("") || citySelected.equals("Todas")) {
//                mLoadCenters.getAllCenters(getContext(), new CentersData.getAllCentersListener() {
//                    @Override
//                    public void getAllCenters(List<Center> centers) {
//                        listenerLoadCenters.onPostExecute(centers);
//                    }
//                });
//            }
//            else{
//                mLoadCenters.getCentersByCity(getContext(), citySelected, getCityId(citySelected), new CentersData.getAllCentersListener() {
//                    @Override
//                    public void getAllCenters(List<Center> centers) {
//                        listenerLoadCenters.onPostExecute(centers);
//                    }
//                });
//            }
//        }
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (Activity) context;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("centers", (ArrayList<Center>) mCenters);
        outState.putString("cityspinner", citySelected);
    }



}
