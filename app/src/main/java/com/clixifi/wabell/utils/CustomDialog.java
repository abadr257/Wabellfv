package com.clixifi.wabell.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.clixifi.wabell.R;

import dmax.dialog.SpotsDialog;



public class CustomDialog {
    private AlertDialog progressDialog;
    Context mCon ;

    public CustomDialog(Context mCon) {
        this.mCon = mCon;
        progressDialog = new SpotsDialog(mCon, R.style.Custom);
    }

    public void ShowDialog(){
        progressDialog.show();
    }
    public void DismissDialog(){
        progressDialog.cancel();
    }
}
