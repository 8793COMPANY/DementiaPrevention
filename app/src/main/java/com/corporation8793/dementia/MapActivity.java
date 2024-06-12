package com.corporation8793.dementia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Random;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mNaverMap;
    Button seoul, daegu, gwangju;

    Region region;

    FusedLocationSource locationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        seoul = findViewById(R.id.seoul);
        daegu = findViewById(R.id.daegu);
        gwangju = findViewById(R.id.gwangju);

        region = new Region();

        seoul.setOnClickListener(v->{
            //                LatLng GWANGJU = new LatLng()
            Log.e("test", region.get_location("서울특별시"));
            String location = region.get_location("서울특별시");

            if (!location.isEmpty()) {
                String[] splitLocation = location.split(",");
                Log.e("test", splitLocation[0]);
                Log.e("test", splitLocation[1]);

                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));

                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
                mNaverMap.moveCamera(cameraUpdate);
            } else {
                Log.e("test", "location is null");
            }

        });

        daegu.setOnClickListener(v->{
            //                LatLng GWANGJU = new LatLng()
            Log.e("test", region.get_location("대구광역시"));
            String location = region.get_location("대구광역시");

            if (!location.isEmpty()) {
                String[] splitLocation = location.split(",");
                Log.e("test", splitLocation[0]);
                Log.e("test", splitLocation[1]);

                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));

                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
                mNaverMap.moveCamera(cameraUpdate);
            } else {
                Log.e("test", "location is null");
            }
        });

        gwangju.setOnClickListener(v->{
            //                LatLng GWANGJU = new LatLng()
            Log.e("test", region.get_location("광주광역시"));
            String location = region.get_location("광주광역시");

            if (!location.isEmpty()) {
                String[] splitLocation = location.split(",");
                Log.e("test", splitLocation[0]);
                Log.e("test", splitLocation[1]);

                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));

                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
                mNaverMap.moveCamera(cameraUpdate);
            } else {
                Log.e("test", "location is null");
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.naverMap);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.naverMap, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(locationSource);

        // 권한 확인
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        Log.e("test", "onMapReady");

        int size = 15;
        double lat, lnt;

        if (locationSource.getLastLocation() != null) {
            lat = locationSource.getLastLocation().getLatitude();
            lnt = locationSource.getLastLocation().getLongitude();

            for (int i = 0; i < size; i++) {
                Marker[] markers = new Marker[size];
                markers[i] = new Marker();

                Random randomInt = new Random();
                int randomIntValue = randomInt.nextInt(20);

                markers[i].setPosition(new LatLng(lat + (double) randomIntValue, lnt + (double) randomIntValue));
                markers[i].setMap(mNaverMap);

                int finalI = i;

                markers[i].setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Toast.makeText(getApplicationContext(), "마커 " + finalI + " 클릭", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        } else {
            Log.e("test", "getLastLocation is null");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow
                );
            }
        }
    }
}