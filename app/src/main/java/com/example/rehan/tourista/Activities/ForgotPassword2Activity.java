package com.example.rehan.tourista.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rehan.tourista.R;

public class ForgotPassword2Activity extends AppCompatActivity {
    Context context = this;
    Button doneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        createWidget();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void createWidget(){
        doneButton = (Button)findViewById(R.id.done_btn_forgot_password2_xml);
    }
}
