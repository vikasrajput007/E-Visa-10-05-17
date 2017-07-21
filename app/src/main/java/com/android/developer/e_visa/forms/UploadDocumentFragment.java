package com.android.developer.e_visa.forms;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.developer.e_visa.MainActivity;
import com.android.developer.e_visa.PaymentDetailFragment;
import com.android.developer.e_visa.R;
import com.android.developer.e_visa.appController.ApplicationController;
import com.android.developer.e_visa.network.ApiClient;
import com.android.developer.e_visa.network.ApiInterface;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.android.developer.e_visa.MainActivity.CAMERA_REQUEST_BILL;
import static com.android.developer.e_visa.MainActivity.GALLERY_PICTURE_BILL;
import static com.android.developer.e_visa.MainActivity.homeFragmentStack;
import static com.android.developer.e_visa.MainActivity.photo_image_bitmap;
import static com.android.developer.e_visa.network.ApiClient.getClient;

public class UploadDocumentFragment extends Fragment implements View.OnClickListener {

    private View view;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Context context;
    private String tag_string_req = "tag_string_req";
    public static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 111;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int MY_SOCKET_TIMEOUT_MS = 90000;
    public static Uri photoURI;
    public static String ImageURI;
    public Bitmap photo_image_bitmap, bitmap, bitmap1, bitmap2;
    String first = "", post_data_url = "", ID = "", destination;
    StringRequest stringRequest;
    Intent dataIntent;
    ImageView passport_image_preview, recent_image_preview, address_proof_preview;
    String mCurrentPhotoPath;
    private Intent pictureActionIntent;
    Button passport_front_page_button, recent_passport_photo_button, address_proof_button;
    LinearLayout proceed_for_payment;
    ProgressDialog progressDialog;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    String city_of_arrival, date_of_arrival, email, religion, education, accquired_nationality, previous_nationality, address_proof,
            fathers_name, fathers_nationality, fathers_place_of_birth, fathers_country_of_birth, mothers_name, mothers_nationality,
            mothers_place_of_birth, mothers_country_of_birth, marriage_status, spouse_name, spouse_nationality, spouse_place_of_birth,
            spouse_country_of_birth, occupation, employer_name, employer_address, in_military_police_security, organization, rank,
            place_of_posting, ever_visited_india, address_where_stayed, visa_no, visa_type, place_of_issue, date_of_issue, is_visited_coutries,
            year_of_visited, no_of_times, morevisitedcountries, day_time_contact_cc, day_time_contact, passport_front, photo, proof;


    public TextView passport_front_page_name, recent_passport_photo_name, address_proof_image_name;
    private String front_page_string, recent_photo_string, address_proof_string;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload_document, container, false);


        initView();
        return view;
    }

    private void initView() {

        context = getActivity();
        progressDialog = new ProgressDialog(getActivity());

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // personal form details
        ID = sharedPref.getString("langid", "");
        destination = sharedPref.getString("destination_country_name_id", "");

        System.out.println("value of destination is ..."+destination);
//        System.out.println("value of langid is ..."+ID);
        email = sharedPref.getString("email_id", "");
        city_of_arrival = sharedPref.getString("arrival_city", "");
        date_of_arrival = sharedPref.getString("arrival_date", "");
        religion = sharedPref.getString("religion", "");
        education = sharedPref.getString("qualificaton", "");
        accquired_nationality = sharedPref.getString("aquiredNationality", "");
        previous_nationality = sharedPref.getString("by_nature", "");
        address_proof = sharedPref.getString("address_proof", "");

        // family form details
        fathers_nationality = sharedPref.getString("father_nationality", "");
        fathers_country_of_birth = sharedPref.getString("father_brth_contry", "");
        mothers_nationality = sharedPref.getString("mothr_nationlity", "");
        mothers_country_of_birth = sharedPref.getString("mthr_brth_contry", "");
        marriage_status = sharedPref.getString("marriage_status", "");
        spouse_nationality = sharedPref.getString("his_her_nationlty", "");
        spouse_country_of_birth = sharedPref.getString("his_her_brth_contry", "");
        fathers_name = sharedPref.getString("father_name", "");
        fathers_place_of_birth = sharedPref.getString("father_birth_place", "");
        mothers_name = sharedPref.getString("mother_name", "");
        mothers_place_of_birth = sharedPref.getString("mother_birth_place", "");
        spouse_name = sharedPref.getString("your_spouse_name", "");
        spouse_place_of_birth = sharedPref.getString("his_her_birth_place", "");

        // professional form details

        occupation = sharedPref.getString("occupation", "");
        employer_name = sharedPref.getString("employer_name", "");
        employer_address = sharedPref.getString("employer_address", "");
        in_military_police_security = sharedPref.getString("mill_pol_security", "");
        organization = sharedPref.getString("organistion", "");
        rank = sharedPref.getString("rank", "");
        place_of_posting = sharedPref.getString("plcae_of_posting", "");
        ever_visited_india = sharedPref.getString("visited_india", "");
        address_where_stayed = sharedPref.getString("stayed_address", "");
//        System.out.println("professional form credential is :"+sharedPref.getString("past_visit",""));
        visa_no = sharedPref.getString("visa_no", "");
        visa_type = sharedPref.getString("type_visa", "");
        place_of_issue = sharedPref.getString("place_of_issue", "");
        date_of_issue = sharedPref.getString("date_of_issue", "");
        is_visited_coutries = sharedPref.getString("past_visited", "");
        year_of_visited = sharedPref.getString("years_visited", "");
        no_of_times = sharedPref.getString("how_many_time", "");
        morevisitedcountries = "";
        day_time_contact_cc = "";
        day_time_contact = sharedPref.getString("need_to_call", "");


        passport_image_preview = (ImageView) view.findViewById(R.id.passport_image_preview);
        recent_image_preview = (ImageView) view.findViewById(R.id.recent_image_preview);
        address_proof_preview = (ImageView) view.findViewById(R.id.address_proof_preview);


        passport_front_page_button = (Button) view.findViewById(R.id.passport_front_page);
        recent_passport_photo_button = (Button) view.findViewById(R.id.recent_passport_photo);
        address_proof_button = (Button) view.findViewById(R.id.address_proof);


        proceed_for_payment = (LinearLayout) view.findViewById(R.id.proceed_for_payment);

        passport_front_page_button.setOnClickListener(this);
        recent_passport_photo_button.setOnClickListener(this);
        address_proof_button.setOnClickListener(this);
        proceed_for_payment.setOnClickListener(this);



        System.out.println("professional form occupation is :" + sharedPref.getString("occupation", ""));
        System.out.println("professional form employer_name is :" + sharedPref.getString("employer_name", ""));
        System.out.println("professional form employer_address is :" + sharedPref.getString("employer_address", ""));
        System.out.println("professional form mill_pol_security is :" + sharedPref.getString("mill_pol_security", ""));
        System.out.println("professional form organistion is :" + sharedPref.getString("organistion", ""));
        System.out.println("professional form rank is :" + sharedPref.getString("rank", ""));
        System.out.println("professional form plcae_of_posting is :" + sharedPref.getString("plcae_of_posting", ""));
        System.out.println("professional form visited_india is :" + sharedPref.getString("visited_india", ""));
        System.out.println("professional form stayed_address is :" + sharedPref.getString("stayed_address", ""));
//        System.out.println("professional form credential is :"+sharedPref.getString("past_visit",""));
        System.out.println("professional form type_visa is :" + sharedPref.getString("type_visa", ""));
        System.out.println("professional form visa_no is :" + sharedPref.getString("visa_no", ""));
        System.out.println("professional form place_of_issue is :" + sharedPref.getString("place_of_issue", ""));
        System.out.println("professional form date_of_issue is :" + sharedPref.getString("date_of_issue", ""));
        System.out.println("professional form past_visited is :" + sharedPref.getString("past_visited", ""));
        System.out.println("professional form years_visited is :" + sharedPref.getString("years_visited", ""));
        System.out.println("professional form how_many_time is :" + sharedPref.getString("how_many_time", ""));
        System.out.println("professional form need_to_call is :" + sharedPref.getString("need_to_call", ""));


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.passport_front_page:

                checkPermission(context);

                first = "1";
                startDialog();

                break;

            case R.id.recent_passport_photo:

                checkPermission(context);
                first = "2";
                startDialog();

                break;

            case R.id.address_proof:

                checkPermission(context);
                first = "3";
                startDialog();

                break;

            case R.id.proceed_for_payment:

                postCustomerDetail();

                break;

        }

    }

    /**
     * To convert the image into base 64 string format, it returns an encoded string
     *
     * @param bmp
     * @return
     */
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void startDialog() {
        android.app.AlertDialog.Builder myAlertDialog = new android.app.AlertDialog.Builder(getActivity());
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to upload your image ?");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                pictureActionIntent.setType("image/*");
                pictureActionIntent.putExtra("data", true);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE_BILL);

            }

        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_BILL);
                    }
                }

            }
        });
        myAlertDialog.show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
        }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", mCurrentPhotoPath);
        return image;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dataIntent = data;
        if (requestCode == CAMERA_REQUEST_BILL && resultCode == RESULT_OK) {

            switch (first) {
                case "1":
                    try {
                        bitmap = setPic();
                         passport_front = getStringImage(bitmap);
                        System.out.println("value of passport string : " + passport_front);
                        passport_image_preview.setImageBitmap(bitmap);
                    } catch (Exception e) {
                    }
                    break;
                case "2":
                    try {
                        bitmap1 = setPic();
                         photo = getStringImage(bitmap1);
                        recent_image_preview.setImageBitmap(bitmap1);
                    } catch (Exception e) {
                    }
                    break;
                case "3":
                    try {
                        bitmap2 = setPic();
                         proof = getStringImage(bitmap2);
                        address_proof_preview.setImageBitmap(bitmap2);
                    } catch (Exception e) {
                    }
                    break;
            }

            first = "";
        } else if (requestCode == GALLERY_PICTURE_BILL) {
            if (resultCode == RESULT_OK) {
                switch (first) {
                    case "1":
                        bitmap = galleryImageSet();
                         passport_front = getStringImage(bitmap);
                        System.out.println("value of passport string : " + passport_front);
                        passport_image_preview.setImageBitmap(bitmap);

                        break;
                    case "2":
                        bitmap1 = galleryImageSet();
                         photo = getStringImage(bitmap1);
                        recent_image_preview.setImageBitmap(bitmap1);

                        break;
                    case "3":
                        bitmap2 = galleryImageSet();
                          proof = getStringImage(bitmap2);
                        address_proof_preview.setImageBitmap(bitmap2);
                        break;
                }

                first = "";
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private Bitmap galleryImageSet() {
        if (dataIntent != null) {

            Cursor cursor;
            String[] projection = {MediaStore.Images.Media.DATA};
            if (Build.VERSION.SDK_INT > 19) {

                try {
                    String wholeID = DocumentsContract.getDocumentId(dataIntent.getData());
                    String id = wholeID.split(":")[1];
                    String sel = MediaStore.Images.Media._ID + "=?";
                    cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, sel, new String[]{id}, null);
                } catch (Exception e) {

                    cursor = context.getContentResolver().query(dataIntent.getData(), null, null, null, null);
                }

            } else {
                cursor = context.getContentResolver().query(dataIntent.getData(), null, null, null, null);
            }
            if (cursor != null) {

                cursor.moveToFirst();

                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                ImageURI = cursor.getString(idx);
                photo_image_bitmap = BitmapFactory.decodeFile(ImageURI);
                photo_image_bitmap = Bitmap.createScaledBitmap(photo_image_bitmap, 200, 200, false);

                ImageURI = ImageURI.substring(ImageURI.lastIndexOf("/"));
                ImageURI = ImageURI.replace("/", "");


            }

        } else {
            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
        return photo_image_bitmap;

    }


    private Bitmap setPic() {

        // Get the dimensions of the View
        int targetW = 300;
        int targetH = 250;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


      //  bmOptions.inSampleSize = 8;
        // The new size we want to scale to
        final int REQUIRED_SIZE=60;

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while(bmOptions.outWidth / scale / 2 >= REQUIRED_SIZE &&
                bmOptions.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }
        // Determine how much to scale down the image
      //  int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View

        bmOptions.inJustDecodeBounds = true;
        bmOptions.inSampleSize = scale;

        System.out.println("what is value of image during bitmap reducing");
        // Decode with inSampleSize
        BitmapFactory.Options bmOption2 = new BitmapFactory.Options();
        bmOption2.inSampleSize = scale;

        photo_image_bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOption2);

        return photo_image_bitmap;
    }


    // posting parameter method to server


    private void postCustomerDetail() {

        progressDialog.setMessage("Please Wait . . . ");
        progressDialog.show();
        ApiInterface apiInterface = getClient().create(ApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();


        System.out.println("value of destination is ..."+destination);
        System.out.println("value of langid is ..."+ID);

        System.out.println("value of destination is ..."+sharedPref.getString("destination_country_name_id", ""));
        System.out.println("value of langid is ..."+sharedPref.getString("langid", ""));


        params.put("langid", ID);
        params.put("destination", destination);
        params.put("city_of_arrival", city_of_arrival);
        params.put("date_of_arrival", date_of_arrival);
        params.put("email", email);
        params.put("religion", religion);
        params.put("education", education);
        params.put("accquired_nationality", accquired_nationality);
        params.put("previous_nationality", previous_nationality);
        params.put("address_proof", address_proof);
        params.put("fathers_name", fathers_name);
        params.put("fathers_nationality", fathers_nationality);
        params.put("fathers_place_of_birth", fathers_place_of_birth);
        params.put("fathers_country_of_birth", fathers_country_of_birth);
        params.put("mothers_name", mothers_name);
        params.put("mothers_nationality", mothers_nationality);
        params.put("mothers_place_of_birth", mothers_place_of_birth);
        params.put("mothers_country_of_birth", mothers_country_of_birth);
        params.put("marriage_status", marriage_status);
        params.put("spouse_name", spouse_name);
        params.put("spouse_nationality", spouse_nationality);
        params.put("spouse_place_of_birth", spouse_place_of_birth);
        params.put("spouse_country_of_birth", spouse_country_of_birth);
        params.put("occupation", occupation);
        params.put("employer_name", employer_name);
        params.put("employer_address", employer_address);
        params.put("in_military_police_security", in_military_police_security);
        params.put("organization", organization);
        params.put("rank", rank);
        params.put("place_of_posting", place_of_posting);
        params.put("ever_visited_india", ever_visited_india);
        params.put("address_where_stayed", address_where_stayed);
        params.put("visa_no", visa_no);
        params.put("visa_type", visa_type);
        params.put("place_of_issue", place_of_issue);
        params.put("date_of_issue", date_of_issue);
        params.put("is_visited_coutries", is_visited_coutries);
        params.put("year_of_visited", year_of_visited);
        params.put("no_of_times", no_of_times);
        params.put("morevisitedcountries", "");
        params.put("day_time_contact_cc", "");
        params.put("day_time_contact", day_time_contact);
        params.put("passport_front", passport_front);
        params.put("photo", photo);
        params.put("proof", proof);

        /**
         * this enqueue is used for asynchronus call to network
         */
        Call<ResponseBody> call = apiInterface.sendUserData(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (!response.body().toString().equals("")) {

                    if (progressDialog.isShowing() && progressDialog != null) {

                        progressDialog.dismiss();
                    }

                }

                String response_body = null;
                try {
                    response_body = response.body().string();

                    System.out.println("exact response is:" + response_body);


                System.out.println("string response of post request :" + response_body);


                System.out.println("response of post request :" + response.isSuccessful());

                Toast.makeText(getActivity(), "Success Post Data", Toast.LENGTH_SHORT).show();


                PaymentDetailFragment paymentDetailFragment = new PaymentDetailFragment();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                homeFragmentStack.add(paymentDetailFragment);
                mFragmentTransaction.replace(R.id.place_holder_layout, paymentDetailFragment);
                mFragmentTransaction.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (progressDialog.isShowing() && progressDialog != null) {

                    progressDialog.dismiss();
                }
                Toast.makeText(getActivity(), "Failure Post Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
