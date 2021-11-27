package com.example.pet;

import static android.content.ContentValues.TAG;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.api.ApiN;
import com.example.pet.dto.postD;
import com.example.pet.dto.resD;
import com.example.pet.dto.tagD;
import com.example.pet.interfaces.NInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class board_normal extends AppCompatActivity implements View.OnClickListener{

    private Button btn_prevPage, btn_nextPage;
    private TextView tv_pageNumber;
    private AutoCompleteTextView et_search_field;
    private Button btn_search;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    int page_n=1;
    postD postD=null;
    String what;
    String kind;
    ArrayList<postD> postDs=null;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    private List<tagD> list;
    private List<String> listt;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_nomal_activity);
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
        et_search_field = (AutoCompleteTextView) findViewById(R.id.et_search_field);


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
                    Call<ArrayList<postD>> call = nInterface.lookuptI(et_search_field.getText().toString(),page_n,10);
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

                                    Call<postD> call = nInterface.lookuppI(postno);
                                    call.enqueue(new Callback<postD>() {
                                        @Override
                                        public void onResponse(Call<postD> call, Response<postD> response) {
                                            postD = response.body();
                                            Intent intent = new Intent(v.getContext(), show_normal.class);
                                            Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                            intent.putExtra("postno",postno);
                                            intent.putExtra("title",postD.getTitle());
                                            intent.putExtra("content",postD.getContent());
                                            intent.putExtra("writer",postD.getWriter());
                                            intent.putExtra("write_date",postD.getWrite_date());
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
                else {
                    Call<ArrayList<postD>> call = nInterface.lookupfI(kind, et_search_field.getText().toString(), page_n, 10);
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

                                    Call<postD> call = nInterface.lookuppI(postno);
                                    call.enqueue(new Callback<postD>() {
                                        @Override
                                        public void onResponse(Call<postD> call, Response<postD> response) {
                                            postD = response.body();
                                            Intent intent = new Intent(v.getContext(), show_normal.class);
                                            Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                            intent.putExtra("postno",postno);
                                            intent.putExtra("title",postD.getTitle());
                                            intent.putExtra("content",postD.getContent());
                                            intent.putExtra("writer",postD.getWriter());
                                            intent.putExtra("write_date",postD.getWrite_date());
                                            Log.d(TAG, "onResponse: 실패"+postD.getWrite_date());
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

        }

    }
    public void search(int page_n) {
        Call<ArrayList<postD>> call2 = nInterface.lookupI(page_n,10);

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

                        Call<postD> call = nInterface.lookuppI(postno);
                        call.enqueue(new Callback<postD>() {
                            @Override
                            public void onResponse(Call<postD> call, Response<postD> response) {
                                postD = response.body();
                                Intent intent = new Intent(v.getContext(), show_normal.class);
                                Log.d(TAG, "글정보 전송 성공 , 결과 \n" + postD.toString());
                                intent.putExtra("postno",postno);
                                intent.putExtra("title",postD.getTitle());
                                intent.putExtra("content",postD.getContent());
                                intent.putExtra("writer",postD.getWriter());
                                intent.putExtra("write_date",postD.getWrite_date());
                                Log.d(TAG, "onResponse: 실패"+postD.getWrite_date());
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

        Call<ArrayList<tagD>> call2 = nInterface.tagautonI();

        call2.enqueue(new Callback<ArrayList<tagD>>() {
            @Override
            public void onResponse(Call<ArrayList<tagD>> call2, Response<ArrayList<tagD>> response) {
                list = response.body();
                Log.d(TAG, "태그 목록 받아오기: 성공 , 결과 \n" + list.toString());
                for(tagD tag : list){
                    listt.add(tag.getTag_name());
                }
                et_search_field.setAdapter(new ArrayAdapter<String>(board_normal.this,
                        android.R.layout.simple_dropdown_item_1line,  listt ));

            }
            @Override
            public void onFailure(Call<ArrayList<tagD>> call2, Throwable t) {
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
