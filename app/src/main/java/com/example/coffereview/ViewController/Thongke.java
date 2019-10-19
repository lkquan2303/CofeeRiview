package com.example.coffereview.ViewController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coffereview.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Thongke extends AppCompatActivity implements OnChartValueSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LineChartView lineChartView;
    private PieChart pieChart1;
    private CombinedChart combinedChart;
    ImageButton btstory, btnmore, btnngay,btnplan;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        btnngay = findViewById(R.id.btnngay);
        btnplan = findViewById(R.id.btnplan);
        btstory = findViewById(R.id.btstory);
        btnmore = findViewById(R.id.btnmore);
        btstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongke.this, TrangChu.class);
                startActivity(intent);
            }
        });
        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongke.this, Setting.class);
                startActivity(intent);
            }
        });
        btnplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongke.this, PlanDetails.class);
                startActivity(intent);
            }
        });
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongke.this, NgayThang.class);
                startActivity(intent);
            }
        });

        pieChart1 = findViewById(R.id.piechar);

        //Pie
        pieChart1.setRotationEnabled(true);
        pieChart1.setDescription(new Description());
        pieChart1.setHoleRadius(35f);
        pieChart1.setTransparentCircleAlpha(0);
        pieChart1.setCenterText("Hello");
        pieChart1.setCenterTextSize(18);
        pieChart1.setDrawEntryLabels(true);
        addDataset(pieChart1);
        pieChart1.setOnChartValueSelectedListener(this);




        //Line
        Intent intent = getIntent();
        int valuerv = intent.getIntExtra("rv", 0);
        int valuev = intent.getIntExtra("v", 0);
        int valuecn = intent.getIntExtra("cn", 0);
        int valuebb = intent.getIntExtra("bb", 0);
        int valuebt = intent.getIntExtra("bt", 0);

        String[] axisData = {"Rất vui", "Vui", "Bình thường", "Chán nản", "Bực bội"};
        int[] yAxisData = {valuerv, valuev, valuebt, valuecn, valuebb};
        lineChartView = findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.parseColor("#000000"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#000000"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Tổng hợp cảm xúc của bạn");
        yAxis.setTextColor(Color.parseColor("#000000"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = valuebb+valuebt+valuecn+valuerv+valuev;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);



    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    //Line
    private DataSet dataChart() {
        LineData d = new LineData();
        Intent intent = getIntent();
        int valuerv = intent.getIntExtra("rv", 0);
        int valuev = intent.getIntExtra("v", 0);
        int valuecn = intent.getIntExtra("cn", 0);
        int valuebb = intent.getIntExtra("bb", 0);
        int valuebt = intent.getIntExtra("bt", 0);
        int[] data = new int[]{valuerv,valuev,valuebt,valuecn,valuebb};
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 5; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Tổng hợp cảm xúc của bạn");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;

    }


    //Pie
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDataset(PieChart pieChart) {
//        List<Float> yData = new ArrayList<>();
        Intent intent = getIntent();
        float valuerv = intent.getIntExtra("rv", 0);
//        yData.add(valuerv);
        float valuev = intent.getIntExtra("v", 0);
//        yData.add(valuev);
        float valuecn = intent.getIntExtra("cn", 0);
//        yData.add(valuecn);
        float valuebb = intent.getIntExtra("bb", 0);
//        yData.add(valuebb);
        float valuebt = intent.getIntExtra("bt", 0);
//        yData.add(valuebt);

        float yData[] = {valuerv ,valuev,valuebt,valuecn,valuebb};
        String yDatacheck[] = {"Rất vui" ,"Vui","Bình thường","Chán nản","Bực bội"};
        String[] xData = { "January", "February", "January" };
        ArrayList<String> xEntrys = new ArrayList<>();
        ArrayList<PieEntry> yEntries = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            yEntries.add(new PieEntry(yData[i],i));
        }

//        for (int i = 0; i < xData.length;i++){
//            xEntrys.add(xData[i]);
//        }
        PieDataSet pieDataSet = new PieDataSet(yEntries, "Diary ");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.WHITE );
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}
