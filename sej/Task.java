package com.example.myapplication;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Task {

    private String err = "";
    private final int numOfRows = 50;
    private final int pageNo = 1;
    private String sidoName = "경기";
    private final String domain = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/";
    private final String searchCondition = "HOUR";
    private final String serviceKey = "<insert your service key>";

    private ArrayList<String> parameterElement = new ArrayList<String>();
    private ArrayList<String> parameterName = new ArrayList<String>();
    private HttpURLConnection conn;

    public String getErr()
    {
        return err;
    }

    public void addParameter(String name, String element)
    {
        parameterName.add(name);
        parameterElement.add(element);
    }

    private String getParameters()
    {
        String parameters = "";
        for(int i=0; i<parameterName.size(); i++)
        {
            parameters += parameterName.get(i);
            parameters += '=';
            parameters += parameterElement.get(i);
            if(i+1 != parameterName.size()) parameters += '&';
        }
        return parameters;
    }


    public String send(String sidoName)
    {
        this.sidoName = sidoName;
        String receive = "";
        int code = 0;
        try
        {
            URL url = new URL(domain + makeRequestParameter() + "&_returnType=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            code = conn.getResponseCode();
            if(code == conn.HTTP_OK)
            {
                String str;
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while((str = reader.readLine()) != null )
                {
                    buffer.append(str);
                }
                receive = buffer.toString();

                reader.close();
                conn.disconnect();
            }
            else
            {
                receive = "" + conn.getResponseCode() + "is not 200.";
                throw new NetworkErrorException("HTTP status is not 200");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("Task:Code", code+"");
        Log.i("Task:String", receive);
        return receive;
    }

    public String sendPost(String url)
    {
        String line;
        String page = "";

        try{
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(getParameters().getBytes("UTF-8")); // 출력 스트림에 출력.
            os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((line = reader.readLine()) != null)
                    page += line;
            }
        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return page;
    }

    private String makeRequestParameter()
    {
        String utf8sido;
        try
        {
            utf8sido = URLEncoder.encode(sidoName, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            utf8sido = sidoName;
        }


        String result = "getCtprvnMesureSidoLIst?";
        result += "serviceKey=" + serviceKey + "&";
        result += "numOfRows=" + numOfRows + "&";
        result += "pageNo=" + pageNo + "&";
        result += "sidoName=" + utf8sido + "&";
        result += "searchCondition=" + searchCondition;
        return result;

    }
}

//https://mailmail.tistory.com/13
