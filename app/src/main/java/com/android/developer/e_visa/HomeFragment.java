package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.developer.e_visa.adapters.LanguageAdapter;
import com.android.developer.e_visa.appController.ApplicationController;
import com.android.developer.e_visa.models.DestinationCountry;
import com.android.developer.e_visa.models.ListDetail;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;

import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
import static com.android.developer.e_visa.MainActivity.listDetails;

/**
 * Created by HP on 03-06-2017.
 */

public class HomeFragment extends Fragment {

    private String tag_json_obj = "tag_json_obj";
    private TextView select_language;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private LinearLayout english_language;
    private GridView gridView;
    LanguageAdapter languageAdapter;
    private String ID = "id";
    int MY_SOCKET_TIMEOUT_MS = 50000;
    private ListDetail listDetail;
    private View view;
    private String select_destination = "des";
    private String select_nationality = "nat";
    private String url_labels;
    ProgressDialog progressDialog;
    Context context;
    SelectDestination selectDestination;
    Bundle args;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_home,container,false);
        initView();
        return view;
    }


    private void initView(){
        context = getActivity();

        // there is getActivty() must be required
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        selectDestination = new SelectDestination();

        progressDialog = new ProgressDialog(context);
        args = new Bundle();
        select_language = (TextView) view.findViewById(R.id.select_language);
        select_language.setSelected(true);
        gridView = (GridView)view.findViewById(R.id.gv_language);

        adapterMethod();

    }


    public void adapterMethod(){
        languageAdapter = new LanguageAdapter(context, listDetails);
        gridView.setAdapter(languageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listDetail = (ListDetail) gridView.getAdapter().getItem(position);
                ID = listDetail.getId();

                // to save the language id into session
                editor.putString("langid",ID);
                editor.commit();


                getDestinationDetail();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction =  mFragmentManager.beginTransaction();
                        args.putString("langid", ID);
                        args.putString("select_nationality", select_nationality);
                        args.putString("select_destination", select_destination);
                        selectDestination.setArguments(args);
                        homeFragmentStack.add(selectDestination);
                        mFragmentTransaction.replace(R.id.place_holder_layout,selectDestination);
                        mFragmentTransaction.commit();


                    }
                }, 2000);

            }
        });


    }


    public void getDestinationDetail() {

        try {

            url_labels = "http://webcreationsx.com/evisa/Api/labels/" + ID;

            progressDialog.show();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_labels, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {


                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }

                    try {


                        select_destination = response.getString("emptyDestinationOption");
                        select_nationality = response.getString("emptyNationalityOption");


                    } catch (Exception e) {

                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }

                }
            })

            {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString =
                                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        return Response.success(new JSONObject(jsonString),
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (JSONException je) {
                        return Response.error(new ParseError(je));
                    }
                }

            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy
                    (MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            ApplicationController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        } catch (Exception e) {
        }


    }


}
