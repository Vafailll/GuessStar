package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private String url="https://avatars.mds.yandex.net/get-pdb/27625/dadab4fe-faa0-4644-9ddf-3338bf9b833a/s1200";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
    }

    public void onClickDowloadImage(View view) {
        DownloadImageTask task=new DownloadImageTask();
        Bitmap bitmap=null;
        try {
            bitmap=task.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }
    private static class DownloadImageTask extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url=null;
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null)
                urlConnection.disconnect();
            }
            return null;
        }
    }
}
