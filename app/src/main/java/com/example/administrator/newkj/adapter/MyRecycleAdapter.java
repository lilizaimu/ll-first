package com.example.administrator.newkj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newkj.R;
import com.example.administrator.newkj.bean.bean.Pic;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/15.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

    private ArrayList<Pic.Body.MyPic> pics;
    Context context;
    public MyRecycleAdapter(Context context){
        pics=new ArrayList<>();
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=LayoutInflater.from(parent.getContext()).inflate(R.layout.piclayout,parent,false);
        MyViewHolder holder=new MyViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Pic.Body.MyPic myPic=pics.get(position);
        holder.tv_intro.setText(myPic.getTitle());
        Glide.with(context).load(myPic.getPicUrl()).into(holder.iv_pic);
        holder.iv_pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pics.remove(position);
                notifyItemRemoved(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public void addDatas(ArrayList<Pic.Body.MyPic> pics,boolean ifClear){
        if (pics==null) return;
        if (ifClear)    this.pics.clear();
        this.pics.addAll(pics);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_pic;
        private TextView tv_intro;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_pic= (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_intro= (TextView) itemView.findViewById(R.id.tv_introu);
        }
    }


}
