package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.pet.api.ApiN;
import com.example.pet.dto.commentD;
import com.example.pet.dto.resD;
import com.example.pet.interfaces.NInterface;
import com.example.pet.lmageload.ImageLoadTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_img extends AppCompatActivity implements View.OnClickListener{
    TextView title_tv,writer_tv,date_tv,content_tv,like_tv;
    Button edit_bt,delete_bt;
    ImageView imgv;
    ToggleButton like_bt;

    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    EditText comment_et;
    Button reg_button;
    int postno;
    String writer2;
    LinearLayout comment_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_img_activity);
        firstInit();

        Intent intent = getIntent();
        postno = getIntent().getIntExtra("postno",1);
        String title = intent.getStringExtra("title");
        String writer = intent.getStringExtra("writer");
        String write_Date = intent.getStringExtra("write_date");
        String content = intent.getStringExtra("content");
        String img_path = intent.getStringExtra("img_path");
        title_tv.setText(title);
        writer_tv.setText(writer);
        date_tv.setText(write_Date);
        content_tv.setText(content);
        sendImageRequest(img_path);
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        writer2=sharedPref.getString("uID", "none");
        if(writer.equals(writer2)){
            edit_bt.setEnabled(true);
            delete_bt.setEnabled(true);
        }
        showcomment();
        chklike();
    }
    public void firstInit() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        writer_tv = (TextView) findViewById(R.id.writer_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        like_tv = (TextView) findViewById(R.id.like_tv);
        edit_bt= (Button) findViewById(R.id.edit_bt);
        delete_bt= (Button) findViewById(R.id.delete_bt);
        imgv = (ImageView) findViewById(R.id.imgv);
        like_bt = (ToggleButton)findViewById(R.id.like_bt);
        like_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addlike();
                } else {
                    dellike();
                }
            }
        });

        comment_et = (EditText) findViewById(R.id.comment_et);
        reg_button= (Button) findViewById(R.id.reg_button);
        comment_layout = findViewById(R.id.comment_layout);

        edit_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        reg_button.setOnClickListener(this);
        like_bt.setOnClickListener(this);
    }
    public void sendImageRequest(String img_path) {

        String url = "http://192.168.0.11:3000" + img_path;
        //String url="https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2";
        ImageLoadTask task = new ImageLoadTask(url,imgv);
        task.execute();
    }

    public void showcomment(){
        comment_layout.removeAllViews();
        Call<ArrayList<commentD>> call2 = nInterface.showcommentipI(postno);
        call2.enqueue(new Callback<ArrayList<commentD>>() {
            @Override
            public void onResponse(Call<ArrayList<commentD>> call2, Response<ArrayList<commentD>> response) {
                ArrayList<commentD> commentDs = response.body();
                if(commentDs ==null)
                    return;
                for(commentD c : commentDs){
                    View customView = getLayoutInflater().inflate(R.layout.recycler_comment,null);
                    ((TextView)customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                    ((TextView)customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                    ((TextView)customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                    comment_layout.addView(customView);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<commentD>> call2, Throwable t) {
                Log.d("test", "글정보 전송: 실패"+t.getMessage());
            }
        });
    }

    public void addcomment(){
        comment_layout.removeAllViews();
        Call<commentD> call2 = nInterface.addcommentipI(postno,writer2,comment_et.getText().toString());
        call2.enqueue(new Callback<commentD>() {
            @Override
            public void onResponse(Call<commentD> call2, Response<commentD> response) {
                commentD c = response.body();
                Log.d("test", "댓글정보 전송: "+c.toString());
                View customView = getLayoutInflater().inflate(R.layout.recycler_comment,null);
                ((TextView)customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                ((TextView)customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                ((TextView)customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                comment_layout.addView(customView);
                comment_et.setText("");
                showcomment();
            }
            @Override
            public void onFailure(Call<commentD> call2, Throwable t) {
                Log.d("test", "글정보 전송: 실패"+t.getMessage());
            }
        });
    }
    public void chklike(){
        Call<resD> call =nInterface.chklikeI(postno,writer2);
        call.enqueue(new Callback<resD>() {
            @Override
            public void onResponse(Call<resD> call2, Response<resD> response) {
                resD c = response.body();
                Log.d("test", "댓글정보 전송: "+c.toString());
                String chk = c.getCode();
                String cnt = c.getMessage();
                like_tv.setText(cnt);
                if(chk.equals("200")){
                    like_bt.setChecked(false);
                }
                else{
                    like_bt.setChecked(true);
                }
            }
            @Override
            public void onFailure(Call<resD> call2, Throwable t) {
                Log.d("like", "like  실패"+t.getMessage());
            }
        });
    }
    public void addlike(){
        Call<resD> call =nInterface.addlikeI(postno,writer2);
        call.enqueue(new Callback<resD>() {
            @Override
            public void onResponse(Call<resD> call2, Response<resD> response) {
                resD c = response.body();
                String cnt = c.getMessage();
                like_tv.setText(cnt);
            }
            @Override
            public void onFailure(Call<resD> call2, Throwable t) {
                Log.d("like", "like  실패"+t.getMessage());
            }
        });

    }
    public void dellike(){
        Call<resD> call =nInterface.dellikeI(postno,writer2);
        call.enqueue(new Callback<resD>() {
            @Override
            public void onResponse(Call<resD> call2, Response<resD> response) {
                resD c = response.body();
                String cnt = c.getMessage();
                like_tv.setText(cnt);
            }
            @Override
            public void onFailure(Call<resD> call2, Throwable t) {
                Log.d("like", "like  실패"+t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_bt:
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete_bt:
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reg_button:
                addcomment();
                break;



        }

    }
}