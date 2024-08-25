package com.aditya.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.aditya.pharmacyapp.Adapter.ListViewAdapter;
import com.aditya.pharmacyapp.ModelClass.MedicineNames;

import java.util.ArrayList;

public class SearchMedicine extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] animalNameList;
    ArrayList<MedicineNames> arraylist = new ArrayList<MedicineNames>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);

        animalNameList = new String[]{"Pan 40mg 15'S Tablets", "Aceclofenac 100mg of 10 Tablets", "Aceclo Plus 150mg strip of 15 Tablets",
                "Paracetamol 100mg of 15 Tablets", "Naproxen 140mg 15'S Tablets", "Diclofenac 350mg strip of 10 Tablets", "Ecosprin 75mg strip of 15 Tablets",
                "Shelcal 100mg of 15 Tablets", "HCQS 140mg 15'S Tablets","Evion 150mg strip of 15 Tablets","EverHerb Ashwagandha","LivEasy Hand Sanitizer",
                "Dettol Antiseptic Liquid 550ML","Supradyn Multi-Vitamin Tablet 15'S","Limcee Plus Orange Flavour Chewable Tablet"};

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < animalNameList.length; i++) {
            MedicineNames medicineNames = new MedicineNames(animalNameList[i]);
            // Binds all strings into an array
            arraylist.add(medicineNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener((SearchView.OnQueryTextListener) SearchMedicine.this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

}