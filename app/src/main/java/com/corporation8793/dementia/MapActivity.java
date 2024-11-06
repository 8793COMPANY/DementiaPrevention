package com.corporation8793.dementia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mNaverMap;
    TextView select_si, select_gu, close_btn;
    Button seoul, daegu, gwangju;
    Region region;
    MedicalCenter medicalCenter;

    FusedLocationSource locationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };

    double lat, lnt;
    Marker[] markers;
    InfoWindow infoWindow;
    boolean select_end = false;

    List<MedicalCenterDetail> center_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Application.FullScreenMode(MapActivity.this);

        select_si = findViewById(R.id.select_si);
        select_gu = findViewById(R.id.select_gu);
        close_btn = findViewById(R.id.close_btn);

        select_si.setTextSize(DisplayFontSize.font_size_x_32);
        select_si.setPadding((int) DisplayFontSize.font_size_x_40, 0, 0, 0);
        select_gu.setTextSize(DisplayFontSize.font_size_x_32);
        select_gu.setPadding((int) DisplayFontSize.font_size_x_40, 0, 0, 0);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        medicalCenter = new MedicalCenter();

        try {
            MedicalCenter.loadData(MapActivity.this);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        select_si.setOnClickListener(v -> {
            MapDialog mapDialog = new MapDialog(MapActivity.this, 1, select_si.getText().toString(), new MapDialog.MapDialogListener() {
                @Override
                public void clickBtn(String si, String gu) {
                    Log.e("test222", si);
                    Log.e("test222", gu);

                    select_end = true;

                    select_si.setText(si);
                    select_gu.setText(gu);

                    if (markers != null) {
                        for (Marker marker : markers) {
                            marker.setMap(null);
                        }
                        Log.e("center_list", "markers not null");
                    } else {
                        Log.e("center_list", "markers null");
                    }

                    center_list = medicalCenter.get_center_location(Application.region_short_to_long(si), gu);
                    Log.e("center_list", center_list.size()+"");

                    if (center_list.isEmpty()) {
                        Toast.makeText(MapActivity.this, "해당하는 곳에는 치매안심 센터가 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                    markers = new Marker[center_list.size()];

                    for (int i = 0; i < center_list.size(); i++) {
                        Log.e("center_list", center_list.get(i).name);
                        Log.e("center_list", center_list.get(i).si);
                        Log.e("center_list", center_list.get(i).gu);
                        Log.e("center_list", center_list.get(i).address);
                        Log.e("center_list", center_list.get(i).center_point.latitude+"");
                        Log.e("center_list", center_list.get(i).center_point.longitude+"");
                        Log.e("center_list", center_list.get(i).center_number);

                        // 마커 찍기
                        markers[i] = new Marker();
                        markers[i].setPosition(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
                        markers[i].setCaptionText(center_list.get(i).name);
                        markers[i].setMap(mNaverMap);
                        markers[i].setTag(center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);

                        int finalI = i;
                        markers[i].setOnClickListener(overlay -> {
                            infoWindow.open(markers[finalI]);
                            return true;
                        });

                        if (finalI != 0) {
                            for (int j = 0; j < i; j++) {
                                // 중복 마커 제거
                                if (markers[j].getCaptionText().equals(center_list.get(i).name)) {
//                                    markers[j].setMap(null);
                                } else if (markers[j].getPosition().latitude == center_list.get(i).center_point.latitude) {
                                    if (markers[j].getPosition().longitude == center_list.get(i).center_point.longitude) {
                                        // 주소가 같은 경우 체크
                                        String before_name = markers[j].getCaptionText();
                                        String before_tag = markers[j].getTag().toString();

                                        markers[j].setCaptionText(before_name + "\n" + center_list.get(i).name);
                                        markers[j].setTag(before_name + " : " + before_tag + "\n" +
                                                center_list.get(i).name + " : " + center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);
                                    }
                                }
                            }
                        }

                        // 마지막 위치로 카메라 이동
                        if (i == (center_list.size() - 1)) {
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
                            mNaverMap.moveCamera(cameraUpdate);
                        }
                    }
                }
            });
            mapDialog.show();
        });

        select_gu.setOnClickListener(v -> {
            MapDialog mapDialog = new MapDialog(MapActivity.this, 2, select_si.getText().toString(), new MapDialog.MapDialogListener() {
                @Override
                public void clickBtn(String si, String gu) {
                    Log.e("test2222", si);
                    Log.e("test2222", gu);

                    select_end = true;

                    select_si.setText(si);
                    select_gu.setText(gu);

                    if (markers != null) {
                        for (Marker marker : markers) {
                            marker.setMap(null);
                        }
                        Log.e("center_list", "markers not null");
                    } else {
                        Log.e("center_list", "markers null");
                    }

                    center_list = medicalCenter.get_center_location(Application.region_short_to_long(si), gu);
                    Log.e("center_list", center_list.size()+"");

                    if (center_list.isEmpty()) {
                        Toast.makeText(MapActivity.this, "해당하는 곳에는 치매안심 센터가 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                    markers = new Marker[center_list.size()];

                    for (int i = 0; i < center_list.size(); i++) {
                        Log.e("center_list", center_list.get(i).name);
                        Log.e("center_list", center_list.get(i).si);
                        Log.e("center_list", center_list.get(i).gu);
                        Log.e("center_list", center_list.get(i).center_point.latitude+"");
                        Log.e("center_list", center_list.get(i).center_point.longitude+"");
                        Log.e("center_list", center_list.get(i).center_number);

                        // 마커 찍기
                        markers[i] = new Marker();
                        markers[i].setPosition(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
                        markers[i].setCaptionText(center_list.get(i).name);
                        markers[i].setMap(mNaverMap);
                        markers[i].setTag(center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);

                        int finalI = i;
                        markers[i].setOnClickListener(overlay -> {
                            infoWindow.open(markers[finalI]);
                            return true;
                        });

                        if (finalI != 0) {
                            for (int j = 0; j < i; j++) {
                                // 중복 마커 제거
                                if (markers[j].getCaptionText().equals(center_list.get(i).name)) {
//                                    markers[j].setMap(null);
                                } else if (markers[j].getPosition().latitude == center_list.get(i).center_point.latitude) {
                                    if (markers[j].getPosition().longitude == center_list.get(i).center_point.longitude) {
                                        // 주소가 같은 경우 체크
                                        String before_name = markers[j].getCaptionText();
                                        String before_tag = markers[j].getTag().toString();

                                        if (!before_name.equals(center_list.get(i).name)) {
                                            markers[j].setCaptionText(before_name + "\n" + center_list.get(i).name);
                                            markers[j].setTag(before_name + " : " + before_tag + "\n" +
                                                    center_list.get(i).name + " : " + center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);
                                        }
                                    }
                                }
                            }
                        }

                        // 마지막 위치로 카메라 이동
                        if (i == (center_list.size() - 1)) {
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
                            mNaverMap.moveCamera(cameraUpdate);
                        }
                    }
                }
            });
            mapDialog.show();
        });

        close_btn.setOnClickListener(view -> {
            finish();
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.naverMap);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.naverMap, mapFragment).commit();
        }

        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        mapFragment.getMapAsync(this);

//        seoul = findViewById(R.id.seoul);
//        daegu = findViewById(R.id.daegu);
//        gwangju = findViewById(R.id.gwangju);
//
//        seoul.setOnClickListener(v->{
//            //                LatLng GWANGJU = new LatLng()
//            Log.e("test", region.get_location("서울특별시"));
//            String location = region.get_location("서울특별시");
//
//            if (!location.isEmpty()) {
//                String[] splitLocation = location.split(",");
//                Log.e("test", splitLocation[0]);
//                Log.e("test", splitLocation[1]);
//
//                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));
//
//                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
//                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
//                mNaverMap.moveCamera(cameraUpdate);
//            } else {
//                Log.e("test", "location is null");
//            }
//
//        });
//
//        daegu.setOnClickListener(v->{
//            //                LatLng GWANGJU = new LatLng()
//            Log.e("test", region.get_location("대구광역시"));
//            String location = region.get_location("대구광역시");
//
//            if (!location.isEmpty()) {
//                String[] splitLocation = location.split(",");
//                Log.e("test", splitLocation[0]);
//                Log.e("test", splitLocation[1]);
//
//                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));
//
//                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
//                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
//                mNaverMap.moveCamera(cameraUpdate);
//            } else {
//                Log.e("test", "location is null");
//            }
//        });
//
//        gwangju.setOnClickListener(v->{
//            //                LatLng GWANGJU = new LatLng()
//            Log.e("test", region.get_location("광주광역시"));
//            String location = region.get_location("광주광역시");
//
//            if (!location.isEmpty()) {
//                String[] splitLocation = location.split(",");
//                Log.e("test", splitLocation[0]);
//                Log.e("test", splitLocation[1]);
//
//                //LatLng GWANGJU = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));
//
//                //CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1])), 15);
//                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0])));
//                mNaverMap.moveCamera(cameraUpdate);
//            } else {
//                Log.e("test", "location is null");
//            }
//        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(locationSource);

        // 권한 확인
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        Log.e("test", "onMapReady");

        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(MapActivity.this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });

        mNaverMap.addOnLoadListener(new NaverMap.OnLoadListener() {
            @Override
            public void onLoad() {
                Log.e("test", "onLoad()");

                checkLocation();
            }
        });

//        if (mNaverMap.getLocationSource() != null) {
//            mNaverMap.getLocationSource().activate(new LocationSource.OnLocationChangedListener() {
//                @Override
//                public void onLocationChanged(@Nullable Location location) {
//                    Log.e("test", "activate");
//                }
//            });
//        } else {
//            Log.e("test", "deactivate");
//        }
//
//        mNaverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
//                Log.e("test", "onMapClick");
//                Log.e("test", "onMapClick : " + latLng.latitude);
//                Log.e("test", "onMapClick : " + latLng.longitude);
//            }
//        });
//
//        mNaverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
//            @Override
//            public void onLocationChange(@NonNull Location location) {
////                Log.e("test", "onLocationChange");
////                Log.e("test", "onLocationChange : " + mNaverMap.getCameraPosition().target.latitude);
////                Log.e("test", "onLocationChange : " + mNaverMap.getCameraPosition().target.longitude);
//
//            }
//        });
//
//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(mNaverMap.getCameraPosition().target.latitude, mNaverMap.getCameraPosition().target.longitude));
//        marker.setMap(mNaverMap);
//
//        Log.e("test", "check2 : " + mNaverMap.getCameraPosition().target.latitude);
//        Log.e("test", "check3 : " + mNaverMap.getCameraPosition().target.longitude);

        // 지도의 카메라 위치 변경시 발생하는 리스너
//        mNaverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(int i, boolean b) {
//                if (locationSource.getLastLocation() != null) {
//                    Log.e("test", "getLastLocation not null");
//
//                    lat = locationSource.getLastLocation().getLatitude();
//                    lnt = locationSource.getLastLocation().getLongitude();
//                } else {
//                    Log.e("test", "getLastLocation is null");
//                }
//            }
//        });

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (locationSource.getLastLocation() != null) {
//                    Log.e("test", "getLastLocation not null");
//
//                    lat = locationSource.getLastLocation().getLatitude();
//                    lnt = locationSource.getLastLocation().getLongitude();
//
//                    Marker marker = new Marker();
//                    marker.setPosition(new LatLng(lat, lnt));
//                    marker.setMap(mNaverMap);
//
//                    for (int i = 0; i < size; i++) {
//                        Log.e("test", "for : " + i);
//
//                        Marker[] markers = new Marker[size];
//                        markers[i] = new Marker();
//
//                        Random randomInt = new Random();
//                        int randomIntValue = randomInt.nextInt(5);
//
////                    markers[i].setPosition(new LatLng(naverMap.getCamera
////                    Position().target.latitude + (double) randomIntValue,
////                            naverMap.getCameraPosition().target.longitude + (double) randomIntValue));
//
////            markers[i].setPosition(new LatLng(mNaverMap.getCameraPosition().target.latitude + (double) randomIntValue,
////                    mNaverMap.getCameraPosition().target.longitude + (double) randomIntValue));
////            markers[i].setMap(mNaverMap);
//                        markers[i].setPosition(new LatLng(lat + Math.random(), lnt + Math.random()));
//                        markers[i].setMap(mNaverMap);
//
//                        int finalI = i;
//
//                        markers[i].setOnClickListener(new Overlay.OnClickListener() {
//                            @Override
//                            public boolean onClick(@NonNull Overlay overlay) {
//                                Toast.makeText(getApplicationContext(), "마커 " + finalI + " 클릭", Toast.LENGTH_SHORT).show();
//                                return false;
//                            }
//                        });
//                    }
//                } else {
//                    Log.e("test", "getLastLocation is null");
//                }
//            }
//        }, 2000);

//        if (locationSource.getLastLocation() != null) {
//            Log.e("test", "getLastLocation not null");
//
//            lat = locationSource.getLastLocation().getLatitude();
//            lnt = locationSource.getLastLocation().getLongitude();
//        } else {
//            Log.e("test", "getLastLocation is null");
//        }
    }

    private void checkLocation() {
        int size = 15;

        if (locationSource.getLastLocation() != null) {
            Log.e("test", "getLastLocation not null");

            lat = locationSource.getLastLocation().getLatitude();
            lnt = locationSource.getLastLocation().getLongitude();

            // 현재 위치 주소 정보 가져오기
            List<Address> address = new ArrayList<>();
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.KOREA);
            try {
                address = geocoder.getFromLocation(lat, lnt, 1);
                Log.e("test2", "address : " + address);
                Log.e("test2", "address2 : " + address.get(0).getAddressLine(0));

                String location = address.get(0).getAddressLine(0);
                String[] split_location = address.get(0).getAddressLine(0).split(" ");

                Log.e("test2", "address3 : " + split_location[2]);

                select_si.setText(Application.region_long_to_short_contains(location));
                select_gu.setText(split_location[2]);

                initMarker(Application.region_long_to_short_contains(location), split_location[2]);

                // 현재 위치에 마커 찍기
//                Marker marker = new Marker();
//                marker.setPosition(new LatLng(lat, lnt));
//                marker.setCaptionText("현재 위치");
//                marker.setIconTintColor(Color.RED);
//                marker.setMap(mNaverMap);

            } catch (IOException e) {
                Log.e("test2", "error : " + e);
                throw new RuntimeException(e);
            }

//            // 현재 위치에 마커 찍기
//            Marker marker = new Marker();
//            marker.setPosition(new LatLng(lat, lnt));
//            marker.setMap(mNaverMap);

//            //랜덤으로 마커 찍기
//            for (int i = 0; i < size; i++) {
//                //Log.e("test", "for : " + i);
//
//                Marker[] markers = new Marker[size];
//                markers[i] = new Marker();
//
//                markers[i].setPosition(new LatLng(lat + Math.random(), lnt + Math.random()));
//                markers[i].setMap(mNaverMap);
//
//                int finalI = i;
//
//                markers[i].setOnClickListener(new Overlay.OnClickListener() {
//                    @Override
//                    public boolean onClick(@NonNull Overlay overlay) {
//                        Toast.makeText(getApplicationContext(), "마커 " + finalI + " 클릭", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//            }
        } else {
            Log.e("test", "getLastLocation is null");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!select_end) {
                        checkLocation();
                    }
                }
            }, 1000);
        }
    }

    private void initMarker(String si, String gu) {
        if (markers != null) {
            for (Marker marker : markers) {
                marker.setMap(null);
            }
            Log.e("center_list", "markers not null");
        } else {
            Log.e("center_list", "markers null");
        }

        center_list = medicalCenter.get_center_location(Application.region_short_to_long(si), gu);
        Log.e("center_list", center_list.size()+"");

        if (center_list.isEmpty()) {
            Toast.makeText(MapActivity.this, "해당하는 곳에는 치매안심 센터가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        markers = new Marker[center_list.size()];

        for (int i = 0; i < center_list.size(); i++) {
            Log.e("center_list", center_list.get(i).name);
            Log.e("center_list", center_list.get(i).si);
            Log.e("center_list", center_list.get(i).gu);
            Log.e("center_list", center_list.get(i).address);
            Log.e("center_list", center_list.get(i).center_point.latitude+"");
            Log.e("center_list", center_list.get(i).center_point.longitude+"");
            Log.e("center_list", center_list.get(i).center_number);

            // 마커 찍기
            markers[i] = new Marker();
            markers[i].setPosition(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
            markers[i].setCaptionText(center_list.get(i).name);
            markers[i].setMap(mNaverMap);
            markers[i].setTag(center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);

            int finalI = i;
            markers[i].setOnClickListener(overlay -> {
                infoWindow.open(markers[finalI]);
                return true;
            });

            if (finalI != 0) {
                for (int j = 0; j < i; j++) {
                    // 중복 마커 제거
                    if (markers[j].getCaptionText().equals(center_list.get(i).name)) {
//                                    markers[j].setMap(null);
                    } else if (markers[j].getPosition().latitude == center_list.get(i).center_point.latitude) {
                        if (markers[j].getPosition().longitude == center_list.get(i).center_point.longitude) {
                            // 주소가 같은 경우 체크
                            String before_name = markers[j].getCaptionText();
                            String before_tag = markers[j].getTag().toString();

                            markers[j].setCaptionText(before_name + "\n" + center_list.get(i).name);
                            markers[j].setTag(before_name + " : " + before_tag + "\n" +
                                    center_list.get(i).name + " : " + center_list.get(i).address + "\n전화번호 : " + center_list.get(i).center_number);
                        }
                    }
                }
            }

            // 마지막 위치로 카메라 이동
            if (i == (center_list.size() - 1)) {
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(center_list.get(i).center_point.latitude, center_list.get(i).center_point.longitude));
                mNaverMap.moveCamera(cameraUpdate);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }
}