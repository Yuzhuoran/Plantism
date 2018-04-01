package com.waterme.plantism;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterme.plantism.R;
import com.waterme.plantism.model.MyTextView;

public class AddPlantActivity extends AppCompatActivity {
    private MyTextView text1;
    private MyTextView text2;
    private MyTextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        text1 = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        text2.setStyle("light");
        button = findViewById(R.id.button_add_plant);

    }
    //TODO
}
