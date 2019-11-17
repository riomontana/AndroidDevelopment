package com.lfo.p2.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lfo.p2.Fragment.AddGroupDialogFragment;
import com.lfo.p2.Controller.Controller;
import com.lfo.p2.Fragment.GroupListDialogFragment;
import com.lfo.p2.Fragment.LoginDialogFragment;
import com.lfo.p2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private Controller controller = new Controller(this);
    private Button btnGroupList;
    private Button btnRegisterGroup;
    private FragmentManager fm;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean addMarker = false;
    private boolean mapReady = false;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private Boolean firstMarker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fm = getSupportFragmentManager();
        btnGroupList = (Button) findViewById(R.id.btnShowGroups);
        btnRegisterGroup = (Button) findViewById(R.id.btnAddGroup);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OMRC());

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        btnGroupList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                controller.setGroupListDialogFragment();
            }
        });

        btnRegisterGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                controller.setAddGroupDialogFragment();
            }
        });

        locationListener = new LocList();
        showLoginDialog();
    }

    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        }
    }

    protected void onPause() {
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }


    public void showLoginDialog() {
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setController(controller);
        loginDialogFragment.show(fm, "fragment_login");
    }

    public void showGroupListDialog(GroupListDialogFragment groupListDialogFragment) {
        groupListDialogFragment.setController(controller);
        groupListDialogFragment.show(fm, "fragment_show_groups");
    }

    public void showAddGroupDialog(AddGroupDialogFragment addGroupDialogFragment) {
        addGroupDialogFragment.setController(controller);
        addGroupDialogFragment.show(fm, "fragment_add_group");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void showToast(String user) {
        Toast.makeText(this, getString(R.string.welcome) + " " +
                        user,
                Toast.LENGTH_LONG).show();
    }

    public void updateMembersLocations(JSONObject jsonMessage) {
        firstMarker = false;
        mMap.clear();
        try {
            Log.d("updateMembersLocations", jsonMessage.toString());
            JSONArray jsonArray = jsonMessage.getJSONArray("location");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                LatLng latLng = new LatLng(
                        Double.parseDouble(json.getString("latitude")),
                        Double.parseDouble(json.getString("longitude")));
                addMarker(latLng,json.getString("member"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class OMRC implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mapReady=true;
            if (addMarker == false && mapReady==true) {
                addMarker = true;
            }
        }
    }

    private void addMarker(LatLng latLng, String member) {
        addMarker = false;
        mMap.addMarker(new MarkerOptions().position(latLng).title(member));
        if(firstMarker) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.logout();
    }

    private class LocList implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            controller.setUserLocation(latitude,longitude);
            if(addMarker) {
                addMarker(new LatLng(latitude,longitude),controller.getUser());
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
    }
}
