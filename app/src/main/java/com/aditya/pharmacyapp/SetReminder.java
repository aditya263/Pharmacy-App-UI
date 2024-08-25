package com.aditya.pharmacyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Objects;

public class SetReminder extends AppCompatActivity implements View.OnClickListener {

    EditText etMedicineName;
    LinearLayout layoutNight,layoutAfternoon,layoutMorning,linearReminder;
    RelativeLayout layoutNightReminder,layoutAfternoonReminder,layoutMorningReminder;
    TextView txtReminderTime,txtNightTime,txtAfternoonTime,txtMorningtime;
    Button btnSaveReminder;
    Intent intent;
    int count = 0;
    int count1 = 0;
    int count2 = 0;
    int hour = 0;
    int min = 0;
    TimePickerDialog Tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_tiltlecenter);
        View view = getSupportActionBar().getCustomView();

        TextView title = findViewById(R.id.title);
        title.setText("Set Reminder");

        ImageView imageButton = (ImageView) view.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtMorningtime=findViewById(R.id.txtMorningtime);
        txtAfternoonTime=findViewById(R.id.txtAfternoonTime);
        txtNightTime=findViewById(R.id.txtNightTime);
        btnSaveReminder=findViewById(R.id.btnSaveReminder);


        txtReminderTime=findViewById(R.id.txtReminderTime);
        layoutMorningReminder=findViewById(R.id.layoutMorningReminder);
        layoutAfternoonReminder=findViewById(R.id.layoutAfternoonReminder);
        layoutNightReminder=findViewById(R.id.layoutNightReminder);
        layoutMorning=findViewById(R.id.layoutMorning);
        layoutAfternoon=findViewById(R.id.layoutAfternoon);
        layoutNight=findViewById(R.id.layoutNight);
        etMedicineName=findViewById(R.id.etMedicineName);
        linearReminder=findViewById(R.id.linearReminder);

        layoutMorning.setOnClickListener(this);
        layoutAfternoon.setOnClickListener(this);
        layoutNight.setOnClickListener(this);
        layoutMorningReminder.setOnClickListener(this);
        layoutAfternoonReminder.setOnClickListener(this);
        layoutNightReminder.setOnClickListener(this);
        btnSaveReminder.setOnClickListener(this);


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSaveReminder:
                Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.layoutMorningReminder:

                Tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay >=3 && hourOfDay<12){
                            if (minute >=0 && minute <=59){
                                String min = "";
                                if (minute < 10)
                                    min = "0" + minute ;
                                else
                                    min = String.valueOf(minute);

                                String hourDay;
                                if (hourOfDay<10)
                                    hourDay = "0" +hourOfDay;
                                else
                                    hourDay = String.valueOf(hourOfDay);

                                txtMorningtime.setText(hourDay+":"+min+" "+"AM");
                            }
                        }else {
                            Toast.makeText(SetReminder.this, "Time for morning reminder is 3:00 AM to 11:59 AM", Toast.LENGTH_SHORT).show();
                        }

                    }
                },hour,min,false);
                Tp.show();
                break;

            case R.id.layoutAfternoonReminder:

                Tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay >=12 && hourOfDay<18){
                            Log.d("YourResponse","hour--"+hourOfDay);

                            if (hourOfDay>12){
                                hourOfDay-=12;
                            }

                            if (minute >=0 && minute <=59){
                                String min = "";
                                if (minute < 10)
                                    min = "0" + minute ;
                                else
                                    min = String.valueOf(minute);
                                 String hourDay= String.valueOf(0);
                                if (hourOfDay<10)
                                    hourDay = "0" +hourOfDay;

                                txtAfternoonTime.setText(hourDay+":"+min+" "+"PM");
                            }
                        }else {
                            Toast.makeText(SetReminder.this, "Time for morning reminder is 12:00 PM to 05:59 PM", Toast.LENGTH_SHORT).show();
                        }

                    }
                },hour,min,false);
                Tp.show();
                break;

            case R.id.layoutNightReminder:

                Tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay >=18 && hourOfDay<24){
                            Log.d("YourResponse","hour--"+hourOfDay);

                            hourOfDay-=12;

                            if (minute >=0 && minute <=59){
                                String min = "";
                                if (minute < 10)
                                    min = "0" + minute ;
                                else
                                    min = String.valueOf(minute);
                                String hourDay;
                                if (hourOfDay<10)
                                    hourDay = "0" +hourOfDay;
                                else
                                    hourDay = String.valueOf(hourOfDay);

                                txtNightTime.setText(hourDay+":"+min+" "+"PM");
                            }
                        }else {
                            Toast.makeText(SetReminder.this, "Time for morning reminder is 18:00 PM to 23:59 PM", Toast.LENGTH_SHORT).show();
                        }

                    }
                },hour,min,false);
                Tp.show();
                break;

            case R.id.layoutMorning:

                if (count==0){
                    count=1;
                    txtReminderTime.setVisibility(View.VISIBLE);
                    linearReminder.setVisibility(View.VISIBLE);
                    layoutMorningReminder.setVisibility(View.VISIBLE);
                    layoutMorning.setBackgroundResource(R.color.random);
                } else if (count==1){
                    count=0;
                    //txtReminderTime.setVisibility(View.GONE);
                    //linearReminder.setVisibility(View.GONE);
                    layoutMorningReminder.setVisibility(View.GONE);
                    layoutMorning.setBackgroundResource(R.color.transparent);
                }
                break;

            case R.id.layoutAfternoon:
                if (count1==0){
                    count1=1;
                    txtReminderTime.setVisibility(View.VISIBLE);
                    linearReminder.setVisibility(View.VISIBLE);
                    layoutAfternoonReminder.setVisibility(View.VISIBLE);
                    layoutAfternoon.setBackgroundResource(R.color.random);
                } else if (count1==1){
                    count1=0;
                    //txtReminderTime.setVisibility(View.GONE);
                    //linearReminder.setVisibility(View.GONE);
                    layoutAfternoonReminder.setVisibility(View.GONE);
                    layoutAfternoon.setBackgroundResource(R.color.transparent);
                }

                break;

            case R.id.layoutNight:
                if (count2==0){
                    count2=1;
                    txtReminderTime.setVisibility(View.VISIBLE);
                    linearReminder.setVisibility(View.VISIBLE);
                    layoutNightReminder.setVisibility(View.VISIBLE);
                    layoutNight.setBackgroundResource(R.color.random);
                } else if (count2==1){
                    count2=0;
                    //txtReminderTime.setVisibility(View.GONE);
                    //linearReminder.setVisibility(View.GONE);
                    layoutNightReminder.setVisibility(View.GONE);
                    layoutNight.setBackgroundResource(R.color.transparent);
                }

                break;
        }
    }
}