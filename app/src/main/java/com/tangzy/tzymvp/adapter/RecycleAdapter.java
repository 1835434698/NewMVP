package com.tangzy.tzymvp.adapter;

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
import androidx.recyclerview.widget.ViewHolderDelegate;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.RecycleViewBean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;


/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {

    private ArrayList<RecycleViewBean> list;
    private Context mContext;
    private SoftReference<Holder> viewHolder;

    public RecycleAdapter (Context context, ArrayList<RecycleViewBean> list){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.d("tagnzy", "onDetachedFromRecyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.d("tagnzy", "onAttachedToRecyclerView");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull Holder holder) {
        Log.d("tagnzy", "onViewDetachedFromWindow");
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull Holder holder) {
        Log.d("tagnzy", "onViewAttachedToWindow");
        super.onViewAttachedToWindow(holder);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleAdapter.Holder holder = new RecycleAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (viewHolder == null){
            viewHolder = new SoftReference<>(holder);
        }
        // TODO: 2019/12/23
        Log.d("tagnzy", "tv_name");
//        holder.tv_name.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getImg());
//        Glide.with(holder.itemView).
       View view = holder.image;
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (activity instanceof FragmentActivity) {

                Fragment fragment = findSupportFragment(view, (FragmentActivity) activity);
            }
        }
    }
    public void clearAdapter(){
        ViewHolderDelegate.setPosition(viewHolder.get(), NO_POSITION);
    }

//    @NonNull
//    public RequestManager get(@NonNull Fragment fragment) {
//        Preconditions.checkNotNull(fragment.getActivity(),
//                "You cannot start a load on a fragment before it is attached or after it is destroyed");
//        if (Util.isOnBackgroundThread()) {
//            return get(fragment.getActivity().getApplicationContext());
//        } else {
//            FragmentManager fm = fragment.getChildFragmentManager();
//            return supportFragmentGet(fragment.getActivity(), fm, fragment);
//        }
//    }
//
//    @NonNull
//    private RequestManager supportFragmentGet(@NonNull Context context, @NonNull FragmentManager fm,
//                                              @Nullable Fragment parentHint) {
//        SupportRequestManagerFragment current = getSupportRequestManagerFragment(fm, parentHint);
//        RequestManager requestManager = current.getRequestManager();
//        if (requestManager == null) {
//            // TODO(b/27524013): Factor out this Glide.get() call.
//            Glide glide = Glide.get(context);
//            requestManager =
//                    factory.build(
//                            glide, current.getGlideLifecycle(), current.getRequestManagerTreeNode(), context);
//            current.setRequestManager(requestManager);
//        }
//        return requestManager;
//    }
//
//    SupportRequestManagerFragment getSupportRequestManagerFragment(
//            @NonNull final FragmentManager fm, @Nullable Fragment parentHint) {
//        SupportRequestManagerFragment current =
//                (SupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
//        if (current == null) {
//            current = pendingSupportRequestManagerFragments.get(fm);
//            if (current == null) {
//                current = new SupportRequestManagerFragment();
//                current.setParentFragmentHint(parentHint);
//                pendingSupportRequestManagerFragments.put(fm, current);
//                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
//                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
//            }
//        }
//        return current;
//    }
//
//    @NonNull
//    public RequestManager get(@NonNull Activity activity) {
//        if (Util.isOnBackgroundThread()) {
//            return get(activity.getApplicationContext());
//        } else {
//            assertNotDestroyed(activity);
//            android.app.FragmentManager fm = activity.getFragmentManager();
//            return fragmentGet(activity, fm, null /*parentHint*/);
//        }
//    }

    @Nullable
    private Fragment findSupportFragment(@NonNull View target, @NonNull FragmentActivity activity) {
        ArrayMap<View, Fragment> tempViewToSupportFragment = new ArrayMap<>();
        tempViewToSupportFragment.clear();
        findAllSupportFragmentsWithViews(
                activity.getSupportFragmentManager().getFragments(), tempViewToSupportFragment);
        Fragment result = null;
        View activityRoot = activity.findViewById(android.R.id.content);
        View current = target;
        while (!current.equals(activityRoot)) {
            result = tempViewToSupportFragment.get(current);
            if (result != null) {
                break;
            }
            if (current.getParent() instanceof View) {
                current = (View) current.getParent();
            } else {
                break;
            }
        }

        tempViewToSupportFragment.clear();
        return result;
    }

    private static void findAllSupportFragmentsWithViews(
            @Nullable Collection<Fragment> topLevelFragments,
            @NonNull Map<View, Fragment> result) {
        if (topLevelFragments == null) {
            return;
        }
        for (Fragment fragment : topLevelFragments) {
            // getFragment()s in the support FragmentManager may contain null values, see #1991.
            if (fragment == null || fragment.getView() == null) {
                continue;
            }
            result.put(fragment.getView(), fragment);
            findAllSupportFragmentsWithViews(fragment.getChildFragmentManager().getFragments(), result);
        }
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
//            ViewHolderDelegate.setPosition(holder, position);


//            iv_select = itemView.findViewById(R.id.iv_select);
//            iv_bank_icon = itemView.findViewById(R.id.iv_bank_icon);
        }
    }
}
