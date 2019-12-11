package com.tangzy.tzymvp.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

/**
 * DialogFragment class
 *
 * @author Administrator
 * @date 2019/12/11
 */
public class CustomDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String tittle = "gsdsdfs";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tittle);
        builder.setMessage("删除歌曲？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "确定", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static void showDialog(FragmentActivity activity){
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.show(activity.getSupportFragmentManager(),"yc");
    }
}

