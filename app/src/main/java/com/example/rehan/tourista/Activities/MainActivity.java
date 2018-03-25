package com.example.rehan.tourista.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rehan.tourista.AppConfig;
import com.example.rehan.tourista.R;
import com.example.rehan.tourista.SQLiteHandler;
import com.example.rehan.tourista.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        JourneysFragment.OnFragmentInteractionListener,
        CostEstimator0Fragment.OnFragmentInteractionListener,
        CostEtimatorFragment.OnFragmentInteractionListener,
        AboutUsFragment.OnFragmentInteractionListener,
        TravelAgentsFragment.OnFragmentInteractionListener,
        BeTravelAgentFragment.OnFragmentInteractionListener,
        FeedbackFragment.OnFragmentInteractionListener {
    private GoogleMap mMap;
    private TextView txtName;
    private TextView txtPhone;
    private SQLiteHandler db;
    private SessionManager session;
    ProgressBar PBar;
    RequestQueue requestQueue;
    int counter = 0;

    //   private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tour");
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = mNavigationView.getHeaderView(0);

        txtName = (TextView) header.findViewById(R.id.t4);
        txtPhone = (TextView) header.findViewById(R.id.t5);
        PBar = (ProgressBar) findViewById(R.id.main_progressBar);
        db = new SQLiteHandler(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String phone = user.get("phone");
        txtName.setText(name);
        txtPhone.setText(phone);
        // Displaying the user details on the screen
        //txtName.setText("hello");
        //txtName.setText("gfvhgvh");
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigationView.setCheckedItem(R.id.nav_home);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        Button endTourButton = (Button) findViewById(R.id.end_tour_btn_main_activity_xml);
        endTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(i);
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_journeys) {
            Intent i = new Intent(this, JourneysActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_cost_estimator) {
            Intent i = new Intent(this, CostEstimator0Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_about_us) {
            Intent i = new Intent(this, AboutUsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_travel_agents) {
            Intent i = new Intent(this, TravelAgentsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_be_travel_agents) {
            Intent i = new Intent(this, BeTravelAgentActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_feedback) {
            Intent i = new Intent(this, FeedbackActivity.class);
            startActivity(i);
        }

//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.mainFrame, fragment);
//            ft.commit();
//        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // getSupportActionBar().setTitle(uri.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(31.4826352,74.0712785)));
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 150);
//        LatLng sydneyy = new LatLng(-34, 145);
//        LatLng Lahore = new LatLng(-78, 72);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Hotel").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
//        mMap.addMarker(new MarkerOptions().position(sydneyy).title("StayPoint").icon(BitmapDescriptorFactory.fromResource(R.drawable.staypoint)));
//        mMap.addMarker(new MarkerOptions().position(Lahore).title("StayPoint").icon(BitmapDescriptorFactory.fromResource(R.drawable.staypoint)));
//

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String name = marker.getTitle();
//                LatLng temp = marker.getPosition();
//                if (name.equalsIgnoreCase("Hotel")) {
//                    jump(1);
//                } else {
//                    jump(2);
//
//                }
//                return false;
//            }
//        });


        String tag_string_req = "req_login";
        PBar.setVisibility(View.GONE);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAP_LOCATIONS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Iterator<?> keys = jObj.keys();
                        while (keys.hasNext()) {
                            counter++;
                            String key = (String) keys.next();
                            if (jObj.get(key) instanceof JSONObject) {
                                JSONObject obj = new JSONObject(jObj.get(key).toString());
                                if (obj.has("hotel_id")) {
                                    String abc = obj.getString("hotel_location");
                                    String Array1[] = abc.split(",",2);

                                    LatLng LL = new LatLng(Double.parseDouble(Array1[0]),Double.parseDouble(Array1[1]));
                                    mMap.addMarker(new MarkerOptions().position(LL).title(obj.getString("hotel_name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));

                                } else if (obj.has("sp_id")) {
                                    String abc = obj.getString("sp_location");
                                    String Array1[] = abc.split(",",2);

                                    LatLng LL = new LatLng(Double.parseDouble(Array1[0]),Double.parseDouble(Array1[1]));
                                    mMap.addMarker(new MarkerOptions().position(LL).title(obj.getString("sp_name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.staypoint)));
                                } else {
                                    String abc = obj.getString("pa_location");
                                    String Array1[] = abc.split(",",2);

                                    LatLng LL = new LatLng(Double.parseDouble(Array1[0]),Double.parseDouble(Array1[1]));
                                    mMap.addMarker(new MarkerOptions().position(LL).title(obj.getString("pa_name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.petrolagency)));
                                }
                                // Toast.makeText(getApplicationContext(),obj.getString("hotel_id"),Toast.LENGTH_SHORT).show();

                            }
                        }
                       // Toast.makeText(getApplicationContext(), String.valueOf(counter), Toast.LENGTH_SHORT).show();


                    } else {
                        PBar.setVisibility(View.INVISIBLE);

                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    PBar.setVisibility(View.INVISIBLE);
                }catch (NumberFormatException j){
                    j.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    PBar.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                // hideDialog();
                PBar.setVisibility(View.INVISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        requestQueue.add(strReq);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    private void jump(int i) {
        if (i == 1) {
            Intent intent = new Intent(this, HotelsActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, StayPointsActivity.class);
            startActivity(intent);
        }
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
