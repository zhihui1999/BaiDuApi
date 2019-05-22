package cn.edu.caztc.baiduapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.aip.nlp.AipNlp;

import org.json.JSONArray;
import org.json.JSONObject;

import static cn.edu.caztc.baiduapi.demo.int1;
import static cn.edu.caztc.baiduapi.demo.int2;
import static cn.edu.caztc.baiduapi.demo.int3;

public class MainActivity extends AppCompatActivity {
    static String TAG="百度";
    static  int i=0;
    //设置APPID/AK/SK
    public static final String APP_ID = "APP_ID";
    public static final String API_KEY = "API_KEY";
    public static final String SECRET_KEY = "SECRET_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText=(EditText)findViewById(R.id.editText);
        Button button=(Button)findViewById(R.id.button);
        final TextView textView=(TextView)findViewById(R.id.textView);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String name=demo.getAuth();
//                Log.d(TAG, "run: "+name);
//
//            }
//        }).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String info=editText.getText().toString();
                        info=test(info);
                        info=json(info);
                        info=info.replaceAll("\t","\n");
                        textView.setText(info);
                    }
                }).start();
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                test();
//               // int1();
//                //poem poem=new poem(APP_ID,API_KEY,SECRET_KEY)
//            }
//        }).start();


    }


        public String test(String text) {
            // 初始化一个AipNlp
            //AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
            poem poem=new poem(APP_ID,API_KEY,SECRET_KEY);
            poem.setConnectionTimeoutInMillis(2000);
            poem.setSocketTimeoutInMillis(60000);
            // 可选：设置网络连接参数
            //client.setConnectionTimeoutInMillis(2000);
            //client.setSocketTimeoutInMillis(60000);

//            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//            client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//            client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
            // 调用接口
            //String text = "我是这个";
            JSONObject res = poem.poem(text,i,null);
            i++;
            Log.d(TAG, "test: "+res.toString());
            return res.toString();
        }


        String json(String jsonData){
            String content = null;
            try {
                //将json字符串jsonData装入JSON数组，即JSONArray
                //jsonData可以是从文件中读取，也可以从服务器端获得
                JSONObject jsonObject = new JSONObject(jsonData);
//                Log.d(TAG, "json: "+jsonObject.getString("error_code"));
                //jsonObject.getString("error_code");

                String poem = jsonObject.getString("poem");
                Log.d(TAG, "poem: "+poem);
                //message =jsonObject.getString("msg");
                JSONArray jsonArray = new JSONArray(poem);

                for (int i = 0; i< jsonArray.length(); i++) {
                    //循环遍历，依次取出JSONObject对象
                    //用getInt和getString方法取出对应键值
                     jsonObject = jsonArray.getJSONObject(i);
                     content = jsonObject.getString("content");
                    Log.d(TAG, "json: "+content);
                }
            } catch (Exception e) {
                Log.d("百度","title: " + e.getMessage());
                content=jsonData+"\n错误代码对照\n52007为输入文本包含政治&黄色内容\n52008为" +
                        "后台服务返回错误\n54003访问频率受限\n54102无写诗结果\n52001请求超时";
            }
            return content;
        }

}
