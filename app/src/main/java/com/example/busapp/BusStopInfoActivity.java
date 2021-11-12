package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Xml;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.busapp.adapter.BusStopInfoRecyclerAdapter;
import com.example.busapp.model.BusInfoItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BusStopInfoActivity extends AppCompatActivity implements Serializable {
    TextView tvBusStopInfo_name;
    TextView tvBusStopInfo_number;
    RecyclerView recyclerView;

    BusStopInfoRecyclerAdapter adapter;

    OkHttpClient client = new OkHttpClient();

    Intent intent;
    String busStopName;
    String arsno;
    String serviceKey = "huDdeTmtzO4PkEqHHpjAlBpK1tTK4WTukS5gazmHdWSiwuQh4N%2Bn5TCVNy%2BVuBPfeOoXmfLpX%2BCUmirgyaAqcA%3D%3D";
    String url;
    ArrayList<BusInfoItem> busInfoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_info);

        intent = getIntent();
        busStopName = intent.getStringExtra("busStopName");
        arsno = intent.getStringExtra("arsno");
        busInfoItems = (ArrayList<BusInfoItem>)intent.getSerializableExtra("busInfoItems");

        tvBusStopInfo_name = findViewById(R.id.tvBusStopInfo_name);
        tvBusStopInfo_name.setText(busStopName);
        tvBusStopInfo_number = findViewById(R.id.tvBusStopInfo_number);
        tvBusStopInfo_number.setText(arsno);
        recyclerView = findViewById(R.id.recyclerview_busStopInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BusStopInfoRecyclerAdapter();
        adapter.addItems(busInfoItems);
        recyclerView.setAdapter(adapter);
    }
}