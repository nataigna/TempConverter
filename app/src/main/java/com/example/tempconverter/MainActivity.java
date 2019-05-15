package com.example.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    //define variables for the widgets
    private TextView textViewCelsius;
    private EditText Fahrenheit;


    // define the SharedPreferences object
    private SharedPreferences savedValues;

    // define instance variables that should be saved
    private String fahrenheitString = " ";
    private float celsius= -17.78f;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to the widgets

        Fahrenheit=(EditText)findViewById(R.id.Fahrenheit);
        textViewCelsius=(TextView) findViewById(R.id.textViewCelsius);

        //set the listeners
        Fahrenheit.setOnEditorActionListener((TextView.OnEditorActionListener) this);

        //get SharedPreferences object
        savedValues= getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public void onPause(){
        //save the instance variables
        SharedPreferences.Editor editor= savedValues.edit();
        editor.putString("fahrenheitString",fahrenheitString);
        editor.putFloat("celsius", celsius);
        editor.commit();

        super.onPause();



    }
    @Override
    public void onResume(){
        super.onResume();

        //get the instance variables
        fahrenheitString=savedValues.getString("fahrenheitString", " ");
        celsius=savedValues.getFloat("celsius", -17.78f);

        //set the fahrenheit widget
        Fahrenheit.setText(fahrenheitString);

        calculateAndDisplay();


    }

    private void calculateAndDisplay() {
        fahrenheitString = Fahrenheit.getText().toString();
        float f;
        if (fahrenheitString.equals(" ")) {
            f = 0;

        } else {
            f = Float.parseFloat(fahrenheitString);

        }
        //calculate celsius
        float c = (f - 32) * 5 / 9;

        //display the result
        textViewCelsius.setText(String.valueOf(c));



    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;


    }
}
