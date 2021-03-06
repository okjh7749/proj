package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.api.ApiN;
import com.example.pet.dto.commentD;
import com.example.pet.interfaces.NInterface;
import com.example.pet.lmageload.ImageLoadTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_find extends AppCompatActivity implements View.OnClickListener{
    TextView title_tv,writer_tv,date_tv,content_tv,kind_tv,lat_tv,long_tv;
    Button edit_bt,delete_bt;
    ImageView imgv;

    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    EditText comment_et;
    Button reg_button;
    int postno;
    String writer2;
    String type;
    LinearLayout comment_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_find_activity);
        firstInit();
        Intent intent = getIntent();
        postno = getIntent().getIntExtra("postno",1);
        String title = intent.getStringExtra("title");
        String writer = intent.getStringExtra("writer");
        String write_Date = intent.getStringExtra("write_date");
        String content = intent.getStringExtra("content");
        String img_path = intent.getStringExtra("img_path");
        String kind = intent.getStringExtra("kind");
        type = intent.getStringExtra("type");
        double longt = getIntent().getDoubleExtra("Longitude",1);
        double latt = getIntent().getDoubleExtra("Latitue",1);
        title_tv.setText(title);
        writer_tv.setText(writer);
        date_tv.setText(write_Date);
        kind_tv.setText(kind);
        lat_tv.setText(String.valueOf(latt));
        long_tv.setText(String.valueOf(longt));
        content_tv.setText(content);
        sendImageRequest(img_path);

        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        writer2=sharedPref.getString("uID", "none");
        if(writer.equals(writer2)){
            edit_bt.setEnabled(true);
            delete_bt.setEnabled(true);
        }
        showcomment();

    }
    public void firstInit() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        writer_tv = (TextView) findViewById(R.id.writer_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        kind_tv = (TextView) findViewById(R.id.kind_tv);
        lat_tv = (TextView) findViewById(R.id.lat_tv);
        long_tv = (TextView) findViewById(R.id.long_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        edit_bt= (Button) findViewById(R.id.edit_bt);
        delete_bt= (Button) findViewById(R.id.delete_bt);
        imgv = (ImageView) findViewById(R.id.imgv);

        comment_et = (EditText) findViewById(R.id.comment_et);
        reg_button= (Button) findViewById(R.id.reg_button);
        comment_layout = findViewById(R.id.comment_layout);

        edit_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        reg_button.setOnClickListener(this);
    }
    public void sendImageRequest(String img_path) {

        String url = "http://192.168.0.11:3000" + img_path;
        //String url="https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2";
        ImageLoadTask task = new ImageLoadTask(url,imgv);
        task.execute();
    }
    public void showcomment(){
        comment_layout.removeAllViews();
        if(type.equals("animal")) {
            Call<ArrayList<commentD>> call2 = nInterface.showcommentfaI(postno);
            call2.enqueue(new Callback<ArrayList<commentD>>() {
                @Override
                public void onResponse(Call<ArrayList<commentD>> call2, Response<ArrayList<commentD>> response) {
                    ArrayList<commentD> commentDs = response.body();
                    if (commentDs == null)
                        return;
                    for (commentD c : commentDs) {
                        View customView = getLayoutInflater().inflate(R.layout.recycler_comment, null);
                        ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                        ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                        ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                        comment_layout.addView(customView);

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<commentD>> call2, Throwable t) {
                    Log.d("test", "????????? ??????: ??????" + t.getMessage());
                }
            });
        }
        else{
            Call<ArrayList<commentD>> call2 = nInterface.showcommentfmI(postno);
            call2.enqueue(new Callback<ArrayList<commentD>>() {
                @Override
                public void onResponse(Call<ArrayList<commentD>> call2, Response<ArrayList<commentD>> response) {
                    ArrayList<commentD> commentDs = response.body();
                    if (commentDs == null)
                        return;
                    for (commentD c : commentDs) {
                        View customView = getLayoutInflater().inflate(R.layout.recycler_comment, null);
                        ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                        ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                        ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                        comment_layout.addView(customView);

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<commentD>> call2, Throwable t) {
                    Log.d("test", "????????? ??????: ??????" + t.getMessage());
                }
            });
        }
    }

    public void addcomment(){
        comment_layout.removeAllViews();
        if(type.equals("animal")) {
            Call<commentD> call2 = nInterface.addcommentfaI(postno, writer2, comment_et.getText().toString());
            call2.enqueue(new Callback<commentD>() {
                @Override
                public void onResponse(Call<commentD> call2, Response<commentD> response) {
                    commentD c = response.body();
                    Log.d("test", "???????????? ??????: " + c.toString());
                    View customView = getLayoutInflater().inflate(R.layout.recycler_comment, null);
                    ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                    ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                    ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                    comment_layout.addView(customView);
                    comment_et.setText("");
                    showcomment();
                }

                @Override
                public void onFailure(Call<commentD> call2, Throwable t) {
                    Log.d("test", "????????? ??????: ??????" + t.getMessage());
                }
            });
        }
        else{
            Call<commentD> call2 = nInterface.addcommentfmI(postno, writer2, comment_et.getText().toString());
            call2.enqueue(new Callback<commentD>() {
                @Override
                public void onResponse(Call<commentD> call2, Response<commentD> response) {
                    commentD c = response.body();
                    Log.d("test", "???????????? ??????: " + c.toString());
                    View customView = getLayoutInflater().inflate(R.layout.recycler_comment, null);
                    ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(c.getWriter());
                    ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(c.getContent());
                    ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(c.getWrite_date());

                    comment_layout.addView(customView);
                    comment_et.setText("");
                    showcomment();
                }

                @Override
                public void onFailure(Call<commentD> call2, Throwable t) {
                    Log.d("test", "????????? ??????: ??????" + t.getMessage());
                }
            });
        }
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