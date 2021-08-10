package br.edu.ifpe.tads.pdm.appalertaaedes.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geolocation {

    public static void getAddress(String locationAddress, Context context, Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String latitude = null;
                String longitude = null;

                try {
                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        StringBuilder lat = new StringBuilder();
                        StringBuilder lon = new StringBuilder();
                        lat.append(address.getLatitude());
                        lon.append(address.getLongitude());

                        latitude = lat.toString();
                        longitude = lon.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);

                    if (latitude != null && longitude != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();

                        bundle.putString("latitude", latitude);
                        bundle.putString("longitude", longitude);
                        message.setData(bundle);
                    }

                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
