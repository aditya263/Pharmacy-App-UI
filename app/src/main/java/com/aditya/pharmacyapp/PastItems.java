package com.aditya.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class PastItems extends AppCompatActivity implements View.OnClickListener {

    TextView txtchange,txtAddress;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_items);

        Objects.requireNonNull( getSupportActionBar() ).setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_tiltlecenter);
        View view =getSupportActionBar().getCustomView();

        TextView title= findViewById(R.id.title);
        title.setText("Past Items");

        ImageView imageButton= (ImageView)view.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtchange=findViewById(R.id.txtchange);
        txtAddress=findViewById(R.id.txtAddress);

        SharedPreferences mPref= getSharedPreferences("session", Context.MODE_PRIVATE);
        String postalCode= mPref.getString("PostalCode", null);
        String locality= mPref.getString("Locality", null);
        String address = postalCode + " " + locality;
        txtAddress.setText(address);

        txtchange.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        String[] years = {"Qty 1","Qty 2","Qty 3","Qty 4"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinner.setAdapter(adapter3);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtchange:
                intent=new Intent(PastItems.this,ChooseAddress.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_past_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search_item:
                intent =new Intent(PastItems.this,SearchMedicine.class);
                startActivity(intent);
                return true;
            case R.id.cart:
                intent =new Intent(PastItems.this,CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}