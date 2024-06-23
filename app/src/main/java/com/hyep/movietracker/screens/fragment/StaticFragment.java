package com.hyep.movietracker.screens.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyep.movietracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaticFragment extends Fragment {

    private BarChart barChart;
    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);

        barChart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);

        setupCharts();

        return view;
    }

    private void setupCharts() {
        List<BarEntry> barEntries = new ArrayList<>();
        List<PieEntry> pieEntries = new ArrayList<>();

        // Generate random data for the bar chart (movies watched per month)
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, random.nextInt(100)));
        }

        // Generate random data for the pie chart (movies watched by genre)
        pieEntries.add(new PieEntry(random.nextInt(100), "Action"));
        pieEntries.add(new PieEntry(random.nextInt(100), "Comedy"));
        pieEntries.add(new PieEntry(random.nextInt(100), "Horror"));

        // Setup BarChart
        BarDataSet barDataSet = new BarDataSet(barEntries, "Movies Watched by Month");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setValueTextColor(R.color.black);
        barChart.setData(barData);
        barChart.getDescription().setTextColor(Color.WHITE); // Outside chart text color
        barChart.getXAxis().setTextColor(Color.WHITE); // Outside chart text color
        barChart.getAxisLeft().setTextColor(Color.WHITE); // Outside chart text color
        barChart.getAxisRight().setTextColor(Color.WHITE); // Outside chart text color
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.invalidate();

        // Setup PieChart
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Movies Watched by Genre");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(R.color.black);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        pieChart.getDescription().setTextColor(Color.WHITE); // Outside chart text color
        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.invalidate();
    }
}