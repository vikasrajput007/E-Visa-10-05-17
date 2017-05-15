package com.android.developer.e_visa;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DestinationCountryActivity extends AppCompatActivity {

    RelativeLayout button;
    ImageView apply_now;
    Spinner spinner, spinner2;
    TextView guidline_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_country);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getActionBar().setTitle("India");
//        getSupportActionBar().setTitle("India");
        //spinner = (Spinner) findViewById(R.id.simpleSpinner);
        //  spinner2 = (Spinner) findViewById(R.id.simpleSpinner2);
        button = (RelativeLayout) findViewById(R.id.apply_now);
        guidline_text = (TextView) findViewById(R.id.guidline_text);

       guidline_text.setText(R.string.guidline_detail);

        String detail = "<div class=\"detail\">\n" +
                "                <ul><li>International Travelers whose sole objective of visiting India is recreation, sight-seeing, casual visit to meet friends or relatives, short duration medical treatment or casual business visit.</li><li>Passport should have at least six months validity from the date of arrival in India. The passport should have at least two blank pages for stamping by the Immigration Officer.</li><li>You should have lived at your current address for atleast 2 years.</li><li>International Travelers should have return ticket or onward journey ticket, with sufficient money to spend during his/her stay in India.</li><li>International Travelers having Pakistani Passport or Pakistani origin HAVE TO apply for regular Visa at Indian Mission in your respective jurisdiction/Country.</li><li>Not available to Diplomatic/Official Passport Holders.</li><li>Not available to individuals endorsed on Parent&rsquo;s/Spouse&rsquo;s Passport i.e. each individual should have a separate passport.</li><li>Not available to International Travel Document holders.</li></ul>                 \n" +
                "                <ul><li>Scanned First Page of Passport, in PDF Format. Size of the PDF File should be in the range of Minimum 10KB to Max 300 KB</li><li>The digital photograph to be uploaded along with the Visa application should meet the following requirements:<ul><li>Format &ndash; JPEG</li><li>Size -Minimum 10 KB to Maximum 1 MB</li><li>The height and width of the Photo must be equal.</li><li>Photo should present Full face, front view, eyes open.</li><li>Center head within frame and present full head from top of hair to bottom of chin.</li><li>Background should be plain light colored or white background.</li><li>No shadows on the face or on the background.</li></ul></li><li>Proof of Address(Any ONE of the following)<ul><li>Utility bill in your name- Water or Electricity bill or driving license or State ID or Lease Agreement. Any one of the Parents ID in case of Minors</li></ul></li></ul>                            </div>";

      //  guidline_text.setText(Html.fromHtml(detail));
    }

    //   spinner.setOnItemSelectedListener(new DestinationCountryActivity.ItemSelectedListener());

}

//    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
//
//        //get strings of first item
//        String firstItem = String.valueOf(spinner2.getSelectedItem());
//
//        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            if (firstItem.equals(String.valueOf(spinner2.getSelectedItem()))) {
//                // ToDo when first item is selected
//            } else {
//                Toast.makeText(parent.getContext(),
//                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_LONG).show();
//                // Todo when item is selected by the user
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg) {
//
//        }
//
//    }



