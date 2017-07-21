package com.android.developer.e_visa.forms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
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

import com.android.developer.e_visa.MainActivity;
import com.android.developer.e_visa.R;
import com.android.developer.e_visa.utils.FormValidation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.android.developer.e_visa.HomeFragment.mater_country_array_list;
import static com.android.developer.e_visa.MainActivity.all_country_list;
import static com.android.developer.e_visa.MainActivity.aquirred_nationality;
import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
import static com.android.developer.e_visa.MainActivity.qualification;
import static com.android.developer.e_visa.MainActivity.religion_list;


public class IndiaVisaFormPersonal extends Fragment implements View.OnClickListener {

    private Spinner city_of_arrival, reiligion, qualificaton, aquiredNationality, by_naturalization, proof_of_address;
    private EditText email_id, retype_email_id, arrival_date;
    private LinearLayout save_and_continue;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar newDate;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private View view;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    IndiaVisaFormFamily indiaVisaFormFamily;
    ArrayAdapter<String> arrivalCityAdapter;
    ArrayAdapter<String> reiligionAdapter;
    ArrayAdapter<String> qualificatonAdapter;
    ArrayAdapter<String> aquiredNationalityAdapter;
    ArrayAdapter<String> by_naturalizationAdapter;
    ArrayAdapter<String> proof_of_addressAdapter;


    private Context context;
    String arrivalItem = "", religion = "", edu_qualification = "", aqu_nationality = "", by_nature = "", address_proof = "";
    boolean isFirstSelection = true;
    boolean is_nature = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.india_destination_personal, container, false);
        initView();
        return view;

    }

    private void initView() {

        try {
            context = getActivity();

            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            editor = sharedPref.edit();


            newDate = Calendar.getInstance();
            city_of_arrival = (Spinner) view.findViewById(R.id.city_of_arrival);
            reiligion = (Spinner) view.findViewById(R.id.reiligion);
            qualificaton = (Spinner) view.findViewById(R.id.qualificaton);
            aquiredNationality = (Spinner) view.findViewById(R.id.aquiredNationality);
            by_naturalization = (Spinner) view.findViewById(R.id.by_naturalization);
            proof_of_address = (Spinner) view.findViewById(R.id.proof_of_address);

            by_naturalization.setEnabled(false);

            email_id = (EditText) view.findViewById(R.id.email_id);
            retype_email_id = (EditText) view.findViewById(R.id.retype_email_id);


            arrival_date = (EditText) view.findViewById(R.id.arrival_date);
            arrival_date.setInputType(InputType.TYPE_NULL);
//        arrival_date.requestFocus();


            save_and_continue = (LinearLayout) view.findViewById(R.id.personal_save_and_continue);
            save_and_continue.setOnClickListener(this);
            arrival_date.setOnClickListener(this);


            // arrival adpater
            arrivalCityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, MainActivity.arrival_cities);
            arrivalCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city_of_arrival.setAdapter(arrivalCityAdapter);
            arrivalCityAdapter.notifyDataSetChanged();
            city_of_arrival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {

                        arrivalItem = (String) parent.getItemAtPosition(position);

                        String arrival_id = String.valueOf(position);

                        System.out.println("id of selected item is:"+arrival_id);
                        // To save destination country name

                        // save to session
                       // editor.putString("arrival_city", arrivalItem);
                        editor.putString("arrival_city", arrival_id);
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // religion adpater
            religion_list.add("Religion");
            reiligionAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, religion_list);
            reiligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reiligion.setAdapter(reiligionAdapter);
            reiligionAdapter.notifyDataSetChanged();
            reiligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        religion = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("religion", String.valueOf(position));
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // qualification adpater
            qualificatonAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, qualification);
            qualificatonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            qualificaton.setAdapter(qualificatonAdapter);
            qualificatonAdapter.notifyDataSetChanged();
            qualificaton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        edu_qualification = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("qualificaton", String.valueOf(position));
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Accquired Nationality adpater
            aquiredNationalityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, aquirred_nationality);
            aquiredNationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            aquiredNationality.setAdapter(aquiredNationalityAdapter);
            aquiredNationalityAdapter.notifyDataSetChanged();
            aquiredNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        aqu_nationality = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("aquiredNationality", String.valueOf(position));
                        editor.commit();

                        // to enable disable the naturalization

                        if (aqu_nationality.equals("Naturalization")) {
                            by_naturalization.setEnabled(true);
                            is_nature = true;
                        } else {
                            by_naturalization.setEnabled(false);

                        }
                        isFirstSelection = false;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // By naturalisation adpater
            mater_country_array_list.set(0, "please choose your previous nationality");
            by_naturalizationAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mater_country_array_list);
            by_naturalizationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            by_naturalization.setAdapter(by_naturalizationAdapter);
            by_naturalizationAdapter.notifyDataSetChanged();
            by_naturalization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        by_nature = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("previous nationality", String.valueOf(position));
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // By naturalisation adpater
            proof_of_addressAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, MainActivity.proof_of_address);
            proof_of_addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            proof_of_address.setAdapter(proof_of_addressAdapter);
            proof_of_addressAdapter.notifyDataSetChanged();
            proof_of_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        address_proof = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("proof_of_address", String.valueOf(position));
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
            case R.id.arrival_date:

                datePickerDialog();
                break;
            case R.id.personal_save_and_continue:
//
                if (arrivalItem.equals("City of arrival in India") || arrivalItem.equals("")) {

                    System.out.println("arrival city is checked or not :" + arrivalItem);
                    Toast.makeText(context, "Please Select Your Arrival City", Toast.LENGTH_SHORT).show();
                }

                if (email_id.getText().toString().isEmpty()) {
                    email_id.setError("Please enter email id");

                } else if (!(FormValidation.isValidEmail(email_id.getText().toString().trim()) &&
                        FormValidation.isValidEmail(retype_email_id.getText().toString().trim()))) {

                    email_id.setError("Please enter correct id");
                    retype_email_id.setError("Please enter correct id");

                } else if (!(email_id.getText().toString().trim().equals(retype_email_id.getText().toString().trim()))) {

                    email_id.setError("Please type same email id");
                }
//
                else if (religion.equals("Religion") || religion.equals("")) {
                    System.out.println("Religion is checked or not :" + religion);

                    Toast.makeText(context, "Please Select Your Religion", Toast.LENGTH_SHORT).show();
                }

                else if (arrival_date.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "Please Choose Arrival Date", Toast.LENGTH_SHORT).show();
                } else if (edu_qualification.equals("Educational Qualification") || edu_qualification.equals("")) {
                    System.out.println("Religion is checked or not :" + edu_qualification);
                    Toast.makeText(context, "Please Select Your Qualification", Toast.LENGTH_SHORT).show();
                } else if (aqu_nationality.equals("How you accquired your current nationality") || aqu_nationality.equals("")) {
                    System.out.println("Religion is checked or not :" + aqu_nationality);
                    Toast.makeText(context, "Please Select Your Nationalization", Toast.LENGTH_SHORT).show();
                } else if (by_nature.equals("Please Choose Your Previous Nationality")) {
                    if (is_nature) {
                        System.out.println("Religion is checked or not :" + by_nature);
                        Toast.makeText(context, "Please Choose Your Previous Nationality", Toast.LENGTH_SHORT).show();
                    }
                } else if (address_proof.equals("Proof of Address in your Name")) {
                    System.out.println("Religion is checked or not :" + address_proof);
                    Toast.makeText(context, "Please Choose Address Proof", Toast.LENGTH_SHORT).show();
                }

                else {

                    // save to session
                    editor.putString("email_id", email_id.getText().toString().trim());
                    editor.putString("arrival_date", arrival_date.getText().toString().trim());

                    editor.commit();


                    indiaVisaFormFamily = new IndiaVisaFormFamily();
                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    homeFragmentStack.add(indiaVisaFormFamily);
                    mFragmentTransaction.replace(R.id.place_holder_layout, indiaVisaFormFamily);
                    mFragmentTransaction.commit();

                }


                break;
        }

    }

    public void datePickerDialog() {
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                arrival_date.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }
}
