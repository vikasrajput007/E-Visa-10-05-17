package com.android.developer.e_visa.forms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.developer.e_visa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IndiaVisaFormProfessional extends Fragment implements View.OnClickListener {
    private Spinner present_occupation, millitry_police_security, visited_india_before, visa_type, before_visited;
    private EditText employer_name, employer_address, organistion, rank, plcae_of_posting, stayed_address, visa_no, place_of_issue, date_of_issue, years_visited, how_many_time, need_to_call;
    private LinearLayout save_and_continue;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar newDate;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.india_destination_professional, container, false);
        initView();
        return view;


    }

    private void initView() {

        context = getActivity();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        newDate = Calendar.getInstance();

        // spinners
        present_occupation = (Spinner) view.findViewById(R.id.present_occupation);
        millitry_police_security = (Spinner) view.findViewById(R.id.millitry_police_security);
        visited_india_before = (Spinner) view.findViewById(R.id.visited_india_before);
        visa_type = (Spinner) view.findViewById(R.id.visa_type);
        before_visited = (Spinner) view.findViewById(R.id.before_visited);

        // text fields

        employer_name = (EditText) view.findViewById(R.id.employer_name);
        employer_address = (EditText) view.findViewById(R.id.employer_address);
        organistion = (EditText) view.findViewById(R.id.organistion);
        rank = (EditText) view.findViewById(R.id.rank);
        plcae_of_posting = (EditText) view.findViewById(R.id.plcae_of_posting);
        stayed_address = (EditText) view.findViewById(R.id.stayed_address);
        visa_no = (EditText) view.findViewById(R.id.visa_no);
        place_of_issue = (EditText) view.findViewById(R.id.place_of_issue);
        date_of_issue = (EditText) view.findViewById(R.id.date_of_issue);
        years_visited = (EditText) view.findViewById(R.id.years_visited);
        how_many_time = (EditText) view.findViewById(R.id.how_many_time);
        need_to_call = (EditText) view.findViewById(R.id.need_to_call);

        save_and_continue = (LinearLayout) view.findViewById(R.id.save_and_continue);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_of_issue:
                datePickerDialog();
                break;

            case R.id.save_and_continue:


                break;
        }

    }

    public void datePickerDialog() {
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                date_of_issue.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }
}
