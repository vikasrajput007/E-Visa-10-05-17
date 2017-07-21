package com.android.developer.e_visa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.developer.e_visa.appController.ApplicationController;
import com.android.developer.e_visa.forms.UploadDocumentFragment;
import com.android.developer.e_visa.models.ListDetail;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    // Stack for back button of all pages
    public static Stack<Fragment> homeFragmentStack = new Stack<>();
    public static int GALLERY_PICTURE_BILL = 3;
    public static int CAMERA_REQUEST_BILL = 4;
    public static Uri photoURI;
    public static String ImageURI;
    String picturePath,imagePath;
    File photofile;
    public static Bitmap photo_image_bitmap;
    String timeStamp, imageFileName,_id="";
    File storageDir, image;

    private String tag_string_req = "tag_string_req";
    // Tag used to cancel the request
    private String tag_json_arry = "json_array_req";
    private String url = "http://webcreationsx.com/evisa-apis/languageapi.php";
    private String ID = "id", mCurrentPhotoPath;
    int MY_SOCKET_TIMEOUT_MS = 80000;
    private ListDetail listDetail;
    private String select_destination, select_nationality;

    public static ArrayList<ListDetail> listDetails = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    HomeFragment homeFragment;

    public static ArrayList<String> all_country_list, insurancedays_list, yesno_list, arrival_cities, religion_list, purpose_list, kenya_road_menu_list,

    account_type_list, qualification, aquirred_nationality, application_status_list, telephone_type_list, gender_type, dubai_visa_type_list,

    dubai_airport_type_list, dubai_document_type_list, how_long_list, spouse_nationality_list, kenya_arrival_menu_list, kenya_air_menu_list,

    iiss_list, proof_of_address, travel_document_type_list, days_stays_us_list, where_stays_us_list, us_going_list,

    marriage_status_list, present_occupation_list, millitary_police_security_org, visited_before, visa_type_list, residence_status_list,

    past_countries_visited, add_more_list, add_more_list2, fathers_nationality, fathers_birth_country, mothers_nationality, mothers_birth_country, us_visa_purpose_list,

    her_nationality, her_birth_country, kenya_ship_menu_list, kenya_apply_list, srilanka_title_list, srilanka_purpose_list, title_list,

    iam_list, funds_list, contact_language_list, eta_type_list, passport_type_list, turky_duration_list, turky_entry_list, gcc_list, port_list,

    behrain_detail_purpose_list, armenia_stay_period_list, evisa_type_list, single_visa_type_list, multiple_visa_type_list, uganda_visa_category,

    immigration_status_list, point_of_entry_list, stay_period_request_list, myanmaar_port_entry_list, myanmaar_accomodation_list,

    myanmaar_visa_type_list, kuwait_passport_list, kuwait_gcc_country_list, kuwait_education_list, building_type_list, career_of_deseas,

    azerbaijan_purpose_list, tajikistan_purpose_list, tajikistan_tuorism_list, tajikistan_private, tajikistan_passport_type, tajikistan_bussiness_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        progressDialog = new ProgressDialog(context);

        getLanguagesName();


        initView();

    }


    private void initView() {

        try {

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            homeFragment = new HomeFragment();
            homeFragmentStack.add(homeFragment);
            mFragmentTransaction.replace(R.id.place_holder_layout, homeFragment, "homeFragment");
            mFragmentTransaction.commit();


            populateTable();

        } catch (Exception e) {

        }
    }


    private void populateTable() {
        runOnUiThread(new Runnable() {
            public void run() {

                try {

                    all_country_list = new ArrayList<>();
                    insurancedays_list = new ArrayList<>();
                    yesno_list = new ArrayList<>();
                    arrival_cities = new ArrayList<>();
                    religion_list = new ArrayList<>();
                    purpose_list = new ArrayList<>();
                    account_type_list = new ArrayList<>();
                    qualification = new ArrayList<>();
                    aquirred_nationality = new ArrayList<>();
                    application_status_list = new ArrayList<>();
                    telephone_type_list = new ArrayList<>();
                    gender_type = new ArrayList<>();
                    iiss_list = new ArrayList<>();
                    travel_document_type_list = new ArrayList<>();
                    days_stays_us_list = new ArrayList<>();
                    where_stays_us_list = new ArrayList<>();
                    us_going_list = new ArrayList<>();
                    residence_status_list = new ArrayList<>();
                    us_visa_purpose_list = new ArrayList<>();
                    dubai_visa_type_list = new ArrayList<>();
                    dubai_airport_type_list = new ArrayList<>();
                    dubai_document_type_list = new ArrayList<>();
                    how_long_list = new ArrayList<>();
                    spouse_nationality_list = new ArrayList<>();
                    kenya_arrival_menu_list = new ArrayList<>();
                    kenya_air_menu_list = new ArrayList<>();
                    kenya_road_menu_list = new ArrayList<>();
                    kenya_ship_menu_list = new ArrayList<>();
                    kenya_apply_list = new ArrayList<>();
                    srilanka_title_list = new ArrayList<>();
                    srilanka_purpose_list = new ArrayList<>();
                    title_list = new ArrayList<>();
                    iam_list = new ArrayList<>();
                    funds_list = new ArrayList<>();
                    contact_language_list = new ArrayList<>();
                    eta_type_list = new ArrayList<>();
                    passport_type_list = new ArrayList<>();
                    turky_duration_list = new ArrayList<>();
                    turky_entry_list = new ArrayList<>();
                    gcc_list = new ArrayList<>();
                    port_list = new ArrayList<>();
                    behrain_detail_purpose_list = new ArrayList<>();
                    armenia_stay_period_list = new ArrayList<>();
                    proof_of_address = new ArrayList<>();
                    marriage_status_list = new ArrayList<>();
                    present_occupation_list = new ArrayList<>();
                    millitary_police_security_org = new ArrayList<>();
                    visited_before = new ArrayList<>();
                    visa_type_list = new ArrayList<>();
                    past_countries_visited = new ArrayList<>();
                    add_more_list = new ArrayList<>();
                    add_more_list2 = new ArrayList<>();
                    fathers_nationality = new ArrayList<>();
                    fathers_birth_country = new ArrayList<>();
                    mothers_nationality = new ArrayList<>();
                    mothers_birth_country = new ArrayList<>();
                    her_nationality = new ArrayList<>();
                    her_birth_country = new ArrayList<>();

                    evisa_type_list = new ArrayList<>();
                    single_visa_type_list = new ArrayList<>();
                    multiple_visa_type_list = new ArrayList<>();
                    uganda_visa_category = new ArrayList<>();
                    immigration_status_list = new ArrayList<>();
                    point_of_entry_list = new ArrayList<>();
                    stay_period_request_list = new ArrayList<>();
                    myanmaar_port_entry_list = new ArrayList<>();
                    myanmaar_accomodation_list = new ArrayList<>();
                    myanmaar_visa_type_list = new ArrayList<>();
                    kuwait_passport_list = new ArrayList<>();
                    kuwait_gcc_country_list = new ArrayList<>();
                    kuwait_education_list = new ArrayList<>();
                    building_type_list = new ArrayList<>();
                    career_of_deseas = new ArrayList<>();
                    azerbaijan_purpose_list = new ArrayList<>();
                    tajikistan_bussiness_list = new ArrayList<>();
                    tajikistan_tuorism_list = new ArrayList<>();
                    tajikistan_private = new ArrayList<>();
                    tajikistan_passport_type = new ArrayList<>();


                    // add first item to all spinner adpater array list

                    insurancedays_list.add("Insurance Days");
                    arrival_cities.add("City of arrival in India");
                    religion_list.clear();
                    religion_list.add("Religion");
                    qualification.clear();
                    qualification.add("Educational Qualification");
                    aquirred_nationality.clear();
                    aquirred_nationality.add("How you accquired your current nationality");

                    //by_naturalisation.add("please choose your previous nationality");
                    proof_of_address.clear();
                    proof_of_address.add("Proof of Address in your Name");

                    marriage_status_list.clear();
                    marriage_status_list.add("Your Marriage Status");

                    present_occupation_list.clear();
                    present_occupation_list.add("Present Occupation");

                    millitary_police_security_org.clear();
                    millitary_police_security_org.add("If in Military/Police/Security Organisation");

                    visited_before.clear();
                    visited_before.add("Have you ever visited India Before");

                    visa_type_list.clear();
                    visa_type_list.add("Visa Type");

                    past_countries_visited.clear();
                    past_countries_visited.add("Have you visited these country");
//
//                    add_more_list.clear();
//                    add_more_list.add("Add More Visisted Country");
//
//                    add_more_list2.clear();
//                    add_more_list2.add("Add More Visisted Country");

                    account_type_list.add("Account Type");

                    fathers_nationality.add("Father's Nationality");
                    fathers_birth_country.add("Father's Birth Country");
                    mothers_nationality.add("Mother's Nationality");
                    mothers_birth_country.add("Mother's Birth Country");
                    her_nationality.add("His/Her Nationality");
                    her_birth_country.add("His/Her Country of Birth");


                } catch (Exception e) {

                }
            }
        });
    }

    private void getLanguagesName() {

        try {
            listDetails.clear();

            progressDialog.show();
            JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }

                    try {
                        for (int i = 0; i < response.length(); i++) {

                            listDetail = new ListDetail();

                            JSONObject jsonObject = response.getJSONObject(i);

                            listDetail.setId(jsonObject.getString("id"));
                            listDetail.setLanguage(jsonObject.getString("language"));
                            listDetail.setImage(jsonObject.getString("image"));

                            listDetails.add(listDetail);

                        }

                        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
                        homeFragment.adapterMethod();


                    } catch (JSONException e) {
                    }


                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }

                }
            });

            req.setRetryPolicy(new DefaultRetryPolicy
                    (MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Adding request to request queue
            ApplicationController.getInstance().addToRequestQueue(req, tag_json_arry);

        } catch (Exception e) {

        }

    }


    @Override
    public void onBackPressed() {

        if (homeFragmentStack.size() > 1) {

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.place_holder_layout, homeFragmentStack.get(homeFragmentStack.size() - 2));
            homeFragmentStack.pop();
            mFragmentTransaction.commit();

        } else if (homeFragmentStack.size() == 1) {


            exitDialog();


        }
    }

    private void exitDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("E Visa");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to exit?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.app_icn);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                dialog.cancel();
                finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}


