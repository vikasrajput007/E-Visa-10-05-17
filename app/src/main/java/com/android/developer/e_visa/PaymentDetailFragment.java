package com.android.developer.e_visa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.android.developer.e_visa.MainActivity.homeFragmentStack;

public class PaymentDetailFragment extends Fragment implements  View.OnClickListener {

    View v;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    Button back_destination_page;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_payment_detail, container, false);
        initView();
        return v;

    }



    private void initView(){

        back_destination_page = (Button)v.findViewById(R.id.back_to_destination_page);
        back_destination_page.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {

        SelectDestination selectDestination = new SelectDestination();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.place_holder_layout, selectDestination);
        mFragmentTransaction.commit();


    }
}
