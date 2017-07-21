package com.android.developer.e_visa.forms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.test.suitebuilder.annotation.Suppress;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.developer.e_visa.R;
import com.android.developer.e_visa.utils.FormValidation;

import java.util.ArrayList;

import static com.android.developer.e_visa.HomeFragment.mater_country_array_list;
import static com.android.developer.e_visa.MainActivity.all_country_list;
import static com.android.developer.e_visa.MainActivity.fathers_birth_country;
import static com.android.developer.e_visa.MainActivity.fathers_nationality;
import static com.android.developer.e_visa.MainActivity.her_birth_country;
import static com.android.developer.e_visa.MainActivity.her_nationality;
import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
import static com.android.developer.e_visa.MainActivity.marriage_status_list;
import static com.android.developer.e_visa.MainActivity.mothers_birth_country;
import static com.android.developer.e_visa.MainActivity.mothers_nationality;

public class IndiaVisaFormFamily extends Fragment implements View.OnClickListener {

    private Spinner father_nationality, father_birth_country, mother_nationality, mother_birth_country, your_marriage_status,
            his_her_nationality, his_her_birth_country;
    private EditText father_name, father_birth_place, mother_name, mother_birth_place, your_spouse_name, his_her_birth_place;
    private LinearLayout save_and_continue;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private View view;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    IndiaVisaFormProfessional indiaVisaFormProfessional;

    private Context context;

    boolean isFirstSelection = true;

    ArrayAdapter<String> fatherNationalityAdapter, father_birth_countryAdapter, mother_nationalityAdapter,
            mother_birth_countryAdapter, marriageArrayAdapter, his_her_nationalityAdapter, his_her_birth_countryAdapter;


    String fathers_national = "", father_brth_contry = "", mothr_nationlity = "", mthr_brth_contry = "", marriage_status = "", his_her_nationlty = "", his_her_brth_contry = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.india_destination_family, container, false);
        initView();
        return view;
    }


    private void initView() {

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
        his_her_nationality.setEnabled(false);
        his_her_birth_country = (Spinner) view.findViewById(R.id.his_her_birth_country);
        his_her_birth_country.setEnabled(false);


        // text fields
        father_name = (EditText) view.findViewById(R.id.father_name);
        father_birth_place = (EditText) view.findViewById(R.id.father_birth_place);
        mother_name = (EditText) view.findViewById(R.id.mother_name);
        mother_birth_place = (EditText) view.findViewById(R.id.mother_birth_place);


        your_spouse_name = (EditText) view.findViewById(R.id.your_spouse_name);
        your_spouse_name.setEnabled(false);
        his_her_birth_place = (EditText) view.findViewById(R.id.his_her_birth_place);
        his_her_birth_place.setEnabled(false);


        save_and_continue = (LinearLayout) view.findViewById(R.id.family_save_and_continue);
        save_and_continue.setOnClickListener(this);

        try {

            mater_country_array_list.set(0,"Father's Nationality");
            //all_country_list.set(0, "Father's Nationality");
            // father nationality adapter

            fatherNationalityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mater_country_array_list);
            fatherNationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            father_nationality.setAdapter(fatherNationalityAdapter);
            fatherNationalityAdapter.notifyDataSetChanged();
            father_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        fathers_national = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        System.out.println("what is fathers nationality :" + fathers_national);
                        // save to session
                        editor.putString("father_nationality", String.valueOf(position));
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


// father country of birth


            fathers_birth_country = (ArrayList<String>) mater_country_array_list.clone();

            fathers_birth_country.set(0, "Father's Birth Country");
            // father nationality adapter
            father_birth_countryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, fathers_birth_country);
            father_birth_countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            father_birth_country.setAdapter(father_birth_countryAdapter);
            father_birth_countryAdapter.notifyDataSetChanged();

            //  spinnerFunction(fathers_nationality);
            father_birth_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        father_brth_contry = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("father_brth_contry", String.valueOf(position));
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // mother nationality adapter

            mothers_nationality = (ArrayList<String>) mater_country_array_list.clone();
            mothers_nationality.set(0, "Mother's Nationality");
            mother_nationalityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mothers_nationality);
            mother_nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mother_nationality.setAdapter(mother_nationalityAdapter);
            mother_nationalityAdapter.notifyDataSetChanged();
            mother_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        mothr_nationlity = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("mothr_nationlity", String.valueOf(position));
                        editor.commit();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // mother nationality adapter

            mothers_birth_country = (ArrayList<String>) mater_country_array_list.clone();
            mothers_birth_country.set(0, "Mother's Birth Country");
            mother_birth_countryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mothers_birth_country);
            mother_birth_countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mother_birth_country.setAdapter(mother_birth_countryAdapter);
            mother_birth_countryAdapter.notifyDataSetChanged();
            mother_birth_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        mthr_brth_contry = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("mthr_brth_contry", String.valueOf(position));
                        editor.commit();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // marriage adapter

            marriageArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, marriage_status_list);
            marriageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            your_marriage_status.setAdapter(marriageArrayAdapter);
            marriageArrayAdapter.notifyDataSetChanged();
            your_marriage_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        marriage_status = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("marriage_status", String.valueOf(position));
                        editor.commit();

                        System.out.println("marriage status is :"+marriage_status);

                        if (!marriage_status.equals("Your Marriage Status") && !marriage_status.equals("Single")) {

                            your_spouse_name.setEnabled(true);
                            his_her_nationality.setEnabled(true);
                            his_her_birth_place.setEnabled(true);
                            his_her_birth_country.setEnabled(true);


                        } else {
                            your_spouse_name.setEnabled(false);
                            his_her_nationality.setEnabled(false);
                            his_her_birth_place.setEnabled(false);
                            his_her_birth_country.setEnabled(false);

                        }
                        isFirstSelection = false;

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // his her nationality adapter
            her_nationality = (ArrayList<String>) mater_country_array_list.clone();
            her_nationality.set(0, "His/Her Nationality");
            his_her_nationalityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, her_nationality);
            his_her_nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            his_her_nationality.setAdapter(his_her_nationalityAdapter);
            his_her_nationalityAdapter.notifyDataSetChanged();
            his_her_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        his_her_nationlty = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("his_her_nationlty", String.valueOf(position));
                        editor.commit();

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // his her birth adapter
            her_birth_country = (ArrayList<String>) mater_country_array_list.clone();
            her_birth_country.set(0, "His/Her Country of Birth");
            his_her_birth_countryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, her_birth_country);
            his_her_birth_countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            his_her_birth_country.setAdapter(his_her_birth_countryAdapter);
            his_her_birth_countryAdapter.notifyDataSetChanged();
            his_her_birth_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        his_her_brth_contry = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("his_her_brth_contry", String.valueOf(position));
                        editor.commit();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (Exception e) {

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.family_save_and_continue:

                System.out.println("is family clicked or not");
                try {

                    if (father_name.getText().toString().equals("")) {

                        Toast.makeText(context, "Please type your father name", Toast.LENGTH_SHORT).show();

                    } else if (fathers_national.equals("Father's Nationality") || fathers_national.equals("")) {
                        Toast.makeText(context, "Please Select Fathers Nationality", Toast.LENGTH_SHORT).show();


                    } else if (father_birth_place.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Type Your Father's birth place", Toast.LENGTH_SHORT).show();

                    } else if (father_brth_contry.equals("Father's Birth Country")) {
                        Toast.makeText(context, "Please Select Fathers Birth Country", Toast.LENGTH_SHORT).show();

                    } else if (mother_name.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Type your Mother Name", Toast.LENGTH_SHORT).show();

                    } else if (mothr_nationlity.equals("Mother's Nationality")) {
                        Toast.makeText(context, "Please Select Mother Nationality", Toast.LENGTH_SHORT).show();

                    } else if (mother_birth_place.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Type Your Mother's birth place", Toast.LENGTH_SHORT).show();

                    } else if (mthr_brth_contry.equals("Mother's Birth Country")) {
                        Toast.makeText(context, "Please Select Mother's Birth Country", Toast.LENGTH_SHORT).show();

                    } else if (marriage_status.equals("Your Marriage Status")) {

                        Toast.makeText(context, "Please Select Your Marriage Status", Toast.LENGTH_SHORT).show();
                    }

                    else if (marriage_status.equals("Single")) {

                        editor.putString("father_name", father_name.getText().toString().trim());
                        editor.putString("father_birth_place", father_birth_place.getText().toString().trim());
                        editor.putString("mother_name", mother_name.getText().toString().trim());
                        editor.putString("mother_birth_place", mother_birth_place.getText().toString().trim());
                        editor.putString("your_spouse_name", your_spouse_name.getText().toString().trim());
                        editor.putString("his_her_birth_place", his_her_birth_place.getText().toString().trim());

                        editor.commit();

                        indiaVisaFormProfessional = new IndiaVisaFormProfessional();
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        homeFragmentStack.add(indiaVisaFormProfessional);
                        mFragmentTransaction.replace(R.id.place_holder_layout, indiaVisaFormProfessional);
                        mFragmentTransaction.commit();

                    }

                    else if (!marriage_status.equals("Single")) {

                        if (your_spouse_name.getText().toString().equals("")) {
                            Toast.makeText(context, "Please Type your Spouse Name", Toast.LENGTH_SHORT).show();

                        } else if (his_her_nationlty.equals("His/Her Nationality")) {
                            Toast.makeText(context, "Please Select His/Her Nationality", Toast.LENGTH_SHORT).show();

                        } else if (his_her_birth_place.getText().toString().equals("")) {
                            Toast.makeText(context, "Please Type his/her birth place", Toast.LENGTH_SHORT).show();

                        } else if (his_her_brth_contry.equals("His/Her Country of Birth")) {
                            Toast.makeText(context, "Please Select His/Her Birth Country", Toast.LENGTH_SHORT).show();

                        } else {
                            // System.out.println("form detail" + mother_birth_place.getText().toString().trim());
                            editor.putString("father_name", father_name.getText().toString().trim());
                            editor.putString("father_birth_place", father_birth_place.getText().toString().trim());
                            editor.putString("mother_name", mother_name.getText().toString().trim());
                            editor.putString("mother_birth_place", mother_birth_place.getText().toString().trim());
                            editor.putString("your_spouse_name", your_spouse_name.getText().toString().trim());
                            editor.putString("his_her_birth_place", his_her_birth_place.getText().toString().trim());

                            editor.commit();

                            indiaVisaFormProfessional = new IndiaVisaFormProfessional();
                            mFragmentManager = getFragmentManager();
                            mFragmentTransaction = mFragmentManager.beginTransaction();
                            homeFragmentStack.add(indiaVisaFormProfessional);
                            mFragmentTransaction.replace(R.id.place_holder_layout, indiaVisaFormProfessional);
                            mFragmentTransaction.commit();

                        }
                    }


                } catch (Exception e) {

                }
                break;

        }
    }


}
