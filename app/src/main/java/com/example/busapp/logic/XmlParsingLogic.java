package com.example.busapp.logic;

import android.util.Xml;

import com.example.busapp.model.BusInfoItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XmlParsingLogic {
    private String serviceKey = "huDdeTmtzO4PkEqHHpjAlBpK1tTK4WTukS5gazmHdWSiwuQh4N%2Bn5TCVNy%2BVuBPfeOoXmfLpX%2BCUmirgyaAqcA%3D%3D";
    private OkHttpClient client;

    public ArrayList<BusInfoItem> xmlParser(String arsno) throws IOException, XmlPullParserException {
        String url = "http://apis.data.go.kr/6260000/BusanBIMS/bitArrByArsno?serviceKey="
                + serviceKey + "&arsno=" + arsno;

        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        Response response = client.newCall(request).execute();
        String xml = response.body().string();

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
