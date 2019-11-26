package com.business.yourtimes;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.business.yourtimes.News.NewsCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RequestHttpURLConnection {

    public String request(String _url, ContentValues __params,String postData) {
        HttpURLConnection urlConn = null;

        StringBuffer sbParams = new StringBuffer();

        try {
         URL url = new URL(_url);
         urlConn = (HttpURLConnection) url.openConnection();

         urlConn.setReadTimeout(10000);
         urlConn.setConnectTimeout(15000);
         urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : GET/POST.
         urlConn.setDoOutput(true);
         urlConn.setDoInput(true);
         urlConn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
         urlConn.setRequestProperty("Content-Type", "application/json");

         //PrintWriter pw = new PrintWriter(new OutputStreamWriter(urlConn.getOutputStream()));
         //w.write(sbParams.toString());
            OutputStream outputStream = urlConn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

         if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
             return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null) {
                page += line;
            }

            return page;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;
    }
}
