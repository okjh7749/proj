package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.pet.dto.postD;
import com.example.pet.interfaces.ApiInterface;
import com.example.pet.interfaces.NInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class map_find extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener, View.OnClickListener, MapView.CurrentLocationEventListener {
    final static String TAG = "MapTAG";
    MapView mMapView;
    MapPOIItem searchMarker = new MapPOIItem();
    ViewGroup mMapViewContainer;
    MapPoint currentMapPoint;

    private Button btn_prevPage, btn_nextPage;
    private TextView tv_pageNumber;
    private EditText et_search_field;
    private Button btn_search;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,find_fbt,location_fbt;
    Spinner spinner;

    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    ArrayList<postD> postDs=null;
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
        setContentView(R.layout.map_find_activity);
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
        et_search_field = (EditText) findViewById(R.id.et_search_field);



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
        Adapter_normal adapter = new Adapter_normal(postDs);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        find_fbt.setOnClickListener(this);
        location_fbt.setOnClickListener(this);
        btn_prevPage.setOnClickListener(this);
        btn_nextPage.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        Spinner spinner =findViewById(R.id.kind_spinner);
        ArrayAdapter findAdapter = ArrayAdapter.createFromResource(this, R.array.my_array4, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(findAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        Spinner spinner2 =findViewById(R.id.who_spinner);
        ArrayAdapter findAdapter2 = ArrayAdapter.createFromResource(this, R.array.my_array3, R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(findAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                who= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prevPage:
                if(page_n > 1)
                    page_n--;
                search(kind,who,page_n,mSearchLat,mSearchLng);
                break;
            case R.id.btn_nextPage:
                page_n++;
                search(kind,who,page_n,mSearchLat,mSearchLng);
                break;
            case R.id.btn_search:
                page_n=1;
                search(kind,who,page_n,mSearchLat,mSearchLng);
                break;
            case R.id.find_fbt:
                page_n=1;
                search(kind,who,page_n,mSearchLat,mSearchLng);
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
        Toast.makeText(this, mapPOIItem.getItemName(), Toast.LENGTH_SHORT).show();
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("선택해주세요");
        builder.setSingleChoiceItems(new String[]{"쪽지", "게시물 보기"}, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                if (index == 0) {


                }
                else if (index == 1) {
                    int postno = mapPOIItem.getTag();
                    if (who.equals("animal")){
                        Call<postD> call = nInterface.lookupfapI(postno);
                        call.enqueue(new Callback<postD>() {
                            @Override
                            public void onResponse(Call<postD> call, Response<postD> response) {
                                postD = response.body();
                                Intent intent = new Intent(map_find.this, show_find.class);
                                Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                intent.putExtra("postno", postno);
                                intent.putExtra("title", postD.getTitle());
                                intent.putExtra("content", postD.getContent());
                                intent.putExtra("writer", postD.getWriter());
                                intent.putExtra("write_date", postD.getWrite_date());
                                intent.putExtra("kind", postD.getKind());
                                intent.putExtra("img_path", postD.getImg_path());
                                intent.putExtra("type", who);
                                intent.putExtra("Latitue", postD.getLatitue());
                                intent.putExtra("Longitude", postD.getLongitude());
                                Log.d(TAG, "onResponse: test" + postno);
                                Log.d(TAG, "onResponse: test" + who);
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(Call<postD> call, Throwable t) {
                                Log.d(TAG, "글정보 전송: 실패" + t.getMessage());
                            }
                        });
                    }
                    else{
                        Call<postD> call = nInterface.lookupfmpI(postno);
                        call.enqueue(new Callback<postD>() {
                            @Override
                            public void onResponse(Call<postD> call, Response<postD> response) {
                                postD = response.body();
                                Intent intent = new Intent(map_find.this, show_find.class);
                                Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                intent.putExtra("postno", postno);
                                intent.putExtra("title", postD.getTitle());
                                intent.putExtra("content", postD.getContent());
                                intent.putExtra("writer", postD.getWriter());
                                intent.putExtra("write_date", postD.getWrite_date());
                                intent.putExtra("kind", postD.getKind());
                                intent.putExtra("img_path", postD.getImg_path());
                                intent.putExtra("Latitue", postD.getLatitue());
                                intent.putExtra("Longitude", postD.getLongitude());
                                Log.d(TAG, "onResponse: test" + postD.getImg_path());
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(Call<postD> call, Throwable t) {
                                Log.d(TAG, "글정보 전송: 실패" + t.getMessage());
                            }
                        });
                    }
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

    public void search(String kind,String who,int page_n,double Latitue,double Longitude) {
        Call<ArrayList<postD>> call2 = nInterface.lookupfaI(kind,who,page_n,10,Latitue,Longitude);

        call2.enqueue(new Callback<ArrayList<postD>>() {
            @Override
            public void onResponse(Call<ArrayList<postD>> call2, Response<ArrayList<postD>> response) {
                postDs = response.body();
                mMapView.removeAllPOIItems();
                mMapView.removeAllCircles();
                MapCircle circle1 = new MapCircle(
                        MapPoint.mapPointWithGeoCoord(mSearchLat, mSearchLng), // center
                        1500, // radius
                        Color.argb(128, 255, 0, 0), // strokeColor
                        Color.argb(128, 0, 255, 0) // fillColor
                );
                circle1.setTag(1997);
                mMapView.addCircle(circle1);
                for(postD post : postDs){
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(post.getTitle());
                    marker.setTag(post.getPostno());
                    double x =post.getLatitue();
                    double y =post.getLongitude();
                    //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
                    marker.setMapPoint(mapPoint);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    mMapView.addPOIItem(marker);
                }
                Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" + postDs.toString());
                RecyclerView recyclerView = findViewById(R.id.rv_list);
                Adapter_normal adapter = new Adapter_normal(postDs);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new Adapter_normal.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        int postno = postDs.get(position).getPostno();

                        Call<postD> call = nInterface.lookupfapI(postno);
                        call.enqueue(new Callback<postD>() {
                            @Override
                            public void onResponse(Call<postD> call, Response<postD> response) {
                                postD = response.body();
                                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(postD.getLatitue(),postD.getLongitude());
                                mMapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);
                            }
                            @Override
                            public void onFailure(Call<postD> call, Throwable t) {
                                Log.d(TAG, "글정보 전송: 실패"+t.getMessage());
                            }
                        });
                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<postD>> call2, Throwable t) {
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