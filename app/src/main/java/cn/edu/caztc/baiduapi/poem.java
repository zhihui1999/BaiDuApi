package cn.edu.caztc.baiduapi;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;

import org.json.JSONObject;

import java.util.HashMap;


public class poem extends BaseClient {



    protected poem(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    public JSONObject poem(String text,int index, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        request.addBody("text", text);
        request.addBody("index",index);
        if (options != null) {
            request.addBody(options);
        }

        request.setUri("https://aip.baidubce.com/rpc/2.0/nlp/v1/poem");
        request.addHeader("Content-Encoding", "UTF-8");
        request.addHeader("Content-Type", "application/json");
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        this.postOperation(request);
        return this.requestServer(request);
    }




}
