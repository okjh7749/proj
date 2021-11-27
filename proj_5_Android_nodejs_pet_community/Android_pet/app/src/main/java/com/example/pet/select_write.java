package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pet.api.ApiN;
import com.example.pet.interfaces.NInterface;

public class select_write extends AppCompatActivity implements View.OnClickListener{
    Button normal_bt, img_bt, found_bt, class_bt;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_write_activity);
        firstInit();
    }
    public void firstInit() {
        normal_bt = (Button) findViewById(R.id.normal_bt);
        img_bt = (Button) findViewById(R.id.img_bt);
        found_bt = (Button) findViewById(R.id.found_bt);
        class_bt = (Button) findViewById(R.id.class_bt);

        normal_bt.setOnClickListener(this);
        img_bt.setOnClickListener(this);
        found_bt.setOnClickListener(this);
        class_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normal_bt:

                Intent intent = new Intent(v.getContext(), write_normal.class);

                startActivity(intent);

                break;
            case R.id.img_bt:
                Intent intent2 = new Intent(v.getContext(), write_img.class);
                startActivity(intent2);
                break;
            case R.id.found_bt:
                Intent intent3 = new Intent(v.getContext(), write_find_map.class);
                startActivity(intent3);
                break;
            case R.id.class_bt:
                Intent intent4 = new Intent(v.getContext(), write_class_map.class);
                startActivity(intent4);
                break;

        }
    }
}