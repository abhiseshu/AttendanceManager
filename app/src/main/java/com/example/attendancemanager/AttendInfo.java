package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendInfo extends AppCompatActivity {

    DbHelper dbHelper;
    int tpresent,ttotal;
    double mPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_info);
        dbHelper = new DbHelper(getApplicationContext());

        tpresent =0 ; ttotal = 0;
        TextView showPercent = (TextView) findViewById(R.id.percent_info_id);

        ArrayList<Word> arrayList = dbHelper.getSubjectList();
        for(int countr = 0;countr< arrayList.size(); countr++){
            Word word = arrayList.get(countr);
            tpresent = tpresent + Integer.parseInt(word.getwPresent());
            ttotal = ttotal + Integer.parseInt(word.getwTotal());
        }
        Log.i("log TTT","Present "+tpresent +" Total" +ttotal);
        mPercent = Math.round((double) tpresent/ttotal *10000)/100.0;
        showPercent.setText(mPercent+"%");

    }
}
