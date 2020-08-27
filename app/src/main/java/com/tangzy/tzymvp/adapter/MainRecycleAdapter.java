package com.tangzy.tzymvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.MainBean;

import java.util.ArrayList;

public class MainRecycleAdapter  extends RecyclerView.Adapter<MainRecycleAdapter.Holder>{

    private Context mContext;
    private ArrayList<MainBean> lists;

    public MainRecycleAdapter(Context context, ArrayList<MainBean> lists) {
        this.mContext = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.btName.setText(lists.get(position).name);
        if (listener != null){
            holder.btName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private Button btName;

        public Holder(View itemView) {
            super(itemView);
            btName = itemView.findViewById(R.id.btName);
        }
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }
}
