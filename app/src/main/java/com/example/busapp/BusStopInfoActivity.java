package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Xml;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busapp.adapter.BusRecyclerAdapter;
import com.example.busapp.adapter.BusStopInfoRecyclerAdapter;
import com.example.busapp.db.MyBusStop;
import com.example.busapp.db.MyBusStopDB;
import com.example.busapp.logic.XmlParsingLogic;
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
    private TextView tvBusStopInfo_name;
    private TextView tvBusStopInfo_number;
    private Button button;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private BusStopInfoRecyclerAdapter adapter;

    private Intent intent;

    private String busStopName;
    private String arsno;
    private ArrayList<BusInfoItem> busInfoItems;

    private MyBusStopDB myBusStopDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_info);

        myBusStopDB = Room.databaseBuilder
                (getApplicationContext(), MyBusStopDB.class, "my-bus-stop-DB")
                .allowMainThreadQueries()
                .build();

        intent = getIntent();
        busStopName = intent.getStringExtra("busStopName");
        arsno = intent.getStringExtra("arsno");
        busInfoItems = (ArrayList<BusInfoItem>)intent.getSerializableExtra("busInfoItems");

        tvBusStopInfo_name = findViewById(R.id.tvBusStopInfo_name);
        tvBusStopInfo_name.setText(busStopName);
        tvBusStopInfo_number = findViewById(R.id.tvBusStopInfo_number);
        tvBusStopInfo_number.setText(arsno);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            MyBusStop myBusStop = new MyBusStop(arsno, busStopName);
            if (myBusStopDB.myBusStopDao().getOne(arsno) != null)
                Toast.makeText(this, "이미 등록된 정류장입니다.", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                myBusStopDB.myBusStopDao().insert(myBusStop);
            }
        });
        recyclerView = findViewById(R.id.recyclerview_busStopInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BusStopInfoRecyclerAdapter();
        adapter.addItems(busInfoItems);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                XmlParsingLogic xmlParsingLogic = new XmlParsingLogic();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<BusInfoItem> refreshBusInfoItems = xmlParsingLogic.xmlParser(arsno);
                            adapter.addItems(refreshBusInfoItems);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }
    // 메인화면 새로고침
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}