package com.example.rehan.tourista;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    Context context = this;
    Spinner citiesSpinner;
    Button nextButton;
    TextView phone_textView;
    TextView name_textView;
    TextView email_textView;
    Spinner city_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        createWidget();
        //phone_textView.setText(getIntent().getStringExtra("PHONE").toString().trim());
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, R.layout.cities_spinner_item_signup);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.cities_spinner_dropdown_item_signup);
// Apply the adapter to the spinner
        citiesSpinner.setAdapter(adapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name_textView.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Please enter your name!", Toast.LENGTH_LONG)
                            .show();
                }

                else if (email_textView.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Please enter your email!", Toast.LENGTH_LONG)
                            .show();
                }
                else if (city_spinner.getSelectedItem().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Please select city!", Toast.LENGTH_LONG)
                            .show();
                }
                else if (isEmailValid(email_textView.getText().toString().trim())==true && validateLetters(name_textView.getText().toString().trim())==true)
                {
                    Intent i = new Intent(context, SignupActivity1.class);
                    i.putExtra("PHONE", getIntent().getStringExtra("PHONE").toString().trim());
                    i.putExtra("NAME", name_textView.getText().toString());
                    i.putExtra("EMAIL", email_textView.getText().toString());
                    i.putExtra("CITY", city_spinner.getSelectedItem().toString());
                    startActivity(i);

                }
                else if(isEmailValid(email_textView.getText().toString().trim())==false)
                {
                    Toast.makeText(getApplicationContext(), "Email is invalid",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(validateLetters(name_textView.getText().toString().trim())==false)
                {
                    Toast.makeText(getApplicationContext(), "Name is invalid",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if (city_spinner.getSelectedItem().toString().trim().equalsIgnoreCase("Pick one")) {
                    Toast.makeText(getApplicationContext(), "Please select city",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    public static boolean validateLetters(String txt) {

        String regx = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    private void createWidget(){
        citiesSpinner = (Spinner) findViewById(R.id.cities_spinner_signup);
        nextButton = (Button) findViewById(R.id.next_btn_signup_xml);
        name_textView = (EditText)findViewById(R.id.name_txtview_signup);
        email_textView = (EditText)findViewById(R.id.email_txtview_signup);
        city_spinner = (Spinner)findViewById(R.id.cities_spinner_signup);
    }
}
