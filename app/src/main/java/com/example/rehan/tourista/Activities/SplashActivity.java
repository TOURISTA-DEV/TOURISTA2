package com.example.rehan.tourista.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rehan.tourista.MainActivity;
import com.example.rehan.tourista.R;
import com.example.rehan.tourista.SQLiteHandler;
import com.example.rehan.tourista.SessionManager;

public class SplashActivity extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms
    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        createWidget();
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {
                    if (session.isLoggedIn()){
                        startActivity(new Intent(SplashActivity.this,
                                MainActivity.class));
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this,
                                LoginActivity.class));
                    }
                    finish();
                }
            };
        };
        splashTread.start();
    }
    private void createWidget(){
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
    }
}
