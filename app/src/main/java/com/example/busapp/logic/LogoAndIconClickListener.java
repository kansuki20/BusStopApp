package com.example.busapp.logic;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class LogoAndIconClickListener implements View.OnClickListener {
    String link;

    public LogoAndIconClickListener(String link) {
        this.link = link;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        v.getContext().startActivity(intent);
    }
}
