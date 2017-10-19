package fr.epsi.ibmworkshopepsi2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import fr.epsi.ibmworkshopepsi2017.Tasks.AuthenticationTask;
import fr.epsi.ibmworkshopepsi2017.Tasks.CallAPI;
import fr.epsi.ibmworkshopepsi2017.Tasks.MyCallBack;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ROOT_LOGIN = "Tester";
    public static final String ROOT_PASSWORD = "test";
    public static Boolean userIsLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userIsLoggedIn = false;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        Button connectButton = (Button) findViewById(R.id.login_validate_btn);
        final TextView loginTextView = (TextView) findViewById(R.id.login_login_txt);
        final TextView passwordTextView = (TextView) findViewById(R.id.login_password_txt);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!userIsLoggedIn) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //On get the id (new task)
                    CallAPI callAPI = new CallAPI(getApplicationContext(), new MyCallBack() {
                        @Override
                        public void callback(String pText) {

                            try {
                                JSONObject response = new JSONObject(pText);
                                boolean connect = response.getBoolean("success");
                                if (connect) {
                                    AlertDialog d = Utilities.buildDialog("Success", "Successfully logged in", MainActivity.this);
                                    userIsLoggedIn = true;
                                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                                    d.cancel();

                                    //Getting infos
                                    JSONObject res =  (JSONObject) response.getJSONArray("res").get(0);
                                    String storageCenterID = res.getString("StorageCenterID");
                                    String deliveryManID = res.getString("DeliveryManID");
                                    HashMap<String, String> tuple = new HashMap<String, String>();
                                    tuple.put(storageCenterID, deliveryManID);
                                    MainViewModel.getInstance().setTupleDeliveryManCenterIDs(tuple);

                                } else {

                                    userIsLoggedIn = false;
                                    Utilities.buildDialog("Error", "Failed to log in", MainActivity.this);
                                    //v.invalidate();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });

                    HashMap<String, String> params = new HashMap<>();
                    params.put("login", loginTextView.getText().toString());
                    params.put("pass", passwordTextView.getText().toString());
                    String stringifiedParams = "";
                    try {
                        stringifiedParams = CallAPI.getPostDataString(params);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    callAPI.execute("http://yoanmercier.fr/tests/connection.php", stringifiedParams);


                }
            });
        }
    }

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

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list_deliveries) {
            Intent intent = new Intent(this, DeliveriesListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_scan) {
            Intent scanIntent = new Intent(this, ScanActivity.class);
            startActivity(scanIntent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        if (userIsLoggedIn) {
            final TextView loginTextView = (TextView) findViewById(R.id.login_login_txt);
            final TextView passwordTextView = (TextView) findViewById(R.id.login_password_txt);
            final Button connectButton = (Button) findViewById(R.id.login_validate_btn);
            final TextView label1 = (TextView) findViewById(R.id.login_label);
            final TextView label2 = (TextView) findViewById(R.id.password_label);
            label1.setVisibility(View.INVISIBLE);
            label2.setVisibility(View.INVISIBLE);
            loginTextView.setVisibility(View.INVISIBLE);
            passwordTextView.setVisibility(View.INVISIBLE);
            connectButton.setVisibility(View.INVISIBLE);
            super.onResume();
        } else {
            super.onResume();
        }
    }
}
