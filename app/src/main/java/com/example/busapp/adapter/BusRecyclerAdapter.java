package com.example.busapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busapp.BusStopInfoActivity;
import com.example.busapp.R;
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
    private ArrayList<BusListItem> itemList = new ArrayList<>();

    OkHttpClient client = new OkHttpClient();
    Intent intent;
    String serviceKey = "huDdeTmtzO4PkEqHHpjAlBpK1tTK4WTukS5gazmHdWSiwuQh4N%2Bn5TCVNy%2BVuBPfeOoXmfLpX%2BCUmirgyaAqcA%3D%3D";
    String url;

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
        holder.linearLayout.setOnClickListener(v -> {
            String busStopName = holder.textView_stopName.getText().toString();
            String busStopNumber = holder.textView_stopNumber.getText().toString();
            url = "http://apis.data.go.kr/6260000/BusanBIMS/bitArrByArsno?serviceKey="
                    + serviceKey + "&arsno=" + busStopNumber;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder()
                                .url(url).build();
                        Response response = client.newCall(request).execute();
                        String xml = response.body().string();
                        ArrayList<BusInfoItem> busInfoItems;
                        busInfoItems = xmlParser(xml);
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
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(BusListItem item) {
        itemList.add(item);
    }
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView_stopName;
        TextView textView_stopNumber;
        LinearLayout linearLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_stopName = itemView.findViewById(R.id.busStop_name);
            textView_stopNumber = itemView.findViewById(R.id.busStop_number);
            linearLayout = itemView.findViewById(R.id.busStop_linear);
        }
    }

    private ArrayList<BusInfoItem> xmlParser(String xml) throws XmlPullParserException, IOException {
        ArrayList<BusInfoItem> busInfoItems = new ArrayList<>();
        BusInfoItem busInfoItem = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(xml));
        String text = "";
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equals("item")) {
                        busInfoItem = new BusInfoItem();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    switch (tagName) {
                        case "item":
                            busInfoItems.add(busInfoItem);
                            break;
                        case "lineno":
                            busInfoItem.setBusNo(text);
                            break;
                        case "min1":
                            busInfoItem.setFirstMin(text);
                            break;
                        case "min2":
                            busInfoItem.setSecondMin(text);
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }

        return busInfoItems;
    }


}
