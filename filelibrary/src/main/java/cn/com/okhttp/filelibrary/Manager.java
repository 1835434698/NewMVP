package cn.com.okhttp.filelibrary;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import cn.com.okhttp.filelibrary.helper.ProgressHelper;
import cn.com.okhttp.filelibrary.listener.impl.UIProgressListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangzy on 2016/8/11.
 */
public class Manager {

    public static void downFile(final String uri, UIProgressListener uiProgressResponseListener, final FileResponseListener fileListener, OkHttpClient client){
        //构造请求
        final Request request1 = new Request.Builder().url(uri).build();
        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(client, uiProgressResponseListener).newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fileListener.onErr("onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fileListener.onResp(response);

            }
        });

    }

    public static interface FileResponseListener {
        public void onResp(Response respons);

        public void onErr(String respons,IOException e);
    }
}
