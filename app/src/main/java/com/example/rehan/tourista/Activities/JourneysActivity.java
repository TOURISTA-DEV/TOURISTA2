package com.example.rehan.tourista.Activities;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rehan.tourista.R;
import com.example.rehan.tourista.RecentRides;
import com.example.rehan.tourista.RecentToursAdapter;
import com.example.rehan.tourista.SQLiteHandler;
import com.example.rehan.tourista.static_classes.user_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class JourneysActivity extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    RequestQueue requestQueue;
    String recent_ride="http://www.touristaapp.com/APIs/recent_trips.php";
    Context context=this;
    RecentToursAdapter adapter;
    private SQLiteHandler db;
    ArrayList<trip> data=new ArrayList<>();
    HashMap<String, String> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys);
        createWidgets();
        initializeObjects();
        send_data_to_server();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new SQLiteHandler(getApplicationContext());
        user= db.getUserDetails();


    }
    private void createWidgets()
    {
        listView=(ListView)findViewById(R.id.list_view_journeys_activity_xml);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    private void initializeObjects()
    {
        requestQueue= Volley.newRequestQueue(context);
        adapter=new RecentToursAdapter(context,data);
    }
    public void send_data_to_server()
    {

        StringRequest request = new StringRequest(Request.Method.POST, recent_ride, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().contains("NoData"))
                {
                    Toast.makeText(context, "No trip Found", Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                   // Toast.makeText(context, "Found", Toast.LENGTH_SHORT).show();
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray user=jsonObject.getJSONArray("recent_trips");
                        trip _ref= new trip();
                        for (int i=0;i<user.length();i++)
                        {


                            JSONObject trip=user.getJSONObject(i);
                            _ref.setDrop_off(trip.getString("Drop_off"));
                            _ref.setPick_up(trip.getString("Pick_up"));
                            _ref.setTrip_no((String.valueOf(i+1)));
                            Log.i("Drop_", _ref.getDrop_off());
                            //_ref.setDrop_off("abc");

                      //      Toast.makeText(context, _ref.getDrop_off(), Toast.LENGTH_SHORT).show();
     //                     Toast.makeText(context, trip.getString("Drop_off"), Toast.LENGTH_SHORT).show();
//

                          data.add(_ref);
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Internet Connection Fialed",Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("tourist_id_FK", user.get("id"));

                return parameters;
            }

        };

//        try {
//
//            progressDialog.setTitle("Adding Details");
//            progressDialog.setMessage("Please Wait");
//            progressDialog.show();
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        requestQueue.add(request);
    }
}