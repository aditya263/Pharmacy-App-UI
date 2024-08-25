package com.aditya.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChooseAddress extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final int REQUEST_LOCATION = 1;
    private final int REQUEST_CHECK_CODE=8989;
    private LocationManager locationManager;
    TextView txtCurrentLocation,txtInvalidPincode;
    Button check;
    EditText etPincode;
    String etPinCode;
    LinearLayout linearLayoutAddNewAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_tiltlecenter);
        View view = getSupportActionBar().getCustomView();

        TextView title = findViewById(R.id.title);
        title.setText("Choose your Location");

        ImageView imageButton = (ImageView) view.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pref = getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = pref.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        txtCurrentLocation = findViewById(R.id.txtCurrentLocation);
        linearLayoutAddNewAddress = findViewById(R.id.linearLayoutAddNewAddress);
        txtInvalidPincode = findViewById(R.id.txtInvalidPincode);
        check = findViewById(R.id.check);
        etPincode = findViewById(R.id.etPincode);

        //add permission
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        txtCurrentLocation.setOnClickListener(this);
        check.setOnClickListener(this);
        linearLayoutAddNewAddress.setOnClickListener(this);


        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInvalidPincode.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInvalidPincode.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                txtInvalidPincode.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linearLayoutAddNewAddress:
                Intent intent=new Intent(ChooseAddress.this,AddNewAddress.class);
                startActivity(intent);
                break;

            case R.id.check:
                etPinCode= etPincode.getText().toString();
                if (etPinCode.length()!=6){
                    txtInvalidPincode.setVisibility(View.VISIBLE);
                }else if (etPinCode.length()==6){
                    getDistrict(etPinCode);
                }
                break;

            case R.id.txtCurrentLocation:
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //write function to enable gps
                    //OnGPS();

                    LocationRequest request=new LocationRequest()
                            .setFastestInterval(1500)
                            .setInterval(3000)
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(request);

                    Task<LocationSettingsResponse> result = LocationServices
                            .getSettingsClient(this).checkLocationSettings(builder.build());

                    result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                            try {
                                task.getResult(ApiException.class);
                            } catch (ApiException e) {
                                switch (e.getStatusCode()){
                                    case LocationSettingsStatusCodes
                                            .RESOLUTION_REQUIRED:

                                        try {
                                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                            resolvableApiException.startResolutionForResult(ChooseAddress.this,REQUEST_CHECK_CODE);
                                        } catch (IntentSender.SendIntentException ex) {
                                            ex.printStackTrace();
                                        } catch (ClassCastException ex){

                                        }
                                        break;

                                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                        break;
                                }
                            }
                        }
                    });
                    
                } else {
                    //GPS is already on
                    getLocation();
                }
                break;
        }
    }

    private void getDistrict(String pinCode){

        progressDialog.setMessage( "Processing..." );
        showDialog();

        RequestQueue queue = Volley.newRequestQueue( this );

        String url ="http://www.postalpincode.in/api/pincode/"+pinCode;


        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                int res=0;
                String msg = null;

                try {
                    JSONObject obj = new JSONObject( response );

                    JSONArray data = obj.getJSONArray( "PostOffice" );
                    int i = 0;
                    JSONObject addressdata=data.getJSONObject(i);

                    String District = addressdata.getString("District");
                    String State = addressdata.getString("State");
                    String Country = addressdata.getString("Country");

                    pref.edit().putString("PostalCode", etPinCode).apply();
                    pref.edit().putString("Locality", District).apply();

                    Intent intent = new Intent(ChooseAddress.this,MainActivity.class);
                /*intent.putExtra("PostalCode",address.getPostalCode());
                intent.putExtra("Locality",address.getLocality());*/
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d( "YourResponse","response-"+response );
                    Log.d("YourResponse", "ERROR-- "+String.valueOf( e ) );
                    //Toast.makeText( getApplicationContext(), "Transaction data not found.", Toast.LENGTH_LONG ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.d( "YourResponse", "error response-" + error );
                Toast.makeText(getApplicationContext(), "Poor Internet Connection", Toast.LENGTH_LONG).show();

            }
        } )
       /* {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        }*/;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        queue.add(stringRequest);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else {

            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps!=null){
                double lat=LocationGps.getLatitude();
                double longi =LocationGps.getLongitude();
                Log.d("YourResponse","lat--"+lat);
                Log.d("YourResponse","longi--"+longi);

                getAddress(lat,longi);
            }else if (LocationNetwork!=null){
                double lat=LocationNetwork.getLatitude();
                double longi =LocationNetwork.getLongitude();

                getAddress(lat,longi);
            }else if (LocationPassive!=null){
                double lat=LocationPassive.getLatitude();
                double longi =LocationPassive.getLongitude();

                getAddress(lat,longi);
            }else {
                Toast.makeText(this, "Can't get your location", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void getAddress(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                Log.d("YourResponse","getPostalCode--"+address.getPostalCode());
                Log.d("YourResponse","getLocality--"+address.getLocality());

                pref.edit().putString("PostalCode", address.getPostalCode()).apply();
                pref.edit().putString("Locality", address.getLocality()).apply();

                Intent intent = new Intent(ChooseAddress.this,MainActivity.class);
                /*intent.putExtra("PostalCode",address.getPostalCode());
                intent.putExtra("Locality",address.getLocality());*/
                startActivity(intent);
                finish();
            }
        } catch (IOException e) {
            Log.e("tag", Objects.requireNonNull(e.getMessage()));
        }

    }

    protected void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    protected void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}