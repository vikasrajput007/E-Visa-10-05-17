package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.developer.e_visa.appController.ApplicationController;
import com.android.developer.e_visa.forms.IndiaVisaFormPersonal;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.developer.e_visa.MainActivity.homeFragmentStack;

public class DestinationCountryFragment extends Fragment {
    private String tag_string_req = "tag_string_req";
    int MY_SOCKET_TIMEOUT_MS = 50000;
    LinearLayout button;
    ImageView apply_now, destination_country_flag, country_banner_image;
    TextView guidline_text, country_name_textview, country_fee, global_service_fee, total,description,guideline_label,fee_detail_label,
            govt_fee_label,service_fee_label,total_label,fill_visa_label,visa_by_mail_label,go_to_label,apply_now_label,
            requirments_for_apply_label,requirments_for_apply;
    LinearLayout guideline_ll;

    View view;
    SharedPreferences sharedPref;
    String ID = "";
    String country_id, country_detail_url, flag_image_url, flag_string;
    StringRequest stringRequest;
    ProgressDialog progressDialog;
    Context context;
    RelativeLayout apply_now_button;
    ArrayList<String> countryDetailArrayList;
    private int i;
    private int govt = 0;
    private int service = 0;
    private int tot = 0;
    IndiaVisaFormPersonal visaForm;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_destination_country_detail, container, false);

        context = getActivity();
        initView();

        return view;

    }


    private void initView() {

        progressDialog = new ProgressDialog(context);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        ID = sharedPref.getString("langid", "");
        country_id = sharedPref.getString("countryid", "");

        System.out.println("value of lang id is:"+ID);
        System.out.println("value of country id is:"+country_id);

        countryDetailArrayList = new ArrayList<>();

        destination_country_flag = (ImageView) view.findViewById(R.id.selected_country_flag);
        country_banner_image = (ImageView) view.findViewById(R.id.country_banner_image);

        country_name_textview = (TextView) view.findViewById(R.id.country_name);

        country_fee = (TextView) view.findViewById(R.id.govt_visa_fee);
        global_service_fee = (TextView) view.findViewById(R.id.global_visa_service_fees);
        total = (TextView) view.findViewById(R.id.total);
        description = (TextView) view.findViewById(R.id.description_text);
        guidline_text = (TextView) view.findViewById(R.id.guidline_text);
//        guideline_ll =(LinearLayout)view.findViewById(R.id.guideline_ll);
//        guideline_ll.setGravity(Gravity.CENTER_HORIZONTAL);


        // label text

        guideline_label = (TextView) view.findViewById(R.id.guideline_label);



        fee_detail_label = (TextView) view.findViewById(R.id.fee_detail_label);
        govt_fee_label = (TextView) view.findViewById(R.id.govt_fee_label);
        fill_visa_label = (TextView) view.findViewById(R.id.fill_visa_label);
        service_fee_label = (TextView) view.findViewById(R.id.service_fee_label);
        total_label = (TextView) view.findViewById(R.id.total_label);
        visa_by_mail_label = (TextView) view.findViewById(R.id.visa_by_mail_label);
        go_to_label = (TextView) view.findViewById(R.id.go_to_label);
        apply_now_label = (TextView) view.findViewById(R.id.apply_now_label);
        requirments_for_apply_label = (TextView) view.findViewById(R.id.requirments_for_apply_label);
        requirments_for_apply = (TextView) view.findViewById(R.id.requirments_for_apply);



        // set labels to textview
        guideline_label.setText(Html.fromHtml(sharedPref.getString("guidline_object", "")));
        fee_detail_label.setText(Html.fromHtml(sharedPref.getString("fee_object", "")));
        govt_fee_label.setText(Html.fromHtml(sharedPref.getString("govt_fees", "")));
        fill_visa_label.setText(Html.fromHtml(sharedPref.getString("fill_application", "")));
        service_fee_label.setText(Html.fromHtml(sharedPref.getString("service_fees", "")));
        total_label.setText(Html.fromHtml(sharedPref.getString("total_fees", "")));
        visa_by_mail_label.setText(Html.fromHtml(sharedPref.getString("visa_by_mail", "")));
        go_to_label.setText(Html.fromHtml(sharedPref.getString("go_to", "")));
        apply_now_label.setText(Html.fromHtml(sharedPref.getString("apply_object", "")));
        requirments_for_apply_label.setText(Html.fromHtml(sharedPref.getString("visa_requirment_label", "")));
        requirments_for_apply.setText(Html.fromHtml(sharedPref.getString("visa_requirment", "")));



        country_name_textview.setText(sharedPref.getString("destination_country_name", ""));


        button = (LinearLayout) view.findViewById(R.id.apply_now);


       // guidline_text.setText(R.string.guidline_detail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                visaForm = new IndiaVisaFormPersonal();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction =  mFragmentManager.beginTransaction();
                homeFragmentStack.add(visaForm);
                mFragmentTransaction.replace(R.id.place_holder_layout,visaForm);
                mFragmentTransaction.commit();


            }
        });


        getDestinationCountries();


    }


    private void getDestinationCountries() {
        country_detail_url = "http://webcreationsx.com/evisa-apis/countrydetailapi.php";
        try {
            progressDialog.show();

            stringRequest = new StringRequest(Request.Method.POST, country_detail_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {


                    System.out.println("value of array is :" + response);

                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("arr");

                        for (i = 0; i < jsonArray.length(); i++) {

                            JSONObject jObject = jsonArray.getJSONObject(i);

                            countryDetailArrayList.add(jObject.getString("id"));
                            countryDetailArrayList.add(jObject.getString("fees"));
                            countryDetailArrayList.add(jObject.getString("globalvisa_fees"));
                            countryDetailArrayList.add(jObject.getString("banner"));
                            countryDetailArrayList.add(jObject.getString("flag"));
                            countryDetailArrayList.add(jObject.getString("guidlines"));
                            countryDetailArrayList.add(jObject.getString("eligibility"));
                            countryDetailArrayList.add(jObject.getString("description"));

                        }


                            govt = Integer.parseInt(countryDetailArrayList.get(1));
                            service = Integer.parseInt(countryDetailArrayList.get(2));
                            tot = govt + service;
                            flag_image_url = countryDetailArrayList.get(3);
                            flag_string = countryDetailArrayList.get(4);

                        System.out.println("first value is :"+Html.fromHtml(countryDetailArrayList.get(5))+ "\n" + "second value is :"+Html.fromHtml(countryDetailArrayList.get(6)) + "\n" + "third value is :"+Html.fromHtml(countryDetailArrayList.get(7)) );

                        guidline_text.setText(Html.fromHtml(countryDetailArrayList.get(5))+""+ "\n"   +"" +Html.fromHtml(countryDetailArrayList.get(6))+ "\n"   +"" +Html.fromHtml(countryDetailArrayList.get(7)));
                       // description.setText(Html.fromHtml(countryDetailArrayList.get(6)));


                        country_fee.setText("$ " + govt);
                        global_service_fee.setText("$ " + service);
                        total.setText("$ " + tot);
                        Picasso.with(context).load(flag_image_url).into(country_banner_image);
                        Picasso.with(context).load(flag_string).into(destination_country_flag);

                    } catch (JSONException e) {

                    }

                }

            }
                    , new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }

                }
            })


            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("langid", ID);
                    params.put("countryid", country_id);

                    return params;
                }

            };

        } catch (Exception e) {
        }

        // to postponed the api hitting and getting response time retry policy is used
        stringRequest.setRetryPolicy(new DefaultRetryPolicy
                (MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

//        requestQueue.add(stringRequest);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, tag_string_req);


    }


}

