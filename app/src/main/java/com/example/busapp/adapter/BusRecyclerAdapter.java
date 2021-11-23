package com.example.busapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.busapp.BusStopInfoActivity;
import com.example.busapp.R;
import com.example.busapp.db.MyBusStop;
import com.example.busapp.db.MyBusStopDB;
import com.example.busapp.logic.XmlParsingLogic;
import com.example.busapp.model.BusInfoItem;
import com.example.busapp.model.BusListItem;

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
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BusRecyclerAdapter extends RecyclerView.Adapter<BusRecyclerAdapter.ItemViewHolder> implements Serializable {
    private ArrayList<MyBusStop> itemList = new ArrayList<>();
    // 중복 클릭 방지용 시간
    private long time = 0;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busstop, parent, false);

        return new ItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textView_stopName.setText(itemList.get(position).getBusStopName());
        holder.textView_stopNumber.setText(itemList.get(position).getBusStopNumber());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(MyBusStop item) {
        itemList.add(item);
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView_stopName;
        TextView textView_stopNumber;
        LinearLayout linearLayout;

        Intent intent;
        AlertDialog.Builder builder;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_stopName = itemView.findViewById(R.id.busStop_name);
            textView_stopNumber = itemView.findViewById(R.id.busStop_number);
            linearLayout = itemView.findViewById(R.id.busStop_linear);
            linearLayout.setOnClickListener(v -> {
                // 중복 클릭 방지용 로직
                if (SystemClock.elapsedRealtime() - time < 3000 ) {
                    return;
                }
                time = SystemClock.elapsedRealtime();
                //---------------------------
                String busStopName = textView_stopName.getText().toString();
                String busStopNumber = textView_stopNumber.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            XmlParsingLogic xmlParsingLogic = new XmlParsingLogic();
                            ArrayList<BusInfoItem> busInfoItems = xmlParsingLogic.xmlParser(busStopNumber);
                            intent = new Intent(v.getContext(), BusStopInfoActivity.class);
                            intent.putExtra("busStopName", busStopName);
                            intent.putExtra("arsno", busStopNumber);
                            intent.putExtra("busInfoItems", busInfoItems);
                            v.getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            });
            linearLayout.setOnLongClickListener(v -> {
                MyBusStopDB myBusStopDB = Room.databaseBuilder
                        (v.getContext(), MyBusStopDB.class, "my-bus-stop-DB")
                        .allowMainThreadQueries()
                        .build();
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(textView_stopName.getText().toString())
                        .setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String busNum = textView_stopNumber.getText().toString();
                        int position = getBindingAdapterPosition();
                        myBusStopDB.myBusStopDao().deleteOne(busNum);
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(v.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            });
        }
    }
}
