package com.waterme.plantism.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.LongDef;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.waterme.plantism.R;
import com.waterme.plantism.data.Channel;
import com.waterme.plantism.data.Condition;
import com.waterme.plantism.data.LocationResult;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.data.Units;
import com.waterme.plantism.listener.GeocodingServiceListener;
import com.waterme.plantism.listener.WeatherServiceListener;
import com.waterme.plantism.model.HistoryData;
import com.waterme.plantism.model.MyEditText;
import com.waterme.plantism.model.MyTextView;
import com.waterme.plantism.model.Plant;
import com.waterme.plantism.service.GoogleMapsGeocodingService;
import com.waterme.plantism.service.WeatherCacheService;
import com.waterme.plantism.service.YahooWeatherService;

/**
 * Created by zhuoran on 3/22/18.
 */

public class StatusFragment extends Fragment implements WeatherServiceListener, GeocodingServiceListener, LocationListener {
    private static final String TAG = "StatusFragment";


    private static final String PLANTID = "PLANTID";
    private static final String UID = "UID";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";
    private static final String PLANT_HISTORY = "history";

    private String plantid;
    private String uid;
    private String sensorId;
    private BarChart ctHumidity;
    private LineChart ctTemperature;
    private MyTextView tvSpecies;
    private MyTextView tvCondition;
    private MyTextView tvLocation;
    private MyTextView tvTemperature;
    private MyEditText tvPlantname;
    // get database reference
    private PlantDbHelper dbHelper = new PlantDbHelper(getContext());

    //yahoo weather
    public static int GET_WEATHER_FROM_CURRENT_LOCATION = 0x00001;
    private ProgressDialog loadingDialog;
    private YahooWeatherService weatherService;
    private GoogleMapsGeocodingService geocodingService;
    private WeatherCacheService cacheService;
    //weather service fail log
    private boolean weatherServicesHasFailed = false;
    private SharedPreferences preferences = null;
    private Handler handler = new Handler();
    private String editString;
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            updateName(editString);
        }
    };
    public StatusFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            plantid = getArguments().getString(PLANTID);
            uid = getArguments().getString(UID);
            sensorId = getArguments().getString("sensor_id");
            Log.d(TAG, "plantid is : "+ plantid);
            Log.d(TAG, "uid is :" + uid);
            Log.d(TAG, "sensor id is " + sensorId);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        weatherService = new YahooWeatherService(this);
        weatherService.setTemperatureUnit(preferences.getString(getString(R.string.pref_temperature_unit), null));

        geocodingService = new GoogleMapsGeocodingService(this);
        cacheService = new WeatherCacheService(getContext());

        if (preferences.getBoolean(getString(R.string.pref_needs_setup), true)) {
            //startSettingsActivity();
        }

    }
    public static Date TimeStamp2Date(String timeStampString) {
        return new java.util.Date(Long.parseLong(timeStampString)*1000);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_status, container, false);

        tvCondition = (MyTextView) rootView.findViewById(R.id.tv_condition);
        tvTemperature = (MyTextView) rootView.findViewById(R.id.tv_condition);
        tvLocation = (MyTextView) rootView.findViewById(R.id.tv_location);
        tvLocation.setStyle("light");

        tvPlantname = (MyEditText) rootView.findViewById(R.id.tv_status_myname);
        MyTextView tvPlantName = (MyTextView) rootView.findViewById(R.id.tv_status_name);
        tvSpecies= rootView.findViewById(R.id.tv_status_name);
        ((MyTextView)rootView.findViewById(R.id.textView_water)).setStyle("light");
        ((MyTextView)rootView.findViewById(R.id.tv_dialog)).setStyle("light");
        ((MyTextView)rootView.findViewById(R.id.textView_sun)).setStyle("light");
        ((MyTextView)rootView.findViewById(R.id.textView_temp)).setStyle("light");
        tvSpecies.setStyle("light");
        //fake textvies

        //end fake
        ImageView imPlant = (ImageView) rootView.findViewById(R.id.im_status);
        ImageView imStatusSun = (ImageView) rootView.findViewById(R.id.im_status_sun);
        ImageView imStatusWater = (ImageView) rootView.findViewById(R.id.im_status_water);
        ImageView imStatusTemp = (ImageView) rootView.findViewById(R.id.im_status_temp);

        ctHumidity = (BarChart) rootView.findViewById(R.id.ct_humidity);
        ctTemperature = (LineChart) rootView.findViewById(R.id.ct_temperature);
        //set weather from yahoo weather api
        // get data base reference
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // set plant condition indicator
        // set humidity barchart
        // set temperature linechart
        /*
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
        //initlineChart(ctTemperature);
        //ctTemperature.setDescription("");*/

        /* init history */

        initHistoryTest();

        /* get history from firebase 7 day*/

        Query historyQuery = FirebaseDatabase.getInstance()
                .getReference().child(USER_CHILD)
                .child(uid)
                .child(USER_PLANTS_CHILD)
                .child(plantid)
                .child(PLANT_HISTORY)
                .limitToLast(7);


        /* add listener */

        historyQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            /* fetch all last 7 children */
            /* this is a final method */

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HistoryData> historyDataList = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    /* get the data model from firebase */
                    historyDataList.add(dsp.getValue(HistoryData.class));
                    String tmp_stamp=historyDataList
                            .get(historyDataList.size() - 1)
                            .getTimestamp();
                    Log.d(TAG, "timestamp is "
                            + TimeStamp2Date(tmp_stamp).toString());
                }

                /* handle charts here */
                setHumidityCharts(historyDataList);
                setTemperatureCharts(historyDataList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //todo test
        //updateName("mmmmika");
        //todo set the plant name according to firebase data instead of somethin
        tvPlantname.setText("somethin");
        tvPlantname.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(delayRun!=null){
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 2000);
            }
        });
        return rootView;
    }

    protected void initlineChart(LineChart mChart,String[] temperature_list){
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
        mChart.setBackgroundColor(Color.rgb(240, 252, 252));
        // mChart.setBackgroundColor(Color.GRAY);
        //设置点击chart图对应的数据弹出标注
//      xl.setAvoidFirstLastClipping(true);
//      xl.setAdjustXLabels(true);
        // 加载数据
        setData(mChart,temperature_list);
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
        XAxis xAxis=mChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setTextSize(9f);//设置字体
        xAxis.setTextColor(Color.rgb(120, 221, 221));//设置字体颜色
        //xAxis.setLabelsToSkip(7);
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(10f);//设置最大值
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
         // xAxis.setLabelCount(7);
//        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
        //xAxis.setValueFormatter();//格式化x轴标签显示字符

        YAxis yAxis=mChart.getAxisLeft();
        yAxis.setLabelCount(5,false);
        yAxis.setValueFormatter(new TemperatureYAxisValueFormatter());
        yAxis.setTextSize(9f);
        yAxis.setTextColor(Color.rgb(133,133,133));
        yAxis.setGridLineWidth(0.7f);
        yAxis.setGridColor(Color.rgb(194,194,194));
        YAxis yAxisright=mChart.getAxisRight();
        yAxisright.setEnabled(false);
        mChart.getLegend().setEnabled(false);
        // 刷新图表
        mChart.invalidate();
    }

    private void setData(LineChart mChart,String[] bb) {
        String[] aa = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
        //String[] bb = {"76.00","74.34","76.67","77.90","72.33","73.33","70.78"};

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
        set1.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set1.setFillColor(Color.rgb(184, 242, 242));
        set1.setCircleColor(Color.rgb(120, 221, 221));
        set1.setCircleColorHole(Color.rgb(120, 221, 221));
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(1f);    //设置线的宽度
        set1.setCircleSize(4f);   //设置小圆的大小
        //set1.setHighLightColor(Color.rgb(244, 0, 0));
        set1.setColor(Color.rgb(120, 221, 221));    //设置曲线的颜色

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
    }

    /* set temperature data */
    private void setTemperatureCharts(List<HistoryData> dataList) {
        String[] temperature_array=new String[7];
        int size=dataList.size();
        for(int i=0;i<7;i++){
            temperature_array[i]=dataList.get(size-1-i).getAir_t();
        }
        initlineChart(ctTemperature,temperature_array);
        Log.d(TAG, "set temperature!");
        ctTemperature.setDescription("");
    }

    /* set humidity charts */
    private void setHumidityCharts(List<HistoryData> dataList) {
        Log.d(TAG, "set humidity!");
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<dataList.size();i++){
            entries.add(new BarEntry(Float.parseFloat(dataList.get(i).getAir_h()),i));
        }
        /*
        entries.add(new BarEntry(74.3f, 0));
        entries.add(new BarEntry(70.6f, 1));
        entries.add(new BarEntry(68.1f, 2));
        entries.add(new BarEntry(92.1f, 3));
        entries.add(new BarEntry(85.0f, 4));
        entries.add(new BarEntry(80.1f, 5));
        entries.add(new BarEntry(76.6f, 6));*/
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
    }

    private void initHistoryTest() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_PLANTS_CHILD)
                .child(plantid)
                .child(PLANT_HISTORY);
        Log.d(TAG, "init history");
        Random random = new Random();
        long epoch1 = Timestamp.valueOf("2018-3-01 00:00:00").getTime()/1000L;
        long epoch2 = Timestamp.valueOf("2018-05-01 00:00:00").getTime()/1000L;
        long randomEpoch = epoch1 + Math.abs(new Random().nextLong()) % (epoch2-epoch1);
        for (int i = 0; i < 10; i++) {
            DatabaseReference newRef = ref.push();
            newRef.setValue(new HistoryData(
                    String.valueOf(60.0+40*random.nextDouble()),
                    String.valueOf(50.0+20*random.nextDouble()),
                    String.valueOf(30.0+65*random.nextDouble()),
                    String.valueOf(randomEpoch)
            ));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void geocodeSuccess(LocationResult location) {

    }

    @Override
    public void geocodeFailure(Exception exception) {

    }

    @Override
    public void serviceSuccess(Channel channel) {
        //loadingDialog.hide();

        Condition condition = channel.getItem().getCondition();
        Units units = channel.getUnits();
        Condition[] forecast = channel.getItem().getForecast();

        //int weatherIconImageResource = getResources().getIdentifier("icon_" + condition.getCode(), "drawable", getPackageName());

        //weatherIconImageView.setImageResource(weatherIconImageResource);
        tvTemperature.setText(getString(R.string.temperature_output, condition.getTemperature(), units.getTemperature()));
        tvCondition.setText(condition.getDescription());
        tvLocation.setText(channel.getLocation());



        //cacheService.save(channel);
    }

    @Override
    public void serviceFailure(Exception exception) {

    }

    private void updateName(final String newName) {

        /* update now root name*/
        /* update plant root */
        final DatabaseReference plantRef = FirebaseDatabase.getInstance().getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_PLANTS_CHILD)
                .child(plantid);

        plantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "plant id " + plantid);
                    Map<String, Object> oldPlant = (Map<String, Object>) dataSnapshot.getValue();
                    Plant newPlant = new Plant();

                    if (oldPlant.containsKey("category")) {
                        Log.d(TAG, "has category!");
                        newPlant.setCategory((String)oldPlant.get("category"));
                    }

                    if (oldPlant.containsKey("history")) {
                        Log.d(TAG, "has history");
                        newPlant.setHistory((Map<String, HistoryData>) oldPlant.get("history"));
                    }

                    if (oldPlant.containsKey("imgUrl")) {
                        Log.d(TAG, "has imageurl!");
                        newPlant.setImgUrl((String) oldPlant.get("imgUrl"));
                    }


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child(USER_CHILD)
                            .child(uid)
                            .child(USER_PLANTS_CHILD);

                    DatabaseReference nowRef = FirebaseDatabase.getInstance().getReference()
                            .child(USER_CHILD)
                            .child(uid)
                            .child(USER_REALTIME_CHILD)
                            .child(sensorId);

                    DatabaseReference sensorRef = FirebaseDatabase.getInstance().getReference()
                            .child(SENSOR_CHILD)
                            .child(sensorId);

                    Map<String, Object> updatePlant= new HashMap<>();
                    Map<String, Object> update = new HashMap<>();

                    /* update now node */
                    update.put("plantMyname", newName);
                    nowRef.updateChildren(update);

                    /* update plant node */
                    updatePlant.put(newName, newPlant);
                    ref.updateChildren(updatePlant);

                    /* update sensor map */
                    update.clear();
                    update.put("plantId", newName);
                    sensorRef.updateChildren(update);
                    plantid = newName;
                    plantRef.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
