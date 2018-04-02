package com.waterme.plantism;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.waterme.plantism.R;
import com.waterme.plantism.model.MyTextView;

import java.util.HashSet;
import java.util.Set;

public class AddSensorActivity extends AppCompatActivity {
    private  PinView pinView;
    private MyTextView text1;
    private MyTextView text2;
    private ConstraintLayout connectLayout;
    private ConstraintLayout mainLayout;
    private MyTextView btnConnect;
    private Set validSensorId;
    private String tmp_id;
    //hard code valid sensor id
    protected void valid_sensor_init(){
        validSensorId= new HashSet<String>();
        validSensorId.add("1223");
        validSensorId.add("1445");
        validSensorId.add("1667");
        validSensorId.add("1889");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      //  text1.setStyle("heavy");
        super.onCreate(savedInstanceState);
        valid_sensor_init();
        setContentView(R.layout.activity_add_sensor);
        text1 = (MyTextView) findViewById(R.id.textView);
        text2 = (MyTextView) findViewById(R.id.textView2);
        text2.setStyle("light");
        btnConnect = (MyTextView) findViewById(R.id.button_connect);

        mainLayout = (ConstraintLayout)findViewById(R.id.connect_main_layout);
        connectLayout = (ConstraintLayout)findViewById(R.id.connect_temp_layout);
        final PinView pinView = (PinView) findViewById(R.id.pinView);
        pinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.colorAccent, getTheme()));
        pinView.setTextColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.black,getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.black, getTheme()));
        pinView.setItemCount(4);
        pinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        pinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        pinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(false);
        pinView.setCursorColor(
                ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp_id=pinView.getText().toString();
                if(validSensorId.contains(tmp_id)) {
                    mainLayout.setVisibility(View.INVISIBLE);
                    connectLayout.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(AddSensorActivity.this, AddPlantActivity.class);
                            intent.putExtra("sensor_id", tmp_id);
                            startActivity(intent);
                        }
                    }, 100);
                }
                else{
                    //TODO remind that the sensor id is invalid
                }
            }
        });
    }

    //TODO
}
