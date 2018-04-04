package com.waterme.plantism.Fragment;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

public class TemperatureYAxisValueFormatter implements YAxisValueFormatter{

    @Override
    public String getFormattedValue(float v, YAxis yAxis) {
        return String.valueOf(v)+"°F";
    }
}
