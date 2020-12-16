package com.tangzy.tzymvp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;


public class BroadCastActivity extends AppCompatActivity {

    private static final String ACTION_UP_STATE_CHANGED = "allin.intent.action.UP_STATE_CHANGED";

    private static final String TAG = "BroadCastActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "onStart");
//        registerReceiver(myReceiver, new IntentFilter(ACTION_UP_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG, "onPause");
        try {
            unregisterReceiver(myReceiver);
        }catch (Exception e){
            Logger.e(TAG, "e = "+e.getMessage());

        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Logger.d(TAG, "onReceive");
        }

//        @Override
//        public void onUpStart(@NonNull Context context, @NonNull Object upId, @Nullable String extra) {
//            RxHandler.INSTANCE.postDelayed(CloudDataBankActivity.this, new Runnable() {
//                @Override
//                public void run() {
//                    if (!TextUtils.isEmpty(extra)) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(extra);
//                            int type = jsonObject.optInt("type");
//                            if (type == getConvenientlyUpImageType()
//                                    || type == getConvenientlyUpVideoType()) {
//                                //云资料库上传图片视频
//                                if (jsonObject.has("param")) {
//                                    JSONObject param = jsonObject.getJSONObject("param");
//                                    if (param != null) {
//                                        String noteId = param.optString("noteId");
//                                        if (StringUtils.isNotEmpty(mStartNoteId)) {
//                                            if (!mStartNoteId.equals(noteId)) {
//                                                for (int i = 0; i < mCloudEntities.size(); i++) {
//                                                    if (StringUtils.isNotEmpty(mCloudEntities.get(i).getNoteId()) && mCloudEntities.get(i).getNoteId().equals(noteId)) {
//                                                        mCloudDataBankAdapter.notifyItemChanged(i);
//                                                        break;
//                                                    }
//                                                }
//                                                mStartNoteId = noteId;
//                                            }
//                                        } else {
//                                            mStartNoteId = noteId;
//                                            for (int i = 0; i < mCloudEntities.size(); i++) {
//                                                if (StringUtils.isNotEmpty(mCloudEntities.get(i).getNoteId()) && mCloudEntities.get(i).getNoteId().equals(noteId)) {
//                                                    mCloudDataBankAdapter.notifyItemChanged(i);
//                                                    break;
//                                                }
//                                            }
//                                        }
//
//                                    }
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            },1000);
//        }
//
//        @Override
//        public void onUpError(@NonNull Context context, @NonNull Object upId, @Nullable String extra) {
//            RxHandler.INSTANCE.postDelayed(CloudDataBankActivity.this, new Runnable() {
//                @Override
//                public void run() {
//                    if (!TextUtils.isEmpty(extra)) {
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(extra);
//                            int type = jsonObject.optInt("type");
//                            if (type == getConvenientlyUpImageType()
//                                    || type == getConvenientlyUpVideoType()) {
//                                //云资料库上传图片视频
//                                if (jsonObject.has("param")) {
//                                    JSONObject param = jsonObject.getJSONObject("param");
//                                    if (param != null) {
//                                        String noteId = param.optString("noteId");
//
//                                        if (StringUtils.isNotEmpty(mFailNoteId)) {
//                                            if (!mFailNoteId.equals(noteId)) {
//                                                for (int i = 0; i < mCloudEntities.size(); i++) {
//                                                    if (StringUtils.isNotEmpty(mCloudEntities.get(i).getNoteId()) && mCloudEntities.get(i).getNoteId().equals(noteId)) {
//                                                        mCloudDataBankAdapter.notifyItemChanged(i);
//                                                        break;
//                                                    }
//                                                }
//                                                mFailNoteId = noteId;
//                                            }
//                                        } else {
//                                            mFailNoteId = noteId;
//                                            for (int i = 0; i < mCloudEntities.size(); i++) {
//                                                if (StringUtils.isNotEmpty(mCloudEntities.get(i).getNoteId()) && mCloudEntities.get(i).getNoteId().equals(noteId)) {
//                                                    mCloudDataBankAdapter.notifyItemChanged(i);
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            },1000);
//
//
//        }
//
//        @Override
//        public void onUpSuccess(@NonNull Context context, @NonNull Object upId, @Nullable String extra) {
//            RxHandler.INSTANCE.postDelayed(CloudDataBankActivity.this, new Runnable() {
//                @Override
//                public void run() {
//                    if (!TextUtils.isEmpty(extra)) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(extra);
//                            int type = jsonObject.optInt("type");
//                            if (type == getConvenientlyUpImageType()
//                                    || type == getConvenientlyUpVideoType()) {
//                                //云资料库上传图片视频
//                                if (jsonObject.has("param")) {
//                                    JSONObject param = jsonObject.getJSONObject("param");
//                                    if (param != null) {
//                                        String noteId = param.optString("noteId");
//                                        for (int i = 0; i < mCloudEntities.size(); i++) {
//                                            if (StringUtils.isNotEmpty(mCloudEntities.get(i).getNoteId()) && mCloudEntities.get(i).getNoteId().equals(noteId)){
//                                                mCloudDataBankAdapter.notifyItemChanged(i);
//                                                break;
//                                            }
//                                        }
//                                    }
//
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            },1000);
//
//        }
//
//        @Override
//        public void onAllUpSuccess(@NonNull Context context, @Nullable String extra) {
//            super.onAllUpSuccess(context, extra);
//        }
    };

}
