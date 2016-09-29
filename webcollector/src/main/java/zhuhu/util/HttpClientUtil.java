package zhuhu.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {
    private static final Log logger = LogFactory.getLog(HttpClientUtil.class);

    /**
     * @param httpClient     HttpClient客户端
     * @param context        上下文
     * @param request        请求
     * @param encoding       字符编码
     * @param isPrintConsole 是否打印到控制台
     * @return 网页内容
     */
    public static String getWebPage(CloseableHttpClient httpClient, HttpClientContext context, HttpRequestBase request, String encoding, boolean isPrintConsole) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request, context);
        } catch (HttpHostConnectException e) {
            logger.error("HttpHostConnectException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        System.out.println("http status:" + response.getStatusLine().getStatusCode());
        BufferedReader rd = null;
        StringBuilder webPage = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
            String line = "";
            webPage = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                webPage.append(line);
                if (isPrintConsole) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.releaseConnection();
        return webPage.toString();
    }

    /**
     * 设置Cookies策略
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getMyHttpClient() {
        CloseableHttpClient httpClient = null;
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        return httpClient;
    }

    /**
     * 设置上下文
     *
     * @return HttpClientContext
     */
    public static HttpClientContext getMyHttpClientContext() {
        HttpClientContext context = null;
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        return context;
    }

    /**
     * 下载图片
     *
     * @param fileURL       文件地址
     * @param path          保存路径
     * @param saveFileName  文件名，包括后缀名
     * @param isReplaceFile 若存在文件时，是否还需要下载文件
     */
    public static void downloadFile(CloseableHttpClient httpClient, HttpClientContext context, String fileURL, String path, String saveFileName, Boolean isReplaceFile) {
        try {
            HttpGet request = new HttpGet(fileURL);
            CloseableHttpResponse response = httpClient.execute(request, context);
            System.out.println("status:" + response.getStatusLine().getStatusCode());
            File file = new File(path);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                //System.out.println("//不存在");
                file.mkdirs();
            } else {
                System.out.println("//目录存在");
            }
            file = new File(path + saveFileName);
            if (!file.exists() || isReplaceFile) {
                //如果文件不存在，则下载
                try {
                    OutputStream os = new FileOutputStream(file);
                    InputStream is = response.getEntity().getContent();
                    byte[] buff = new byte[(int) response.getEntity().getContentLength()];
                    while (true) {
                        int readed = is.read(buff);
                        if (readed == -1) {
                            break;
                        }
                        byte[] temp = new byte[readed];
                        System.arraycopy(buff, 0, temp, 0, readed);
                        os.write(temp);
                        System.out.println("文件下载中....");
                    }
                    is.close();
                    os.close();
                    System.out.println(fileURL + "文件下载成功");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println(path);
                System.out.println("该文件存在！");
            }
            request.releaseConnection();
        } catch (IllegalArgumentException e) {
            System.out.println("连接超时...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 输出Cookies
     *
     * @param cs
     */
    public static void getCookies(CookieStore cs) {
        List<Cookie> cookies = cs.getCookies();
        if (cookies == null) {
            System.out.println("该CookiesStore无Cookie");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                Cookie cookie = cookies.get(i);
                System.out.println("cookie：" + cookie.getName() + ":" + cookie.getValue());
                System.out.println("过期时间:" + cookie.getExpiryDate() + ",Comment" + cookie.getComment() + ",CommentURL" + cookie.getCommentURL() + ",domain" + cookie.getDomain() + ",ports" + cookie.getPorts()
                );
            }
        }
    }

    public static void getAllHeaders(Header[] headers) {
        System.out.println("Headers：");
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i]);
        }
    }

    /**
     * InputStream转换为String
     *
     * @param is
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String isToString(InputStream is, String encoding) throws Exception {
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, encoding);
        return writer.toString();
    }

    /**
     * unicode转化String
     *
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 设置request请求参数
     *
     * @param request
     * @param params
     */
    public static void setHttpPostParams(HttpPost request, Map<String, String> params) throws UnsupportedEncodingException {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            formParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "utf-8");
        request.setEntity(entity);
    }


    /**
     * 序列化对象
     *
     * @param object
     * @throws Exception
     */
    public static void serialize(Object object, String filePath) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            fos.close();
            oos.close();
            logger.info("序列化成功！");
        } catch (FileNotFoundException e) {
            logger.error("序列化失败！", e);
        } catch (IOException e) {
            logger.error("序列化失败！", e);
        }
    }

    /**
     * 反序列化对象
     *
     * @param filePath
     * @throws Exception
     */
    public static Object deserialize(String filePath) {
        InputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        try {
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            logger.error("反序列化失败！", e);
        } catch (IOException e) {
            logger.error("反序列化失败！", e);
        } catch (ClassNotFoundException e) {
            logger.error("反序列化失败！", e);
        } catch (NullPointerException e) {
            logger.error("反序列化失败！", e);
        }
        logger.error("反序列化成功！");
        return object;
    }


}
