package com.waterme.plantism.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.waterme.plantism.R;
import com.waterme.plantism.data.PlantDbHelper;

/**
 * Created by zhuoran on 3/22/18.
 */

public class StatusFragment extends Fragment {
    private static final String TAG = "StatusFragment";


    private static final String PLANTID = "PLANTID";
    private static final String UID = "UID";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    private String plantid;
    private String uid;

    // get database reference
    private PlantDbHelper dbHelper = new PlantDbHelper(getContext());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            plantid = getArguments().getString(PLANTID);
            uid = getArguments().getString(UID);
            Log.d(TAG, "plantid is : "+ plantid);
            Log.d(TAG, "uid is :" + uid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_status, container, false);

        TextView tvOutdoor = (TextView) rootView.findViewById(R.id.tv_outdoor);
        TextView tvWeather = (TextView) rootView.findViewById(R.id.tv_weather);
        TextView tvMyPlantName = (TextView) rootView.findViewById(R.id.tv_status_myname);
        TextView tvPlantName = (TextView) rootView.findViewById(R.id.tv_status_name);
        //fake textvies

        //end fake
        ImageView imPlant = (ImageView) rootView.findViewById(R.id.im_status);
        ImageView imStatusSun = (ImageView) rootView.findViewById(R.id.im_status_sun);
        ImageView imStatusWater = (ImageView) rootView.findViewById(R.id.im_status_water);
        ImageView imStatusTemp = (ImageView) rootView.findViewById(R.id.im_status_temp);

        BarChart ctHumidity = (BarChart) rootView.findViewById(R.id.ct_humidity);
        LineChart ctTemperature = (LineChart) rootView.findViewById(R.id.ct_temperature);

        // get data base reference
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // set plant condition indicator
        // set humidity barchart
        // set temperature linechart
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(74.3f, 0));
        entries.add(new BarEntry(70.6f, 1));
        entries.add(new BarEntry(68.1f, 2));
        entries.add(new BarEntry(92.1f, 3));
        entries.add(new BarEntry(85.0f, 4));
        entries.add(new BarEntry(80.1f, 5));
        entries.add(new BarEntry(76.6f, 6));
        BarDataSet dataset = new BarDataSet(entries, "% humidity");
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("SUN");
        labels.add("MON");
        labels.add("TUE");
        labels.add("WED");
        labels.add("THU");
        labels.add("FRI");
        labels.add("SAT");
        BarData data = new BarData(labels, dataset);
        ctHumidity.setData(data);
        ctHumidity.setDescription("Humidity History");
        ctHumidity.animateY(2000);
        initlineChart(ctTemperature);
        ctTemperature.setDescription("Temperature History");
        return rootView;
    }

    protected void initlineChart(LineChart mChart){
        //设置透明度
        mChart.setAlpha(0.8f);
        //设置网格底下的那条线的颜色
        mChart.setBorderColor(Color.rgb(213, 216, 214));
        //设置Y轴前后倒置
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //设置是否能扩大扩小
        mChart.setPinchZoom(true);
        // 设置背景颜色
        // mChart.setBackgroundColor(Color.GRAY);
        //设置点击chart图对应的数据弹出标注
//      xl.setAvoidFirstLastClipping(true);
//      xl.setAdjustXLabels(true);
        // 加载数据
        setData(mChart);
        //从X轴进入的动画
        mChart.animateX(4000);
        mChart.animateY(3000);   //从Y轴进入的动画
        mChart.animateXY(3000, 3000);    //从XY轴一起进入的动画

        //设置最小的缩放
        mChart.setScaleMinima(0.5f, 1f);
        //设置视口
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);  //设置图最下面显示的类型
        l.setTextSize(15);
        l.setTextColor(Color.rgb(104, 241, 175));
        l.setFormSize(30f); // set the size of the legend forms/shapes

        // 刷新图表
        mChart.invalidate();
    }
    private void setData(LineChart mChart) {
        String[] aa = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
        String[] bb = {"76.00","74.34","76.67","77.90","72.33","73.33","70.78"};

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < aa.length; i++) {
            xVals.add(aa[i]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < bb.length; i++) {
            yVals.add(new Entry(Float.parseFloat(bb[i]), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "°F temperature");

        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setCircleSize(5f);   //设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
    }
}
