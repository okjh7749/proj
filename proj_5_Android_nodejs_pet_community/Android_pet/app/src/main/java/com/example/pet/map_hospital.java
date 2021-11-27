package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.pet.api.ApiClient;
import com.example.pet.api.ApiN;
import com.example.pet.dto.CategoryResult;
import com.example.pet.dto.Document;
import com.example.pet.dto.mam;
import com.example.pet.dto.postD;
import com.example.pet.dto.resD;
import com.example.pet.interfaces.ApiInterface;
import com.example.pet.interfaces.NInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class map_hospital extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener, View.OnClickListener, MapView.CurrentLocationEventListener {
    final static String TAG = "MapTAG";
    MapView mMapView;
    MapPOIItem searchMarker = new MapPOIItem();
    ViewGroup mMapViewContainer;
    MapPoint currentMapPoint;

    private Button btn_prevPage, btn_nextPage;
    private TextView tv_pageNumber;
    private Button btn_search;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,find_fbt,location_fbt;
    String API_KEY = "KakaoAK c0eff73f6d8a25de747dad29ecbe4ba1";
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    ArrayList<postD> postDs=null;
    ArrayList<Document> documents=null;
    com.example.pet.dto.postD postD=null;

    private double mCurrentLng; //Long = X, Lat = Yㅌ
    private double mCurrentLat;
    private double mSearchLng = -1;
    private double mSearchLat = -1;
    boolean isTrackingMode = false;

    String who;
    String kind;
    int page_n=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_hospital_activity);
        firstInit();
    }

    public void firstInit() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        btn_prevPage = (Button) findViewById(R.id.btn_prevPage);
        btn_nextPage = (Button) findViewById(R.id.btn_nextPage);
        btn_search =(Button) findViewById(R.id.btn_search);
        tv_pageNumber = (TextView) findViewById(R.id.tv_pageNumber);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        find_fbt = (FloatingActionButton) findViewById(R.id.find_fbt);
        location_fbt = (FloatingActionButton) findViewById(R.id.location_fbt);


        mMapView = new MapView(this);
        mMapViewContainer = findViewById(R.id.map_view);
        mMapViewContainer.addView(mMapView);
        mMapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mMapView.setPOIItemEventListener(this);
        mMapView.setOpenAPIKeyAuthenticationResultListener(this);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter_map adapter = new Adapter_map(documents);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        find_fbt.setOnClickListener(this);
        location_fbt.setOnClickListener(this);
        btn_prevPage.setOnClickListener(this);
        btn_nextPage.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prevPage:
                if(page_n > 1)
                    page_n--;
                search(page_n,mSearchLat,mSearchLng);
                break;
            case R.id.btn_nextPage:
                page_n++;
                search(page_n,mSearchLat,mSearchLng);
                break;
            case R.id.find_fbt:
                page_n=1;
                search(page_n,mSearchLat,mSearchLng);
                break;
            case R.id.location_fbt:
                isTrackingMode = false;
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                break;
            case R.id.fab:
                anim();
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(v.getContext(), select_write.class);
                startActivity(intent);
                Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                Intent intent2 = new Intent(v.getContext(), select_board.class);
                startActivity(intent2);
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        //이 좌표로 지도 중심 이동
        mMapView.setMapCenterPoint(currentMapPoint, true);
        //전역변수로 현재 좌표 저장
        mSearchLat = mapPointGeo.latitude;
        mSearchLng = mapPointGeo.longitude;
        Log.d(TAG, "현재위치 => " + mSearchLat + "  " + mSearchLng);
        //트래킹 모드가 아닌 단순 현재위치 업데이트일 경우, 한번만 위치 업데이트하고 트래킹을 중단시키기 위한 로직
        if (!isTrackingMode) {
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapView.getMapCenterPoint().getMapPointGeoCoord();
        mSearchLng = mapPointGeo.longitude;
        mSearchLat = mapPointGeo.latitude;
        Log.d(TAG, "중앙좌표 위치 => " + mSearchLat + "  " + mSearchLng);

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        double lat = mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude;
        double lng = mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude;
        String s =Double.toString(lat);
        String ss =Double.toString(lng);
        Toast.makeText(this, mapPOIItem.getItemName(), Toast.LENGTH_SHORT).show();
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("선택해주세요");
        builder.setSingleChoiceItems(new String[]{"게시물 보기"}, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                if (index == 1) {

                    int postno = mapPOIItem.getTag();
                    Call<CategoryResult> call = apiInterface.getSearchKeyword(API_KEY,"동물병원",ss, s, 100, 1,"distance");
                    call.enqueue(new Callback<CategoryResult>() {
                        @Override
                        public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                            CategoryResult categoryResult = response.body();
                            Document doc = categoryResult.getDocuments().get(0);
                            Intent intent = new Intent(map_hospital.this, show_place.class);
                            assert response.body() != null;
                            intent.putExtra("doc", doc);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<CategoryResult> call, Throwable t) {
                            Log.d(TAG, "글정보 전송: 실패" + t.getMessage());
                        }
                    });
                }
            }
        });
        builder.addButton("취소", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public void search(int page_n,double Latitue,double Longitude) {
        String s =Double.toString(Latitue);
        String ss =Double.toString(Longitude);
        Call<CategoryResult> call2 = apiInterface.getSearchKeyword(API_KEY,"동물병원",ss,s,2000,page_n,"distance");

        call2.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call2, Response<CategoryResult> response) {
                CategoryResult categoryResult = response.body();
                List<Document> documents = categoryResult.getDocuments();
                Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" +documents);
                mMapView.removeAllPOIItems();
                mMapView.removeAllCircles();
                MapCircle circle1 = new MapCircle(
                        MapPoint.mapPointWithGeoCoord(Latitue, Longitude), // center
                        2000, // radius
                        Color.argb(128, 255, 0, 0), // strokeColor
                        Color.argb(128, 0, 255, 0) // fillColor
                );
                circle1.setTag(1997);
                mMapView.addCircle(circle1);

                for (Document document : documents) {
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(document.getPlaceName());
                    double x = Double.parseDouble(document.getY());
                    double y = Double.parseDouble(document.getX());
                    //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
                    marker.setMapPoint(mapPoint);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    mMapView.addPOIItem(marker);

                }

                Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" + documents.toString());
                RecyclerView recyclerView = findViewById(R.id.rv_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(map_hospital.this));
                Adapter_map adapter = new Adapter_map(documents);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new Adapter_map.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Document doc =documents.get(position);
                        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord( Double.parseDouble(doc.getY()),Double.parseDouble(doc.getX()));
                        mMapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);

                    }
                });
            }
            @Override
            public void onFailure(Call<CategoryResult> call2, Throwable t) {
                Log.d(TAG, "글 목록 받아오기: 실패"+t.getMessage());
            }
        });
    }
    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}