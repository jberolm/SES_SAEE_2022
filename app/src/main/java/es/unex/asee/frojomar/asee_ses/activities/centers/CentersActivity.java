package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.navigationdrawer.NavDrawerActivity;
import es.unex.asee.frojomar.asee_ses.model.Center;

public class CentersActivity extends NavDrawerActivity implements ListCentersFragment.CentersInterface {
    private static final String TAG = "CentersActivity";

    private ListCentersFragment mListCentersFragment;
    private DetailCentersFragment mDetailsCentersFragment;
    private CenterViewPagerFragment mCenterViewPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");

        Fragment f= (Fragment) getSupportFragmentManager().findFragmentByTag("to_detail");
        if(f==null){
            f= (Fragment) getSupportFragmentManager().findFragmentByTag("to_list");
            if(f==null){
            mListCentersFragment = new ListCentersFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

            Log.i(TAG, "Cargando el mListCentersFragment");
            fragmentTransaction.add(R.id.centers_fragments_container, mListCentersFragment, "to_list");
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
        ConstraintLayout childLayout = (ConstraintLayout) View.inflate(this, R.layout.activity_centers, null);
        mainLayout.addView(childLayout);
    }


    public boolean isInTwoPaneMode() {

        return findViewById(R.id.centers_fragments_container) == null;

    }

    // Display selected Center information
    public void onCenterSelected(Integer pos,Center center, List<Center> centers) {

        Log.i(TAG, "Entered onCenterSelected(" + center.getId() + ")");

        // If there is no DetailCentersFragment instance, then create one



        // If in single-pane mode, replace single visible Fragment

        mCenterViewPagerFragment = CenterViewPagerFragment.newInstance(pos, (ArrayList<Center>) centers);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.centers_fragments_container, mCenterViewPagerFragment, "to_detail").addToBackStack(null).commit();

        // execute transaction now
        getSupportFragmentManager().executePendingTransactions();




    }

}
