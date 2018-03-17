package com.example.rehan.tourista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SignupActivity extends AppCompatActivity {
    Context context = this;
    Spinner citiesSpinner;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        createWidget();
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
                Intent i = new Intent(context, SignupActivity1.class);
                startActivity(i);
            }
        });
    }
    private void createWidget(){
        citiesSpinner = (Spinner) findViewById(R.id.cities_spinner_signup);
        nextButton = (Button) findViewById(R.id.next_btn_signup_xml);
    }
}
