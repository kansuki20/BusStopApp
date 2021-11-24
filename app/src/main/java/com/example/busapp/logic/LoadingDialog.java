package com.example.busapp.logic;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.example.busapp.R;

public class LoadingDialog extends Dialog {
    private LoadingDialog dialog;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
    }

    public void dialogOn() {
        dialog = new LoadingDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
    public void dialogOff() {
        dialog.dismiss();
    }
}
