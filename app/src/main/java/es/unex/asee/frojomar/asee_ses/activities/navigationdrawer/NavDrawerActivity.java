package es.unex.asee.frojomar.asee_ses.activities.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.LoginActivity;
import es.unex.asee.frojomar.asee_ses.activities.SettingsActivity;
import es.unex.asee.frojomar.asee_ses.activities.centers.CentersActivity;

public abstract class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        //TODO poner nombre de la persona con sesión iniciada
//        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
//        TextView nameView= (TextView) findViewById(R.id.nav_header_title);
//        String name = preferences.getString("name","");
//        if(name.equals("")){
//            name=getResources().getString(R.string.nav_header_title);
//        }
//        nameView.setText(name);

        this.linkSpecificLayoutforActivity();
    }

    /**
     * This method, it's necessary in all the Activities that extends NavDrawerActivity, to link the
     * layout for this activity with the layouts for NavDrawerActivity class.
     */
    protected abstract void linkSpecificLayoutforActivity();

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        if(preferences.getString("login", "no").equals("yes")){
            mNavigationView.getMenu().findItem(R.id.init_session).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.close_session).setVisible(true);
        }
        else{
            mNavigationView.getMenu().findItem(R.id.init_session).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.close_session).setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           Intent intent= new Intent(this, SettingsActivity.class);
           startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.go_to_appointments) {
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.go_to_centers) {
            Intent intent= new Intent(this,CentersActivity.class);
            startActivity(intent);
        } else if (id == R.id.init_session) {
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.close_session) {
            SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            if(preferences.getString("login", "no").equals("yes")){
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Log.i("PRUEBA", "Closing session");
            }
            mNavigationView.getMenu().findItem(R.id.init_session).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.close_session).setVisible(false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
