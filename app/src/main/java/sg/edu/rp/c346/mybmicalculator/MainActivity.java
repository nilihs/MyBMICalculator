package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDescription = findViewById(R.id.textViewDescription);

        etWeight.requestFocus();

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etHeight.getText().toString().trim().length() > 0 && etWeight.getText().toString().trim().length() > 0 ) {
                    Float bmi = (Float.parseFloat(etWeight.getText().toString())) / (Float.parseFloat(etHeight.getText().toString())) / (Float.parseFloat(etHeight.getText().toString()));
                    tvBMI.setText("Last Calculated BMI: " + bmi.toString());
                    tvDate.setText("Last Calculated Date: " + datetime);
                    etWeight.setText("");
                    etHeight.setText("");

                    if (bmi < 18.5) {
                        tvDescription.setText("You are underweight");
                    }

                    else if (bmi < 25.0) {
                        tvDescription.setText("Your BMI is normal");
                    }

                    else if (bmi < 30) {
                        tvDescription.setText("You are overweight");
                    }

                    else if (bmi >= 30) {
                        tvDescription.setText("You are obese");
                    }

                }

                else {
                    Toast.makeText(MainActivity.this, "Please input height and weight to calculate BMI", Toast.LENGTH_LONG).show();
                }
            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBMI.setText("Last Calculated BMI: ");
                tvDate.setText("Last Calculated Date: ");
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get the user input from the EditText and store it in a variable
        String date = tvDate.getText().toString();
        String bmi = tvBMI.getText().toString();
        String description = tvDescription.getText().toString();

        // Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1d: Add the key-value pair
        // The value should be from the variable defined in Step 1a
        prefEdit.putString("date", date);
        prefEdit.putString("bmi", bmi);
        prefEdit.putString("description", description);

        // Step 1e: Call commit() method to save the changes into SharedPreferences
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2b: Retrieve the saved data from the SharedPreferences object
        String msgDate = prefs.getString("date", "Last Calculated Date: ");
        String msgBMI = prefs.getString("bmi", "Last Calculated BMI");
        String msgDescription = prefs.getString("description", "");

        // Step 2c: Update the UI element with the value
        tvDate.setText(msgDate);
        tvBMI.setText(msgBMI);
        tvDescription.setText(msgDescription);


    }
}
