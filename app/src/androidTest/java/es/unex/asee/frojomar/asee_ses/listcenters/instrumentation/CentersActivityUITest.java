package es.unex.asee.frojomar.asee_ses.listcenters.instrumentation;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.centers.CentersActivity;



@LargeTest
public class CentersActivityUITest {


    @Rule
    public final ActivityTestRule<CentersActivity> mActivityRule = new ActivityTestRule<>(CentersActivity.class);


    @Test
    public void visualizarCentros(){
        // check that the number of RecyclerView elements is greater than 0
        if (getRVcount() > 0){
            //Check that RecyclerView elements have an R.id.image_center element
            onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.image_center))));
            //Check that RecyclerView elements have an R.id.name_center element
            onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.name_center))));
            //Check that RecyclerView elements have an R.id.adress_center element
            onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.adress_center))));
        }
    }

    private int getRVcount(){
        //change R.id.my_recycler_view with the name of the id of your RecyclerView
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.my_recycler_view);
        return recyclerView.getAdapter().getItemCount();
    }


}
