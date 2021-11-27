package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pet.api.ApiN;
import com.example.pet.dto.commentD;
import com.example.pet.dto.tagD;
import com.example.pet.interfaces.NInterface;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tag_piechart2 extends AppCompatActivity {

    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    private List<tagD> list;
    private List<String> listt;
    ArrayList NoOfEmp = new ArrayList();
    ArrayList name = new ArrayList();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_piechart2_activity);
        find10tag();
    }
    public void find10tag(){
        list = new ArrayList<tagD>();
        listt = new ArrayList<String>();
        Call<ArrayList<tagD>> call2 = nInterface.favoritetagiI();
        call2.enqueue(new Callback<ArrayList<tagD>>() {
            @Override
            public void onResponse(Call<ArrayList<tagD>> call2, Response<ArrayList<tagD>> response) {
                list = response.body();
                Log.d("test", "댓글정보 전송: "+list.toString());
                int i =0;

                for(tagD tag : list){
                    NoOfEmp.add(new Entry(tag.getCnt(), i++));
                    name.add(tag.getTag_name());
                }
                PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
                PieData data = new PieData(name, dataSet);
                PieChart pieChart = findViewById(R.id.piechart);
                pieChart.setData(data);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.animateXY(5000, 5000);


            }
            @Override
            public void onFailure(Call<ArrayList<tagD>> call2, Throwable t) {
                Log.d("test", "글정보 전송: 실패"+t.getMessage());
            }
        });
    }

}