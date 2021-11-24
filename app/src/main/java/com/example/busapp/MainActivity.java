package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.example.busapp.adapter.BusRecyclerAdapter;
import com.example.busapp.db.BusStop;
import com.example.busapp.db.BusStopDB;
import com.example.busapp.db.MyBusStop;
import com.example.busapp.db.MyBusStopDB;
import com.example.busapp.logic.LoadingDialog;
import com.example.busapp.logic.XmlParsingLogic;
import com.example.busapp.model.BusInfoItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    private RecyclerView recyclerView;
    private BusRecyclerAdapter adapter;
    //DB
    private BusStopDB busStopDB = null;
    private MyBusStopDB myBusStopDB = null;
    //busanBus.txt 파싱용
    private String line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBusStopDB = Room.databaseBuilder
                (getApplicationContext(), MyBusStopDB.class, "my-bus-stop-DB")
                .allowMainThreadQueries()
                .build();
        busStopDB = Room.databaseBuilder
                (getApplicationContext(), BusStopDB.class, "bus-stop-DB")
                .allowMainThreadQueries()
                .build();
        // DB에 버스정보가 없을 때 txt파일에서 버스 정보 읽어와서 저장 (앱 실행때만 실행)
        if (busStopDB.busStopDao().getAll().toString() == "[]") {
            try {
                BufferedReader bufferedReader = new BufferedReader
                        (new InputStreamReader(getResources().getAssets().open("busanBus.txt")));
                while ((line = bufferedReader.readLine()) != null) {
                    String busStopNumber = line.split("\t")[0];
                    String busStopName = line.split("\t")[1];
                    BusStop busStop = new BusStop(busStopNumber, busStopName);
                    busStopDB.busStopDao().insert(busStop);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        autoCompleteTextView = findViewById(R.id.search);
        List<BusStop> busStops = busStopDB.busStopDao().getAll();
        ArrayList<String> busInfo = new ArrayList<>();
        for (int i = 0; i < busStops.size(); i++) {
            busInfo.add(busStops.get(i).getBusStopNumber() + "\t\t" + busStops.get(i).getBusStopName());
        }
        ArrayAdapter baseAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, busInfo);

        autoCompleteTextView.setAdapter(baseAdapter);
        autoCompleteTextView.setOnItemClickListener(clickListener);
        autoCompleteTextView.setOnClickListener(v -> {
            autoCompleteTextView.setText("");
        });

        recyclerView = findViewById(R.id.recyclerview_busStopList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<MyBusStop> myBusStopList = myBusStopDB.myBusStopDao().getAll();
        ArrayList<MyBusStop> myBusStops = new ArrayList<>();

        for (int i=0; i<myBusStopList.size(); i++)
            myBusStops.add(myBusStopList.get(i));

        adapter = new BusRecyclerAdapter();
        adapter.addItems(myBusStops);
        recyclerView.setAdapter(adapter);
    }

    private OnItemClickListener clickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LoadingDialog loadingDialog = new LoadingDialog(view.getContext());
            loadingDialog.dialogOn();

            XmlParsingLogic xmlParsingLogic = new XmlParsingLogic();

            String busStopInfo = ((TextView)view).getText().toString();
            String arsno = busStopInfo.split("\t")[0];
            Intent intent = new Intent(view.getContext(), BusStopInfoActivity.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<BusInfoItem> busInfoItems = xmlParsingLogic.xmlParser(arsno);
                        intent.putExtra("busStopName", busStopInfo.split("\t")[2]);
                        intent.putExtra("arsno", busStopInfo.split("\t")[0]);
                        intent.putExtra("busInfoItems", busInfoItems);
                        loadingDialog.dialogOff();
                        finish();
                        view.getContext().startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };



}