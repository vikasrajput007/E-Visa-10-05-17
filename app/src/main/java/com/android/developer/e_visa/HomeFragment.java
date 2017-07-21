package com.android.developer.e_visa;

        import android.app.ProgressDialog;
        import android.content.Context;
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
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.developer.e_visa.adapters.LanguageAdapter;
        import com.android.developer.e_visa.appController.ApplicationController;
        import com.android.developer.e_visa.models.ListDetail;
        import com.android.developer.e_visa.utils.ContractVariable;
        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.NetworkResponse;
        import com.android.volley.ParseError;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HttpHeaderParser;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import retrofit2.http.POST;

        import static com.android.developer.e_visa.MainActivity.account_type_list;
        import static com.android.developer.e_visa.MainActivity.application_status_list;
        import static com.android.developer.e_visa.MainActivity.aquirred_nationality;
        import static com.android.developer.e_visa.MainActivity.armenia_stay_period_list;
        import static com.android.developer.e_visa.MainActivity.arrival_cities;
        import static com.android.developer.e_visa.MainActivity.azerbaijan_purpose_list;
        import static com.android.developer.e_visa.MainActivity.behrain_detail_purpose_list;
        import static com.android.developer.e_visa.MainActivity.building_type_list;
        import static com.android.developer.e_visa.MainActivity.career_of_deseas;
        import static com.android.developer.e_visa.MainActivity.contact_language_list;
        import static com.android.developer.e_visa.MainActivity.days_stays_us_list;
        import static com.android.developer.e_visa.MainActivity.dubai_airport_type_list;
        import static com.android.developer.e_visa.MainActivity.dubai_document_type_list;
        import static com.android.developer.e_visa.MainActivity.dubai_visa_type_list;
        import static com.android.developer.e_visa.MainActivity.eta_type_list;
        import static com.android.developer.e_visa.MainActivity.evisa_type_list;
        import static com.android.developer.e_visa.MainActivity.funds_list;
        import static com.android.developer.e_visa.MainActivity.gcc_list;
        import static com.android.developer.e_visa.MainActivity.gender_type;
        import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
        import static com.android.developer.e_visa.MainActivity.how_long_list;
        import static com.android.developer.e_visa.MainActivity.iam_list;
        import static com.android.developer.e_visa.MainActivity.iiss_list;
        import static com.android.developer.e_visa.MainActivity.immigration_status_list;
        import static com.android.developer.e_visa.MainActivity.insurancedays_list;
        import static com.android.developer.e_visa.MainActivity.kenya_air_menu_list;
        import static com.android.developer.e_visa.MainActivity.kenya_apply_list;
        import static com.android.developer.e_visa.MainActivity.kenya_arrival_menu_list;
        import static com.android.developer.e_visa.MainActivity.kenya_road_menu_list;
        import static com.android.developer.e_visa.MainActivity.kenya_ship_menu_list;
        import static com.android.developer.e_visa.MainActivity.kuwait_education_list;
        import static com.android.developer.e_visa.MainActivity.kuwait_gcc_country_list;
        import static com.android.developer.e_visa.MainActivity.kuwait_passport_list;
        import static com.android.developer.e_visa.MainActivity.listDetails;
        import static com.android.developer.e_visa.MainActivity.marriage_status_list;
        import static com.android.developer.e_visa.MainActivity.millitary_police_security_org;
        import static com.android.developer.e_visa.MainActivity.multiple_visa_type_list;
        import static com.android.developer.e_visa.MainActivity.myanmaar_accomodation_list;
        import static com.android.developer.e_visa.MainActivity.myanmaar_port_entry_list;
        import static com.android.developer.e_visa.MainActivity.myanmaar_visa_type_list;
        import static com.android.developer.e_visa.MainActivity.passport_type_list;
        import static com.android.developer.e_visa.MainActivity.past_countries_visited;
        import static com.android.developer.e_visa.MainActivity.point_of_entry_list;
        import static com.android.developer.e_visa.MainActivity.port_list;
        import static com.android.developer.e_visa.MainActivity.present_occupation_list;
        import static com.android.developer.e_visa.MainActivity.proof_of_address;
        import static com.android.developer.e_visa.MainActivity.purpose_list;
        import static com.android.developer.e_visa.MainActivity.qualification;
        import static com.android.developer.e_visa.MainActivity.religion_list;
        import static com.android.developer.e_visa.MainActivity.residence_status_list;
        import static com.android.developer.e_visa.MainActivity.single_visa_type_list;
        import static com.android.developer.e_visa.MainActivity.spouse_nationality_list;
        import static com.android.developer.e_visa.MainActivity.srilanka_purpose_list;
        import static com.android.developer.e_visa.MainActivity.srilanka_title_list;
        import static com.android.developer.e_visa.MainActivity.stay_period_request_list;
        import static com.android.developer.e_visa.MainActivity.tajikistan_bussiness_list;
        import static com.android.developer.e_visa.MainActivity.tajikistan_passport_type;
        import static com.android.developer.e_visa.MainActivity.tajikistan_private;
        import static com.android.developer.e_visa.MainActivity.tajikistan_purpose_list;
        import static com.android.developer.e_visa.MainActivity.tajikistan_tuorism_list;
        import static com.android.developer.e_visa.MainActivity.telephone_type_list;
        import static com.android.developer.e_visa.MainActivity.title_list;
        import static com.android.developer.e_visa.MainActivity.travel_document_type_list;
        import static com.android.developer.e_visa.MainActivity.turky_duration_list;
        import static com.android.developer.e_visa.MainActivity.turky_entry_list;
        import static com.android.developer.e_visa.MainActivity.uganda_visa_category;
        import static com.android.developer.e_visa.MainActivity.us_going_list;
        import static com.android.developer.e_visa.MainActivity.us_visa_purpose_list;
        import static com.android.developer.e_visa.MainActivity.visa_type_list;
        import static com.android.developer.e_visa.MainActivity.visited_before;
        import static com.android.developer.e_visa.MainActivity.where_stays_us_list;
        import static com.android.developer.e_visa.MainActivity.yesno_list;
        import static com.android.developer.e_visa.utils.ContractVariable.AccountType;
        import static com.android.developer.e_visa.utils.ContractVariable.ApplicationStatus;
        import static com.android.developer.e_visa.utils.ContractVariable.ArmeniaStayPeriod;
        import static com.android.developer.e_visa.utils.ContractVariable.ArrivalCities;
        import static com.android.developer.e_visa.utils.ContractVariable.AzerbaijanPurpose;
        import static com.android.developer.e_visa.utils.ContractVariable.BahrainDetailPurpose;
        import static com.android.developer.e_visa.utils.ContractVariable.BuildingType;
        import static com.android.developer.e_visa.utils.ContractVariable.Career;
        import static com.android.developer.e_visa.utils.ContractVariable.CarrierOfDieases;
        import static com.android.developer.e_visa.utils.ContractVariable.DaysStayInUs;
        import static com.android.developer.e_visa.utils.ContractVariable.DubaiAirport;
        import static com.android.developer.e_visa.utils.ContractVariable.DubaiDocuments;
        import static com.android.developer.e_visa.utils.ContractVariable.DubaiVisaType;
        import static com.android.developer.e_visa.utils.ContractVariable.ETAtype;
        import static com.android.developer.e_visa.utils.ContractVariable.Education;
        import static com.android.developer.e_visa.utils.ContractVariable.EvisaType;
        import static com.android.developer.e_visa.utils.ContractVariable.Gcc;
        import static com.android.developer.e_visa.utils.ContractVariable.Gender;
        import static com.android.developer.e_visa.utils.ContractVariable.Howlong;
        import static com.android.developer.e_visa.utils.ContractVariable.Iiss;
        import static com.android.developer.e_visa.utils.ContractVariable.ImmigrationStatus;
        import static com.android.developer.e_visa.utils.ContractVariable.KenyaAirMenu;
        import static com.android.developer.e_visa.utils.ContractVariable.KenyaApplyFor;
        import static com.android.developer.e_visa.utils.ContractVariable.KenyaArrivalMenu;
        import static com.android.developer.e_visa.utils.ContractVariable.KenyaRoadMenu;
        import static com.android.developer.e_visa.utils.ContractVariable.KenyaShipMenu;
        import static com.android.developer.e_visa.utils.ContractVariable.KuwaitEducation;
        import static com.android.developer.e_visa.utils.ContractVariable.KuwaitGccCountry;
        import static com.android.developer.e_visa.utils.ContractVariable.KuwaitPassport;
        import static com.android.developer.e_visa.utils.ContractVariable.MultipleEvisa;
        import static com.android.developer.e_visa.utils.ContractVariable.MyanmarAccomodationType;
        import static com.android.developer.e_visa.utils.ContractVariable.MyanmarPortEntry;
        import static com.android.developer.e_visa.utils.ContractVariable.MyanmarVisaType;
        import static com.android.developer.e_visa.utils.ContractVariable.PointOfEntry;
        import static com.android.developer.e_visa.utils.ContractVariable.Port;
        import static com.android.developer.e_visa.utils.ContractVariable.Purpose;
        import static com.android.developer.e_visa.utils.ContractVariable.ResidenceStatus;
        import static com.android.developer.e_visa.utils.ContractVariable.SingleEvisa;
        import static com.android.developer.e_visa.utils.ContractVariable.Spousenationality;
        import static com.android.developer.e_visa.utils.ContractVariable.SrilankaTitle;
        import static com.android.developer.e_visa.utils.ContractVariable.SrilankaVisaPurpose;
        import static com.android.developer.e_visa.utils.ContractVariable.StayPeriodRequested;
        import static com.android.developer.e_visa.utils.ContractVariable.TajikistanBusiness;
        import static com.android.developer.e_visa.utils.ContractVariable.TajikistanPassportType;
        import static com.android.developer.e_visa.utils.ContractVariable.TajikistanPrivate;
        import static com.android.developer.e_visa.utils.ContractVariable.TajikistanPurpose;
        import static com.android.developer.e_visa.utils.ContractVariable.TajikistanTourism;
        import static com.android.developer.e_visa.utils.ContractVariable.TelephoneType;
        import static com.android.developer.e_visa.utils.ContractVariable.Title;
        import static com.android.developer.e_visa.utils.ContractVariable.TravelDocumentType;
        import static com.android.developer.e_visa.utils.ContractVariable.UgandaVisaCategory;
        import static com.android.developer.e_visa.utils.ContractVariable.UsGoing;
        import static com.android.developer.e_visa.utils.ContractVariable.UsVisaPurpose;
        import static com.android.developer.e_visa.utils.ContractVariable.VisaType;
        import static com.android.developer.e_visa.utils.ContractVariable.WhereStayInUs;
        import static com.android.developer.e_visa.utils.ContractVariable.acquiredNationality;
        import static com.android.developer.e_visa.utils.ContractVariable.contactLanguage;
        import static com.android.developer.e_visa.utils.ContractVariable.funds;
        import static com.android.developer.e_visa.utils.ContractVariable.iam;
        import static com.android.developer.e_visa.utils.ContractVariable.insurancedays;
        import static com.android.developer.e_visa.utils.ContractVariable.isVisited;
        import static com.android.developer.e_visa.utils.ContractVariable.passportType;
        import static com.android.developer.e_visa.utils.ContractVariable.status;
        import static com.android.developer.e_visa.utils.ContractVariable.proof;
        import static com.android.developer.e_visa.utils.ContractVariable.religion;
        import static com.android.developer.e_visa.utils.ContractVariable.turkeyDuration;
        import static com.android.developer.e_visa.utils.ContractVariable.turkeyEntry;
        import static com.android.developer.e_visa.utils.ContractVariable.yesno;

/**
 * Created by HP on 03-06-2017.
 */

public class HomeFragment extends Fragment {
    private String tag_json_obj = "tag_json_obj";
    private TextView select_language;
    private String tag_string_req = "tag_string_req";
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private LinearLayout english_language;
    private GridView gridView;
    LanguageAdapter languageAdapter;
    private String ID = "id";
    int MY_SOCKET_TIMEOUT_MS = 80000;
    private ListDetail listDetail;
    private View view;
    private String select_destination = "destination";
    private String select_nationality = "nationality";
    private String url_labels;
    ProgressDialog progressDialog;
    Context context;
    SelectDestination selectDestination;
    Bundle args;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public static ArrayList mater_country_array_list ;
    private ArrayList<String> master_country = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_home, container, false);
        initView();
        return view;
    }


    private void initView() {
        context = getActivity().getApplicationContext();

        // there is getActivty() must be required
        sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        mater_country_array_list = new ArrayList<String>();
        selectDestination = new SelectDestination();

        progressDialog = new ProgressDialog(getActivity());
        args = new Bundle();

        select_language = (TextView) view.findViewById(R.id.select_language);
        select_language.setSelected(true);
        gridView = (GridView) view.findViewById(R.id.gv_language);

        adapterMethod();

    }


    public void adapterMethod() {
        languageAdapter = new LanguageAdapter(context, listDetails);
        gridView.setAdapter(languageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listDetail = (ListDetail) gridView.getAdapter().getItem(position);
                ID = listDetail.getId();

                progressDialog.show();
                progressDialog.setMessage("Please Wait . . . ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);

                // To get the master countries for selection
                getMasterCountry();

                System.out.println("value of master country response :");
                getDestinationDetail();

                System.out.println(" is  getDestinationDetail()  started:");
                // to save the language id into session
                editor.putString("langid", ID);
                editor.commit();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (progressDialog.isShowing() && progressDialog != null) {

                            progressDialog.dismiss();
                        }
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        args.putString("langid", ID);
                        args.putString("select_nationality", select_nationality);
                        args.putString("select_destination", select_destination);
                        selectDestination.setArguments(args);
                        homeFragmentStack.add(selectDestination);
                        mFragmentTransaction.replace(R.id.place_holder_layout, selectDestination);
                        mFragmentTransaction.commit();

                    }
                }, 3000);

            }
        });


    }


    public void getMasterCountry(){
        try {

            System.out.println("value of master country response : into master country ");
            String master_url = "http://webcreationsx.com/evisa-apis/mastercountriesapi.php";
//            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, master_url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    try {


//                        System.out.println("value of master country response : into response ");
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("arr");


                        System.out.println("value of master country response :"+response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jObject = jsonArray.getJSONObject(i);

                            master_country.add(jObject.getString("name"));

                            // nat_id_only.add(jObject.getString("id"));
                        }

                        mater_country_array_list = new ArrayList<>(master_country);
                    } catch (JSONException e) {
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


    public void getDestinationDetail() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    url_labels = "http://webcreationsx.com/evisa/Api/labels/" + ID;

                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_labels, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                select_destination = response.getString("emptyDestinationOption");
                                select_nationality = response.getString("emptyNationalityOption");

                                insurancedays = response.getJSONObject("insurancedays");
                                insurancedays_list.add(insurancedays.getString("1"));
                                insurancedays_list.add(insurancedays.getString("2"));
                                insurancedays_list.add(insurancedays.getString("3"));
                                insurancedays_list.add(insurancedays.getString("4"));


                                visited_before.add("Yes");
                                visited_before.add("No");


                                millitary_police_security_org.add("Yes");
                                millitary_police_security_org.add("No");

                                status = response.getJSONObject("Status");
                                marriage_status_list.add(status.getString("1"));
                                marriage_status_list.add(status.getString("2"));
                                marriage_status_list.add(status.getString("3"));
                                marriage_status_list.add(status.getString("4"));
                                marriage_status_list.add(status.getString("5"));

                                proof = response.getJSONObject("Proof");
                                proof_of_address.add(proof.getString("1"));
                                proof_of_address.add(proof.getString("2"));
                                proof_of_address.add(proof.getString("3"));
                                proof_of_address.add(proof.getString("4"));
                                proof_of_address.add(proof.getString("5"));
                                proof_of_address.add(proof.getString("6"));
                                proof_of_address.add(proof.getString("7"));

                                religion = response.getJSONObject("Religion");
                                religion_list.add(religion.getString(("1")));
                                religion_list.add(religion.getString("2"));
                                religion_list.add(religion.getString("3"));
                                religion_list.add(religion.getString("4"));
                                religion_list.add(religion.getString("5"));
                                religion_list.add(religion.getString("6"));
                                religion_list.add(religion.getString("7"));
                                religion_list.add(religion.getString("8"));
                                religion_list.add(religion.getString("9"));
                                religion_list.add(religion.getString("10"));
                                religion_list.add(religion.getString("11"));


                                Purpose = response.getJSONObject("Purpose");
                                purpose_list.add(Purpose.getString("1"));
                                purpose_list.add(Purpose.getString("2"));
                                purpose_list.add(Purpose.getString("3"));
                                purpose_list.add(Purpose.getString("4"));
                                purpose_list.add(Purpose.getString("5"));
                                purpose_list.add(Purpose.getString("6"));
                                purpose_list.add(Purpose.getString("7"));


                                isVisited = response.getJSONObject("isVisited");
                                past_countries_visited.add("No");
                                past_countries_visited.add(isVisited.getString("1"));
                                past_countries_visited.add(isVisited.getString("2"));
                                past_countries_visited.add(isVisited.getString("3"));
                                past_countries_visited.add(isVisited.getString("4"));
                                past_countries_visited.add(isVisited.getString("5"));
                                past_countries_visited.add(isVisited.getString("6"));
                                past_countries_visited.add(isVisited.getString("7"));


                                AccountType = response.getJSONObject("AccountType");
                                account_type_list.add(AccountType.getString("1"));
                                account_type_list.add(AccountType.getString("2"));
                                account_type_list.add(AccountType.getString("3"));
                                account_type_list.add(AccountType.getString("4"));


                                ArrivalCities = response.getJSONObject("ArrivalCities");
                                arrival_cities.add(ArrivalCities.getString("1"));
                                arrival_cities.add(ArrivalCities.getString("2"));
                                arrival_cities.add(ArrivalCities.getString("3"));
                                arrival_cities.add(ArrivalCities.getString("4"));
                                arrival_cities.add(ArrivalCities.getString("5"));
                                arrival_cities.add(ArrivalCities.getString("6"));
                                arrival_cities.add(ArrivalCities.getString("7"));
                                arrival_cities.add(ArrivalCities.getString("8"));
                                arrival_cities.add(ArrivalCities.getString("9"));
                                arrival_cities.add(ArrivalCities.getString("10"));
                                arrival_cities.add(ArrivalCities.getString("11"));
                                arrival_cities.add(ArrivalCities.getString("12"));
                                arrival_cities.add(ArrivalCities.getString("13"));
                                arrival_cities.add(ArrivalCities.getString("14"));
                                arrival_cities.add(ArrivalCities.getString("15"));
                                arrival_cities.add(ArrivalCities.getString("16"));
                                arrival_cities.add(ArrivalCities.getString("17"));
                                arrival_cities.add(ArrivalCities.getString("18"));
                                arrival_cities.add(ArrivalCities.getString("19"));
                                arrival_cities.add(ArrivalCities.getString("20"));
                                arrival_cities.add(ArrivalCities.getString("21"));
                                arrival_cities.add(ArrivalCities.getString("22"));
                                arrival_cities.add(ArrivalCities.getString("23"));
                                arrival_cities.add(ArrivalCities.getString("24"));
                                arrival_cities.add(ArrivalCities.getString("25"));
                                arrival_cities.add(ArrivalCities.getString("26"));


//                                qualification.clear();
                                Education = response.getJSONObject("Education");
                                qualification.add(Education.getString("1"));
                                qualification.add(Education.getString("2"));
                                qualification.add(Education.getString("3"));
                                qualification.add(Education.getString("4"));
                                qualification.add(Education.getString("5"));
                                qualification.add(Education.getString("6"));
                                qualification.add(Education.getString("7"));
                                qualification.add(Education.getString("8"));
                                qualification.add(Education.getString("9"));
                                qualification.add(Education.getString("10"));


                                Career = response.getJSONObject("Career");
                                present_occupation_list.add(Career.getString("1"));
                                present_occupation_list.add(Career.getString("2"));
                                present_occupation_list.add(Career.getString("3"));
                                present_occupation_list.add(Career.getString("4"));
                                present_occupation_list.add(Career.getString("5"));
                                present_occupation_list.add(Career.getString("6"));
                                present_occupation_list.add(Career.getString("7"));
                                present_occupation_list.add(Career.getString("8"));
                                present_occupation_list.add(Career.getString("9"));
                                present_occupation_list.add(Career.getString("10"));
                                present_occupation_list.add(Career.getString("11"));
                                present_occupation_list.add(Career.getString("12"));
                                present_occupation_list.add(Career.getString("13"));
                                present_occupation_list.add(Career.getString("14"));
                                present_occupation_list.add(Career.getString("15"));
                                present_occupation_list.add(Career.getString("16"));
                                present_occupation_list.add(Career.getString("17"));
                                present_occupation_list.add(Career.getString("18"));
                                present_occupation_list.add(Career.getString("19"));
                                present_occupation_list.add(Career.getString("20"));
                                present_occupation_list.add(Career.getString("21"));
                                present_occupation_list.add(Career.getString("22"));
                                present_occupation_list.add(Career.getString("23"));
                                present_occupation_list.add(Career.getString("24"));
                                present_occupation_list.add(Career.getString("25"));
                                present_occupation_list.add(Career.getString("26"));
                                present_occupation_list.add(Career.getString("27"));
                                present_occupation_list.add(Career.getString("28"));
                                present_occupation_list.add(Career.getString("29"));
                                present_occupation_list.add(Career.getString("30"));


                                VisaType = response.getJSONObject("VisaType");
                                visa_type_list.add(VisaType.getString("1"));
                                visa_type_list.add(VisaType.getString("2"));

//                                aquirred_nationality.clear();
                                acquiredNationality = response.getJSONObject("acquiredNationality");
                                aquirred_nationality.add(acquiredNationality.getString("1"));
                                aquirred_nationality.add(acquiredNationality.getString("2"));
                                aquirred_nationality.add(acquiredNationality.getString("3"));
                                aquirred_nationality.add(acquiredNationality.getString("4"));


                                ApplicationStatus = response.getJSONObject("ApplicationStatus");
                                application_status_list.add(ApplicationStatus.getString("1"));
                                application_status_list.add(ApplicationStatus.getString("2"));
                                application_status_list.add(ApplicationStatus.getString("3"));
                                application_status_list.add(ApplicationStatus.getString("4"));


                                TelephoneType = response.getJSONObject("TelephoneType");
                                telephone_type_list.add(TelephoneType.getString("1"));
                                telephone_type_list.add(TelephoneType.getString("2"));
                                telephone_type_list.add(TelephoneType.getString("3"));
                                telephone_type_list.add(TelephoneType.getString("4"));


                                ContractVariable.Gender = response.getJSONObject("Gender");
                                gender_type.add(Gender.getString("1"));
                                gender_type.add(Gender.getString("2"));

                                Iiss = response.getJSONObject("Iiss");
                                iiss_list.add(Iiss.getString("1"));
                                iiss_list.add(Iiss.getString("2"));
                                iiss_list.add(Iiss.getString("3"));
                                iiss_list.add(Iiss.getString("4"));


                                TravelDocumentType = response.getJSONObject("TravelDocumentType");
                                travel_document_type_list.add(TravelDocumentType.getString("1"));
                                travel_document_type_list.add(TravelDocumentType.getString("2"));
                                travel_document_type_list.add(TravelDocumentType.getString("3"));
                                travel_document_type_list.add(TravelDocumentType.getString("4"));
                                travel_document_type_list.add(TravelDocumentType.getString("5"));
                                travel_document_type_list.add(TravelDocumentType.getString("6"));


                                ContractVariable.DaysStayInUs = response.getJSONObject("DaysStayInUs");
                                days_stays_us_list.add(DaysStayInUs.getString("1"));
                                days_stays_us_list.add(DaysStayInUs.getString("2"));
                                days_stays_us_list.add(DaysStayInUs.getString("3"));


                                WhereStayInUs = response.getJSONObject("WhereStayInUs");
                                where_stays_us_list.add(WhereStayInUs.getString("1"));
                                where_stays_us_list.add(WhereStayInUs.getString("2"));
                                where_stays_us_list.add(WhereStayInUs.getString("3"));
                                where_stays_us_list.add(WhereStayInUs.getString("4"));


                                ContractVariable.UsGoing = response.getJSONObject("UsGoing");
                                us_going_list.add(UsGoing.getString("1"));
                                us_going_list.add(UsGoing.getString("2"));


                                ResidenceStatus = response.getJSONObject("ResidenceStatus");
                                residence_status_list.add(ResidenceStatus.getString("1"));
                                residence_status_list.add(ResidenceStatus.getString("2"));
                                residence_status_list.add(ResidenceStatus.getString("3"));
                                residence_status_list.add(ResidenceStatus.getString("4"));
                                residence_status_list.add(ResidenceStatus.getString("5"));

                                UsVisaPurpose = response.getJSONObject("UsVisaPurpose");
                                us_visa_purpose_list.add(UsVisaPurpose.getString("1"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("2"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("3"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("4"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("5"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("6"));
                                us_visa_purpose_list.add(UsVisaPurpose.getString("7"));


                                ContractVariable.DubaiVisaType = response.getJSONObject("DubaiVisaType");
                                dubai_visa_type_list.add(DubaiVisaType.getString("1"));
                                dubai_visa_type_list.add(DubaiVisaType.getString("2"));
                                dubai_visa_type_list.add(DubaiVisaType.getString("3"));
                                dubai_visa_type_list.add(DubaiVisaType.getString("4"));


                                ContractVariable.DubaiAirport = response.getJSONObject("DubaiAirport");
                                dubai_airport_type_list.add(DubaiAirport.getString("1"));
                                dubai_airport_type_list.add(DubaiAirport.getString("2"));
                                dubai_airport_type_list.add(DubaiAirport.getString("3"));


                                ContractVariable.DubaiDocuments = response.getJSONObject("DubaiDocuments");
                                dubai_document_type_list.add(DubaiDocuments.getString("1"));
                                dubai_document_type_list.add(DubaiDocuments.getString("2"));
                                dubai_document_type_list.add(DubaiDocuments.getString("3"));
                                dubai_document_type_list.add(DubaiDocuments.getString("4"));


                                ContractVariable.Howlong = response.getJSONObject("Howlong");
                                how_long_list.add(Howlong.getString("1"));
                                how_long_list.add(Howlong.getString("2"));


                                ContractVariable.Spousenationality = response.getJSONObject("Spousenationality");
                                spouse_nationality_list.add(Spousenationality.getString("1"));
                                spouse_nationality_list.add(Spousenationality.getString("2"));
                                spouse_nationality_list.add(Spousenationality.getString("3"));


                                ContractVariable.KenyaArrivalMenu = response.getJSONObject("KenyaArrivalMenu");
                                kenya_arrival_menu_list.add(KenyaArrivalMenu.getString("1"));
                                kenya_arrival_menu_list.add(KenyaArrivalMenu.getString("2"));
                                kenya_arrival_menu_list.add(KenyaArrivalMenu.getString("3"));


                                ContractVariable.KenyaAirMenu = response.getJSONObject("KenyaAirMenu");
                                kenya_air_menu_list.add(KenyaAirMenu.getString("1"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("2"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("3"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("4"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("5"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("6"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("7"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("8"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("9"));
                                kenya_air_menu_list.add(KenyaAirMenu.getString("10"));


                                ContractVariable.KenyaRoadMenu = response.getJSONObject("KenyaRoadMenu");
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("1"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("2"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("3"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("4"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("5"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("6"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("7"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("8"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("9"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("10"));
                                kenya_road_menu_list.add(KenyaRoadMenu.getString("11"));


                                ContractVariable.KenyaShipMenu = response.getJSONObject("KenyaShipMenu");
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("1"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("2"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("3"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("4"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("5"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("6"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("7"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("8"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("9"));
                                kenya_ship_menu_list.add(KenyaShipMenu.getString("10"));


                                ContractVariable.KenyaApplyFor = response.getJSONObject("KenyaApplyFor");
                                kenya_apply_list.add(KenyaApplyFor.getString("1"));
                                kenya_apply_list.add(KenyaApplyFor.getString("2"));


                                ContractVariable.SrilankaTitle = response.getJSONObject("SrilankaTitle");
                                srilanka_title_list.add(SrilankaTitle.getString("1"));
                                srilanka_title_list.add(SrilankaTitle.getString("2"));
                                srilanka_title_list.add(SrilankaTitle.getString("3"));
                                srilanka_title_list.add(SrilankaTitle.getString("4"));
                                srilanka_title_list.add(SrilankaTitle.getString("5"));
                                srilanka_title_list.add(SrilankaTitle.getString("6"));
                                srilanka_title_list.add(SrilankaTitle.getString("7"));


                                ContractVariable.SrilankaVisaPurpose = response.getJSONObject("SrilankaVisaPurpose");
                                srilanka_purpose_list.add(SrilankaVisaPurpose.getString("1"));
                                srilanka_purpose_list.add(SrilankaVisaPurpose.getString("2"));

                                ContractVariable.Title = response.getJSONObject("Title");
                                title_list.add(Title.getString("1"));
                                title_list.add(Title.getString("2"));
                                title_list.add(Title.getString("3"));
                                title_list.add(Title.getString("4"));
                                title_list.add(Title.getString("5"));
                                title_list.add(Title.getString("6"));
                                title_list.add(Title.getString("7"));


                                ContractVariable.iam = response.getJSONObject("iam");
                                iam_list.add(iam.getString("1"));
                                iam_list.add(iam.getString("2"));
                                iam_list.add(iam.getString("3"));
                                iam_list.add(iam.getString("4"));
                                iam_list.add(iam.getString("5"));
                                iam_list.add(iam.getString("6"));


                                ContractVariable.funds = response.getJSONObject("funds");
                                funds_list.add(funds.getString("1"));
                                funds_list.add(funds.getString("2"));
                                funds_list.add(funds.getString("3"));
                                funds_list.add(funds.getString("4"));
                                funds_list.add(funds.getString("5"));
                                funds_list.add(funds.getString("6"));
                                funds_list.add(funds.getString("7"));


                                ContractVariable.contactLanguage = response.getJSONObject("contactLanguage");
                                contact_language_list.add(contactLanguage.getString("1"));
                                contact_language_list.add(contactLanguage.getString("2"));

                                ContractVariable.ETAtype = response.getJSONObject("ETAtype");
                                eta_type_list.add(ETAtype.getString("1"));
                                eta_type_list.add(ETAtype.getString("2"));


                                ContractVariable.passportType = response.getJSONObject("passportType");
                                passport_type_list.add(passportType.getString("1"));
                                passport_type_list.add(passportType.getString("2"));
                                passport_type_list.add(passportType.getString("3"));
                                passport_type_list.add(passportType.getString("4"));

                                ContractVariable.turkeyDuration = response.getJSONObject("turkeyDuration");
                                turky_duration_list.add(turkeyDuration.getString("1"));
                                turky_duration_list.add(turkeyDuration.getString("2"));

                                turkeyEntry = response.getJSONObject("turkeyEntry");
                                turky_entry_list.add(turkeyEntry.getString("1"));
                                turky_entry_list.add(turkeyEntry.getString("2"));


                                ContractVariable.Gcc = response.getJSONObject("Gcc");
                                gcc_list.add(Gcc.getString("1"));
                                gcc_list.add(Gcc.getString("2"));
                                gcc_list.add(Gcc.getString("3"));
                                gcc_list.add(Gcc.getString("4"));
                                gcc_list.add(Gcc.getString("5"));


                                Port = response.getJSONObject("Port");
                                port_list.add(Port.getString("1"));
                                port_list.add(Port.getString("2"));
                                port_list.add(Port.getString("3"));
                                port_list.add(Port.getString("4"));
                                port_list.add(Port.getString("5"));


                                ContractVariable.BahrainDetailPurpose = response.getJSONObject("BahrainDetailPurpose");
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("1"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("2"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("3"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("4"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("5"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("6"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("7"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("8"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("9"));
                                behrain_detail_purpose_list.add(BahrainDetailPurpose.getString("10"));


                                ContractVariable.ArmeniaStayPeriod = response.getJSONObject("ArmeniaStayPeriod");
                                armenia_stay_period_list.add(ArmeniaStayPeriod.getString("1"));
                                armenia_stay_period_list.add(ArmeniaStayPeriod.getString("2"));


                                ContractVariable.EvisaType = response.getJSONObject("EvisaType");
                                evisa_type_list.add(EvisaType.getString("1"));
                                evisa_type_list.add(EvisaType.getString("2"));

                                ContractVariable.SingleEvisa = response.getJSONObject("SingleEvisa");
                                single_visa_type_list.add(SingleEvisa.getString("1"));
                                single_visa_type_list.add(SingleEvisa.getString("2"));


                                ContractVariable.MultipleEvisa = response.getJSONObject("MultipleEvisa");
                                multiple_visa_type_list.add(MultipleEvisa.getString("1"));


                                ContractVariable.UgandaVisaCategory = response.getJSONObject("UgandaVisaCategory");
                                uganda_visa_category.add(UgandaVisaCategory.getString("1"));
                                uganda_visa_category.add(UgandaVisaCategory.getString("2"));
                                uganda_visa_category.add(UgandaVisaCategory.getString("3"));


                                ContractVariable.ImmigrationStatus = response.getJSONObject("ImmigrationStatus");
                                immigration_status_list.add(ImmigrationStatus.getString("1"));
                                immigration_status_list.add(ImmigrationStatus.getString("2"));
                                immigration_status_list.add(ImmigrationStatus.getString("3"));
                                immigration_status_list.add(ImmigrationStatus.getString("4"));
                                immigration_status_list.add(ImmigrationStatus.getString("5"));

                                ContractVariable.PointOfEntry = response.getJSONObject("PointOfEntry");
                                point_of_entry_list.add(PointOfEntry.getString("1"));
                                point_of_entry_list.add(PointOfEntry.getString("2"));
                                point_of_entry_list.add(PointOfEntry.getString("3"));
                                point_of_entry_list.add(PointOfEntry.getString("4"));
                                point_of_entry_list.add(PointOfEntry.getString("5"));
                                point_of_entry_list.add(PointOfEntry.getString("6"));

                                // ContractVariable.MaritalStatus = response.getJSONObject("MaritalStatus");


                                ContractVariable.StayPeriodRequested = response.getJSONObject("StayPeriodRequested");
                                stay_period_request_list.add(StayPeriodRequested.getString("1"));
                                stay_period_request_list.add(StayPeriodRequested.getString("2"));
                                stay_period_request_list.add(StayPeriodRequested.getString("3"));

                                ContractVariable.MyanmarPortEntry = response.getJSONObject("MyanmarPortEntry");
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("1"));
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("2"));
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("3"));
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("4"));
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("5"));
                                myanmaar_port_entry_list.add(MyanmarPortEntry.getString("6"));


                                ContractVariable.MyanmarAccomodationType = response.getJSONObject("MyanmarAccomodationType");
                                myanmaar_accomodation_list.add(MyanmarAccomodationType.getString("1"));
                                myanmaar_accomodation_list.add(MyanmarAccomodationType.getString("2"));
                                myanmaar_accomodation_list.add(MyanmarAccomodationType.getString("3"));
                                myanmaar_accomodation_list.add(MyanmarAccomodationType.getString("4"));
                                myanmaar_accomodation_list.add(MyanmarAccomodationType.getString("5"));


                                ContractVariable.MyanmarVisaType = response.getJSONObject("MyanmarVisaType");
                                myanmaar_visa_type_list.add(MyanmarVisaType.getString("1"));
                                myanmaar_visa_type_list.add(MyanmarVisaType.getString("1"));

                                ContractVariable.KuwaitPassport = response.getJSONObject("KuwaitPassport");
                                kuwait_passport_list.add(KuwaitPassport.getString("1"));
                                kuwait_passport_list.add(KuwaitPassport.getString("2"));
                                kuwait_passport_list.add(KuwaitPassport.getString("3"));
                                kuwait_passport_list.add(KuwaitPassport.getString("4"));


                                ContractVariable.KuwaitGccCountry = response.getJSONObject("KuwaitGccCountry");
                                kuwait_gcc_country_list.add(KuwaitGccCountry.getString("1"));
                                kuwait_gcc_country_list.add(KuwaitGccCountry.getString("2"));
                                kuwait_gcc_country_list.add(KuwaitGccCountry.getString("3"));
                                kuwait_gcc_country_list.add(KuwaitGccCountry.getString("4"));
                                kuwait_gcc_country_list.add(KuwaitGccCountry.getString("5"));


                                ContractVariable.KuwaitEducation = response.getJSONObject("KuwaitEducation");
                                kuwait_education_list.add(KuwaitEducation.getString("1"));
                                kuwait_education_list.add(KuwaitEducation.getString("2"));
                                kuwait_education_list.add(KuwaitEducation.getString("3"));
                                kuwait_education_list.add(KuwaitEducation.getString("4"));
                                kuwait_education_list.add(KuwaitEducation.getString("5"));

                                ContractVariable.BuildingType = response.getJSONObject("BuildingType");
                                building_type_list.add(BuildingType.getString("1"));
                                building_type_list.add(BuildingType.getString("2"));
                                building_type_list.add(BuildingType.getString("3"));
                                building_type_list.add(BuildingType.getString("4"));
                                building_type_list.add(BuildingType.getString("5"));


                                ContractVariable.CarrierOfDieases = response.getJSONObject("CarrierOfDieases");
                                career_of_deseas.add(CarrierOfDieases.getString("1"));
                                career_of_deseas.add(CarrierOfDieases.getString("2"));


                                ContractVariable.AzerbaijanPurpose = response.getJSONObject("AzerbaijanPurpose");
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("1"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("2"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("3"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("4"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("5"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("6"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("7"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("8"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("9"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("10"));
                                azerbaijan_purpose_list.add(AzerbaijanPurpose.getString("11"));


                                ContractVariable.TajikistanPurpose = response.getJSONObject("TajikistanPurpose");
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("1"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("2"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("3"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("4"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("5"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("6"));
                                tajikistan_purpose_list.add(TajikistanPurpose.getString("7"));


                                ContractVariable.TajikistanBusiness = response.getJSONObject("TajikistanBusiness");
                                tajikistan_bussiness_list.add(TajikistanBusiness.getString("1"));
                                tajikistan_bussiness_list.add(TajikistanBusiness.getString("2"));
                                tajikistan_bussiness_list.add(TajikistanBusiness.getString("3"));


                                ContractVariable.TajikistanTourism = response.getJSONObject("TajikistanTourism");
                                tajikistan_tuorism_list.add(TajikistanTourism.getString("1"));
                                tajikistan_tuorism_list.add(TajikistanTourism.getString("2"));


                                ContractVariable.TajikistanPrivate = response.getJSONObject("TajikistanPrivate");
                                tajikistan_private.add(TajikistanPrivate.getString("1"));
                                tajikistan_private.add(TajikistanPrivate.getString("2"));
                                tajikistan_private.add(TajikistanPrivate.getString("3"));


                                ContractVariable.TajikistanPassportType = response.getJSONObject("TajikistanPassportType");
                                tajikistan_passport_type.add(TajikistanPassportType.getString("1"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("2"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("3"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("4"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("5"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("6"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("7"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("8"));
                                tajikistan_passport_type.add(TajikistanPassportType.getString("9"));


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
        });


    }
}
