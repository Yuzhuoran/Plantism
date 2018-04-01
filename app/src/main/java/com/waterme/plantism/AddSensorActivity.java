package com.waterme.plantism;

import android.content.Intent;
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

public class AddSensorActivity extends AppCompatActivity {
    private  PinView pinView;
    private MyTextView text1;
    private MyTextView text2;
    private ConstraintLayout connectLayout;
    private ConstraintLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      //  text1.setStyle("heavy");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);
        text1 = (MyTextView) findViewById(R.id.textView);
        text2 = (MyTextView) findViewById(R.id.textView2);
        text2.setStyle("light");
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
        pinView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    //此处会响应2次 分别为ACTION_DOWN和ACTION_UP
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                    event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.d("whatever",pinView.getText().toString());
                    //mainLayout.isContextClickable(false);
                    mainLayout.setVisibility(View.INVISIBLE);
                    connectLayout.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    //TODO
}
