package com.zxl.baselib.api;

import com.zxl.baselib.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 * @author zxl
 * @date 2018/4/10
 */

public class BaseApiRetrofit {
    private final OkHttpClient mClient;

    protected OkHttpClient getClient() {
        return mClient;
    }

    protected BaseApiRetrofit(){
        //OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
            builder.sslSocketFactory(getSSLSocketFactory());
            builder.hostnameVerifier(getHostnameVerifier());

        }
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
//        mClient.sslSocketFactory(getSSLSocketFactory(BaseApp.getContext()));
        mClient = builder.build();
    }

//    public static SSLSocketFactory getSSLSocketFactory(Context context) {
//        final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码，该证书默认密码是123456
//        final String CLIENT_AGREEMENT = "TLS";//使用协议
//        final String CLIENT_TRUST_KEYSTORE = "BKS";
//        SSLContext sslContext = null;
//        try {
//            //取得SSL的SSLContext实例
//            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//            //取得TrustManagerFactory的X509密钥管理器实例
//            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            //取得BKS密库实例
//            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//            InputStream is = context.getResources().openRawResource(R.raw.key);
//            try {
//                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
//            } finally {
//                is.close();
//            }
//            //初始化密钥管理器
//            trustManager.init(tks);
//            //初始化SSLContext
//            sslContext.init(null, trustManager.getTrustManagers(), null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("SslContextFactory", e.getMessage());
//        }
//        return sslContext.getSocketFactory();
//    }
//
//
//    private static String[] VERIFY_HOST_NAME_ARRAY = new String[]{};
//
//    public static final HostnameVerifier createInsecureHostnameVerifier() {
//        return new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                if (TextUtils.isEmpty(hostname)) {
//                    return false;
//                }
//                return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
//            }
//        };
//    }


    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory()
    {
        try
        {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager()
    {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[]{};
            }
        }};
        return trustAllCerts;
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier()
    {
        HostnameVerifier hostnameVerifier = new HostnameVerifier()
        {
            @Override
            public boolean verify(String s, SSLSession sslSession)
            {
                return true;
            }
        };
        return hostnameVerifier;
    }





}
