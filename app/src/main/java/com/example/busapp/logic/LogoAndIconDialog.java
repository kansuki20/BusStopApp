package com.example.busapp.logic;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.busapp.MainActivity;
import com.example.busapp.R;

public class LogoAndIconDialog {
    private Dialog dialog;

    private TextView busLogoProducer;
    private TextView busLogoDownload;
    private TextView loadingIconProducer;
    private TextView loadingIconDownload;

    public void showDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_produce);
        busLogoProducer = (TextView) dialog.findViewById(R.id.bus_logo_producer_tv);
        busLogoProducer.setOnClickListener(new LogoAndIconClickListener("https://www.urbanbrush.net/designer-tommy/"));
        busLogoDownload = (TextView) dialog.findViewById(R.id.bus_logo_download_tv);
        busLogoDownload.setOnClickListener(new LogoAndIconClickListener("https://www.logoyogo.com/downloads/%eb%b2%84%ec%8a%a4-%ec%95%84%ec%9d%b4%ec%bd%98-%eb%a1%9c%ea%b3%a0-%ec%9d%bc%eb%9f%ac%ec%8a%a4%ed%8a%b8-ai-%eb%ac%b4%eb%a3%8c-%eb%8b%a4%ec%9a%b4%eb%a1%9c%eb%93%9c/"));
        loadingIconProducer = (TextView) dialog.findViewById(R.id.loading_icon_producer_tv);
        loadingIconProducer.setOnClickListener(new LogoAndIconClickListener("https://kr.pikbest.com/designers/108844.html?c1=5"));
        loadingIconDownload = (TextView) dialog.findViewById(R.id.loading_icon_download_tv);
        loadingIconDownload.setOnClickListener(new LogoAndIconClickListener("https://kr.pikbest.com/png-images/qiantu-blue-technology-border-rotation-loading-gif-animation_2514433.html"));
        dialog.show();
    }
}
