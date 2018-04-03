package com.gongyou.worker.network;




import com.gongyou.worker.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: zlc
 * Date: 2017/5/11.
 */

public class Retrofit2Manager {

    private static Retrofit2Manager retrofit2Manager;
    private static final String BASE_URL = Api.BASE_URL;
    private static final String BASE_URL_ZRF = Api.BASE_URL_ZRF;
    private static final int Time_Out = 10;
    private static Retrofit retrofit;
    private static Retrofit retrofit1;

    private Retrofit2Manager() {
    }

    public static Retrofit2Manager getInstance() {
        if (retrofit2Manager == null) {
            synchronized (Retrofit2Manager.class) {
                if (retrofit2Manager == null) {
                    retrofit2Manager = new Retrofit2Manager();
                }
            }
        }
        return retrofit2Manager;
    }

    public <T> T create(Class<T> clazz) {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkhttpClient())
                    .baseUrl(BASE_URL)
                    .build();
        return retrofit.create(clazz);
    }

    public <T> T create(Class<T> clazz, int type) {
        if (retrofit1 == null)
            retrofit1 = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkhttpClient())
                    .baseUrl(BASE_URL_ZRF)
                    .build();
        return retrofit1.create(clazz);
    }

    private OkHttpClient getOkhttpClient() {
//        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
//        OkHttpClient.Builder builder = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Time_Out, TimeUnit.SECONDS);
        builder.readTimeout(Time_Out, TimeUnit.SECONDS);
        builder.writeTimeout(Time_Out, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    private <T> String getBaseUrl(Class<T> clazz) {
        try {
            Field base_url = clazz.getField("Base_Url");
            return (String) base_url.get(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtil.i("LogUtils--> ", "request:" + request.toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtil.i("LogUtils--> ", "response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }

    public void setCertificates(OkHttpClient.Builder okHttpClient, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
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
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
//            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
