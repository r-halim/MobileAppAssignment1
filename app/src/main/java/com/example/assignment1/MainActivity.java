package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking to the TD website, when the text view box is clicked
        TextView link = findViewById(R.id.td_reference);
        link.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://tools.td.com/mortgage-payment-calculator/"));
                startActivity(intent);
            }
        });
    }

    //Function for the EMI calculation
    public void EMI_Calculation(View view){
        double mortgage_principal_amount, interest_input, years_input;

        //Gets mortgage input by user and converts to double
        String mortgage = ((EditText) findViewById(R.id.mortgage_amount)).getText().toString();
        mortgage_principal_amount = Double.parseDouble(mortgage);

        //Gets interest input by user and converts to double; converting the value input to a decimal, and splits it over 12 months for the monthly interest
        String interest_rate = ((EditText) findViewById(R.id.interest_amount)).getText().toString();
        interest_input = Double.parseDouble(interest_rate);
        double interest_rate_monthly = (interest_input / 100) / 12;

        //Gets years input by user and converts to double; converting the years input to months
        String years = ((EditText) findViewById(R.id.years_amount)).getText().toString();
        years_input = Double.parseDouble(years);
        double months = years_input * 12;

        /* EMI formula used:
        (P × R × (1 + R)^N / ((1 + R)^N - 1)
        P - Principal Amount
        R - Rate of interest
        N - loan tenure */

        //Calculating EMI
        double calc_emi = ((mortgage_principal_amount * interest_rate_monthly) * ((Math.pow(1 + interest_rate_monthly, months)) / (Math.pow(1 + interest_rate_monthly, months)-1)));

        //Printing the result to the output_result
        TextView output_result = (TextView) findViewById(R.id.output_result);
        //Error catching in the event were the output value is negative
        if (calc_emi < 0) {
            output_result.setText("Invalid Input; Please ensure all inputs are positive.");
        }else{
            //outputting calculated EMI, formatted to 2 decimal places
            output_result.setText("The monthly payment would be: \n"+ (String.format("%.2f", calc_emi)));
        }

    }

}
