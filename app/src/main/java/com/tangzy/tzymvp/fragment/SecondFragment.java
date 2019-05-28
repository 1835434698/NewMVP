package com.tangzy.tzymvp.fragment;

import android.view.View;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.databind.FragmentActivityBinder;
import com.tangzy.tzymvp.fragment.base.BaseFragment;
import com.tangzy.tzymvp.listener.NoDoubleClickListener;
import com.tangzy.tzymvp.view.DiffuseView;
import com.tangzy.tzymvp.viewbind.SecondFragDelegate;

public class SecondFragment extends BaseFragment<SecondFragDelegate> {

    private DiffuseView diffuseView;
    @Override
    protected Class<SecondFragDelegate> getDelegateClass() {
        return SecondFragDelegate.class;
    }

    @Override
    public BaseDataBinder getDataBinder() {
        return new FragmentActivityBinder();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.get(R.id.button).setOnClickListener(noDoubleClickListener);
        viewDelegate.get(R.id.button2).setOnClickListener(noDoubleClickListener);
        diffuseView = viewDelegate.get(R.id.diffuseView);
    }


    NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    diffuseView.start();
                    break;
                case R.id.button2:
                    diffuseView.stop();
                    break;
                default:
                    break;
            }
        }
    };
}
