package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.developer.e_visa.adapters.LanguageAdapter;
import com.android.developer.e_visa.appController.ApplicationController;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // Stack for back button of all pages
    public static Stack<Fragment> homeFragmentStack = new Stack<>();

    private String tag_string_req = "tag_string_req";
    // Tag used to cancel the request
    private String tag_json_arry = "json_array_req";
    private String url = "http://webcreationsx.com/evisa-apis/languageapi.php";
    private String ID = "id";
    int MY_SOCKET_TIMEOUT_MS = 80000;
    private ListDetail listDetail;
    private String select_destination, select_nationality;

    public static ArrayList<ListDetail> listDetails = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    HomeFragment homeFragment;

    public static ArrayList<String> arrival_cities = new ArrayList<>();
    public static ArrayList<String> religion = new ArrayList<>();
    public static ArrayList<String> qualification = new ArrayList<>();
    public static ArrayList<String> aquirred_nationality = new ArrayList<>();
    public static ArrayList<String> by_naturalisation = new ArrayList<>();
    public static ArrayList<String> proof_of_address = new ArrayList<>();
    public static ArrayList<String> marriage_status_list = new ArrayList<>();
    public static ArrayList<String> present_occupation = new ArrayList<>();
    public static ArrayList<String> millitary_police_security_org = new ArrayList<>();
    public static ArrayList<String> visited_before = new ArrayList<>();
    public static ArrayList<String> visa_type = new ArrayList<>();
    public static ArrayList<String> past_countries_visited = new ArrayList<>();
    public static ArrayList<String> fathers_nationality = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        progressDialog = new ProgressDialog(context);

        initView();

    }


    private void initView() {

        // add first item to all spinner adpater array list

        try {

            arrival_cities.add("City of arrival in India");
            religion.add("Reiligion");
            qualification.add("Educational Qualification");
            aquirred_nationality.add("How you accquired your current nationality");
            by_naturalisation.add("If by Naturalization");
            proof_of_address.add("Proof of Address in your Name(Any One)");
            marriage_status_list.add("Your Marriage Status");
            present_occupation.add("Present Occupation");
            millitary_police_security_org.add("If in Military/Police/Security Organisation");
            visited_before.add("Have you ever visited India Before");
            visa_type.add("Visa Type");
            past_countries_visited.add("Have you visited any of these country in the past");
            fathers_nationality.add("Father's Nationality");
            // to get all languages and opens the home fragment into activity
            getLanguagesName();

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            homeFragment = new HomeFragment();
            homeFragmentStack.add(homeFragment);
            mFragmentTransaction.replace(R.id.place_holder_layout, homeFragment, "homeFragment");
            mFragmentTransaction.commit();

        } catch (Exception e) {

        }
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

                    System.out.println("the first response  :" + response);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
