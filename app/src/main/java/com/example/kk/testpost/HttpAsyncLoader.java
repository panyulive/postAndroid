package com.example.kk.testpost;

import java.io.File;
import java.io.FileWriter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class HttpAsyncLoader extends AsyncTaskLoader<String> {
    private String url = null;
    private String id = null;

    public HttpAsyncLoader(Context context, String url, String id) {
        super(context);
        this.url = url;
        this.id = id;

    }

    @Override
    public String loadInBackground() {
        String pd = "成功";
        try {
            Log.d("START", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            HttpPost method = new HttpPost(url);

            DefaultHttpClient client = new DefaultHttpClient();

            // POST データの設定
            StringEntity paramEntity = new StringEntity(id);
            paramEntity.setChunked(false);
            paramEntity.setContentType("application/x-www-form-urlencoded");
            method.setEntity(paramEntity);

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            Log.d("HttpStatus", String.valueOf(status));
            if (status != HttpStatus.SC_OK) {
                throw new Exception("");
            }
            else {
                //ファイルの書き込み
                File file1 = new File(Environment.getExternalStorageDirectory(), "image.jpg");
                FileWriter fw1 = new FileWriter(file1);
                fw1.write(EntityUtils.toString(response.getEntity()));
                fw1.close();
            }
        } catch (Exception e) {
            Log.d("POSTERROR", e.toString());
            return null;
        }
        return pd;
    }

    @Override
    protected void onStartLoading() {
        // TODO 自動生成されたメソッド・スタブ
        forceLoad();
    }
}