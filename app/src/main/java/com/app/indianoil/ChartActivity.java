package com.app.indianoil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.indianoil.modal.ResultModal;
import com.app.indianoil.util.UtilClass;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.apache.commons.collections4.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView img_back;
    View view,viewleft;
    TextView txt_dates,txt_columnleft;
    XAxis xAxis;
    ArrayList<String> DataList=new ArrayList<String>();
    List<String> DatesList=new ArrayList<String>();
    ArrayList<ResultModal> productList= new ArrayList<>();
    Button btn_showdata;
    EditText edt_fromdate,edt_todate;
    Date datestart,dateend;
    Calendar calendar2,calendar1;
    int mYear,mMonth,mDay;
    String spinnervalue="Stock at start of day (in KL)",date,fromDate,toDate,data;
    LineChart mChart ;
    Spinner spin_columnname;
    String columnname[] = {"Stock at start of day (in KL)","Stock at end of day (in KL)","Receipt on date (in KL)",
            "MS Price (in Rs)","HSD Price","Last Price change date-time","Sale from MS from DU-1","Sale from MS from DU-2","Sale from HSD from DU-1",
            "Sale from HSD from DU-2"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        init();
        setListener();
    }

    public void init()
    {
        productList= UtilClass.getArrayListrocode(UtilClass.resultlistrocode,this);
//        viewleft=findViewById(R.id.viewleft);
//        txt_columnleft=findViewById(R.id.txt_columnleft);
        img_back=findViewById(R.id.img_back);
        view=findViewById(R.id.view);
        txt_dates=findViewById(R.id.txt_dates);
        btn_showdata=findViewById(R.id.btn_showdata);
        edt_fromdate=findViewById(R.id.edt_fromdate);
        edt_todate=findViewById(R.id.edt_todate);
        mChart  = (LineChart) findViewById(R.id.chart);
        spin_columnname=findViewById(R.id.spin_columnname);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item_layout, columnname);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//android.R.layout.simple_spinner_dropdown_item
        spin_columnname.setAdapter(dataAdapter);

        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getLegend().setEnabled(false);
        mChart.setScaleMinima(0f, 0f);
        mChart.fitScreen();
//        mChart.getXAxis().setGranularity(1f);
//        mChart.getXAxis().setGranularityEnabled(true);
//        mChart.getXAxis().setAxisMinValue(0f); //experiment with these values
//        mChart.getXAxis().setAxisMaxValue(10f);
        mChart.getXAxis().setLabelCount(5);
        mChart.getAxisRight().setEnabled(false);
        mChart.getDescription().setEnabled(false);
//        MarkerView mv = new MarkerView(getApplicationContext(), R.drawable.circle);
//        mv.setChartView(mChart);
//        mChart.setMarker(mv);

        renderData();
    }

    public void renderData() {
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        //llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 20f, 1f);
        xAxis.setAxisMaximum(5f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(5);
        leftAxis.setDrawLimitLinesBehindData(false);

        //setData();
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();

        if(!CollectionUtils.isEmpty(DataList))
        {
            for(int i=0;i<DataList.size();i++)
            {
                Log.e("dataempty: ",DataList.get(i).toString() );
                Log.e("dateempty: ",DatesList.get(i).toString() );
                float data= Float.parseFloat(DataList.get(i));
                //int dataint= (int) data;
                int val=i;
                values.add(new Entry(val,data));
            }
        }
        else
        {
            Toast.makeText(this,"Select Dates to show analysis!",Toast.LENGTH_SHORT).show();
        }
        mChart.getAxisRight().setStartAtZero(false);
        mChart.getAxisLeft().setAxisMinValue(values.size()+1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(DatesList));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        mChart.getXAxis().setLabelCount(values.size()+1);
        mChart.setMaxVisibleValueCount(5);
        mChart.setVisibleXRangeMaximum(5);
        xAxis.setGranularity(1f);
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(values);
            mChart.getLegend().setEnabled(true);
            mChart.getLineData().addDataSet(set1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
        else
            {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(true);
            set1.enableDashedLine(5f, 2f, 0f);
            set1.enableDashedHighlightLine(5f, 2f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(1f);
            set1.setDrawCircleHole(true);
            set1.setDrawFilled(true);

                set1.setValueTextColor(Color.rgb(0, 0, 0));
                set1.setDrawValues(true);
                set1.setValueTextSize(10f);
                mChart.setExtraOffsets(0, 0, 0, 0);

            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(10.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.caret_arrow_up);
                set1.setFillColor(Color.DKGRAY);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
//                mChart.setViewPortOffsets(1, 0, 0, 0);
//                mChart.getAxisLeft().setSpaceTop(40);
             //   mChart.fitScreen();
            mChart.setData(data);
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }


    public void setListener()
    {
        btn_showdata.setOnClickListener(this);
        edt_fromdate.setOnClickListener(this);
        edt_todate.setOnClickListener(this);
        img_back.setOnClickListener(this);

        spin_columnname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnervalue=adapterView.getItemAtPosition(i).toString();
                UtilClass.saveallsharedpref(getApplicationContext(),UtilClass.columnname,spinnervalue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }


//    public void setChartData()
//    {
////        LineDataSet lineDataSet=new LineDataSet(datavalues(),"values");
////
////        ArrayList<ILineDataSet> datasets=new ArrayList<>();
////        datasets.add(lineDataSet);
////
////        LineData data=new LineData(datasets);
////        chart.setData(data);
////        chart.invalidate();
//
//        //////////////
//        int[] numArr = {4,2,10};
//
////        final String [] numMap = new Arraylist<>();
////
////        numMap.put(10, "first");
////        numMap.put(20, "second");
////        numMap.put(30, "third");
////        numMap.put(40, "fourth");
////        numMap.put(50, "fifth");
////        numMap.put(60, "sixth");
////        numMap.put(70, "sixth");
////        numMap.put(80, "sixth");
////        numMap.put(90, "sixth");
////        numMap.put(100, "sixth");
//
//        List<Entry> entries1 = new ArrayList<Entry>();
//
//        for(int num : numArr){
//            entries1.add(new Entry(num, num));
//        }
//        chart.getXAxis().setDrawLabels(false);
//        chart.getXAxis().setDrawGridLines(false);
//
//        LineDataSet dataSet = new LineDataSet(datavalues(), "");
//
//        LineData data = new LineData(dataSet);
//
//        Legend l = chart.getLegend();
//        l.setFormSize(10f); // set the size of the legend forms/shapes
//        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//     //   l.setCustom(DatesList);
//       //l.setPosition(LegendPosition.BELOW_CHART_LEFT);
//        l.setTextSize(15f);
//        l.setTextColor(Color.GREEN);
//        l.setXEntrySpace(3f); // set the space between the legend entries on the x-axis
//        l.setYEntrySpace(3f); // set the space between the legend entries on the y-axis
//
//        // set custom labels and colors
//      //  l.setCustom(getResources().getColor(R.color.white), DatesList);
//
//        YAxis yAxis=chart.getAxisLeft();
//        chart.getAxisRight().setEnabled(false);
//       // yAxis.setDrawTopYLabelEntry(true);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(false);
//        xAxis.setLabelRotationAngle(-90);
//
//
//        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//
//                return DatesList.get((int)value);
//            }
//
//        });
//        chart.setBackgroundColor(Color.TRANSPARENT);
//
//        // disable description text
//        chart.getDescription().setEnabled(false);
//
//        // enable touch gestures
//        chart.setTouchEnabled(false);
//        chart.setDragEnabled(false);
//        chart.setScaleEnabled(true);
//        // chart.setScaleXEnabled(true);
//        // chart.setScaleYEnabled(true);
//
//        // force pinch zoom along both axis
//        chart.setPinchZoom(false);
//        // set listeners
//        chart.setDrawGridBackground(false);
//        chart.setData(data);
//        chart.invalidate();
//    }

    public void datePicker(final String type)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = null,yearr,day = null;
                        int mnth;
                        monthOfYear=(monthOfYear+1);
                        if(monthOfYear < 10){

                            month = String.valueOf(monthOfYear);
                            Log.e("monthOfYear: ", month);
                        }
                        else
                        {
                            month= String.valueOf(monthOfYear);
                        }
                        if(dayOfMonth < 10){

                            day  = String.valueOf(dayOfMonth);
                            Log.e("dayOfMonth: ",day);
                        }
                        else
                        {
                            day= String.valueOf(dayOfMonth);
                        }
                        date = day+"/"+ (month )+ "/"+year;

                        if(type.equalsIgnoreCase("from"))
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar1 = Calendar.getInstance();
                            try {
                                datestart=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");

                            fromDate = formatter.format(datestart);

                            Log.e("startdate: ",fromDate );
                            edt_fromdate.setText(fromDate);

                            calendar1.setTime(datestart);

                            Log.e("Compare Result start : " , String.valueOf(datestart));
                        }
                        else if(type.equalsIgnoreCase("to"))
                        {
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar2 = Calendar.getInstance();
                            try {
                                dateend=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            toDate = formatter.format(dateend);
                            edt_todate.setText(toDate);
                            Log.e("enddate: ",toDate );
                            calendar2.setTime(dateend);

                            Log.e("Compare Result end: " , String.valueOf(dateend));
                        }

                    }

                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd/MM/yy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public void columnselectstart(String colmnname,int i)
    {
        Log.e( "columnselectstart: ","columnselectstart" );
        if(colmnname.equalsIgnoreCase("Stock at start of day (in KL)"))
        {
            data=productList.get(i).getStockstart();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Stock at end of day (in KL)"))
        {
            data=productList.get(i).getStockend();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Receipt on date (in KL)"))
        {
            data=productList.get(i).getReceipt();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("MS Price (in Rs)"))
        {
            data=productList.get(i).getMsprice().toString();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("HSD Price"))
        {
            data=productList.get(i).getHsdprice();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-1"))
        {
            data=productList.get(i).getMs_du1();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-2"))
        {
            data=productList.get(i).getMs_du2();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-1"))
        {
            data=productList.get(i).getHsd_du1();
            DataList.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-2"))
        {
            data=productList.get(i).getHsd_du2();
            DataList.add(data);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_showdata:
                fromDate=edt_fromdate.getText().toString();
                toDate=edt_todate.getText().toString();
                if(TextUtils.isEmpty(fromDate)&&!(TextUtils.isEmpty(toDate)))
                {
                    Toast.makeText(getApplicationContext(),"Please select From Date!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(toDate)&&!(TextUtils.isEmpty(fromDate)))
                {
                    Toast.makeText(getApplicationContext(),"Please select To Date!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(toDate)&&TextUtils.isEmpty(fromDate))
                {
                    Toast.makeText(getApplicationContext(),"Please select Date!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatesList=new ArrayList<>();
                    DataList=new ArrayList<>();
                    List<Date> dates=getDates(fromDate,toDate);
                    DateFormat formatter = new SimpleDateFormat("d/M/yy");
                    for(Date date:dates)
                    {
                        String today = formatter.format(date);
                        if(!CollectionUtils.isEmpty(productList))
                        {
                            for(int j=0;j<productList.size();j++)
                            {
                                if(today.equalsIgnoreCase(productList.get(j).getDate()))
                                {
                                    DatesList.add(today);
                                    Log.e("onClick: ","yes" );
                                    columnselectstart(spinnervalue,j);
                                }
                            }
                        }
                    }
                    if(!CollectionUtils.isEmpty(DataList))
                    {
                        setData();
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                        view.setVisibility(View.VISIBLE);
                        txt_dates.setVisibility(View.VISIBLE);
//                        viewleft.setVisibility(View.VISIBLE);
//                        txt_columnleft.setVisibility(View.VISIBLE);
//                        txt_columnleft.setText(spinnervalue);
                    }
                    else
                    {
                        Toast.makeText(this,"No Data to show!",Toast.LENGTH_SHORT).show();
                    }

                }

                Log.e("listsizee: ", String.valueOf(DataList.size()));

                break;

            case R.id.edt_fromdate:

                datePicker("from");

                break;

            case R.id.edt_todate:

                datePicker("to");

                break;

            case R.id.img_back:
                Intent intent=new Intent(getApplicationContext(),ShowDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    public ArrayList<Entry> datavalues()
    {
        ArrayList<Entry> values=new ArrayList();
        if(!CollectionUtils.isEmpty(DataList))
        {
            for(int i=0;i<DataList.size();i++)
            {
                float val= Float.parseFloat(DataList.get(i));
                values.add(new Entry(i,val));
            }
        }
        return values;
    }

}
