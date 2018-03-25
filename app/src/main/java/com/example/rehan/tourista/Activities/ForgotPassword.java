package com.example.rehan.tourista.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rehan.tourista.R;

public class ForgotPassword extends AppCompatActivity {
    Context context=this;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        createWidget();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ForgotPassword1.class);
                startActivity(i);
            }
        });
    }

    private void createWidget(){
        nextButton = (Button) findViewById(R.id.next_btn_forgot_password_xml);
    }
}
