package com.example.myapplication;

public class DataArp {
    private int dt_year;
    private int dt_month;
    private int dt_day;
    private int dt_hh;
    private int dt_mm;

    private String sidoName;
    private String cityName;

    private float no2;
    private float o3;
    private short pm10;
    private short pm25;
    private float so2;
    private float co;

    public DataArp(String sidoName, String cityName)
    {
        this.sidoName = sidoName;
        this.cityName = cityName;
    }

    public DataArp(String sidoName, String cityName, float no2, float o3, short pm10, short pm25, float so2, float co, String dt)
    {
        this.sidoName = sidoName;
        this.cityName = cityName;
        set(no2, o3, pm10, pm25, so2, co, dt);
    }

    public void set(float no2, float o3, short pm10, short pm25, float so2, float co, String dt)
    {
        this.no2 = no2;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.so2 = so2;
        this.co = co;
        dt_year = Integer.parseInt(dt.substring(0, 4));
        dt_month = Integer.parseInt(dt.substring(5, 7));
        dt_day = Integer.parseInt(dt.substring(8, 10));
        dt_hh = Integer.parseInt(dt.substring(11, 13));
        dt_mm = Integer.parseInt(dt.substring(14, 16));
    }

    public String getSidoName() {return sidoName;}
    public String getCityName() {return cityName;}
    public String getTime()
    {
        String result = "";
        result += dt_year + "-";
        result += dt_month + "-";
        result += dt_day + "-";
        /*if(dt_hh >= 0 && dt_hh < 12) result += "오전 " + ((dt_hh==0)?12:dt_hh) + "시 ";
        else if(dt_hh == 24) result += "오전 " + 12 + "시 ";
        else result += "오후 " + ((dt_hh==12)?12:dt_hh-12) + "시 ";*/
        if(dt_hh == 12)
            result += 12 + ":" + ((dt_mm < 10)?'0':"") + dt_mm + " PM";
        else if(dt_hh == 24)
            result += 12 + ":" + ((dt_mm < 10)?'0':"") + dt_mm + " AM";
        else if(dt_hh<12)
            result += dt_hh + ":" + ((dt_mm < 10)?'0':"") + dt_mm + " AM";
        else
            result += dt_hh-12 + ":" + ((dt_mm < 10)?'0':"") + dt_mm + " PM";
        return result;
    }
    public float getNo2() {return no2;}
    public float geto3() {return o3;}
    public short getPm10() {return pm10;}
    public short getPm25() {return pm25;}
    public float getSo2() {return so2;}
    public float getCo() {return co;}

    private int getNo2Score()
    {
        float c = no2;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 0.03)
        {
            bplow = 0;
            bphigh = 0.03;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 0.06)
        {
            bplow = 0.031;
            bphigh = 0.06;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 0.2)
        {
            bplow = 0.061;
            bphigh = 0.2;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 0.201;
            bphigh = 2;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    private int getO3Score()
    {
        float c = o3;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 0.03)
        {
            bplow = 0;
            bphigh = 0.03;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 0.09)
        {
            bplow = 0.031;
            bphigh = 0.09;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 0.15)
        {
            bplow = 0.091;
            bphigh = 0.15;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 0.151;
            bphigh = 0.6;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    private int getPm10Score()
    {
        float c = pm10;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 30)
        {
            bplow = 0;
            bphigh = 30;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 80)
        {
            bplow = 31;
            bphigh = 80;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 150)
        {
            bplow = 81;
            bphigh = 150;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 151;
            bphigh = 600;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    private int getPm25Score()
    {
        float c = pm25;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 15)
        {
            bplow = 0;
            bphigh = 15;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 35)
        {
            bplow = 16;
            bphigh = 35;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 75)
        {
            bplow = 36;
            bphigh = 75;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 76;
            bphigh = 500;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    private int getSo2Score()
    {
        float c = so2;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 0.02)
        {
            bplow = 0;
            bphigh = 0.02;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 0.05)
        {
            bplow = 0.021;
            bphigh = 0.05;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 0.15)
        {
            bplow = 0.051;
            bphigh = 0.15;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 0.151;
            bphigh = 1;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    private int getCoScore()
    {
        float c = co;
        double bphigh, bplow;
        int ihigh, ilow;

        if(c <= 2)
        {
            bplow = 0;
            bphigh = 2;
            ilow = 0;
            ihigh = 50;
        }
        else if(c <= 9)
        {
            bplow = 2.01;
            bphigh = 9;
            ilow = 51;
            ihigh = 100;
        }
        else if(c <= 15)
        {
            bplow = 9.01;
            bphigh = 15;
            ilow = 101;
            ihigh = 250;
        }
        else
        {
            bplow = 15.01;
            bphigh = 50;
            ilow = 251;
            ihigh = 500;
        }

        return (int)((ihigh - ilow) / (bphigh - bplow) * (c - bplow) + ilow);
    }

    public int score()
    {
        int count = 0;
        int max = 0;
        int scores[] = {getNo2Score(), getO3Score(), getPm10Score(), getPm25Score(), getSo2Score(), getCoScore()};
        int score_result = 0;
        for(int i=0; i<scores.length; i++)
        {
            if(scores[i]>100)
                count++;
            if(max < scores[i])
                max = scores[i];
        }
        if(count == 2)
            score_result = max + 50;
        else if(count == 3)
            score_result = max + 75;
        else
            score_result = max;
        return score_result;
    }

    public String cause()
    {
        String types[] = {"이산화질소", "오존", "미세먼지", "초미세먼지", "아황산가스", "일산화탄소"};
        int scores[] = {getNo2Score(), getO3Score(), getPm10Score(), getPm25Score(), getSo2Score(), getCoScore()};
        int max = 0;
        int index = 0;
        for(int i=0; i<scores.length; i++)
        {
            if(max < scores[i])
            {
                max = scores[i];
                index = i;
            }

        }
        return types[index];
    }



}
