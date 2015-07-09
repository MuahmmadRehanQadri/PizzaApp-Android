package com.example.muhammadrehanqadri.iwantpizza.util;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.muhammadrehanqadri.iwantpizza.R;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import java.util.List;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.view.MenuItem;
import android.widget.Spinner;
import android.util.Log;
import java.util.ArrayList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Criteria;
import android.widget.EditText;




public class mapaddress extends ActionBarActivity {
    ArrayList<LatLng> markerPoints;
    private String address;

    public void onMapReady(GoogleMap map, Double lat, Double lng) {

        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(
                "Current"));
    }

    private GoogleMap map;
    double latitude;
    double longitude;
    Spinner dropdown;
    double Deslatitude;
    double Deslongitude;
    MarkerOptions markerOptions;
    EditText dress;
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapaddress);

        final TextView textView = (TextView) findViewById(R.id.textView6);

        setButtonClickListener();



        markerPoints = new ArrayList<LatLng>();

        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager()
                        .findFragmentById(R.id.map)).getMap();
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                map.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // Getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // No network provider is enabled
                } else {
                    Criteria criteria = new Criteria();
                    String bestProvider= locationManager.getBestProvider(criteria,
                            true);
                    Location location = locationManager.getLastKnownLocation(bestProvider);
                    if (location != null) {
                        onLocationChanged(location);

                        // Getting latitude of the current location
                        latitude = location.getLatitude();

                        // Getting longitude of the current location
                        longitude = location.getLongitude();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude, longitude), 12));
                        // Setting latitude and longitude in the TextView
                        // tv_location
                        Toast.makeText(
                                getApplicationContext(),
                                "Latitude:" + latitude + ", Longitude:" + longitude,
                                Toast.LENGTH_LONG).show();
                    }
                    locationManager.requestLocationUpdates(bestProvider, 20000, 0,
                            (android.location.LocationListener) this);
                    onMapReady(map, latitude, longitude);


                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        map.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // TODO Auto-generated method stub

                if(markerPoints.size()>0){
                    markerPoints.clear();
                    map.clear();
                }
                markerPoints.add(point);
                Location location = new Location("Test");
                location.setLatitude(point.latitude);
                location.setLongitude(point.longitude);

                // txtinfo.setText(location.toString());


                Deslatitude=location.getLatitude();
                Deslongitude=location.getLongitude();
                //Convert Location to LatLng
                LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                markerOptions = new MarkerOptions()
                        .position(newLatLng)
                        .title(newLatLng.toString());
                map.addMarker(markerOptions);
                address=getCompleteAddressString(Deslatitude,Deslongitude);
                textView.setText(address);
            }
        });
    }

    private void setButtonClickListener() {
        Button buttonSelectLocation = (Button) findViewById(R.id.buttonSelectLocation);
        buttonSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("location", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    private void onLocationChanged(Location location) {
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapaddress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
