package com.waterme.plantism;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.waterme.plantism.model.MyTextView;

public class HeaderActivity extends AppCompatActivity {
    protected MyTextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navheader);
        user_name=findViewById(R.id.tv_user_name);
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_name.setText(sharedPre.getString("uname",""));
        user_name.setText("Tony Stark");
    }
}
