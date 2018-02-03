package com.adatafun.base.data.center.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpclient 辅助类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * <p>
 * 2017/5/8 9:36
 *
 * @author tiecheng
 * @version beta1.0
 */
public class HttpClientUtils {

    public static final String POST_METHOD = "POST";

    public static final String GET_METHOD = "GET";

    public static final String JSON_DATA = "json";

    private static PoolingHttpClientConnectionManager pool = null;

    private static CloseableHttpClient httpClient;

    private static SSLContextBuilder builder = null;

    private static SSLConnectionSocketFactory sslsf = null;

    static {
        pool = new PoolingHttpClientConnectionManager();
//        pool.setMaxTotal(20); // 总的并发量
//        pool.setDefaultMaxPerRoute(2); // 单个主机的并发量
//        httpClient = HttpClients.custom().setConnectionManager(pool).build();
        try {
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, new TrustManager[]{
//                    //证书信任管理器（用于https请求）
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] arg0,
//                                                       String arg1) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] arg0,
//                                                       String arg1) throws CertificateException {
//                        }
//
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//                    }
//            }, new SecureRandom());
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            //获取注册建造者
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            //注册http和https请求
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    registryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", sslsf)
                            .build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            //获取HttpClient池管理者
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 总的并发量
            pool.setMaxTotal(200);
            // 单个主机的并发量
            pool.setDefaultMaxPerRoute(20);
            //初始化httpClient
            httpClient = HttpClients.custom().
                    setConnectionManager(pool).
                    setSSLSocketFactory(sslsf).
                    setConnectionManagerShared(true).
                    setDefaultRequestConfig(requestConfig).
                    build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpClient createHttpClient() {
        if (httpClient == null) {
            return null;
        } else {
            return httpClient;
        }
    }

    /**
     * GET请求
     *
     * @param url    请求地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String httpGetForWebService(String url, Map<String, String> params) throws Exception {
        URI uri = getGetURI(url, getNameValuePairs(params));
        HttpGet get = new HttpGet(uri);
        return getWebServiceResultPro(get);
    }

    public static String httpGetForWebService(String url) throws Exception {
        URI uri = getGetURI(url);
        HttpGet get = new HttpGet(uri);
        return getWebServiceResultPro(get);
    }

    /**
     * GET请求
     *
     * @param scheme
     * @param host
     * @param path
     * @param params
     * @return
     * @throws Exception
     */
    public static String httpGetForWebService(String scheme, String host, String path, Map<String, String> params) throws Exception {
        HttpGet get = new HttpGet(getGetURI(scheme, host, path, getNameValuePairs(params)));
        return getWebServiceResultPro(get);
    }

    /**
     * GET请求
     *
     * @param scheme
     * @param host
     * @param path
     * @param nameValuePairs
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String httpGetForWebService(String scheme, String host, String path, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException {
        HttpGet get = new HttpGet(getGetURI(scheme, host, path, nameValuePairs));
        return getWebServiceResultPro(get);
    }

    /**
     * POST请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static String httpPostForWebService(String url, Map<String, String> params) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        return getWebServiceResult(post);
    }

    public static String httpPostForWebService(String url, Object object) throws Exception {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(JSON.toJSONString(object), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        return getWebServiceResultPro(post);
    }

    public static String httpPostForWebService(String url, Map<String, String> params, String type) throws Exception {
        HttpPost post = new HttpPost(url);
        if (JSON_DATA.equals(type)) {
            StringEntity entity = new StringEntity(getJsonParams(params).toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
        } else { // 表单形式 默认
            post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        }
        return getWebServiceResult(post);
    }

    /**
     * URL地址+内容请求，默认是JSON和UTF-8
     * {@link com.adatafun.base.data.center.util.HttpClientUtils#httpPostForWebService(URI, String, String, String)}
     */
    public static String httpPostForWebService(URI uri, String context) throws Exception {
        String charset = "UTF-8";
        String contentType = "application/json";
        return httpPostForWebService(uri, context, charset, contentType);
    }

    /**
     * URL地址+内容+响应编码，默认是JSON
     * {@link com.adatafun.base.data.center.util.HttpClientUtils#httpPostForWebService(URI, String, String, String)}
     */
    public static String httpPostForWebService(URI uri, String context, String charset) throws Exception {
        String contentType = "application/json";
        return httpPostForWebService(uri, context, charset, contentType);
    }

    /**
     * HttpClient发送POST请求
     *
     * @param uri         请求的地址
     * @param context     请求内容
     * @param charset     编码
     * @param contentType 请求头
     * @return
     * @throws Exception
     */
    public static String httpPostForWebService(URI uri, String context, String charset, String contentType) throws Exception {
        HttpPost post = new HttpPost(uri);
        StringEntity entity = new StringEntity(context, charset);
        entity.setContentEncoding(charset);
        entity.setContentType(contentType);
        post.setEntity(entity);
        return getWebServiceResultPro(post);
    }

    public static String httpPostForWebService(String scheme, String host, String path, Map<String, String> params) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme, host, path));
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        return getWebServiceResultPro(post);
    }

    public static String httpPostForWebService(String scheme, String host, String path, List<NameValuePair> nameValuePairs) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme, host, path));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
        return getWebServiceResultPro(post);
    }

    public static String httpPostForWebService(String scheme, String host, String path, Map<String, String> params, String code) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme, host, path));
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), code));
        return getWebServiceResultPro(post);
    }

    public static String httpPostForWebService(String scheme, String host, String path, List<NameValuePair> nameValuePairs, String code) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme, host, path));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, code));
        return getWebServiceResultPro(post);
    }

    private static String getWebServiceResult(HttpRequestBase httpRequestBase) {
        String result = "";
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase);
             InputStream inputStream = response.getEntity().getContent();
             ByteArrayOutputStream outSteam = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[2048];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outSteam.write(bytes, 0, len);
            }
            result = outSteam.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getWebServiceResult(HttpRequestBase httpRequestBase, String charset) {
        String result = "";
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase);
             InputStreamReader isr = new InputStreamReader(response.getEntity().getContent(), charset);
             OutputStreamWriter osw = new OutputStreamWriter(new ByteArrayOutputStream())) {
            char[] buf = new char[2048];
            int len;
            while ((len = isr.read(buf)) != -1) {
                osw.write(buf, 0, len);
            }
            result = osw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getWebServiceResultPro(HttpRequestBase httpRequestBase) {
        return getWebServiceResultPro(httpRequestBase, "UTF-8");
    }

    private static String getWebServiceResultPro(HttpRequestBase httpRequestBase, String charset) {
        String result = "";
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase)) {
            result = EntityUtils.toString(response.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static JSONObject getJsonParams(Map<String, String> params) {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }
        return object;
    }

    public static final List<NameValuePair> getNameValuePairs(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

    private static final URI getGetURI(String scheme, String host, String path, List<NameValuePair> nameValuePairs) throws URISyntaxException {
        return new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .setParameters(nameValuePairs)
                .build();
    }

    private static final URI getGetURI(String url) throws URISyntaxException {
        return new URIBuilder()
                .setPath(url)
                .build();
    }

    /**
     * @param scheme
     * @param host
     * @param path
     * @return
     * @throws URISyntaxException
     */
    private static final URI getPostURI(String scheme, String host, String path) throws URISyntaxException {
        return new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .build();
    }

    /**
     * @param url
     * @param nameValuePairs
     * @return
     * @throws URISyntaxException
     */
    public static final URI getGetURI(String url, List<NameValuePair> nameValuePairs) throws URISyntaxException {
        return new URIBuilder()
                .setPath(url)
                .setParameters(nameValuePairs)
                .build();
    }

    public static void main(String[] args) throws Exception {
        String flightJson = "{\"isStop\": 0, \"arrCity\": \"广州\", \"arrDate\": \"2018-01-04\", \"arrGate\": \"\", \"depCity\": \"曼谷\", \"depDate\": \"2018-01-04\", \"isShare\": 0, \"legFlag\": 1, \"flightId\": 6, \"flightNo\": \"CZ358\", \"isCustom\": 1, \"boardGate\": \"D1\", \"arrAirport\": \"广州白云\", \"boardState\": \"\", \"depAirport\": \"曼谷(素万那普)\", \"updateFlag\": \"0\", \"arrTerminal\": \"A\", \"arrTimeZone\": \"28800\", \"depTerminal\": \"T1\", \"depTimeZone\": \"25200\", \"flightState\": \"到达\", \"isNearOrFar\": 3, \"arrActualDate\": \"2018-01-04 15:52:00\", \"depActualDate\": \"2018-01-04 12:22:00\", \"shareFlightNo\": \"\", \"arrAirportCode\": \"CAN\", \"checkInCounter\": \"U,W\", \"depAirportCode\": \"BKK\", \"flightCategory\": 2, \"baggageCarousel\": \"\", \"arrEstimatedDate\": \"2018-01-04 15:42:00\", \"arrScheduledDate\": \"2018-01-04 15:50:00\", \"depEstimatedDate\": \"2018-01-04 12:00:00\", \"depScheduledDate\": \"2018-01-04 12:00:00\", \"airlineChineseName\": \"中国南方航空股份有限公司\"}";
        JSONObject object = JSONObject.parseObject(flightJson);
        String url = "https://api-demo.baiyunairport.top/fli/FlightInfoUpdate";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = client.execute(httpGet);
        System.out.println(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        String s = HttpClientUtils.httpPostForWebService(url, object);
        System.out.println(s);
    }

}
