package com.example.cp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

public class rating_pei extends AppCompatActivity {

    private static final String TAG = rating_pei.class.getSimpleName();
    private static final String FETCH_URL = "http://192.168.117.51/fetch_ratings.php";

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_pei);

        pieChart = findViewById(R.id.pieChart);

//        setupPieChart(); // Call method to setup pie chart
        fetchChartData();
    }

    private void setupPieChart() {
//        pieChart.setUsePercentValues(true); // Enable percent values
        pieChart.getDescription().setEnabled(false); // Disable description text
//        pieChart.setExtraOffsets(5, 5, 5, 5); // Adjust space between slices
        pieChart.setDrawHoleEnabled(true); // Enable center hole
        pieChart.setHoleColor(Color.WHITE); // Set color of the center hole
        pieChart.setTransparentCircleRadius(61f); // Adjust size of the transparent circle around the hole
        pieChart.animateY(1000); // Animate chart
        pieChart.setDrawEntryLabels(false); // Disable legend

        // Disable legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
    }

    private void fetchChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>(); // Initialize entries ArrayList
        ArrayList<String> labels = new ArrayList<>(); // Initialize labels ArrayList
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                FETCH_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        setupPieChart();
                        double academicAvg = response.optDouble("academic_avg", 0.0);
                        double administrativeAvg = response.optDouble("administrative_avg", 0.0);
                        double infraAvg = response.optDouble("infra_avg", 0.0);
                        double extracurricularAvg = response.optDouble("extracurricular_avg", 0.0);
                        double campusAvg = response.optDouble("campus_avg", 0.0);
                        double facultyAvg = response.optDouble("faculty_avg", 0.0);
                        double techAvg = response.optDouble("tech_avg", 0.0);
                        double socialAvg = response.optDouble("social_avg", 0.0);
                        double cleanlinessAvg = response.optDouble("cleanliness_avg", 0.0);
                        double parkingAvg = response.optDouble("parking_avg", 0.0);

                        entries.add(new PieEntry((float) academicAvg, ""));
                        entries.add(new PieEntry((float) administrativeAvg, ""));
                        entries.add(new PieEntry((float) infraAvg, ""));
                        entries.add(new PieEntry((float) extracurricularAvg, ""));
                        entries.add(new PieEntry((float) campusAvg, ""));
                        entries.add(new PieEntry((float) facultyAvg, ""));
                        entries.add(new PieEntry((float) techAvg, ""));
                        entries.add(new PieEntry((float) socialAvg, ""));
                        entries.add(new PieEntry((float) cleanlinessAvg, ""));
                        entries.add(new PieEntry((float) parkingAvg, ""));


                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.GRAY);
                        colors.add(Color.BLUE);
                        colors.add(Color.RED);
                        colors.add(Color.GREEN);
                        colors.add(Color.CYAN);
                        colors.add(Color.YELLOW);
                        colors.add(Color.rgb(125, 60, 152));
                        colors.add(Color.rgb(88, 24, 69));
                        colors.add(Color.rgb(255, 87, 51));
                        colors.add(Color.rgb(0, 150, 136));


                        PieDataSet dataSet = new PieDataSet(entries, "");
                        dataSet.setColors(colors);

                        PieData data = new PieData(dataSet);
                        pieChart.setData(data);
                        pieChart.animateY(1000);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error", error);
                        Toast.makeText(rating_pei.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
    }
}