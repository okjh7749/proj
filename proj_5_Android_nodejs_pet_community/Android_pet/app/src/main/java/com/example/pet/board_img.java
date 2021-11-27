package com.example.pet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.api.ApiN;
import com.example.pet.dto.likeD;
import com.example.pet.dto.postD;
import com.example.pet.dto.tagD;
import com.example.pet.interfaces.NInterface;
import com.example.pet.lmageload.ImageLoadTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class board_img extends AppCompatActivity implements View.OnClickListener {

    private Button btn_prevPage, btn_nextPage;
    private TextView tv_pageNumber;
    private AutoCompleteTextView et_search_field;
    private Button btn_search;
    ImageButton img1,img2,img3;
    String kind;
    String what;
    String sort;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    int page_n=1;
    com.example.pet.dto.postD postD=null;
    ArrayList<postD> postDs=null;
    int i=0;


    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    private List<tagD> list;
    private List<String> listt;
    int[] link= new int[3];

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_img_activity);

        search(page_n);
        firstInit();
        autotag();
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Spinner spinner =findViewById(R.id.find_spinner);
        ArrayAdapter findAdapter = ArrayAdapter.createFromResource(this, R.array.my_array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(findAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        Spinner spinner2 =findViewById(R.id.sort_spinner);
        ArrayAdapter findAdapter2 = ArrayAdapter.createFromResource(this, R.array.my_array2, R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(findAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        cntlike();

    }



    public void firstInit() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        btn_prevPage = (Button) findViewById(R.id.btn_prevPage);
        btn_nextPage = (Button) findViewById(R.id.btn_nextPage);
        btn_search =(Button) findViewById(R.id.btn_search);
        et_search_field = (AutoCompleteTextView) findViewById(R.id.et_search_field);
        tv_pageNumber = (TextView) findViewById(R.id.tv_pageNumber);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        img1 = (ImageButton) findViewById(R.id.img1);
        img2 = (ImageButton) findViewById(R.id.img2);
        img3 = (ImageButton) findViewById(R.id.img3);
        img1.setDrawingCacheEnabled(true);
        img2.setDrawingCacheEnabled(true);
        img3.setDrawingCacheEnabled(true);


        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);

        btn_prevPage.setOnClickListener(this);
        btn_nextPage.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prevPage:
                if(page_n > 1)
                    page_n--;
                search(page_n);
                break;
            case R.id.btn_nextPage:
                page_n++;
                search(page_n);
                break;
            case R.id.btn_search:
                NInterface nInterface = ApiN.getApiN().create(NInterface.class);
                if(kind.equals("tag")){
                    Log.d(TAG, "tag 검색 시작: 성공 , 결과 \n" + kind);
                    Call<ArrayList<postD>> call = nInterface.lookupitI(et_search_field.getText().toString(),page_n,10);
                    call.enqueue(new Callback<ArrayList<postD>>() {
                        @Override
                        public void onResponse(Call<ArrayList<postD>> call, Response<ArrayList<postD>> response) {
                            postDs = response.body();
                            Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" + postDs.toString());
                            RecyclerView recyclerView = findViewById(R.id.rv_list);
                            Adapter_normal adapter = new Adapter_normal(postDs);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new Adapter_normal.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    int postno = postDs.get(position).getPostno();

                                    Call<postD> call = nInterface.lookuppiI(postno);
                                    call.enqueue(new Callback<postD>() {
                                        @Override
                                        public void onResponse(Call<postD> call, Response<postD> response) {
                                            postD = response.body();
                                            Intent intent = new Intent(v.getContext(), show_img.class);
                                            Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                            intent.putExtra("postno",postno);
                                            intent.putExtra("title",postD.getTitle());
                                            intent.putExtra("content",postD.getContent());
                                            intent.putExtra("writer",postD.getWriter());
                                            intent.putExtra("write_date",postD.getWrite_date());
                                            intent.putExtra("img_path",postD.getImg_path());
                                            Log.d(TAG, "onResponse: 실패"+postD.getImg_path());
                                            startActivity(intent);
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
                        public void onFailure(Call<ArrayList<postD>> call, Throwable t) {
                            Log.d(TAG, "글 목록 받아오기: 실패"+t.getMessage());
                        }
                    });

                }
                else {
                    Call<ArrayList<postD>> call = nInterface.lookupifI(kind,et_search_field.getText().toString(), sort, page_n, 10);
                    call.enqueue(new Callback<ArrayList<postD>>() {
                        @Override
                        public void onResponse(Call<ArrayList<postD>> call, Response<ArrayList<postD>> response) {
                            postDs = response.body();
                            Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" + postDs.toString());
                            RecyclerView recyclerView = findViewById(R.id.rv_list);

                            Adapter_normal adapter = new Adapter_normal(postDs);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new Adapter_normal.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    int postno = postDs.get(position).getPostno();

                                    Call<postD> call = nInterface.lookuppiI(postno);
                                    call.enqueue(new Callback<postD>() {
                                        @Override
                                        public void onResponse(Call<postD> call, Response<postD> response) {
                                            postD = response.body();
                                            Intent intent = new Intent(v.getContext(), show_img.class);
                                            Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                            intent.putExtra("postno",postno);
                                            intent.putExtra("title",postD.getTitle());
                                            intent.putExtra("content",postD.getContent());
                                            intent.putExtra("writer",postD.getWriter());
                                            intent.putExtra("write_date",postD.getWrite_date());
                                            intent.putExtra("img_path",postD.getImg_path());
                                            Log.d(TAG, "onResponse: 성공"+postD.getPostno());
                                            startActivity(intent);
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
                        public void onFailure(Call<ArrayList<postD>> call, Throwable t) {
                            Log.d(TAG, "글 목록 받아오기: 실패"+t.getMessage());
                        }
                    });
                }
                break;
            case R.id.fab:
                anim();
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(v.getContext(),select_write.class);
                startActivity(intent);
                Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                Intent intent2 = new Intent(v.getContext(),select_board.class);
                startActivity(intent2);
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img1:
                move(link[0]);
                break;
            case R.id.img2:
                move(link[1]);
                break;
            case R.id.img3:
                move(link[2]);
                break;

        }

    }
    public void search(int page_n){
        Call<ArrayList<postD>> call2 = nInterface.lookupiI(page_n,10);

        call2.enqueue(new Callback<ArrayList<postD>>() {
            @Override
            public void onResponse(Call<ArrayList<postD>> call2, Response<ArrayList<postD>> response) {
                postDs = response.body();
                Log.d(TAG, "글 목록 받아오기: 성공 , 결과 \n" + postDs.toString());
                RecyclerView recyclerView = findViewById(R.id.rv_list);

                Adapter_normal adapter = new Adapter_normal(postDs);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new Adapter_normal.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        int postno = postDs.get(position).getPostno();
                        Call<postD> call = nInterface.lookuppiI(postno);
                        call.enqueue(new Callback<postD>() {
                            @Override
                            public void onResponse(Call<postD> call, Response<postD> response) {
                                postD = response.body();
                                Intent intent = new Intent(v.getContext(), show_img.class);
                                Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                intent.putExtra("postno",postno);
                                intent.putExtra("title",postD.getTitle());
                                intent.putExtra("content",postD.getContent());
                                intent.putExtra("writer",postD.getWriter());
                                intent.putExtra("write_date",postD.getWrite_date());
                                intent.putExtra("img_path",postD.getImg_path());
                                startActivity(intent);
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
    public void autotag(){

        list = new ArrayList<tagD>();
        listt = new ArrayList<String>();

        Call<ArrayList<tagD>> call2 = nInterface.tagautoiI();

        call2.enqueue(new Callback<ArrayList<tagD>>() {
            @Override
            public void onResponse(Call<ArrayList<tagD>> call2, Response<ArrayList<tagD>> response) {
                list = response.body();
                Log.d(TAG, "태그 목록 받아오기: 성공 , 결과 \n" + list.toString());
                for(tagD tag : list){
                    listt.add(tag.getTag_name());
                }
                et_search_field.setAdapter(new ArrayAdapter<String>(board_img.this,
                        android.R.layout.simple_dropdown_item_1line,  listt ));

            }
            @Override
            public void onFailure(Call<ArrayList<tagD>> call2, Throwable t) {
                Log.d(TAG, "글 목록 받아오기: 실패"+t.getMessage());
            }
        });

    }
    public void cntlike(){
        i=0;
        Call<ArrayList<likeD>> call2 = nInterface.cntlikeI();

        call2.enqueue(new Callback<ArrayList<likeD>>() {
            @Override
            public void onResponse(Call<ArrayList<likeD>> call2, Response<ArrayList<likeD>> response) {
                ArrayList<likeD> list = response.body();
                Log.d(TAG, "태그 목록 받아오기: 성공 , 결과 \n" + list.toString());

                for(likeD likeD : list){
                    int postnos = likeD.getPostno();
                    Call<postD> call = nInterface.lookuppiI(postnos);
                    call.enqueue(new Callback<postD>() {
                        @Override
                        public void onResponse(Call<postD> call, Response<postD> response) {
                            postD = response.body();
                            sendImageRequest(postD.getImg_path(),i);
                            Log.d("get path", "postno 받기"+postnos);
                            if(i<3) {
                                link[i] = postnos;

                                i = i + 1;
                            }
                            else{

                            }
                        }

                        @Override
                        public void onFailure(Call<postD> call, Throwable t) {
                            Log.d(TAG, "글정보 전송: 실패" + t.getMessage());
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<ArrayList<likeD>> call2, Throwable t) {
                Log.d(TAG, "글 목록 받아오기: 실패"+t.getMessage());
            }
        });
    }
    public void sendImageRequest(String img_path,int cnt) {

        String url = "http://192.168.0.11:3000" + img_path;
        //String url="https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2";
        if(cnt ==0){
            ImageLoadTask task = new ImageLoadTask(url,img1);
            task.execute();
        }
        else if(cnt ==1){
            ImageLoadTask task = new ImageLoadTask(url,img2);
            task.execute();
        }else{
            ImageLoadTask task = new ImageLoadTask(url,img3);
            task.execute();
        }


    }
    public void move(int postno){
        Call<postD> call = nInterface.lookuppiI(postno);
        call.enqueue(new Callback<postD>() {
            @Override
            public void onResponse(Call<postD> call, Response<postD> response) {
                postD = response.body();
                Intent intent = new Intent(board_img.this, show_img.class);
                Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                intent.putExtra("postno",postno);
                intent.putExtra("title",postD.getTitle());
                intent.putExtra("content",postD.getContent());
                intent.putExtra("writer",postD.getWriter());
                intent.putExtra("write_date",postD.getWrite_date());
                intent.putExtra("img_path",postD.getImg_path());
                Log.d(TAG, "onResponse: 성공"+postD.getPostno());
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<postD> call, Throwable t) {
                Log.d(TAG, "글정보 전송: 실패"+t.getMessage());
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