package cn.edu.caztc.baiduapi;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class demo {

    static String TAG = "百度";
    public static HttpURLConnection connection;

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "d53kPiooV0lEmeBeWaptIHWU";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "jknvPqWojkK4QdRPEysKrlz8kSSEEgRj";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            Log.d(TAG, "getAuth: " + result);
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            Log.d(TAG, "getAuth: " + System.err);
            e.printStackTrace(System.err);
        }
        return null;
    }


    public static void int1() {
        try {
            String data = "https://aip.baidubce.com/rpc/2.0/nlp/v1/poem";
            URL url = new URL(data);
            //HttpURLConnection
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);//表示从服务器获取数据
            connection.setDoOutput(true);//表示向服务器写数据
            //获得上传信息的字节大小以及长度
            connection.setRequestMethod("POST");
            //是否使用缓存
            connection.setUseCaches(false);
            //表示设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
if (connection==null)
    Log.d(TAG, "int1: kong");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "int1: "+e.getLocalizedMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, "int1: "+e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "int1: "+e.getLocalizedMessage());
            e.printStackTrace();
        }

        int2();
    }

    public static void int2() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", "24.298fa70180de2bf8a07d171711e25f85.2592000.1561023403.282335-15926335");
        params.put("text", "百度翻译");
        StringBuffer buffer = new StringBuffer();
        try {//把请求的主体写入正文！！
            if (params != null && !params.isEmpty()) {
//迭代器
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buffer.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(), "GBK")).
                            append("&");
                }
            }
//   System.out.println(buffer.toString());
            //Log.d(TAG, "int2:1 "+buffer.toString());
            //删除最后一个字符&，多了一个;主体设置完毕
            buffer.deleteCharAt(buffer.length() - 1);
            //Log.d(TAG, "int2:2 "+buffer.toString());
            byte[] mydata = buffer.toString().getBytes();
            //Log.d(TAG, "int2:3 "+buffer.toString().getBytes());
//获得输出流，向服务器输出数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata, 0, mydata.length);
            //Log.d(TAG, "int2: "+mydata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int3();
    }

    public static void int3() {
        //通常叫做内存流，写在内存中的
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    data.toString();
                    outputStream.write(data, 0, len);
                }
//result是在服务器端设置的doPost函数中的
                result = new String(outputStream.toByteArray(), "GBK");
                outputStream.flush();
                Log.d(TAG, "int3: "+result);
            } catch (IOException e) {
                Log.d(TAG, "int3: "+e.getLocalizedMessage());
            }

        }
    }
}