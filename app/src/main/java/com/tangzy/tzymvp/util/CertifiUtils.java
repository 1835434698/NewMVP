package com.tangzy.tzymvp.util;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;

import com.tangzy.tzymvp.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 *
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
     *
     *1、keytool -genkey -v -alias tomcat -keyalg RSA -keystore D:\apache-tomcat-8.5.32\tomcat.keystore -validity 36500  生成服务端证书
     * 2、服务端配置
     <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
     maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
     clientAuth="false" sslProtocol="TLS"
     keystoreFile="/tomcat.keystore"
     keystorePass="123456"/>
     * 3、keytool -export -alias tomcat -file D:\apache-tomcat-8.5.32\skxy.cer -keystore D:\apache-tomcat-8.5.32\tomcat.keystore -storepass 123456  客户端证书
     *
     * @param handler
     * @param url
     */
    public void OnCertificateOfVerification(final SslErrorHandler handler, String url) {
        if (VERIFY_HOST_NAME_ARRAY == null){
            VERIFY_HOST_NAME_ARRAY  = new ArrayList<>();
            VERIFY_HOST_NAME_ARRAY.add("192.168.70.85");
        }

        OkHttpClient.Builder builder = null;
        try {
            builder = setCertificates(new OkHttpClient.Builder(), Constant.app.getAssets().open("skxy.cer")).hostnameVerifier(new HostnameVerifier() {
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
                Logger.e("证书验证失败"+e.getMessage());
                handler.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("证书验证成功"+response.body().string());
                handler.proceed();
            }
        });
    }

    private OkHttpClient.Builder setCertificates(OkHttpClient.Builder client, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509","BC");
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
//
//    private SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
//        SSLContext context = SSLContext.getInstance("TLS");
//        TrustManager[] trustManagers = {new MyX509TrustManager()};
//        context.init(null, trustManagers, new SecureRandom());
//        return context.getSocketFactory();
//    }
//
//    private class MyX509TrustManager implements X509TrustManager {
//
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            if (chain == null) {
//                throw new CertificateException("checkServerTrusted: X509Certificate array is null");
//            }
//            if (chain.length < 1) {
//                throw new CertificateException("checkServerTrusted: X509Certificate is empty");
//            }
//            if (!(null != authType && authType.equals("ECDHE_RSA"))) {
//                throw new CertificateException("checkServerTrusted: AuthType is not ECDHE_RSA");
//            }
//
//            //检查所有证书
//            try {
//                TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
//                factory.init((KeyStore) null);
//                for (TrustManager trustManager : factory.getTrustManagers()) {
//                    ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
//                }
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (KeyStoreException e) {
//                e.printStackTrace();
//            }
//
//            //获取本地证书中的信息
//            String clientEncoded = "";
//            String clientSubject = "";
//            String clientIssUser = "";
//            try {
//                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//                InputStream inputStream = Constant.app.getAssets().open("client.cer");
//                X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
//                clientEncoded = new BigInteger(1, clientCertificate.getPublicKey().getEncoded()).toString(16);
//                clientSubject = clientCertificate.getSubjectDN().getName();
//                clientIssUser = clientCertificate.getIssuerDN().getName();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            //获取网络中的证书信息
//            X509Certificate certificate = chain[0];
//            PublicKey publicKey = certificate.getPublicKey();
//            String serverEncoded = new BigInteger(1, publicKey.getEncoded()).toString(16);
//
//            if (!clientEncoded.equals(serverEncoded)) {
//                throw new CertificateException("server's PublicKey is not equals to client's PublicKey");
//            }
//            String subject = certificate.getSubjectDN().getName();
//            if (!clientSubject.equals(subject)) {
//                throw new CertificateException("server's subject is not equals to client's subject");
//            }
//            String issuser = certificate.getIssuerDN().getName();
//            if (!clientIssUser.equals(issuser)) {
//                throw new CertificateException("server's issuser is not equals to client's issuser");
//            }
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[0];
//        }
//    }
//
//
//    private final static String CLIENT_PRI_KEY = "client.bks";
//    private final static String TRUSTSTORE_PUB_KEY = "truststore.bks";
//    private final static String CLIENT_BKS_PASSWORD = "123456";
//    private final static String TRUSTSTORE_BKS_PASSWORD = "123456";
//    private final static String KEYSTORE_TYPE = "BKS";
//    private final static String PROTOCOL_TYPE = "TLS";
//    private final static String CERTIFICATE_FORMAT = "X509";
//
//    public static SSLSocketFactory getSSLCertifcation(Context context) {
//        SSLSocketFactory sslSocketFactory = null;
//        try {
//            // 服务器端需要验证的客户端证书，其实就是客户端的keystore
//            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);// 客户端信任的服务器端证书
//            KeyStore trustStore = KeyStore.getInstance(KEYSTORE_TYPE);//读取证书
//            InputStream ksIn = context.getAssets().open(CLIENT_PRI_KEY);
//            InputStream tsIn = context.getAssets().open(TRUSTSTORE_PUB_KEY);//加载证书
//            keyStore.load(ksIn, CLIENT_BKS_PASSWORD.toCharArray());
//            trustStore.load(tsIn, TRUSTSTORE_BKS_PASSWORD.toCharArray());
//            ksIn.close();
//            tsIn.close();
//            //初始化SSLContext
//            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TYPE);
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(CERTIFICATE_FORMAT);
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(CERTIFICATE_FORMAT);
//            trustManagerFactory.init(trustStore);
//            keyManagerFactory.init(keyStore, CLIENT_BKS_PASSWORD.toCharArray());
//            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
//            sslSocketFactory = sslContext.getSocketFactory();
//        } catch (Exception e) {
//            Logger.e(e.getMessage());
//            e.printStackTrace();
//        }//省略各种异常处理，请自行添加
//        return sslSocketFactory;
//    }
}
