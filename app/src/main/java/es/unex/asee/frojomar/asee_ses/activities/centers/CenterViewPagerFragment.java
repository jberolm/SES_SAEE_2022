package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Center;

public class CenterViewPagerFragment extends Fragment {

    private static final String EXTRA_INITIAL_ITEM_POS = "initial_item_pos";
    private static final String EXTRA_CENTERS_ITEMS = "centers_items";

    public CenterViewPagerFragment() {
        // Required empty public constructor
    }

    public static CenterViewPagerFragment newInstance(int currentItem, ArrayList<Center> centersItems) {
        CenterViewPagerFragment centerViewPagerFragment = new CenterViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_INITIAL_ITEM_POS, currentItem);
        bundle.putParcelableArrayList(EXTRA_CENTERS_ITEMS, centersItems);
        centerViewPagerFragment.setArguments(bundle);
        return centerViewPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
        setSharedElementReturnTransition(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_centers_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int currentItem = getArguments().getInt(EXTRA_INITIAL_ITEM_POS);
        ArrayList<Center> centersItems = getArguments().getParcelableArrayList(EXTRA_CENTERS_ITEMS);

        CenterPagerAdapter centerPagerAdapter = new CenterPagerAdapter(getChildFragmentManager(), centersItems);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.centers_view_page);
        viewPager.setAdapter(centerPagerAdapter);
        viewPager.setCurrentItem(currentItem);
    }
}
