package com.example.smarttumbler.statistic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarttumbler.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistic, container, false);
        BarChart barChart = root.findViewById(R.id.barChart);

        ArrayList<BarEntry> airMinum = new ArrayList<>();
        airMinum.add(new BarEntry(2010,2000));
        airMinum.add(new BarEntry(2011,1800));
        airMinum.add(new BarEntry(2012,2400));
        airMinum.add(new BarEntry(2013,1000));
        airMinum.add(new BarEntry(2014,3000));
        airMinum.add(new BarEntry(2015,2000));
        airMinum.add(new BarEntry(2016,1800));

        BarDataSet barDataSet = new BarDataSet(airMinum, "Jumlah Air Minum");
        barDataSet.setBarBorderColor(ColorTemplate.PASTEL_COLORS[1]);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText(".");
        barChart.animateY(2000);

        return root;

//
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(10/07/2021, 1),
//                new DataPoint(11/07/2021, 5),
//                new DataPoint(12/07/2021, 3),
//                new DataPoint(13/07/2021, 2),
//                new DataPoint(14/07/2021, 6),
//                new DataPoint(15/07/2021, 8),
//                new DataPoint(16/07/2021, 4),
//        });
//        graph.addSeries(series);
//
//        return root;
    }
}