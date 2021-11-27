package com.example.pet;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.dto.CategoryResult;
import com.example.pet.dto.Document;
import com.example.pet.dto.postD;

import java.util.ArrayList;
import java.util.List;


public class Adapter_map extends RecyclerView.Adapter<Adapter_map.ViewHolder> implements View.OnClickListener{
    List<Document> dlist;
    private OnItemClickListener mListener = null;

    public Adapter_map(List<Document> dlist){

        this.dlist= dlist;

    }

    @Override
    public int getItemCount() {
        if(dlist==null){
            return 0;
        }
        return dlist.size();
    }

    @NonNull
    @Override
    public Adapter_map.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.recycler_map, parent, false) ;
        ViewHolder viewHolder= new ViewHolder(context,view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Document Document = dlist.get(position);

        holder.tv_list_name.setText(Document.getPlaceName());
        holder.tv_list_road.setText(Document.getRoadAddressName());
        holder.tv_list_phon.setText(Document.getPhone());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_list_name;
        TextView tv_list_road;
        TextView tv_list_phon;
        ViewHolder(Context context,View itemView) {
            super(itemView); // 뷰 객체에 대한 참조
            tv_list_name = itemView.findViewById(R.id.tv_list_name);
            tv_list_road = itemView.findViewById(R.id.tv_list_road);
            tv_list_phon = itemView.findViewById(R.id.tv_list_phon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Log.d(TAG, "onResponse: 성공4 , 결과 \n" + pos);
                        mListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }





    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }


    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener ;
    }









    @Override
    public void onClick(View view) {

    }



}
