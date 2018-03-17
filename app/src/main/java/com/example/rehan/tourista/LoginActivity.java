package com.example.rehan.tourista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Context context=this;
    Button nextButton;
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createWidget();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Please enter phone number!", Toast.LENGTH_LONG)
                            .show();
                }
                else{
                    Intent i = new Intent(context, LoginActivity1.class);
                    i.putExtra("PHONE", phoneEditText.getText().toString());
                    startActivity(i);
                }

            }
        });
    }
    private void createWidget(){
        nextButton =(Button)findViewById(R.id.next_btn_login_xml);
        phoneEditText = (EditText)findViewById(R.id.phone_edit_text_login_xml);

        Toast.makeText(getApplicationContext(),
                "final changing !", Toast.LENGTH_LONG)
                .show();
    }

}
