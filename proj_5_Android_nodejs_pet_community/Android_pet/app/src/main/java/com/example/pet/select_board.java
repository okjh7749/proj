package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_board extends AppCompatActivity implements View.OnClickListener {

    Button normal_bt, img_bt, found_bt, class_bt, map_bt,ntag,itag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_board_activity);
        firstInit();

    }

    public void firstInit() {
        normal_bt = (Button) findViewById(R.id.normal_bt);
        img_bt = (Button) findViewById(R.id.img_bt);
        found_bt = (Button) findViewById(R.id.found_bt);
        class_bt = (Button) findViewById(R.id.class_bt);
        map_bt = (Button) findViewById(R.id.map_bt);
        ntag = (Button) findViewById(R.id.ntag);
        itag = (Button) findViewById(R.id.itag);


        normal_bt.setOnClickListener(this);
        img_bt.setOnClickListener(this);
        found_bt.setOnClickListener(this);
        class_bt.setOnClickListener(this);
        map_bt.setOnClickListener(this);
        ntag.setOnClickListener(this);
        itag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normal_bt:
                Intent intent = new Intent(v.getContext(), board_normal.class);
                startActivity(intent);
                break;
            case R.id.img_bt:
                Intent intent2 = new Intent(v.getContext(), board_img.class);
                startActivity(intent2);
                break;
            case R.id.found_bt:
                Intent intent3 = new Intent(v.getContext(), map_find.class);
                startActivity(intent3);
                break;
            case R.id.class_bt:
                Intent intent4 = new Intent(v.getContext(), map_class.class);
                startActivity(intent4);
                break;
            case R.id.map_bt:
                Intent intent5 = new Intent(v.getContext(), map_hospital.class);
                startActivity(intent5);
                break;
            case R.id.ntag:
                Intent intent6 = new Intent(v.getContext(), tag_piechart.class);
                startActivity(intent6);
                break;
            case R.id.itag:
                Intent intent7 = new Intent(v.getContext(), tag_piechart2.class);
                startActivity(intent7);
                break;

        }
    }
}