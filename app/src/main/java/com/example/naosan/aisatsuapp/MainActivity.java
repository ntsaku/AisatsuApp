package com.example.naosan.aisatsuapp;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTextViewTime;  /*TimePickerDialog でセットされた値を表示するTextView*/
    TextView mTextViewMessage;  /*あいさつを表示するTextView*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
//        現在の時間の取得
        int curHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//        現在の分の取得
        int curMinute = calendar.get(Calendar.MINUTE);

        mTextViewTime = (TextView) findViewById(R.id.textViewTime);
        mTextViewTime.setText(String.valueOf(curHourOfDay) + ":" + String.valueOf(curMinute));

        Button buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(this);

        Button buttonMessage = (Button) findViewById(R.id.buttonMessage);
        buttonMessage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int curHourOfDay;
        int idx;
        String aisatsuMessage;  /*あいさつ文字列*/

        if (v.getId() == R.id.buttonTime) {
            showTimePickerDialog();
        } else if (v.getId() == R.id.buttonMessage) {
//            mTextViewTime には HH:MM形式で時刻が設定されている。
//            区切り記号「:」より前（HH)を時間として抜き出して、適切な挨拶文を
//            mTextViewMessage に設定する。　　
            String string = mTextViewTime.getText().toString(); /* HH:MM*/
            idx = string.indexOf(":");
            curHourOfDay = Integer.valueOf(string.substring(0,idx));

            if (curHourOfDay >= 2 && curHourOfDay < 10) {
                aisatsuMessage = "おはよう";
            } else if (curHourOfDay >= 10 &&  curHourOfDay < 18) {
                aisatsuMessage = "こんにちは";
            } else { /*18:00-1:59*/
                aisatsuMessage = "こんばんは";
            }
            mTextViewMessage = (TextView) findViewById(R.id.textViewMessage);
            mTextViewMessage.setText(aisatsuMessage);
        } else  {
//          想定外エラー
            Log.d("UI-PARTS", "想定外エラーが発生しました。");
        }
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
//        現在の時間の取得
        int curHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//        現在の分の取得
        int curMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("UI-PARTS", String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                        String string = new String(hourOfDay + ":" + minute);
                        mTextViewTime.setText(string);
                    }
                },
                curHourOfDay, // 初期値（現在の時間）
                curMinute,  // 初期値（現在の分）
                true);
        timePickerDialog.show();
    }
}
