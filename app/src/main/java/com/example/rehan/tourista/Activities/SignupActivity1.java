package com.example.rehan.tourista.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rehan.tourista.AppConfig;
import com.example.rehan.tourista.MainActivity;
import com.example.rehan.tourista.R;
import com.example.rehan.tourista.SQLiteHandler;
import com.example.rehan.tourista.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity1 extends AppCompatActivity {

    String phone;
    String name;
    String email;
    TextView password_textView;
    TextView confirm_password_textView;
    String city;
    Button signUp_Button;
    private SessionManager session;
    private SQLiteHandler db;
    ProgressBar PBar;
    Context context = this;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        createWidget();
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity1.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        signUp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String email = inputEmail.getText().toString().trim();
                //String phone = getIntent().getStringExtra("PHONE").toString().trim();
                //String password = password_textView.getText().toString().trim();

                // Check for empty data in the form
                if (! password_textView.getText().toString().trim().isEmpty() && !confirm_password_textView.getText().toString().trim().isEmpty() && password_textView.getText().toString().trim().equals(confirm_password_textView.getText().toString().trim())) {
                    // login user
                    checkSignUp(phone, name, email, city, password_textView.getText().toString().trim());

                }
                else if(! password_textView.getText().toString().trim().equals(confirm_password_textView.getText().toString().trim())){
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Password mismatch", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void checkSignUp(final String phone, final String name , final String email , final String city , final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_SignUp";

        //Toast.makeText(getApplicationContext(),"Signing In ... ",Toast.LENGTH_SHORT).show();
        PBar.setVisibility(View.VISIBLE);
        signUp_Button.setEnabled(false);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Log.d(TAG, "Login Response: " + response.toString());
                //hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        Toast.makeText(getApplicationContext(),
                                "Signed up successfully!", Toast.LENGTH_LONG).show();
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String phone = user.getString("phone");
                        String city = user.getString("city");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, phone, email, city, uid, created_at);
                        finish();
                        Intent intent = new Intent(SignupActivity1.this,
                                MainActivity.class);
                        startActivity(intent);


                        // Launch main activity

                    } else {
                        PBar.setVisibility(View.INVISIBLE);
                        signUp_Button.setEnabled(true);

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
                    signUp_Button.setEnabled(true);

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
                signUp_Button.setEnabled(true);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("name", name);
                params.put("email", email);
                params.put("city", city);
                params.put("password", password_textView.getText().toString().trim());
                return params;
            }

        };

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        requestQueue.add(strReq);
    }

    private void createWidget(){
        password_textView = (EditText)findViewById(R.id.password_txtview_signup1);
        confirm_password_textView = (EditText)findViewById(R.id.confirm_password_txtview_signup1);
        phone = getIntent().getStringExtra("PHONE").toString().trim();
        name = getIntent().getStringExtra("NAME").toString().trim();
        email = getIntent().getStringExtra("EMAIL").toString().trim();
        city = getIntent().getStringExtra("CITY").toString().trim();
        signUp_Button = (Button) findViewById(R.id.done_btn_forgot_password2_xml);
        requestQueue= Volley.newRequestQueue(context);
        PBar = (ProgressBar)findViewById(R.id.signup_progressBar);
    }
}
