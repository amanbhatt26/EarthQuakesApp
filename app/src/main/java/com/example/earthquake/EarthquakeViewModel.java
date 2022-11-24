package com.example.earthquake;

import android.app.Application;
import android.location.Location;
import android.net.ParseException;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class EarthquakeViewModel extends AndroidViewModel {
    private static final String TAG = "EarthquakeViewModel";
    MutableLiveData<List<Earthquake>> earthquakes;
    public EarthquakeViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Earthquake>> getEarthquakes(){
        if(earthquakes == null){
            earthquakes = new MutableLiveData<List<Earthquake>>();
            loadData();
        }

        return earthquakes;
    }

    public void loadData(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            ArrayList<Earthquake> earthquakeList = new ArrayList<>();
            URL url;
            try{
                String quakeFeed = getApplication().getString(R.string.earthquake_feed);
                url = new URL(quakeFeed);
                URLConnection urlConnection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                int resCode = httpURLConnection.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    InputStream in = httpURLConnection.getInputStream();
                    DocumentBuilderFactory dbf =
                            DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
// Parse the earthquake feed.
                    Document dom = db.parse(in);
                    Element docEle = dom.getDocumentElement();
// Get a list of each earthquake entry.
                    NodeList nl = docEle.getElementsByTagName("entry");
                    if (nl != null && nl.getLength() > 0) {
                        for (int i = 0 ; i < nl.getLength(); i++) {
// Check to see if our loading has been cancelled, in which
// case return what we have so far.
                            Element entry =
                                    (Element)nl.item(i);
                            Element id =
                                    (Element)entry.getElementsByTagName("id").item(0);
                            Element title =
                                    (Element)entry.getElementsByTagName("title").item(0);
                            Element g =
                                    (Element)entry.getElementsByTagName("georss:point")
                                            .item(0);
                            Element when =
                                    (Element)entry.getElementsByTagName("updated").item(0);
                            Element link =
                                    (Element)entry.getElementsByTagName("link").item(0);
                            String idString = id.getFirstChild().getNodeValue();
                            String details = title.getFirstChild().getNodeValue();
                            String hostname = "http://earthquake.usgs.gov";
                            String linkString = hostname + link.getAttribute("href");
                            String point = g.getFirstChild().getNodeValue();
                            String dt = when.getFirstChild().getNodeValue();
                            SimpleDateFormat sdf =
                                    new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                            Date qdate = new GregorianCalendar(0,0,0).getTime();
                            try {
                                qdate = sdf.parse(dt);
                            } catch (ParseException e) {
                                Log.e(TAG, "Date parsing exception.", e);
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            String[] location = point.split(" ");
                            Location l = new Location("dummyGPS");
                            l.setLatitude(Double.parseDouble(location[0]));
                            l.setLongitude(Double.parseDouble(location[1]));
                            String magnitudeString = details.split(" ")[1];
                            int end = magnitudeString.length()-1;
                            double magnitude =
                                    Double.parseDouble(magnitudeString.substring(0, end));
                            if (details.contains("-"))
                                details = details.split("-")[1].trim();
                            else
                                details = "";
                            final Earthquake earthquake = new Earthquake(idString,
                                    qdate,
                                    details, l,
                                    magnitude,
                                    linkString);
// Add the new earthquake to our result array.
                            earthquakeList.add(earthquake);
                        }
                    }
                }
                httpURLConnection.disconnect();
                }
            catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException", e);
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            } catch (ParserConfigurationException e) {
                Log.e(TAG, "Parser Configuration Exception", e);
            } catch (SAXException e) {
                Log.e(TAG, "SAX Exception", e);
            }

            handler.post(() -> {
                //UI Thread work here
                earthquakes.setValue(earthquakeList);
            });
        });
    }
}
