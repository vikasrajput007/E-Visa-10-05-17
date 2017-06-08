package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.developer.e_visa.appController.ApplicationController;
import com.android.developer.e_visa.utils.CustomProgressDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.developer.e_visa.MainActivity.homeFragmentStack;


public class SelectDestination extends Fragment implements View.OnClickListener {

    // Tag used to cancel the request
    private String tag_string_req = "tag_string_req";
    int MY_SOCKET_TIMEOUT_MS = 50000;
    private String nationality_url, destination_country_url;
    private String ID = "id";
    private ImageView air;
    private Bitmap bitmap;
    private Spinner spinner, spinner2;
    private String selected_id, select_destination, select_nationality;
    String dest_selectedItem = "";
    String nat_selectedItem = "";
    private Context context;
    ProgressDialog progressDialog;
    TextView title_label_text;
    StringRequest stringRequest;
    // array list for destination country
    private ArrayList<String> dest_country = new ArrayList<>();
    private ArrayList<String> dest_id_Only = new ArrayList<>();
    // destination country adapter
    ArrayAdapter<String> destArrayAdapter;

    // array list for nationality country
    private ArrayList<String> nat_country = new ArrayList<>();
    // private ArrayList<String> nat_id_only = new ArrayList<>();
    // nationality country adapter
    ArrayAdapter<String> natspinnerArrayAdapter;
    View view;
    Bundle bundle;
    boolean isFirstSelection = true;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    DestinationCountryFragment destinationCountryFragment;
    String getAllLabelUrl = "";
    List<String> labels = new ArrayList<>();
//    JSONObject guidline_object,fee_object,apply_object,fill_application,visa_by_mail,go_to,visa_requirment,visa_requirment_label,
//            govt_fees,service_fees,total_fees;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_destination, container, false);

        bundle = this.getArguments();

        try {
            if (bundle != null) {

                select_destination = bundle.getString("select_destination");
                select_nationality = bundle.getString("select_nationality");
                ID = bundle.getString("langid");

            }


        } catch (NullPointerException e) {

        }

        initView();
        return view;

    }


    private void initView() {

        context = getActivity();

        // there is getActivty() must be required
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        progressDialog = new ProgressDialog(context);
        spinner = (Spinner) view.findViewById(R.id.simpleSpinner);
        spinner2 = (Spinner) view.findViewById(R.id.simpleSpinner2);

        title_label_text = (TextView) view.findViewById(R.id.title_label);
        air = (ImageView) view.findViewById(R.id.submit);
        air.setOnClickListener(this);

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.air);
        air.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap, 25));

        getDestinationCountries();

    }

    // destination country comes here

    private void getDestinationCountries() {
        destination_country_url = "http://webcreationsx.com/evisa-apis/destinationsapi.php";
        try {
            progressDialog.show();

            stringRequest = new StringRequest(Method.POST, destination_country_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {


                    System.out.println("destination response is :" + response);


                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }
                    try {

                        dest_country.add(select_destination);

                        // this anything is added into array list very first element cause it increase size by one and get proper
                        // selected destination country in the same sequence
                        dest_id_Only.add("anything");

                        JSONObject jsonObject = new JSONObject(response);


                        title_label_text.setText(Html.fromHtml(jsonObject.getString("selectdestinationlabel")));

                        JSONArray jsonArray = jsonObject.getJSONArray("arr");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jObject = jsonArray.getJSONObject(i);

                            dest_country.add(jObject.getString("name"));

                            dest_id_Only.add(jObject.getString("id"));

                        }


                        try {

                            destArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, dest_country);

                            destArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(destArrayAdapter);
                            destArrayAdapter.notifyDataSetChanged();

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // this check is used to avoid the run of onItemSelected() method at time of fragment creation...
                                    if (isFirstSelection) {
                                        isFirstSelection = false;
                                    } else {
                                        dest_selectedItem = (String) parent.getItemAtPosition(position);

                                        selected_id = dest_id_Only.get(position);

                                        // To save destination country name
                                        editor.putString("destination_country_name", dest_selectedItem);

                                        // To save the language id into session
                                        editor.putString("countryid", selected_id);
                                        editor.commit();


                                        /**
                                         * Method to get the nationality country according to the destination selection
                                         */
                                        getNationalityCountries();


                                        /**
                                         * Method to get all labels
                                         */

                                        getAllLabels();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (Exception e) {

                        }

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

    private void getNationalityCountries() {

        try {

            progressDialog.show();
            progressDialog.setMessage("Nationality Loading");
            nationality_url = "http://webcreationsx.com/evisa-apis/countriesapi.php";
            stringRequest = new StringRequest(Method.POST, nationality_url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }
                    try {
                        nat_country.add(select_nationality);
                        // nat_id_only.add("anything");

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("arr");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jObject = jsonArray.getJSONObject(i);

                            nat_country.add(jObject.getString("name"));

                            // nat_id_only.add(jObject.getString("id"));
                        }

                        natspinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, nat_country);
                        natspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(natspinnerArrayAdapter);
                        natspinnerArrayAdapter.notifyDataSetChanged();

                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (isFirstSelection) {
                                    isFirstSelection = false;
                                } else {

                                    nat_selectedItem = (String) parent.getItemAtPosition(position);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


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
                }
            })


            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("langid", ID);
                    params.put("id", selected_id);

                    return params;
                }

            };

        } catch (Exception e) {
        }

        ApplicationController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void getAllLabels() {

        try {


            getAllLabelUrl = "http://www.webcreationsx.com/evisa-apis/cmslabels.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getAllLabelUrl, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    System.out.println("the label response is :" + response);


                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("arr");


                        System.out.println("value of label array is :"+jsonArray.toString());

//


                        // to save the language id into session
                        editor.putString("guidline_object",jsonArray.getJSONObject(4).getString("Guidlines label"));
                        editor.putString("fee_object",jsonArray.getJSONObject(5).getString("Fee details label"));
                        editor.putString("apply_object",jsonArray.getJSONObject(6).getString("Apply Now Label"));
                        editor.putString("fill_application",jsonArray.getJSONObject(7).getString("fill application label"));
                        editor.putString("visa_by_mail",jsonArray.getJSONObject(8).getString("get visa label"));
                        editor.putString("go_to",jsonArray.getJSONObject(9).getString("go label"));
                        editor.putString("visa_requirment",jsonArray.getJSONObject(10).getString("visa requirements"));
                        editor.putString("visa_requirment_label",jsonArray.getJSONObject(11).getString("Visa Requirements label"));
                        editor.putString("govt_fees",jsonArray.getJSONObject(13).getString("Govt visa fee label"));
                        editor.putString("service_fees",jsonArray.getJSONObject(14).getString("Global visa fee label"));
                        editor.putString("total_fees",jsonArray.getJSONObject(15).getString("Total Label"));
                        editor.commit();

                    } catch (Exception e) {

                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })

            {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("langid", ID);
                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy
                    (MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            ApplicationController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

        } catch (Exception e) {
        }


    }

    @Override
    public void onClick(View v) {

        destinationCountryFragment = new DestinationCountryFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        homeFragmentStack.add(destinationCountryFragment);
        mFragmentTransaction.replace(R.id.place_holder_layout, destinationCountryFragment);
        mFragmentTransaction.commit();


    }

    public static class ImageConverter {

        public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
    }
}
