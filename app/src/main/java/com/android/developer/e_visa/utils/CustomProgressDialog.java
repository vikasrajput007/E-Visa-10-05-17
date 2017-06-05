package com.android.developer.e_visa.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zx on 5/29/2017.
 */

public class CustomProgressDialog {

    Context context;
    ProgressDialog pDialog;

    public CustomProgressDialog(Context context) {
        this.context = context;
        pDialog = new ProgressDialog(context);
    }


    public void showProgressDialog() {
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog.isShowing()&& pDialog!=null)
            pDialog.hide();
    }


}
