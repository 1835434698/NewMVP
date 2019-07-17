package com.tangzy.tzymvp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.net.FileOkHttp;
import com.tangzy.tzymvp.net.OkHttpManager;
import com.tangzy.tzymvp.net.ResponseListener;
import com.tangzy.tzymvp.util.FileUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.ShardPreferenceManager;
import com.tangzy.tzymvp.util.Toasts;
import com.tangzy.tzymvp.util.UtilZip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.com.okhttp.filelibrary.listener.impl.UIProgressListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/7/17
 */
public class TwoActivity extends AppCompatActivity {
    private final String updateFile = "/storage/emulated/0/DemoApp/one";

    private static final String TAG = "TwoActivity";
    private Button button;
    private GridView gridView;
    private BaseAdapter adapter;
    private static int length = 0;
    String path = Constant.path+"/";
    Handler handler = new Handler();

    static int[] gvImageId = {R.drawable.iv_griview_item, R.drawable.iv_griview_item, R.drawable.iv_griview_item,
            R.drawable.iv_griview_item, R.drawable.iv_griview_item, R.drawable.iv_griview_item};
    static JSONArray gvNames = new JSONArray();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        init();
    }

    protected void init() {
        button = (Button)findViewById(R.id.button);
        gridView = (GridView)findViewById(R.id.gridView);
        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);

//        cd = new CustomDialogTwo(context, R.style.driver_customDialog_two, "张先生", "18501942558", new CustomDialogTwo.OKTwoListener() {
//            @Override
//            public void onEnsure() {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    return;
//                }else {
//                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:18501942558")));
//                    cd.dismiss();
//                }
//            }
//        });
//        Window window = cd.getWindow();
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.x = 0;
//        params.y = -150;
//        window.setAttributes(params);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGridView();

    }

    public void getGridView() {

        Logger.d(TAG, "getGridView ");
        final JSONObject httpParams =  new JSONObject();
        try {
            httpParams.put("userName","123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        final String url = Constant.url+"gridView";
        OkHttpManager.INSTANCE.asyncRequest("gridView", httpParams,listener, false);
//        OkHttpManager.asyncRequest("gridView.php", httpParams,listener, false);
    }


    private ResponseListener listener = new ResponseListener() {
        @Override
        public void onResp(String respons, String uri) {
            Logger.d(TAG, "onResp = "+respons);
            if (uri.equals("gridView")){
//            if (uri.equals("gridView.php")){
                JSONObject json = null;
                try {
                    json = new JSONObject(respons);
                    if (json.optString("retCode").equals("000000")){
                        //                    gvNames = json.optJSONObject("gridview");
                        gvNames = json.optJSONArray("gridView");
                        length = gvNames.length();
                        adapter.notifyDataSetChanged();
                    }else {
                        Toasts.showToastLong(json.optString("retMessage"));
                    }
                } catch (JSONException e) {
                    Toasts.showToastLong("返回数据格式不正确");
                    e.printStackTrace();
                }
            }else if (uri.equals("getFile")){
//                stopProgressDialog();

            }

        }

        @Override
        public void onErr(int errorCode, String respons, String uri) {
//            stopProgressDialog();
            Logger.d(TAG, "onErr = " + respons);

        }
    };

    private class GridViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(TwoActivity.this, R.layout.gridview_item, null);
            RelativeLayout gridView_item = (RelativeLayout) view.findViewById(R.id.gridView_item);
            ImageView iv_itemimage = (ImageView) view.findViewById(R.id.iv_userfragment_itemimage);
            TextView tv_itemname = (TextView) view.findViewById(R.id.tv_userfragment_itemname);
//            iv_itemimage.setImageResource(gvImageId[position]);
//            tv_itemname.setText(gvNames[position]);
            final JSONObject json = gvNames.optJSONObject(position);
            int version = ShardPreferenceManager.getInt(TwoActivity.this, "version_"+json.optString("name"));
            if (version < json.optInt("version", 0)){
                ShardPreferenceManager.setInt(TwoActivity.this, "version_"+json.optString("name"),json.optInt("version", 0));
                downLoad(json);
            }


            tv_itemname.setText(json.optString("text"));
            gridView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(path+json.optString("name"));
                    if (!file.exists()) {
                        downLoad(json);
                    }else {
                        File file1 = new File(path+json.optString("name")+"/index.html");
                        if (!file1.exists()){
                            try {
                                UtilZip.UnZipFolder(path+json.optString("name")+".zip", path);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Intent intent = new Intent(TwoActivity.this, WebActivity.class);
                            intent.putExtra("path",path+json.optString("name")+"/index.html");
                            TwoActivity.this.startActivity(intent);
                        }
                    }
                }
            });
            return view;
        }
    }

    private void downLoad(final JSONObject json) {

        Logger.d(TAG, "getFile ");
//        startProgressDialog("下载中...");

        Map<String, String> httpParams =  new HashMap<>();
        httpParams.put("userName","123456");
        httpParams.put("filename",json.optString("name"));

        UIProgressListener uiProgressListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long currentBytes, long contentLength, boolean done) {
                Logger.d(TAG, "dome = "+done);

            }

            @Override
            public void onUIStart(long currentBytes, long contentLength, boolean done) {
                super.onUIStart(currentBytes, contentLength, done);
            }

            @Override
            public void onUIFinish(long currentBytes, long contentLength, boolean done) {
                super.onUIFinish(currentBytes, contentLength, done);
            }
        };

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(TAG, "onErr ");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        stopProgressDialog();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.d(TAG, "onResp");
                String path = Constant.path+"/";
                try {
                    FileUtils.writeToFile(response.body().bytes(),path+json.optString("name")+".zip");
                    UtilZip.UnZipFolder(path+json.optString("name")+".zip", path);
                }catch (Exception e){
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        stopProgressDialog();
                    }
                });
            }
        };

        FileOkHttp.fileDown("getFile",httpParams,uiProgressListener,callback);

    }

}
