package info.tutsmodel.requestjson;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by locnt_000 on 8/28/2015.
 */
public class FetchJSON {
    String strUrl = "";
    String string = "";
    private volatile boolean isReading = true;

    public FetchJSON(String url) {
        this.strUrl = url;
    }

    public JSONArray fetchJSON() throws JSONException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream is = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strUrl);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    is = conn.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        string += line;
                    }
                    isReading = false;
                    Log.d("fetch", "finish");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        thread.start();
        while (isReading) ;

        /*
        Nếu JSON cấu trúc
        [
            {
                "name":"NTL",
                "age":20
                "class":[{},{}]
            },
            {
                "name":"tutsmodel.info",
                "age":1
                "class":[{},{}]
            }
        ]
        thì "return new JSONArray(string);"
        ////////////////////
        Nếu JSON dạng cấu trúc
        {
            "name":"NTL",
            "age":20
            "class":[{},{}]
        }
        thì thay đổi thành "return new JSONObject(string);"
         */
        return new JSONArray(string);
    }
}
