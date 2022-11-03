package es.unex.asee.frojomar.asee_ses.activities.centers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;


public class CenterPagerAdapter extends FragmentStatePagerAdapter {

    private List<Center> centersItems;

    CenterPagerAdapter(FragmentManager fm, List<Center> centersItems) {
        super(fm);
        this.centersItems = centersItems;
    }

    @Override
    public Fragment getItem(int position) {
        Center center = centersItems.get(position);
        return DetailCentersFragment.newInstance(center);
    }

    @Override
    public int getCount() {
        return centersItems.size();
    }

}