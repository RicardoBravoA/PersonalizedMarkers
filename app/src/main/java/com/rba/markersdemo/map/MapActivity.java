package com.rba.markersdemo.map;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.appolica.interactiveinfowindow.InfoWindow;
import com.rba.markersdemo.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, MapView, InfoWindowManager.WindowShowListener{

    private InfoWindow formWindow;
    private InfoWindowManager infoWindowManager;
    private Marker marker;
    private MyInfoWindow myInfoWindow;
    @BindView(R.id.linNavigation) LinearLayout linNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        MapInfoWindowFragment mapInfoWindowFragment =
                (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.infoWindowMap);

        infoWindowManager = mapInfoWindowFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);

        infoWindowManager.setWindowShowListener(this);
        mapInfoWindowFragment.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        this.marker = marker;
        InfoWindow infoWindow = formWindow;

        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true);
        }

        myInfoWindow.setTitle(marker.getTitle());

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(-12.123955, -76.999392);


        final Marker marker = googleMap.addMarker(
                new MarkerOptions().position(latLng).title("I'm here"));

        final int offsetX = (int) getResources().getDimension(R.dimen.marker_offset_x);
        final int offsetY = (int) getResources().getDimension(R.dimen.marker_offset_y);

        final InfoWindow.MarkerSpecification markerSpec =
                new InfoWindow.MarkerSpecification(offsetX, offsetY);

        myInfoWindow = new MyInfoWindow();
        myInfoWindow.setView(this);
        formWindow = new InfoWindow(marker, markerSpec, myInfoWindow);

        googleMap.setOnMarkerClickListener(MapActivity.this);

        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.animateCamera(yourLocation);

        googleMap.getUiSettings().setZoomControlsEnabled(true);


    }

    @Override
    public void onMarkerClick() {
        linNavigation.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.imgGoogle)
    public void onClickGoogle(){
        try {
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", -12.099087, -77.002227, "Naruto Japanese Food");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch(ActivityNotFoundException ex){
            Intent intent =
                    new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps") );
            startActivity(intent);
        }
    }

    @OnClick(R.id.imgWaze)
    public void onClickWaze(){
        try {
            String url = "waze://?q=Naruto Japanese Food";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch(ActivityNotFoundException ex){
            Intent intent =
                    new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze") );
            startActivity(intent);
        }
    }

    @Override
    public void onWindowShowStarted(@NonNull InfoWindow infoWindow) {
    }

    @Override
    public void onWindowShown(@NonNull InfoWindow infoWindow) {
    }

    @Override
    public void onWindowHideStarted(@NonNull InfoWindow infoWindow) {
    }

    @Override
    public void onWindowHidden(@NonNull InfoWindow infoWindow) {
        linNavigation.setVisibility(View.GONE);
    }
}
