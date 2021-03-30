package com.example.myapplication;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GSONParser {
    private JsonParser jsonParser = new JsonParser();
    private JsonObject jo[];

    public GSONParser()
    {

    }

    public float getCo(int index) {
        try
        {
            return Float.parseFloat(Util.cutChar(jo[index].get("coValue").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public float getNo2(int index) {
        try
        {
            return Float.parseFloat(Util.cutChar(jo[index].get("no2Value").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public float getO3(int index) {
        try
        {
            return Float.parseFloat(Util.cutChar(jo[index].get("o3Value").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public float getSo2(int index) {
        try
        {
            return Float.parseFloat(Util.cutChar(jo[index].get("so2Value").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public short getPm10(int index) {
        try
        {
            return (short)Integer.parseInt(Util.cutChar(jo[index].get("pm10Value").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public short getPm25(int index) {
        try
        {
            return (short)Integer.parseInt(Util.cutChar(jo[index].get("pm25Value").toString(), '"'));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public String getCityName(int index) {
        try
        {
            return Util.cutChar(jo[index].get("cityName").toString(), '"');
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public String getSidoName(int index)
    {
        try
        {
            return Util.cutChar(jo[index].get("sidoName").toString(), '"');
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public String getDt(int index)
    {
        try
        {
            return Util.cutChar(jo[index].get("dataTime").toString(), '"');
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public int length()
    {
        return jo.length;
    }

    public void parser(String json)
    {
        JsonObject je = (JsonObject)jsonParser.parse(json);
        JsonArray ja1 = je.getAsJsonArray("list");

        jo = new JsonObject[ja1.size()];
        for(int i=0; i<ja1.size(); i++)
        {
            jo[i] = (JsonObject) ja1.get(i);
        }

    }

}

/*
  properties
  "_returnType":"json",
  "cityName":"가평군",
  "cityNameEng":"Gapyeong-gun",
  "coValue":"0.5",
  "dataGubun":"",
  "dataTime":"2019-04-04 10:00",
  "districtCode":"031",
  "districtNumSeq":"001",
  "itemCode":"",
  "khaiValue":"",
  "no2Value":"0.010",
  "numOfRows":"10",
  "o3Value":"0.033",
  "pageNo":"1",
  "pm10Value":"44",
  "pm25Value":"24",
  "resultCode":"",
  "resultMsg":"",
  "searchCondition":"",
  "serviceKey":"",
  "sidoName":"경기",
  "so2Value":"0.003",
  "totalCount":""


 */