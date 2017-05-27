package com.android.developer.e_visa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.developer.e_visa.models.Languages;
import com.android.developer.e_visa.models.ListDetail;
import com.android.developer.e_visa.retrofit.ApiClient;
import com.android.developer.e_visa.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDestination extends AppCompatActivity implements View.OnClickListener {

    ImageView air;
    Bitmap bitmap;
    Spinner spinner, spinner2;
    private List<ListDetail> listDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.simpleSpinner);
        spinner2 = (Spinner) findViewById(R.id.simpleSpinner2);


        if (Build.VERSION.SDK_INT >= 21) {
//            spinner.setBackgroundTintList(Resources.c);
        }
        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        air = (ImageView) findViewById(R.id.submit);
        air.setOnClickListener(this);

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.air);
        air.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap, 25));

        getLanguages();


    }


    private void getLanguages(){

//While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        apiService.response(new Callback<List<ListDetail>>() {


            @Override
            public void onResponse(Call<List<ListDetail>> call, Response<List<ListDetail>> response) {
                loading.dismiss();



                System.out.println("size of language array :"+ response.body().toString());

            }

            @Override
            public void onFailure(Call<List<ListDetail>> call, Throwable t) {

            }
        });

    }



    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent(SelectDestination.this, DestinationCountryActivity.class);
        startActivity(i);
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



