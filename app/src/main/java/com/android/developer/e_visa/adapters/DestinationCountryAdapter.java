package com.android.developer.e_visa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.developer.e_visa.R;
import com.android.developer.e_visa.models.DestinationCountry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 01-06-2017.
 */

public class DestinationCountryAdapter extends ArrayAdapter<DestinationCountry>{
    Context context;
    int resource_id;
    ArrayList<DestinationCountry> destinationCountryList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public DestinationCountryAdapter(Context context,  int resource,  ArrayList<DestinationCountry> objects) {
        super(context, resource, objects);

        this.context = context;
        destinationCountryList = objects;
        resource_id = resource;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }






    @Override
    public View getView(int position,   View convertView,   ViewGroup parent) {


        if (convertView == null) {


            convertView = layoutInflater.inflate(R.layout.spinner_item, null);

            TextView textView = (TextView)convertView.findViewById(R.id.spinner_text);
            textView.setText(destinationCountryList.get(position).getName());

        }

        return convertView;
    }
}
