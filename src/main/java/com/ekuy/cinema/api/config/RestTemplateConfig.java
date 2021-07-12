package com.ekuy.cinema.api.config;

import com.ekuy.cinema.api.util.ConfigUtil;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description: V-自定义restTemplate模板
 */
@SuppressWarnings("all")
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        // 设置编码格式为UTF-8
        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        httpMessageConverters.stream().forEach(httpMessageConverter -> {
            if(httpMessageConverter instanceof StringHttpMessageConverter){
                StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        int maxTotal = Integer.parseInt(ConfigUtil.get("http.pool.maxTotal"));
        int maxPerRoute = Integer.parseInt(ConfigUtil.get("http.pool.maxPerRoute"));
        int socketTimeOut = Integer.parseInt(ConfigUtil.get("http.pool.socketTimeout"));
        int connectTimeOut = Integer.parseInt(ConfigUtil.get("http.pool.connectTimeout"));
        int connectionRequestTimeout = Integer.parseInt(ConfigUtil.get("http.pool.connectionRequestTimeout"));
//        int maxTotal = 100;
//        int maxPerRoute = 2000;
//        int socketTimeOut = 5000;
//        int connectTimeOut = 5000;
//        int connectionRequestTimeout = 10000;

        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(maxTotal);
        // 可为每个域名设置单独的连接池数量，特殊配置，针对域名或者指定ip配置池大小
//        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("10.66.224.52")), 50);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        //SocketTimeout:服务器返回数据(response)的时间，超过该时间抛出read timeout
        //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
        //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeOut)
                .setConnectTimeout(connectTimeOut)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        // 重试处理器，StandardHttpRequestRetryHandler这个是官方提供的，看了下感觉比较挫，很多错误不能重试，可自己实现HttpRequestRetryHandler接口去做
        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setRetryHandler(retryHandler)
                .build();
    }
}
