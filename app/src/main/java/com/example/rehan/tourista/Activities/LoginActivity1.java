package com.example.rehan.tourista.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity1 extends AppCompatActivity {
    Context context = this;
    TextView dontHaveAccount_textView;
    TextView forgotPassword_textView;
    Button signin_Button;
    TextView password_textView;
    TextView phone_textView;
    ProgressBar PBar;
    private SQLiteHandler db;
    private SessionManager session;
    RequestQueue requestQueue;
   // private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        createWidget();
        signin_Button = (Button) findViewById(R.id.signin_btn_login1_xml);
        // Progress bar

        phone_textView.setText(getIntent().getStringExtra("PHONE").toString().trim());
        // Session manager
        session = new SessionManager(getApplicationContext());
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity1.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        dontHaveAccount_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SignupActivity.class);
                i.putExtra("PHONE", getIntent().getStringExtra("PHONE").toString().trim());
                startActivity(i);


                startActivity(i);
            }
        });

        forgotPassword_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ForgotPassword.class);
                startActivity(i);
            }
        });

        signin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_textView = (TextView) findViewById(R.id.password_txt_view_login1_xml);
               // String email = inputEmail.getText().toString().trim();
                String phone = getIntent().getStringExtra("PHONE").toString().trim();
                String password = password_textView.getText().toString().trim();

                // Check for empty data in the form
                if (!phone.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(phone, password);

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }

    private void checkLogin(final String phone, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        //Toast.makeText(getApplicationContext(),"Signing In ... ",Toast.LENGTH_SHORT).show();
        PBar.setVisibility(View.VISIBLE);
        signin_Button.setEnabled(false);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

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

                            Intent intent = new Intent(LoginActivity1.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();

                        // Launch main activity

                    } else {
                        PBar.setVisibility(View.INVISIBLE);
                        signin_Button.setEnabled(true);

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
                    signin_Button.setEnabled(true);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              //  Log.e(TAG, "Login Error: " + error.getMessage());
//

                Toast.makeText(getApplicationContext(),
                        "No Internet Connection!", Toast.LENGTH_LONG).show();
               // hideDialog();
                PBar.setVisibility(View.INVISIBLE);
                signin_Button.setEnabled(true);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        requestQueue.add(strReq);
    }



    private void createWidget(){
        dontHaveAccount_textView = (TextView)findViewById(R.id.dont_have_account_txt_view_login1_xml);
        forgotPassword_textView = (TextView) findViewById(R.id.forgot_password_txt_view_login1_xml);
        signin_Button = (Button) findViewById(R.id.signin_btn_login1_xml);
        requestQueue= Volley.newRequestQueue(context);
        PBar = (ProgressBar)findViewById(R.id.login_progressBar);
        phone_textView = (TextView)findViewById(R.id.phone_edit_text_login1_xml);

    }
}
