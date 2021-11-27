package com.example.pet;

import static android.content.ContentValues.TAG;

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
import android.widget.TextView;

import com.example.pet.api.ApiN;
import com.example.pet.dto.postD;
import com.example.pet.dto.resD;
import com.example.pet.interfaces.NInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class write_class extends AppCompatActivity implements View.OnClickListener{
    TextView writer_tv,long_tv,lat_tv;
    EditText title_et,content_et,add_time_et;
    Button reg_button;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    double longt;
    double latt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_class_activity_);
        firstInit();
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String writer=sharedPref.getString("uID", "none");
        Intent intent = getIntent();
        longt = getIntent().getDoubleExtra("long",1);
        latt = getIntent().getDoubleExtra("lat",1);

        writer_tv.setText(writer);
        long_tv.setText(String.valueOf(longt));
        lat_tv.setText(String.valueOf(latt));
    }
    public void firstInit() {
        title_et = (EditText) findViewById(R.id.title_et);
        writer_tv = (TextView) findViewById(R.id.writer_tv);
        content_et = (EditText) findViewById(R.id.content_et);
        add_time_et=(EditText) findViewById(R.id.add_time_et);
        long_tv= (TextView)findViewById(R.id.long_tv);
        lat_tv= (TextView)findViewById(R.id.lat_tv);
        reg_button = (Button) findViewById(R.id.reg_button);


        reg_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_button:
                Call<resD> call2 = nInterface.postcI(title_et.getText().toString(), content_et.getText().toString(), writer_tv.getText().toString(), add_time_et.getText().toString(), latt, longt);
                call2.enqueue(new Callback<resD>() {
                    @Override
                    public void onResponse(Call<resD> call2, Response<resD> response) {
                        resD resD = response.body();
                        Log.d(TAG, "글 쓰기: 성공 , 결과 \n" + resD);
                        Intent intent = new Intent(v.getContext(), map_class.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<resD> call2, Throwable t) {
                        Log.d(TAG, "글 쓰기: 실패" + t.getMessage());
                    }
                });
                break;
        }
    }
}