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
        link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://tools.td.com/mortgage-payment-calculator/"));
            startActivity(intent);
        });
    }

    //Function for the EMI calculation
    @SuppressLint({"SetTextI18n", "DefaultLocale"}) // to ignore warnings
    public void EMI_Calculation(View view) {
        double mortgage_principal_amount, interest_input, years_input;

        // Linking the result to the output_result. Also used to display input errors to the user
        TextView output_result = (TextView) findViewById(R.id.output_result);

        // Gets all three inputs from user (mortgage amount, interest amount and number of years)
        String mortgage = ((EditText) findViewById(R.id.mortgage_amount)).getText().toString();
        String interest_rate = ((EditText) findViewById(R.id.interest_amount)).getText().toString();
        String years = ((EditText) findViewById(R.id.years_amount)).getText().toString();

        // Checks if any of the inputs are empty
        if (mortgage.isEmpty() || interest_rate.isEmpty() || years.isEmpty()) {
            output_result.setText("Please enter valid values for all inputs.");
            return; // Exit the method early, since at least 1 input is not valid
        }

        // Converts all three inputs to double
        mortgage_principal_amount = Double.parseDouble(mortgage);
        interest_input = Double.parseDouble(interest_rate);
        years_input = Double.parseDouble(years);

        // Checks if any of the input values are not positive
        if (mortgage_principal_amount <= 0 || interest_input <= 0 || years_input <= 0) {
            output_result.setText("Please ensure all inputs are positive.");
            return; // Exit the method early, since at least 1 input is not valid
        }

        // Calculates the number of months and monthly interest rate
        double interest_rate_monthly = (interest_input / 100) / 12;
        double months = years_input * 12;

        // EMI formula used:
        // (P × R × (1 + R)^N / ((1 + R)^N - 1)
        // P - Principal Amount
        // R - Rate of interest
        // N - loan tenure

        // Calculating EMI and outputting the result
        double calc_emi = ((mortgage_principal_amount * interest_rate_monthly) * ((Math.pow(1 + interest_rate_monthly, months)) / (Math.pow(1 + interest_rate_monthly, months) - 1)));
        output_result.setText("The monthly payment would be: \n" + (String.format("%.2f", calc_emi)));

    }

}
