package com.aditya.pharmacyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aditya.pharmacyapp.ModelClass.MedicineNames;
import com.aditya.pharmacyapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<MedicineNames> medicineNamesList = null;
    private ArrayList<MedicineNames> arraylist;

    public ListViewAdapter(Context context, List<MedicineNames> medicineNamesList) {
        mContext = context;
        this.medicineNamesList = medicineNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<MedicineNames>();
        this.arraylist.addAll(medicineNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return medicineNamesList.size();
    }

    @Override
    public MedicineNames getItem(int position) {
        return medicineNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(medicineNamesList.get(position).getMedicineName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        medicineNamesList.clear();
        if (charText.length() == 0) {
            medicineNamesList.addAll(arraylist);
        } else {
            for (MedicineNames wp : arraylist) {
                if (wp.getMedicineName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    medicineNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
