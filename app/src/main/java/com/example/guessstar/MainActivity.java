package com.example.guessstar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String sait="http://www.posh24.se/kandisar";
    private ImageView imageViewStar;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private ArrayList<String>names;
    private ArrayList<String>images;

    private int nubmerQueestion;
    private int numberOfRightAsk;
    private ArrayList<Button>buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewStar=(ImageView)findViewById(R.id.imageViewStar);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        names=new ArrayList<>();
        images=new ArrayList<>();
        buttons=new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        getContent();
        playGame();

    }
    private void getContent(){
        DownloadTask task=new DownloadTask();
        String start="<div class=\"articleContainer contentBlock \">";
        String finish="<div class=\"col-xs-12 col-sm-6 col-md-4\">";
        String result="";
        String splitContent="";

        try {
             result=task.execute(sait).get();
            Pattern pattern=Pattern.compile(start+"(.*?)"+finish);
            Matcher matcher=pattern.matcher(result);

            while(matcher.find()) {
                splitContent=matcher.group(1);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pattern patternImage=Pattern.compile("<img src=\"(.*?)\"");
        Matcher matcherImage=patternImage.matcher(splitContent);
        while(matcherImage.find()) {
            images.add(matcherImage.group(1));
        }
        Pattern patternName=Pattern.compile("alt=\"(.*?)\"/>");
        Matcher matcherName=patternName.matcher(splitContent);
        while (matcherName.find())
        {
            names.add(matcherName.group(1));
        }

    }
    private void playGame(){
    generateQuestion();
    DownloadImg img=new DownloadImg();
        try {
            Bitmap bitmap=img.execute(images.get(nubmerQueestion)).get();
            if(bitmap!=null){
                imageViewStar.setImageBitmap(bitmap);
                for (int i = 0; i <buttons.size() ; i++) {
                    if(i==numberOfRightAsk){
                        buttons.get(i).setText(names.get(nubmerQueestion));
                    }else{
                        int wrongAnswer=generateWrongAnswer();
                        buttons.get(i).setText(names.get(wrongAnswer));
                    }
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int generateWrongAnswer(){
        return (int)(Math.random()*names.size());
    }
    private void generateQuestion(){
    nubmerQueestion=(int)(Math.random()*names.size());
    numberOfRightAsk=(int)(Math.random()*buttons.size());
    }

    public void onClickAnswer(View view) {

        Button button=(Button)view;
        String tag=button.getTag().toString();
        if(Integer.parseInt(tag )==numberOfRightAsk){
            Toast.makeText(this, "Верный ответ!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Неверной правильный ответ: "+names.get(nubmerQueestion), Toast.LENGTH_SHORT).show();
        }
        playGame();
    }

    private static class DownloadTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            URL url=null;
            StringBuilder result=new StringBuilder();
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                BufferedReader bufferedReader=new BufferedReader(reader);
                String line=bufferedReader.readLine();
                while (line!=null){
                    result.append(line);
                    line=bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }

            return result.toString();
        }
    }
    private static class DownloadImg extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url=null;
            Bitmap img=null;
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                img= BitmapFactory.decodeStream(in);
                return img;
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
