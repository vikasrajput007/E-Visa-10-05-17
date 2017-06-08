package com.android.developer.e_visa.forms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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

import static com.android.developer.e_visa.MainActivity.homeFragmentStack;


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
    private Context context;
    String arrivalItem;
    boolean isFirstSelection = true;

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


            email_id = (EditText) view.findViewById(R.id.email_id);
            retype_email_id = (EditText) view.findViewById(R.id.retype_email_id);


            arrival_date = (EditText) view.findViewById(R.id.arrival_date);
            arrival_date.setInputType(InputType.TYPE_NULL);
//        arrival_date.requestFocus();


            save_and_continue = (LinearLayout) view.findViewById(R.id.save_and_continue);
            save_and_continue.setOnClickListener(this);
            arrival_date.setOnClickListener(this);


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
                        // To save destination country name

                        // save to session
                        editor.putString("arrival_city", arrivalItem);
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
            case R.id.save_and_continue:

                try {
                    if (email_id.getText().toString().isEmpty()) {
                        email_id.setError("Please enter email id");

                    } else if (!(FormValidation.isValidEmail(email_id.getText().toString().trim()) &&
                            FormValidation.isValidEmail(retype_email_id.getText().toString().trim()))) {

                        email_id.setError("Please enter correct id");
                        retype_email_id.setError("Please enter correct id");

                    }

//                    else if (arrivalItem.equals("City of arrival in India")) {
//                        Toast.makeText(getActivity(), "Please Select Arrival City", Toast.LENGTH_SHORT).show();
//                    }
                    else if (!(email_id.getText().toString().trim().equals(retype_email_id.getText().toString().trim()))) {

                        email_id.setError("Please type same email id");
                    }

                    else {

                        indiaVisaFormFamily = new IndiaVisaFormFamily();
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        homeFragmentStack.add(indiaVisaFormFamily);
                        mFragmentTransaction.replace(R.id.place_holder_layout, indiaVisaFormFamily);
                        mFragmentTransaction.commit();

                    }

                } catch (NullPointerException e) {

                }
                break;
        }

    }

    public void datePickerDialog() {
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                arrival_date.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }
}
