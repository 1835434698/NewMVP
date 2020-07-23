package com.tangzy.pdfviewpage.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
//
//import com.tangzy.tzymvp.R;
//import com.tangzy.tzymvp.bean.RecycleViewBean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import es.voghdev.pdfviewpager.library.R;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;


/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {

    private SoftReference<PDFPagerAdapter> source;
    private Context mContext;

    public RecycleAdapter(Context context, PDFPagerAdapter adapter){
        source = new SoftReference<>(adapter);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleAdapter.Holder holder = new RecycleAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.bottom_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("tagnzy", "tv_name");
        holder.image.setImageBitmap(source.get().getItem(position));
//        Glide.with(holder.itemView).

    }


    @Override
    public int getItemCount() {
        return source.get().getCount();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView image;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
