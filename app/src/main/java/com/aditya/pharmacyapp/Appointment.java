package com.aditya.pharmacyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class Appointment extends AppCompatActivity implements View.OnClickListener {

    TextView datePicker,timePicker;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_tiltlecenter);
        View view = getSupportActionBar().getCustomView();

        TextView title = view.findViewById(R.id.title);
        title.setText("Appointment");

        ImageView imageButton = (ImageView) view.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        timePicker=findViewById(R.id.timePicker);
        datePicker=findViewById(R.id.datePicker);
        btnSubmit=findViewById(R.id.btnSubmit);

        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:
                Toast.makeText(this, "Appointment Fixed", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.datePicker:
                // Get Current Date
                int mYear,mMonth,mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                                datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.timePicker:
                int hour = 0;
                int min = 0;
                TimePickerDialog Tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeSet = "";
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            timeSet = "PM";
                        } else if (hourOfDay == 0) {
                            hourOfDay += 12;
                            timeSet = "AM";
                        } else if (hourOfDay == 12){
                            timeSet = "PM";
                        }else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minute < 10)
                            min = "0" + minute ;
                        else
                            min = String.valueOf(minute);

                        // Append in a StringBuilder
                        String aTime = String.valueOf(hourOfDay) + ':' +
                                min + " " + timeSet;
                        timePicker.setText(aTime);

                    }
                },hour,min,false);
                Tp.show();
                break;

        }
    }
}