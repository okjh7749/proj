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

import com.example.pet.dto.postD;

import java.util.ArrayList;


public class Adapter_normal extends RecyclerView.Adapter<Adapter_normal.ViewHolder> implements View.OnClickListener{
    private ArrayList<postD> postDs = null;
    private OnItemClickListener mListener = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv;
        TextView writer_tv;
        TextView day_tv;
        ViewHolder(Context context,View itemView) {
            super(itemView); // 뷰 객체에 대한 참조
            title_tv = itemView.findViewById(R.id.title_tv);
            writer_tv = itemView.findViewById(R.id.writer_tv);
            day_tv = itemView.findViewById(R.id.day_tv);
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

    public Adapter_normal(ArrayList<postD> postDs){

        this.postDs= postDs;

    }



    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }


    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    @NonNull
    @Override
    public Adapter_normal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.recycler_board, parent, false) ;
        ViewHolder viewHolder= new ViewHolder(context,view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        postD postDd = postDs.get(position);

        holder.day_tv.setText(postDd.getWrite_date());
        holder.writer_tv.setText(postDd.getWriter());
        holder.title_tv.setText(postDd.getTitle());


    }


    @Override
    public int getItemCount() {
        if(postDs==null){
            return 0;
        }
        return postDs.size();
    }

    @Override
    public void onClick(View view) {

    }



}
