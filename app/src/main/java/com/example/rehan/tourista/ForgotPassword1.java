package com.example.rehan.tourista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPassword1 extends AppCompatActivity {
    Context context = this;
    Button nxtButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);
        createWidget();
        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ForgotPassword2Activity.class);
                startActivity(i);
            }
        });
    }

    private void createWidget(){
        nxtButton = (Button)findViewById(R.id.next_btn_forgot_password1_xml);
    }
}
