package com.corporation8793.dementia;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mNaverMap;
    Button seoul, daegu, gwangju;

    Region region;

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

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;

        Log.e("test", "onMapReady");
    }
}