package com.android.developer.e_visa.forms;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.developer.e_visa.R;
import com.android.developer.e_visa.appController.ApplicationController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.android.developer.e_visa.MainActivity.add_more_list;
import static com.android.developer.e_visa.MainActivity.add_more_list2;
import static com.android.developer.e_visa.MainActivity.millitary_police_security_org;
import static com.android.developer.e_visa.MainActivity.past_countries_visited;
import static com.android.developer.e_visa.MainActivity.present_occupation_list;
import static com.android.developer.e_visa.MainActivity.visa_type_list;
import static com.android.developer.e_visa.MainActivity.visa_type_list;
import static com.android.developer.e_visa.MainActivity.visited_before;

public class IndiaVisaFormProfessional extends Fragment implements View.OnClickListener {
    // Tag used to cancel the request
    private String tag_string_req = "tag_string_req", post_data_url, ID = "", destination;
    private Spinner present_occupation, millitry_police_security, visited_india_before, visa_type, past_visited, past_visited_more, past_visited_more2;
    private EditText employer_name, employer_address, organistion, rank, plcae_of_posting, stayed_address, visa_no, place_of_issue, date_of_issue, years_visited, how_many_time,
            years_visited_more, how_many_time_more, years_visited_more2, how_many_time_more2, need_to_call;
    private LinearLayout professional_save_and_continue, add_more, add_more2;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar newDate;
    StringRequest stringRequest;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private View view;
    ProgressDialog progressDialog;
    private Context context;

    boolean isFirstSelection = true;
    boolean is_yes = false;

    ArrayAdapter<String> present_occupationAdapter, millitry_police_securityAdapter, visited_india_beforeAdapter,
            visa_typeAdapter, past_visitedAdapter, past_visitedAdapterMore, past_visitedAdapterMore2;

    private String occupation = "", mill_pol_security = "", visited_india = "", type_visa = "", past_visit = "", past_visit_more = "", past_visit_more2,
            years_visit, years_visit_more, years_visit_more2, how_many_time_visit, how_many_time_visit_more, how_many_time_visit_more2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.india_destination_professional, container, false);
        initView();
        return view;


    }

    private void initView() {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        ID = sharedPref.getString("langid", "");
        destination = sharedPref.getString("destination_country_name", "");

        context = getActivity();
        progressDialog = new ProgressDialog(context);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        newDate = Calendar.getInstance();

        // spinners
        present_occupation = (Spinner) view.findViewById(R.id.present_occupation);
        millitry_police_security = (Spinner) view.findViewById(R.id.millitry_police_security);
        visited_india_before = (Spinner) view.findViewById(R.id.visited_india_before);

        visa_type = (Spinner) view.findViewById(R.id.visa_type);
        visa_type.setEnabled(false);

        past_visited = (Spinner) view.findViewById(R.id.before_visited);
        past_visited_more = (Spinner) view.findViewById(R.id.before_visited_more);
        past_visited_more2 = (Spinner) view.findViewById(R.id.before_visited_more2);

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

        years_visited_more = (EditText) view.findViewById(R.id.years_visited_more);
        how_many_time_more = (EditText) view.findViewById(R.id.how_many_time_more);

        years_visited_more2 = (EditText) view.findViewById(R.id.years_visited_more2);
        how_many_time_more2 = (EditText) view.findViewById(R.id.how_many_time_more2);

        //  disabled textField

        organistion.setEnabled(false);
        rank.setEnabled(false);
        plcae_of_posting.setEnabled(false);
        stayed_address.setEnabled(false);
        visa_no.setEnabled(false);
        place_of_issue.setEnabled(false);
        date_of_issue.setEnabled(false);
//        years_visited.setEnabled(false);
//        how_many_time.setEnabled(false);
//        years_visited_more.setEnabled(false);
//        how_many_time_more.setEnabled(false);
//        years_visited_more2.setEnabled(false);
//        how_many_time_more2.setEnabled(false);


        need_to_call = (EditText) view.findViewById(R.id.need_to_call);

        professional_save_and_continue = (LinearLayout) view.findViewById(R.id.professional_save_and_continue);
        add_more = (LinearLayout) view.findViewById(R.id.add_more);
        add_more2 = (LinearLayout) view.findViewById(R.id.add_more2);

//        add_more.setVisibility(View.GONE);
//        add_more2.setVisibility(View.GONE);

        //add_more.setOnClickListener(this);
        //add_more2.setOnClickListener(this);
        professional_save_and_continue.setOnClickListener(this);
        date_of_issue.setOnClickListener(this);

        try {


            // father nationality adapter

            present_occupationAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, present_occupation_list);
            present_occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            present_occupation.setAdapter(present_occupationAdapter);
            present_occupationAdapter.notifyDataSetChanged();
            present_occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        occupation = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("occupation", occupation);
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // military police security adapter


            millitry_police_securityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, millitary_police_security_org);
            millitry_police_securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            millitry_police_security.setAdapter(millitry_police_securityAdapter);
            millitry_police_securityAdapter.notifyDataSetChanged();
            millitry_police_security.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        mill_pol_security = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
//                        editor.putString("mill_pol_security", mill_pol_security);
//                        editor.commit();

                        if (!mill_pol_security.equals("If in Military/Police/Security Organisation")) {

                            if (mill_pol_security.equals("Yes")) {
                                organistion.setEnabled(true);
                                rank.setEnabled(true);
                                plcae_of_posting.setEnabled(true);

                            }
                        }

                        if (mill_pol_security.equals("If in Military/Police/Security Organisation") || mill_pol_security.equals("No")) {
                            organistion.setEnabled(false);
                            rank.setEnabled(false);
                            plcae_of_posting.setEnabled(false);

                        }


                    }
                    isFirstSelection = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //  visited india before adapter


            visited_india_beforeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, visited_before);
            visited_india_beforeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            visited_india_before.setAdapter(visited_india_beforeAdapter);
            visited_india_beforeAdapter.notifyDataSetChanged();
            visited_india_before.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        visited_india = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("visited_india", visited_india);
                        editor.commit();

                        if (!visited_india.equals("Have you ever visited India Before")) {

                            if (visited_india.equals("Yes")) {
                                stayed_address.setEnabled(true);
                                visa_type.setEnabled(true);
                                visa_no.setEnabled(true);
                                place_of_issue.setEnabled(true);
                                date_of_issue.setEnabled(true);
                            }

                        }

                        if (visited_india.equals("No")) {
                            stayed_address.setEnabled(false);
                            visa_type.setEnabled(false);
                            visa_no.setEnabled(false);
                            place_of_issue.setEnabled(false);
                            date_of_issue.setEnabled(false);
                        }

                    }

                    isFirstSelection = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // visa type adapter

            visa_typeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, visa_type_list);
            visa_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            visa_type.setAdapter(visa_typeAdapter);
            visa_typeAdapter.notifyDataSetChanged();
            visa_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        type_visa = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("type_visa", type_visa);
                        editor.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//            her_birth_country = (ArrayList<String>) all_country_list.clone();
//            her_birth_country.set(0, "His/Her Birth Country");
            // no. of times before visit adapter

            past_visitedAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, past_countries_visited);
            past_visitedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            past_visited.setAdapter(past_visitedAdapter);
            past_visitedAdapter.notifyDataSetChanged();
            past_visited.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        past_visit = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("past_visit", past_visit);
                        editor.commit();

                        if (!past_visit.equals("Have you visited any of these country in the past")) {

                            if (!past_visit.equals("No")) {
//                                years_visited.setEnabled(true);
//                                how_many_time.setEnabled(true);
                                add_more.setVisibility(View.VISIBLE);


                            }
                        } else if (!past_visit.equals("Have you visited any of these country in the past") || !past_visit.equals("No")) {


                            years_visited.setEnabled(false);
                            how_many_time.setEnabled(false);

                            add_more.setVisibility(View.GONE);
                            add_more2.setVisibility(View.GONE);

                            past_visited_more.setVisibility(View.GONE);
                            years_visited_more.setVisibility(View.GONE);
                            how_many_time_more.setVisibility(View.GONE);

                            past_visited_more2.setVisibility(View.GONE);
                            years_visited_more2.setVisibility(View.GONE);
                            how_many_time_more2.setVisibility(View.GONE);


                        }
                        isFirstSelection = false;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//
            //adapter for more visited country and cloning the array list also

            // add_more_list = (ArrayList<String>) past_countries_visited.clone();
//            add_more_list.remove(0);
            //  add_more_list.set(0, "Select More Country");
            isFirstSelection = true;
            past_visitedAdapterMore = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, past_countries_visited);
            past_visitedAdapterMore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            past_visited_more.setAdapter(past_visitedAdapterMore);
            past_visitedAdapterMore.notifyDataSetChanged();
            past_visited.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                    if (isFirstSelection) {
                        isFirstSelection = false;
                    } else {
                        past_visit_more = (String) parent.getItemAtPosition(position);
                        // To save destination country name

                        // save to session
                        editor.putString("past_visit_more", past_visit_more);
                        editor.commit();

                        if (!past_visit_more.equals("Have you visited any of these country in the past")) {
                            if (!past_visit_more.equals("No")) {

//                                years_visited_more.setEnabled(true);
//                                how_many_time_more.setEnabled(true);
                                editor.putString("past_visit_more", past_visit_more);
                                editor.commit();
                                add_more2.setVisibility(View.VISIBLE);

                            }
                        } else {

//                            years_visited_more.setEnabled(false);
//                            how_many_time_more.setEnabled(false);

                            add_more2.setVisibility(View.GONE);

                            past_visited_more2.setVisibility(View.GONE);
                            years_visited_more2.setVisibility(View.GONE);
                            how_many_time_more2.setVisibility(View.GONE);
                        }

                    }
                    isFirstSelection = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//
//            //  adapter for more visited country and cloning the array list
//
//            add_more_list2 = (ArrayList<String>) past_countries_visited.clone();
//            add_more_list2.set(0, "Select More Country");
//            past_visitedAdapterMore2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, add_more_list2);
//            past_visitedAdapterMore2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            past_visited_more2.setAdapter(past_visitedAdapterMore2);
//            past_visitedAdapterMore2.notifyDataSetChanged();
//            past_visited_more2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
//                    if (isFirstSelection) {
//                        isFirstSelection = false;
//                    } else {
//                        past_visit_more2 = (String) parent.getItemAtPosition(position);
//                        // To save destination country name
//
//                        // save to session
//                        editor.putString("past_visit_more2", past_visit_more2);
//                        editor.commit();
//
//                        if (!past_visit_more2.equals("More Country")) {
//
//                            years_visited_more2.setEnabled(true);
//                            how_many_time_more2.setEnabled(true);
//                            editor.putString("past_visit_more2", past_visit_more2);
//                            editor.commit();
//
//
//                        }
//
//                        if (past_visit_more2.equals("More Country")) {
//                            years_visited.setEnabled(false);
//                            how_many_time.setEnabled(false);
//
////                            past_visited_more2.setVisibility(View.GONE);
////                            years_visited_more2.setVisibility(View.GONE);
////                            how_many_time_more2.setVisibility(View.GONE);
//                        }
//
//                    }
//                    isFirstSelection = false;
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

        } catch (Exception e) {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_of_issue:
                datePickerDialog();
                break;

            case R.id.add_more:
                past_visited_more.setVisibility(View.VISIBLE);
                years_visited_more.setVisibility(View.VISIBLE);
                how_many_time_more.setVisibility(View.VISIBLE);
                break;

//            case R.id.add_more2:
//                past_visited_more2.setVisibility(View.VISIBLE);
//                years_visited_more2.setVisibility(View.VISIBLE);
//                how_many_time_more2.setVisibility(View.VISIBLE);
//                break;

            case R.id.professional_save_and_continue:

                try {
                    if (occupation.equals("Present Occupation") || occupation.equals("")) {

                        Toast.makeText(context, "Please Select Your Present Occupation", Toast.LENGTH_SHORT).show();
                    } else if (employer_name.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Type Employer Name", Toast.LENGTH_SHORT).show();
                    } else if (employer_address.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Type Employer Address", Toast.LENGTH_SHORT).show();
                    } else if (mill_pol_security.equals("If in Military/Police/Security Organisation")) {
                        Toast.makeText(context, "Please Select Military/Police/Security Organisation", Toast.LENGTH_SHORT).show();

                    } else if (mill_pol_security.equals("Yes")) {

                        if (plcae_of_posting.getText().toString().equals("")) {
                            Toast.makeText(context, "Please Type Place of Posting", Toast.LENGTH_SHORT).show();
                        } else if (rank.getText().toString().equals("")) {
                            Toast.makeText(context, "Please Type Rank", Toast.LENGTH_SHORT).show();
                        } else if (organistion.getText().toString().equals("")) {
                            Toast.makeText(context, "Please Type Organisation Name", Toast.LENGTH_SHORT).show();
                        }



                        else {


                                editor.putString("employer_name", employer_name.getText().toString());
                                editor.putString("employer_address", employer_address.getText().toString());


                                editor.putString("mill_pol_security", mill_pol_security);
                                editor.putString("organistion", organistion.getText().toString());
                                editor.putString("rank", rank.getText().toString());
                                editor.putString("plcae_of_posting", plcae_of_posting.getText().toString());
//                            }
//                        }

//                        if(!visited_india.equals("Have you ever visited India Before")){
                                editor.putString("visited_india", visited_india);
                                editor.putString("stayed_address", stayed_address.getText().toString());
                                editor.putString("type_visa", type_visa);
                                editor.putString("visa_no", visa_no.getText().toString());
                                editor.putString("place_of_issue", place_of_issue.getText().toString());
                                editor.putString("date_of_issue", date_of_issue.getText().toString());

//                        }
//
//                        if(!past_visit.equals("Have you ever visited India Before")){
                                editor.putString("past_visited", past_visit);
                                editor.putString("years_visited", years_visited.getText().toString());
                                editor.putString("how_many_time", how_many_time.getText().toString());

//                        }

                                editor.putString("need_to_call", need_to_call.getText().toString());

                                // method to save and post the customer data to server

                                System.out.println("is post customer detail running ?");

                                postCustomerDetail();

                                System.out.println("is post customer detail running ?" + 2);

                            }
                        }

                        } catch (Exception e){

                    System.out.println("what is the error of this api "+e.toString());
                        }


                break;
        }

    }

    private void postCustomerDetail() {

        try {

            progressDialog.show();
            progressDialog.setMessage("Please Wait . . .");
            post_data_url = "htttp://webcreationsx.com/evisa-apis/indiaapi.php";
            stringRequest = new StringRequest(Request.Method.POST, post_data_url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        System.out.println("response of indian api :" + jsonObject.toString());


                    } catch (Exception e) {

                    }

                }

            }
                    , new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }

                    System.out.println("response of indian api :" + error.toString());


                }
            })


            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    System.out.println("value of lang id is :" + ID);
                    params.put("langid", ID);
                    params.put("destination", destination);
                    params.put("city_of_arrival", sharedPref.getString("arrival_city", ""));
//                    params.put("applicantid", ID);  email_id
                    params.put("date_of_arrival", sharedPref.getString("arrival_date", ""));
                    params.put("email", sharedPref.getString("email_id", ""));
                    params.put("religion", sharedPref.getString("religion", ""));
                    params.put("education", sharedPref.getString("qualificaton", ""));
                    params.put("accquired_nationality", sharedPref.getString("aquiredNationality", ""));
                    params.put("previous nationality", sharedPref.getString("previous nationality", ""));
                    params.put("address_proof", sharedPref.getString("proof_of_address", ""));
                    params.put("fathers_name", sharedPref.getString("father_name", ""));
                    params.put("fathers_nationality", sharedPref.getString("father_nationality", ""));
                    params.put("fathers_place_of_birth", sharedPref.getString("father_birth_place", ""));
                    params.put("fathers_country_of_birth", sharedPref.getString("father_brth_contry", ""));
                    params.put("mothers_name", sharedPref.getString("mother_name", ""));
                    params.put("mothers_nationality", sharedPref.getString("mothr_nationlity", ""));
                    params.put("mothers_place_of_birth", sharedPref.getString("mother_birth_place", ""));
                    params.put("mothers_country_of_birth", sharedPref.getString("mthr_brth_contry", ""));
                    params.put("marriage_status", sharedPref.getString("marriage_status", ""));
                    params.put("spouse_name", sharedPref.getString("your_spouse_name", ""));
                    params.put("spouse_nationality", sharedPref.getString("his_her_nationlty", ""));
                    params.put("spouse_place_of_birth", sharedPref.getString("his_her_birth_place", ""));
                    params.put("spouse_country_of_birth", sharedPref.getString("his_her_brth_contry", ""));
                    params.put("occupation", sharedPref.getString("occupation", ""));
                    params.put("employer_name", sharedPref.getString("employer_name", ""));
                    params.put("employer_address", sharedPref.getString("employer_address", ""));
                    params.put("in_military_police_security", sharedPref.getString("mill_pol_security", ""));
                    params.put("organization", sharedPref.getString("organistion", ""));
                    params.put("rank", sharedPref.getString("rank", ""));
                    params.put("place_of_posting", sharedPref.getString("plcae_of_posting", ""));
                    params.put("ever_visited_india", sharedPref.getString("visited_india", ""));
                    params.put("address_where_stayed", sharedPref.getString("stayed_address", ""));
                    params.put("visa_no", sharedPref.getString("visa_no", ""));
                    params.put("visa_type", sharedPref.getString("type_visa", ""));
                    params.put("place_of_issue", sharedPref.getString("place_of_issue", ""));
                    params.put("date_of_issue", sharedPref.getString("date_of_issue", ""));
                    params.put("is_visited_coutries", sharedPref.getString("past_visited", ""));
                    params.put("year_of_visited", sharedPref.getString("years_visited", ""));
                    params.put("no_of_times", sharedPref.getString("how_many_time", ""));
                    // params.put("morevisitedcountries", ID);
                   // params.put("day_time_contact_cc", ID);
                    params.put("day_time_contact", sharedPref.getString("need_to_call", ""));
//                    params.put("passport_front", ID);
//                    params.put("photo", ID);
//                    params.put("proof", ID);
//                    params.put("application_status", ID);
//                    params.put("date_created", ID);
//                    params.put("time_created", ID);
//                    params.put("is_translated", ID);
//                    params.put("translator_user ", ID);

                    return params;
                }

            };

        } catch (Exception e) {
        }

        ApplicationController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }


    public void datePickerDialog() {
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                date_of_issue.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }
}
