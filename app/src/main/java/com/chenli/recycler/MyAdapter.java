package com.chenli.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author cl
 * @创建时间 2016/12/31 10:54
 * @描述 ${todo}
 * @版本 $$Rev$$
 * @更新者 $$Author$$
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private OnchildClickListener listener;
    private RecyclerView recyclerView;

    public void setOnchildClickListener(OnchildClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = View.inflate(context, R.layout.item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView=null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //recyclerview被点击的事件
    @Override
    public void onClick(View view) {
    if(recyclerView!=null&&listener!=null){
        int position = recyclerView.getChildAdapterPosition(view);
        listener.onChildClick(recyclerView,view,position,list.get(position));
    }
    }

    public void remove(int position){
        list.remove(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = ((TextView) itemView.findViewById(R.id.item_text));
        }
    }

    public interface OnchildClickListener {
        void onChildClick(RecyclerView parent, View view, int position, String data);//其中的String为List中的泛型
    }
}
