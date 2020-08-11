package com.oao.api.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@ConditionalOnMissingBean(OkHttpClient.class)
public class OaoOkHttpClientAutoConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(65, TimeUnit.SECONDS)
                .writeTimeout(65, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .hostnameVerifier((s, sslSession) -> true)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES));
        return builder.build();
    }

    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            public void checkClientTrusted(
                    X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    X509Certificate[] certs, String authType) {
            }
        };
    }

    @Bean
    public SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{x509TrustManager()}, new java.security.SecureRandom());
            return sc.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
