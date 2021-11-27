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
import android.widget.TextView;

import com.example.pet.api.ApiN;
import com.example.pet.dto.postD;
import com.example.pet.interfaces.NInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class write_normal extends AppCompatActivity implements View.OnClickListener {
    TextView writer_tv;
    EditText title_et,content_et;
    Button reg_button;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    postD postD=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_nomal_activity);
        firstInit();
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

        String writer=sharedPref.getString("uID", "none");
        writer_tv.setText(writer);

    }
    public void firstInit() {
        title_et = (EditText) findViewById(R.id.title_et);
        writer_tv = (TextView) findViewById(R.id.writer_tv);
        content_et = (EditText) findViewById(R.id.content_et);
        reg_button = (Button) findViewById(R.id.reg_button);

        reg_button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_button:
                Call<postD> call2 = nInterface.postI(title_et.getText().toString(),content_et.getText().toString(),writer_tv.getText().toString());

                call2.enqueue(new Callback<postD>() {
                    @Override
                    public void onResponse(Call<postD> call2, Response<postD> response) {
                        postD = response.body();
                        Log.d(TAG, "글 쓰기: 성공 , 결과 \n" + postD.toString());
                        Call<ArrayList<postD>> call = nInterface.lookupI(1,10);
                        Intent intent = new Intent(v.getContext(), board_normal.class);
                        startActivity(intent);

                    }
                    @Override
                    public void onFailure(Call<postD> call2, Throwable t) {
                        Log.d(TAG, "글 쓰기: 실패"+t.getMessage());
                    }
                });

        }

    }

}