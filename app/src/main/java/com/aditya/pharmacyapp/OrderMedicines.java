package com.aditya.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class OrderMedicines extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout expandView;
    LinearLayout linearlayout;
    ImageView arrow;
    TextView searchView;
    int count = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_medicines);

        Objects.requireNonNull( getSupportActionBar() ).setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_tiltlecenter);
        View view =getSupportActionBar().getCustomView();

        TextView title= findViewById(R.id.title);
        title.setText("Order Medicines");

        ImageView imageButton= (ImageView)view.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        expandView=findViewById(R.id.expandView);
        linearlayout=findViewById(R.id.linearlayout);
        arrow=findViewById(R.id.arrow);
        searchView=findViewById(R.id.searchView);

        expandView.setOnClickListener(this);
        searchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.expandView:
                if (count==0){
                    count = 1;
                    arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    linearlayout.setVisibility(View.VISIBLE);
                }else if (count==1){
                    count = 0;
                    arrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                    linearlayout.setVisibility(View.GONE);
                }
                break;

            case R.id.searchView:
                intent=new Intent(OrderMedicines.this,SearchMedicine.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.cart:
                intent =new Intent(OrderMedicines.this,CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}