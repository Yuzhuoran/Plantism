package com.waterme.plantism;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.waterme.plantism.R;

public class AddSensorActivity extends AppCompatActivity {
    private  PinView pinView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("whatever","hello");
        setContentView(R.layout.activity_add_sensor);
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
        pinView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("whatever",pinView.getText().toString());
                    //此处会响应2次 分别为ACTION_DOWN和ACTION_UP
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                    event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.d("whatever",pinView.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    //TODO
}
