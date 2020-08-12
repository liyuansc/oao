package com.oao.api.config;

import lombok.SneakyThrows;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class OaoOkHttpClientAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient(SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager, ConnectionPool connectionPool) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(65, TimeUnit.SECONDS)
//                .writeTimeout(65, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSocketFactory, x509TrustManager)
                .hostnameVerifier((s, sslSession) -> true)
                .connectionPool(connectionPool);
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPool OkHttpClientConnectionPool() {
        return new ConnectionPool(200, 900, TimeUnit.SECONDS);
    }

    @Bean
    @ConditionalOnMissingBean
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

    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean
    public SSLSocketFactory sslSocketFactory(X509TrustManager x509TrustManager) {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{x509TrustManager}, new java.security.SecureRandom());
        return sc.getSocketFactory();
    }
}
