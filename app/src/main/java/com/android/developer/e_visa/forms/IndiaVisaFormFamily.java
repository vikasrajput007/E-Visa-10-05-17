package com.android.developer.e_visa.forms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.android.developer.e_visa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.android.developer.e_visa.MainActivity.fathers_nationality;
import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
import static com.android.developer.e_visa.MainActivity.marriage_status_list;

public class IndiaVisaFormFamily extends Fragment implements View.OnClickListener {

    private Spinner father_nationality, father_birth_country, mother_nationality, mother_birth_country, your_marriage_status, his_her_nationality,his_her_birth_country;
    private EditText father_name, father_birth_place,mother_name,mother_birth_place,your_spouse_name, his_her_birth_place ;
    private LinearLayout save_and_continue;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private View view;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    IndiaVisaFormProfessional indiaVisaFormProfessional;
    ArrayAdapter<String> fatherNtionalityAdapter;
    private Context context;
    String fathers_national,marriage_status;
    boolean isFirstSelection = true;

    ArrayAdapter<String> fatherNationalityAdapter;
    ArrayAdapter<String> marriageArrayAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.india_destination_family, container, false);
        initView();
        return view;
    }


    private void initView(){

        context = getActivity();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //spinners
        father_nationality = (Spinner) view.findViewById(R.id.father_nationality);
        father_birth_country = (Spinner) view.findViewById(R.id.father_birth_country);
        mother_nationality = (Spinner) view.findViewById(R.id.mother_nationality);

        mother_birth_country = (Spinner) view.findViewById(R.id.mother_birth_country);
        your_marriage_status = (Spinner) view.findViewById(R.id.your_marriage_status);
        his_her_nationality = (Spinner) view.findViewById(R.id.his_her_nationality);
        his_her_birth_country = (Spinner) view.findViewById(R.id.his_her_birth_country);


        // text fields
        father_name = (EditText)view.findViewById(R.id.father_name);
        father_birth_place = (EditText)view.findViewById(R.id.father_birth_place);
        mother_name = (EditText)view.findViewById(R.id.mother_name);
        mother_birth_place = (EditText)view.findViewById(R.id.mother_birth_place);
        your_spouse_name = (EditText)view.findViewById(R.id.your_spouse_name);
        his_her_birth_place = (EditText)view.findViewById(R.id.his_her_birth_place);


        save_and_continue = (LinearLayout)view.findViewById(R.id.save_and_continue);
        save_and_continue.setOnClickListener(this);


        fatherNationalityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, fathers_nationality);
        fatherNationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        father_nationality.setAdapter(fatherNationalityAdapter);
        fatherNationalityAdapter.notifyDataSetChanged();

      //  spinnerFunction(fathers_nationality);
        father_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                if (isFirstSelection) {
                    isFirstSelection = false;
                } else {
                    fathers_national = (String) parent.getItemAtPosition(position);
                    // To save destination country name

                    // save to session
                    editor.putString("father_nationality", fathers_national);
                    editor.commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       // spinnerFunction(marriage_status_list);
//        marriageArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, marriage_status_list);
//        marriageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        father_nationality.setAdapter(marriageArrayAdapter);
//        marriageArrayAdapter.notifyDataSetChanged();
//        your_marriage_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (isFirstSelection) {
//                    isFirstSelection = false;
//                } else {
//                    marriage_status = (String) parent.getItemAtPosition(position);
//                    // To save destination country name
//
//                    // save to session
//                    editor.putString("marriage_status", marriage_status);
//                    editor.commit();
//
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }





//    public void spinnerFunction(ArrayList<String> array_list){
//
//        genralArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, array_list);
//        genralArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        father_nationality.setAdapter(genralArrayAdapter);
//        genralArrayAdapter.notifyDataSetChanged();
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.your_marriage_status:





                break;
            case R.id.save_and_continue:

                indiaVisaFormProfessional = new IndiaVisaFormProfessional();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                homeFragmentStack.add(indiaVisaFormProfessional);
                mFragmentTransaction.replace(R.id.place_holder_layout, indiaVisaFormProfessional);
                mFragmentTransaction.commit();
                break;


        }
    }


}
