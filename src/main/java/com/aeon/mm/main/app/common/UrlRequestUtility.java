package com.aeon.mm.main.app.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class UrlRequestUtility {

    public static final int CONNECTION_TIMEOUT = 180000;
    public static final int TIMER = 180000;

    public static String createPostParams(Object params){
       if((params instanceof String)){
            return params.toString();
        }
        if(!(params instanceof Map)){
            return null;
        }
        return params.toString();
        
        /*//HttpClient builder = HttpClientBuilder.create().build();
        //Builder builder = new Builder();
        
        URIBuilder builder = new URIBuilder();
        
        HashMap hashMap = (HashMap)params;
        if(hashMap == null){
            return null;
        }
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            builder.appendQueryParameter(entry.getKey().toString(),entry.getValue().toString());
            iterator.remove();
        }
        return builder.build().getEncodedQuery();*/
    }

    public static StringBuffer createRequestBody(String method, URL url, String mimeType, String body, String accessToken) throws IOException{
        HttpURLConnection urlConnection = null;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        urlConnection.setReadTimeout(TIMER);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(method);
        urlConnection.setRequestProperty("Content-Type",mimeType);

        OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
        writer.write(body);
        writer.flush();
        writer.close();
        outputStream.close();
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null){
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true){
            String line = reader.readLine();
            if(line == null){
                break;
            }
            buffer.append(line + "\n");
        }
        if(buffer.length() ==0){
            return null;
        }
        if(urlConnection == null){
            return buffer;
        }
        urlConnection.disconnect();
        return buffer;
    }
}
