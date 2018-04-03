package com.waterme.plantism;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterme.plantism.R;
import com.waterme.plantism.model.MyTextView;

public class AddPlantActivity extends AppCompatActivity {
    private MyTextView text1;
    private MyTextView text2;
    private MyTextView button;
    private String sensor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        text1 = findViewById(R.id.tv_add_plant1);
        text2 = findViewById(R.id.tv_add_plant2);
        text2.setStyle("light");
        button = findViewById(R.id.button_add_plant);
        Bundle bundle = getIntent().getExtras();
        sensor_id = "";
        if (bundle != null) {
            sensor_id = bundle.getString("sensor_id");
            /* set the search result and clear the focus */
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensor_id.length()>0) {
                    Intent intent = new Intent(AddPlantActivity.this, SearchActivity.class);
                    intent.putExtra("sensor_id", sensor_id);
                    startActivity(intent);
                }
            }
        });

    }
    //TODO
}
