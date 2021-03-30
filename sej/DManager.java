package com.example.myapplication;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DManager {
    private ArrayList<ArrayList<DataArp>> dat0;
    private String names[][] = {
            {"서울", "강남구", "강서구", "관악구", "광진구", "구로구",
                    "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
                    "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"},
            {"부산", "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구",
                    "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"},
            {"경기", "가평군", "고양시", "과천시", "광명시", "광명시",
                    "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시",
                    "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군", "오산시",
                    "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"},
            {"대구", "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"},
            {"인천", "강화군", "계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구"},
            {"광주", "광산구", "남구", "동구", "북구", "서구"},
            {"대전", "대덕구", "동구", "서구", "유성구", "중구"},
            {"울산", "남구", "동구", "북구", "울주군", "중구"},
            {"강원", "강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군",
                    "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군",
                    "화천군", "횡성군"},
            {"충북", "괴산군", "단양군", "보은군", "명동군", "옥천군", "음성군", "제천시", "진천군",
                    "청주시", "충주시", "증평군"},
            {"충남", "금산군", "논산시", "당진군", "보령시", "부여군", "서산시", "서천군", "아산시",
                    "예산군", "천안시", "청양군", "태안군", "홍성군", "계룡시"},
            {"전북", "고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군",
                    "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"},
            {"전남", "고흥군", "광양시", "나주시", "담양군", "목포시", "무안군", "순천시", "신안군",
                    "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군",
                    "해남군", "화순군"},
            {"경북", "경산시", "경주시", "구미시", "김천시", "상주시", "안동시", "영주시", "영천시",
                    "칠곡군", "포항시"},
            {"경남", "거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "양산시",
                    "진주시", "창원시", "통영시", "하동군", "함안군", "함양군"},
            {"제주", "서귀포시", "제주시"},
            {"세종", "세종시"}
    };

    public DManager() {
        dat0 = new ArrayList<ArrayList<DataArp>>();
        for (int i = 0; i < names.length; i++) {
            String nm[] = names[i];
            ArrayList<DataArp> dat1 = new ArrayList<DataArp>();
            for (int j = 1; j < nm.length; j++) {
                DataArp dat = new DataArp(nm[0], nm[j]);
                dat1.add(dat);
            }
            dat0.add(dat1);
        }
    }

    private DataArp getDepth2(ArrayList<DataArp> depth1, String cityName)
    {
        if(depth1 != null)
        {
            for(int i=0; i<depth1.size(); i++)
            {
                DataArp dat = depth1.get(i);
                if(Util.equalStringBytes(dat.getCityName(), cityName))
                    return dat;
            }
        }
        return null;
    }

    private ArrayList<DataArp> getDepth1(String sidoName)
    {
        for(int i=0; i<dat0.size(); i++)
        {
            DataArp dat = dat0.get(i).get(0);
            if(Util.equalStringBytes(dat.getSidoName(), sidoName))
                return dat0.get(i);
        }
        return null;
    }

    public String[] getDepth2Names(String sidoName)
    {
        ArrayList<DataArp> depth1 = getDepth1(sidoName);
        String[] strarray = new String[depth1.size()];
        for(int i=0; i<depth1.size(); i++)
        {
            strarray[i] = depth1.get(i).getCityName();
        }
        return strarray;
    }

    public String[] getDepth1Names()
    {
        String[] strarray = new String[dat0.size()];
        for(int i=0; i<dat0.size(); i++)
        {
            strarray[i] = dat0.get(i).get(0).getSidoName();
        }
        return strarray;
    }

    public DataArp getData(String sidoName, String cityName)
    {
        return getDepth2(getDepth1(sidoName), cityName);
    }

    public void update(String sidoName, String cityName, float no2, float o3, short pm10, short pm25, float so2, float co, String dt)
    {
        DataArp dat = getDepth2(getDepth1(sidoName), cityName);
        if(dat != null) dat.set(no2, o3, pm10, pm25, so2, co, dt);
    }

    public String[][] getNames() {
        return names;
    }

    public DataArp getMax(String sidoName)
    {
        ArrayList<DataArp> list = getDepth1(sidoName);
        DataArp max;

        max = list.get(0);
        for (int i=1; i<list.size(); i++)
        {
            if(max.score() < list.get(i).score())
                max = list.get(i);
        }
        return max;
    }

    public DataArp getMin(String sidoName)
    {
        ArrayList<DataArp> list = getDepth1(sidoName);
        DataArp min;

        min = list.get(0);
        for (int i=1; i<list.size(); i++)
        {
            if(min.score() > list.get(i).score())
                min = list.get(i);
        }
        return min;
    }

    public ArrayList<String> getAllNames()
    {
        ArrayList<String> allNames = new ArrayList<String>();
        String[] sidoNames = getDepth1Names();
        String[] cityNames;

        for (int i=0; i<sidoNames.length; i++)
        {
            cityNames = getDepth2Names(sidoNames[i]);
            for (int j=0; j<cityNames.length; j++)
                allNames.add(sidoNames[i] + ' ' + cityNames[j]);
        }
        return allNames;
    }
}
