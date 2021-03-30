package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Widget
    public TextView t;
    public TextView motd;
    public TextView wt_Date, wt_SidoName, wt_No2, wt_O3, wt_PM10, wt_PM25, wt_So2, wt_Co;
    public TextView wt_Score, wt_Cause;
    public TextView wt_max, wt_min;
    public TextView wt_max0, wt_min0;
    public TextView wt_max1, wt_min1;
    public ImageView btn_share, btn_refresh, btn_select;

    // Current Status
    private String sidoName;
    private String cityName;
    private DataArp dp;

    // Process Class
    private OParser ps;

    // Const
    private final String sfname = "district";
    private final int OPARSER_OK = 12;

    private final int SELECT_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.onMultiWindowModeChanged(true);

        // TextView
        motd = (TextView) findViewById(R.id.motd);
        t = (TextView) findViewById(R.id.text);
        wt_Date = (TextView) findViewById(R.id.t_Data1);
        wt_SidoName = (TextView) findViewById(R.id.t_SidoName1);
        wt_No2 = (TextView) findViewById(R.id.t_no1);
        wt_O3 = (TextView) findViewById(R.id.t_o31);
        wt_PM10 = (TextView) findViewById(R.id.t_pm101);
        wt_PM25 = (TextView) findViewById(R.id.t_pm251);
        wt_So2 = (TextView) findViewById(R.id.t_so21);
        wt_Co = (TextView) findViewById(R.id.t_co1);
        wt_Score = (TextView) findViewById(R.id.t_score);
        wt_Cause = (TextView) findViewById(R.id.t_cause);

        wt_min = (TextView) findViewById(R.id.t_min);
        wt_max = (TextView) findViewById(R.id.t_max);
        wt_min0 = (TextView) findViewById(R.id.t_min0);
        wt_max0 = (TextView) findViewById(R.id.t_max0);
        wt_min1 = (TextView) findViewById(R.id.t_min1);
        wt_max1 = (TextView) findViewById(R.id.t_max1);

        // Button
        btn_share = (ImageView) findViewById(R.id.btn_share);
        btn_refresh = (ImageView) findViewById(R.id.btn_share);
        btn_select = (ImageView) findViewById(R.id.btn_select);

        // Load Cache
        SharedPreferences sf = getSharedPreferences(sfname, 0);
        sidoName = sf.getString("sido", "경기");
        cityName = sf.getString("city", "성남시");

        ps = new OParser(hd);
        ps.ready(sidoName, cityName);

        // 재탐색 버튼
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ps.ready(sidoName, cityName);
            }
        });

        // 선택 버튼
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                intent.putExtra("Names", ps.getAllNames());
                startActivityForResult(intent, SELECT_ACTIVITY);
                overridePendingTransition(R.anim.toright, R.anim.goleft);
            }
        });

        // 공유기능 버튼
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                int score = dp.score();
                String ment = getString(R.string.app_name) + ": 오늘의 " + dp.getSidoName()
                        + " " + dp.getCityName() + " 일대의 미세먼지 점수는 " + dp.score() + "점이래요. ";
                if(score <= 50)
                    ment += "쾌적한 공기를 마셔요~";
                else if(score <= 100)
                    ment += "그럭저럭 괜찮지만 목이 아프다면 주의하세요!";
                else if(score <= 150)
                    ment += "외출할 때는 미세먼지 마스크를 착용하세요.";
                else
                    ment += "외출을 자제하고 공기청정기를 사용하세요!!!";
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, ment);

                Intent chooser = Intent.createChooser(intent, "주변인들에게 오늘 상황을 알려주세요.");
                startActivity(chooser);
            }
        });
    }

    Handler hd = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(this.obtainMessage(12) != null)
                view();
            else if(this.obtainMessage(13) != null)
                viewToast("갱신에 실패했습니다.");
            else if(this.obtainMessage(14) != null)
                viewToast("처리중입니다...");
            else if(this.obtainMessage(15) != null)
                viewToast("존재하지 않는 지명을 입력했습니다.");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_ACTIVITY && resultCode == 1)
        {
            String select = data.getStringExtra("Selected");
            sidoName = select.substring(0, 2);
            cityName = select.substring(3);
            ps.ready(sidoName, cityName);
        }
    }

    private void view()
    {
        dp = ps.finished();
        if(dp == null) return;
        wt_SidoName.setText(dp.getSidoName() + " " + dp.getCityName());
        wt_Date.setText(dp.getTime());
        wt_Co.setText("" + ((dp.getCo()>=0)?dp.getCo():"정보없음"));
        wt_So2.setText("" + ((dp.getSo2()>=0)?dp.getSo2():"정보없음"));
        wt_No2.setText("" + ((dp.getNo2()>=0)?dp.getNo2():"정보없음"));
        wt_O3.setText("" + ((dp.geto3()>=0)?dp.geto3():"정보없음"));
        wt_PM10.setText("" + ((dp.getPm10()>=0)?dp.getPm10():"정보없음"));
        wt_PM25.setText("" + ((dp.getPm25()>=0)?dp.getPm25():"정보없음"));
        int score = dp.score();
        wt_Score.setText(""+score);
        wt_Cause.setText(dp.cause());
        if(score<=50)
            motd.setText("와우! 오늘은 쾌적한 하루~");
        else if(score<=100)
            motd.setText("그럭저럭 버틸만 해.");
        else if(score<=150)
            motd.setText("마스크를 꼭 챙겨야겠어...");
        else
            motd.setText("최악의 날... 외출을 자제해!");
        save();

        DataArp max = ps.getMax(sidoName);
        DataArp min = ps.getMin(sidoName);

        if(max != null && min != null)
        {
            wt_min.setText(sidoName + "min");
            wt_max.setText(sidoName + "max");
            wt_max0.setText(max.getCityName());
            wt_min0.setText(min.getCityName());
            wt_max1.setText("" + max.score());
            wt_min1.setText("" + min.score());
        }

    }

    private void save()
    {
        SharedPreferences sf = getSharedPreferences(sfname, 0);
        SharedPreferences.Editor sfedit = sf.edit();
        sfedit.putString("sido", dp.getSidoName());
        sfedit.putString("city", dp.getCityName());
        sfedit.commit();
    }

    private void viewToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
