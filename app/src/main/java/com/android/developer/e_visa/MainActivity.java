package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // Stack for back button of all pages
    public static Stack<Fragment> homeFragmentStack = new Stack<>();


    // Tag used to cancel the request
    private String tag_json_arry = "json_array_req";
    private String url = "http://webcreationsx.com/evisa-apis/languageapi.php";


    private String ID = "id";
    int MY_SOCKET_TIMEOUT_MS = 50000;
    private ListDetail listDetail;
    private String select_destination, select_nationality;

    public static ArrayList<ListDetail> listDetails = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    HomeFragment homeFragment;
    JsonArrayRequest req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        progressDialog = new ProgressDialog(context);

        initView();

    }


    private void initView() {


        // to get all languages and opens the home fragment into activity
        getLanguagesName();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        homeFragmentStack.add(homeFragment);
        mFragmentTransaction.replace(R.id.place_holder_layout, homeFragment, "homeFragment");
        mFragmentTransaction.commit();

    }


    private void getLanguagesName() {

        try {
            listDetails.clear();

            progressDialog.show();
            req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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
