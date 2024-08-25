package com.aditya.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    ImageView notification,cart;
    FlipperLayout imageSlider;
    RelativeLayout askDoctor,orderMedicines,takeAppointment,rlReminder;
    LinearLayout pastOrder,pastItem;
    TextView searchView,txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askDoctor=findViewById(R.id.askDoctor);
        rlReminder=findViewById(R.id.rlReminder);
        notification=findViewById(R.id.notification);
        cart=findViewById(R.id.cart);
        orderMedicines=findViewById(R.id.orderMedicines);
        imageSlider=findViewById(R.id.imageSlider);
        pastOrder=findViewById(R.id.pastOrder);
        pastItem=findViewById(R.id.pastItem);
        searchView=findViewById(R.id.searchView);
        takeAppointment=findViewById(R.id.takeAppointment);
        txtAddress=findViewById(R.id.txtAddress);
        setLayout();


        /*Intent intent =getIntent();
        String postalCode =intent.getStringExtra("PostalCode");
        String locality =intent.getStringExtra("Locality");
        String address = postalCode + " " + locality;
        Log.d("YourResponse","add--"+address);
        txtAddress.setText(address);*/

        notification.setOnClickListener(this);
        cart.setOnClickListener(this);
        askDoctor.setOnClickListener(this);
        rlReminder.setOnClickListener(this);
        orderMedicines.setOnClickListener(this);
        takeAppointment.setOnClickListener(this);
        pastOrder.setOnClickListener(this);
        pastItem.setOnClickListener(this);
        searchView.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.notification:
                intent =new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
                break;

            case R.id.cart:
                intent =new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
                break;

            case R.id.rlReminder:
                intent =new Intent(MainActivity.this,DosageReminder.class);
                startActivity(intent);
                break;

            case R.id.takeAppointment:
                intent =new Intent(MainActivity.this,Appointment.class);
                startActivity(intent);
                break;

            case R.id.askDoctor:
                intent=new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent);
                break;

            case R.id.pastOrder:
                intent=new Intent(MainActivity.this,Orders.class);
                startActivity(intent);
                break;

            case R.id.pastItem:
                intent=new Intent(MainActivity.this,PastItems.class);
                startActivity(intent);
                break;

            case R.id.orderMedicines:
                intent=new Intent(MainActivity.this,OrderMedicines.class);
                startActivity(intent);
                break;

            case R.id.searchView:
                intent=new Intent(MainActivity.this,SearchMedicine.class);
                startActivity(intent);
                break;

            case R.id.txtAddress:
                intent=new Intent(MainActivity.this,ChooseAddress.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void setLayout() {
        /*String url[]=new String[]{
                "https://cdn-w.medlife.com/2020/05/15-Off-Offer-page-Main.jpg",
                "https://www.couponlisty.com/wp-content/uploads/2019/10/Medlife-Wallet-Offers.jpg",
                "https://i.pinimg.com/originals/46/0e/e4/460ee4fa3a8de21b3aab2bc866a0071c.png",
                "https://ecouponshop.com/wp-content/uploads/2019/10/1mg-coupon-code-and-offers-848x470.jpg"
        };*/

        int[] url =new int[]{
                R.drawable.offer_one,R.drawable.offer_rwo,R.drawable.offer_three
        };

        for (int i=0;i<url.length;i++){
            FlipperView view=new FlipperView(getBaseContext());
            view.setImageUrl(String.valueOf(url[i]));
            imageSlider.addFlipperView(view);
            view.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                @Override
                public void onFlipperClick(FlipperView flipperView) {
                    Toast.makeText(MainActivity.this, ""+(imageSlider.getCurrentPagePosition()+1), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mPref= getSharedPreferences("session", Context.MODE_PRIVATE);
        String postalCode= mPref.getString("PostalCode", null);
        String locality= mPref.getString("Locality", null);
        String address = postalCode + " " + locality;
        txtAddress.setText(address);
    }
}