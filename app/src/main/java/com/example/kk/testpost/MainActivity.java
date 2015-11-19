package com.example.kk.testpost;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

public class MainActivity extends Activity implements LoaderCallbacks<String> {

    private static final int ADDRESSLOADER_ID = 0;
    private String param = "id=37";
    final String url = "http://192.168.100.135/api/v3/image/download";
    private  TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.progressTextView);
        File file = new File(Environment.getExternalStorageDirectory(), "AEDMap~HandS~");
        if (file.exists() == false) {
            file.mkdir();
        }
        getLoaderManager().restartLoader(ADDRESSLOADER_ID, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {

        return new HttpAsyncLoader(this, url, param);

    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if(data != null){
            //JPEGに変換
       //     File file = new File(Environment.getExternalStorageDirectory(), "image.txt");
       //     String fileName = file.getName();
       //     fileName = file.getParent() + File.separator + fileName.replaceFirst("\\..*", ".jpg");
       //     if(file.renameTo(new File(fileName))) {
                textView.setText(data);
        //    }
        }
        else{
            textView.setText("失敗");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // TODO 自動生成されたメソッド・スタブ
    }

    public boolean mkdir(String path) {
        File file = new File(path);
        return file.mkdir();
    }
}
