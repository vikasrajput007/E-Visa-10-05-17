package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.developer.e_visa.adapters.LanguageAdapter;
import com.android.developer.e_visa.appController.ApplicationController;
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

public class DestinationCountryFragment extends Fragment {
    private String tag_string_req = "tag_string_req";
    int MY_SOCKET_TIMEOUT_MS = 50000;
    RelativeLayout button;
    ImageView apply_now, destination_country_flag, country_banner_image;
    TextView guidline_text, country_name_textview, country_fee, global_service_fee, total;
    View view;
    SharedPreferences sharedPref;
    String ID = "";
    String country_id, country_detail_url, flag_image_url, country_name;
    StringRequest stringRequest;
    ProgressDialog progressDialog;
    Context context;
    ArrayList<String> countryDetailArrayList;
    private int i;
    private int govt = 0;
    private int service = 0;
    private int tot = 0;

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

        countryDetailArrayList = new ArrayList<>();

        System.out.println("value of id is:" + ID);
        System.out.println("value of id is:" + country_id);

        destination_country_flag = (ImageView) view.findViewById(R.id.selected_country_flag);
        country_banner_image = (ImageView) view.findViewById(R.id.country_banner_image);

        country_name_textview = (TextView) view.findViewById(R.id.country_name);

        country_fee = (TextView) view.findViewById(R.id.govt_visa_fee);
        global_service_fee = (TextView) view.findViewById(R.id.global_visa_service_fees);
        total = (TextView) view.findViewById(R.id.total);


        country_name_textview.setText(sharedPref.getString("destination_country_name", ""));


        button = (RelativeLayout) view.findViewById(R.id.apply_now);
        guidline_text = (TextView) view.findViewById(R.id.guidline_text);

        guidline_text.setText(R.string.guidline_detail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //


            }
        });

//        String detail = "<div class=\"detail\">\n" +
//                "                <ul><li>International Travelers whose sole objective of visiting India is recreation, sight-seeing, casual visit to meet friends or relatives, short duration medical treatment or casual business visit.</li><li>Passport should have at least six months validity from the date of arrival in India. The passport should have at least two blank pages for stamping by the Immigration Officer.</li><li>You should have lived at your current address for atleast 2 years.</li><li>International Travelers should have return ticket or onward journey ticket, with sufficient money to spend during his/her stay in India.</li><li>International Travelers having Pakistani Passport or Pakistani origin HAVE TO apply for regular Visa at Indian Mission in your respective jurisdiction/Country.</li><li>Not available to Diplomatic/Official Passport Holders.</li><li>Not available to individuals endorsed on Parent&rsquo;s/Spouse&rsquo;s Passport i.e. each individual should have a separate passport.</li><li>Not available to International Travel Document holders.</li></ul>                 \n" +
//                "                <ul><li>Scanned First Page of Passport, in PDF Format. Size of the PDF File should be in the range of Minimum 10KB to Max 300 KB</li><li>The digital photograph to be uploaded along with the Visa application should meet the following requirements:<ul><li>Format &ndash; JPEG</li><li>Size -Minimum 10 KB to Maximum 1 MB</li><li>The height and width of the Photo must be equal.</li><li>Photo should present Full face, front view, eyes open.</li><li>Center head within frame and present full head from top of hair to bottom of chin.</li><li>Background should be plain light colored or white background.</li><li>No shadows on the face or on the background.</li></ul></li><li>Proof of Address(Any ONE of the following)<ul><li>Utility bill in your name- Water or Electricity bill or driving license or State ID or Lease Agreement. Any one of the Parents ID in case of Minors</li></ul></li></ul>                            </div>";


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

                        }

                        for (int j = 0; j < countryDetailArrayList.size(); j++) {

                            govt = Integer.parseInt(countryDetailArrayList.get(1));
                            service = Integer.parseInt(countryDetailArrayList.get(2));
                            tot = govt + service;

                            country_fee.setText("$ " + govt);
                            global_service_fee.setText("$ " + service);
                            total.setText("$ " + tot);
                            flag_image_url = countryDetailArrayList.get(3);

                        }

                        Picasso.with(context).load(flag_image_url).into(country_banner_image);

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

