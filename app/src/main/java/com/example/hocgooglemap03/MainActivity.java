package com.example.hocgooglemap03;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Polyline polyline = null;
    List<LatLng> lngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    Spinner spKieuBanDo;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents2();
    }

    private void addEvents2() {
        spKieuBanDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int type = GoogleMap.MAP_TYPE_HYBRID;
                switch (i) {
                    case 0:
                        type = GoogleMap.MAP_TYPE_NONE;
                        break;
                    case 1:
                        type = GoogleMap.MAP_TYPE_NORMAL;
                        break;
                    case 2:
                        type = GoogleMap.MAP_TYPE_SATELLITE;
                        break;
                    case 3:
                        type = GoogleMap.MAP_TYPE_HYBRID;
                        break;
                    case 4:
                        type = GoogleMap.MAP_TYPE_TERRAIN;
                        break;
                }

                mMap.setMapType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addControls() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        spKieuBanDo = findViewById(R.id.spKieuBanDo);
        String[] arr = getResources().getStringArray(R.array.maps_type);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arr);
        spKieuBanDo.setAdapter(adapter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        final LatLng coQuan = new LatLng(21.0250, 105.7914);
        //mMap.addMarker(new MarkerOptions().position(coQuan).title("SKY-GO").snippet("Cơ quan").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coQuan, 15));


        addEvents();
    }

    private void addEvents() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                Marker marker = mMap.addMarker(markerOptions);
                lngList.add(latLng);
                markerList.add(marker);
                if (polyline != null) {
                    polyline.remove();
                }

                PolylineOptions polylineOptions = new PolylineOptions().addAll(lngList).clickable(true).width(10).color(Color.CYAN);
                polyline = mMap.addPolyline(polylineOptions);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (polyline != null) {
                    polyline.remove();
                }

                for (Marker marker : markerList) {
                    marker.remove();
                }
                lngList.clear();
            }
        });
    }
}