package com.example.hackathon;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telecom.StatusHints;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.JsObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MapsZone extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;
    double latitude, longitude;

    public MapsZone() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps_zone, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://fullstackers.000webhostapp.com/json/data/data.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        System.out.println(i + " = latitude" + jsonArray.getJSONObject(i).getString("latitude"));
                        System.out.println(i + " = longitude" + jsonArray.getJSONObject(i).getString("longitude"));
                        LatLng sydney = new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).getString("latitude")), Double.parseDouble(jsonArray.getJSONObject(i).getString("longitude")));
                        mMap.addMarker(new MarkerOptions().position(sydney)
                                .title("Labour "+(i+1)));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);

                        if (mMap != null) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title("my location"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        LatLng sydney = new LatLng(21.777, 71);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://fullstackers.000webhostapp.com/json/process.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("error")) {
                    new AlertDialog.Builder(getContext())
                            .setMessage(response)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(getContext())
                            .setMessage(response)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("Error" + error);
                new AlertDialog.Builder(getContext())
                        .setMessage("Error6" + error.getMessage())
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (latitude != 0 && longitude != 0) {
                    params.put("longitude", longitude + "");
                    System.out.println(latitude + "aaaaaa");
                    params.put("latitude", latitude + "");
                    System.out.println(longitude + "bbbbbb");
                    return params;
                }
                params.put("longitude", "");
                System.out.println(latitude + "aaaaaa");
                params.put("latitude", "");
                System.out.println(longitude + "bbbbbb");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}