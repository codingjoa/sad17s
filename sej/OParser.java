package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

public class OParser {
    private GSONParser dataParser;
    private Task dataCrawler;
    private DManager dataManager;
    private DataArp lastData;
    private boolean processLock = false;
    private Handler mainHandler;
    // Jan-Feb-Mar-Apr-May-Jun-Jul-Aug-Sep-Oct-Nov-Dec

    public OParser(Handler mainHandler)
    {
        dataParser = new GSONParser();
        dataCrawler = new Task();
        dataManager = new DManager();
        this.mainHandler = mainHandler;
    }

    private class Crawler extends Thread
    {
        private String sidoName;
        private String cityName;

        public void setSidoName(String sidoName, String cityName) {
            this.sidoName = sidoName;
            this.cityName = cityName;
        }

        private void crawling()
        {
            // 크롤링하세요
            try
            {
                String json = dataCrawler.send(sidoName);
                dataParser.parser(json);
                for (int i=0; i<dataParser.length(); i++)
                {
                    dataManager.update(dataParser.getSidoName(i), dataParser.getCityName(i),
                            dataParser.getNo2(i), dataParser.getO3(i), dataParser.getPm10(i),
                            dataParser.getPm25(i), dataParser.getSo2(i),
                            dataParser.getCo(i), dataParser.getDt(i));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();
            try {
                processLock = true;
                crawling();
                lastData = dataManager.getData(sidoName, cityName);
                mainHandler.sendEmptyMessage(12);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                processLock = false;
            }
        }
    }

    public DataArp getMax(String sidoName) { return dataManager.getMax(sidoName); }
    public DataArp getMin(String sidoName) { return dataManager.getMin(sidoName); }

    public DataArp finished()
    {
        return lastData;
    }

    public void ready(String sidoName, String cityName)
    {
        if(processLock)
        {
            mainHandler.sendEmptyMessage(14);
        }
        lastData = dataManager.getData(sidoName, cityName);
        if(lastData == null)
        {
            mainHandler.sendEmptyMessage(15);
        }
        else if(lastData.getPm25() == 0)
        {
            // 크롤링
            Crawler c = new Crawler();
            c.setSidoName(sidoName, cityName);
            c.start();
        }
        else
        {
            mainHandler.sendEmptyMessage(12);
        }
    }
    public ArrayList<String> getAllNames()
    {
        return dataManager.getAllNames();
    }
}
