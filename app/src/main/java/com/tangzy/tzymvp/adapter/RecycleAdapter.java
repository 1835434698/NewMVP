package com.tangzy.tzymvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.RecycleViewBean;

import java.util.ArrayList;


/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {

    private ArrayList<RecycleViewBean> list;
    private Context mContext;

    public RecycleAdapter (Context context, ArrayList<RecycleViewBean> list){
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleAdapter.Holder holder = new RecycleAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // TODO: 2019/12/23
        Log.d("tagnzy", "tv_name");
//        holder.tv_name.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private View v_line;
        private ImageView image, iv_select;

        public Holder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            image = itemView.findViewById(R.id.image);
//            iv_select = itemView.findViewById(R.id.iv_select);
//            iv_bank_icon = itemView.findViewById(R.id.iv_bank_icon);
        }
    }
}
