package com.tangzy.tzymvp.util;

import android.text.TextUtils;
import android.webkit.SslErrorHandler;

import com.tangzy.tzymvp.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * 作用 ：证书验证的方法
 */
public enum CertifiUtils {
    INSTANCE;
    private List<String> VERIFY_HOST_NAME_ARRAY;

    /**
     * 单项验证验证证书
     * 1、客户端保存着服务端的证书并信任该证书即可
     * 2、https一般是单向认证，这样可以让绝大部分人都可以访问你的站点。
     * 用法
     * onReceivedSslError
     * CertifiUtils.INSTANCE.OnCertificateOfVerification(handler, view.getUrl());
     * <p>
     * 1、keytool -genkey -v -alias tomcat -keyalg RSA -keystore D:\apache-tomcat-8.5.32\tomcat.keystore -validity 36500  生成服务端证书
     * 2、服务端配置
     * <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
     * maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
     * clientAuth="false" sslProtocol="TLS"
     * keystoreFile="/tomcat.keystore"
     * keystorePass="123456"/>
     * 3、keytool -export -alias tomcat -file D:\apache-tomcat-8.5.32\skxy.cer -keystore D:\apache-tomcat-8.5.32\tomcat.keystore -storepass 123456  客户端证书
     *
     * @param handler
     * @param url
     */
    public void OnCertificateOfVerification(final SslErrorHandler handler, String url) {
        if (VERIFY_HOST_NAME_ARRAY == null) {
            VERIFY_HOST_NAME_ARRAY = new ArrayList<>();
            VERIFY_HOST_NAME_ARRAY.add("192.168.70.85");
        }

        OkHttpClient.Builder builder = null;
        try {
            builder = setCertificates(new OkHttpClient.Builder(), Constant.app.getAssets().open("server.cer")).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (TextUtils.isEmpty(hostname)) {
                        return false;
                    }//192.168.70.85:8443
                    return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
                }
            });
        } catch (IOException e) {
            builder = new OkHttpClient.Builder();
        }
        Request request = new Request.Builder().url(url)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("证书验证失败" + e.getMessage());
                handler.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("证书验证成功" + response.body().string());
                handler.proceed();
            }
        });
    }

    private OkHttpClient.Builder setCertificates(OkHttpClient.Builder client, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);


//初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(Constant.app.getAssets().open("client.cer"), "123456".toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());


            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            X509TrustManager trustManager = Platform.get().trustManager(sslSocketFactory);

            client.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            Logger.e(e.getMessage());
            e.printStackTrace();
        }
        return client;
    }

}
