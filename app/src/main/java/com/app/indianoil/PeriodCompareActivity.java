package com.app.indianoil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PeriodCompareActivity extends AppCompatActivity  implements View.OnClickListener
{
    EditText edt_from_dateA,edt_to_dateA,edt_from_dateB,edt_to_dateB;
    TextView edt_result,edt_totalA,edt_totalB;
    ImageView img_back;
    Button btn_showdata;
    Date datestart,dateend;
    Calendar calendar2,calendar1;
    int mYear,mMonth,mDay;
    String date,fromdateA,fromDateB,toDateA,toDAteB,enddate,spinnervalue="",data;
    Spinner spin_columnname;
    ArrayList<String> datelistA=new ArrayList<>();
    ArrayList<String> datelistB=new ArrayList<>();
    ArrayList<String> DataListA=new ArrayList<String>();
    ArrayList<String> DataListB=new ArrayList<String>();
    ArrayList<ResultModal> productList= new ArrayList<>();
    String columnname[] = {"All","Stock at start of day (in KL)","Stock at end of day (in KL)","Receipt on date (in KL)",
            "MS Price (in Rs)","HSD Price","Last Price change date-time","Sale from MS from DU-1","Sale from MS from DU-2","Sale from HSD from DU-1",
            "Sale from HSD from DU-2"};

    String havestart="No" ,haveend="No",aaaa="No",bbbb="No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_compare);
        init();
        setListener();
    }

    public void init()
    {
        productList= UtilClass.getArrayListrocode(UtilClass.resultlistrocode,this);
        img_back=findViewById(R.id.img_back);
        spin_columnname=findViewById(R.id.spin_columnname);
        edt_from_dateA=findViewById(R.id.edt_from_dateA);
        edt_to_dateA=findViewById(R.id.edt_to_dateA);
        edt_from_dateB=findViewById(R.id.edt_from_dateB);
        edt_to_dateB=findViewById(R.id.edt_to_dateB);
        edt_result=findViewById(R.id.edt_result);
        edt_totalA=findViewById(R.id.edt_totalA);
        edt_totalB=findViewById(R.id.edt_totalB);
        btn_showdata=findViewById(R.id.btn_showdata);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item_layout, columnname);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//android.R.layout.simple_spinner_dropdown_item
        spin_columnname.setAdapter(dataAdapter);
    }

    public void setListener()
    {
        edt_from_dateA.setOnClickListener(this);
        edt_to_dateA.setOnClickListener(this);
        edt_from_dateB.setOnClickListener(this);
        edt_to_dateB.setOnClickListener(this);
        btn_showdata.setOnClickListener(this);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.edt_from_dateA:
                datePicker("fromA");
                break;
            case R.id.edt_to_dateA:
                datePicker("toA");
                break;
            case R.id.edt_from_dateB:
                datePicker("fromB");
                break;
            case R.id.edt_to_dateB:
                datePicker("toB");
                break;
            case R.id.btn_showdata:
                if(TextUtils.isEmpty(spinnervalue))
                {
                    Toast.makeText(getApplicationContext(),"Please select Column Name first!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(TextUtils.isEmpty(fromdateA))
                    {
                        Toast.makeText(getApplicationContext(),"Please select From Date of Period A!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(toDateA))
                    {
                        Toast.makeText(getApplicationContext(),"Please select To Date of Period A!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(fromDateB))
                    {
                        Toast.makeText(getApplicationContext(),"Please select From Date of Period B!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(toDAteB))
                    {
                        Toast.makeText(getApplicationContext(),"Please select To Date of Period B!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        List<Date> dates=getDates(fromdateA,toDateA);
                        DateFormat formatter = new SimpleDateFormat("d/M/yy");
                        for(Date date:dates)
                        {
                            String today = formatter.format(date);
                            for(int j=0;j<productList.size();j++)
                            {
                                if(today.equalsIgnoreCase(productList.get(j).getDate()))
                                {
                                    Log.e("onClick: ","yes" );
                                    aaaa="Yes";
                                    columnselectstart(spinnervalue,j);
                                }
                            }
                        }

                        List<Date> dates1=getDates(fromDateB,toDAteB);
                        for(Date date:dates1)
                        {
                            String today = formatter.format(date);
                            for(int j=0;j<productList.size();j++)
                            {
                                if(today.equalsIgnoreCase(productList.get(j).getDate()))
                                {
                                    bbbb="Yes";
                                    Log.e("onClick: ","yesB" );
                                    columnselectend(spinnervalue,j);
                                }
                            }
                        }

                        Log.e("result",aaaa + "..."+bbbb );
                        if(aaaa.equalsIgnoreCase("No")&&bbbb.equalsIgnoreCase("Yes"))
                        {
                            Toast.makeText(getApplicationContext(),"Period A Dates Not Available to compare!",Toast.LENGTH_SHORT).show();
                            aaaa="No";
                            bbbb="No";
                        }
                        else if(bbbb.equalsIgnoreCase("No")&&aaaa.equalsIgnoreCase("Yes"))
                        {
                            Toast.makeText(getApplicationContext(),"Period B Dates Not Available to compare!",Toast.LENGTH_SHORT).show();
                            aaaa="No";
                            bbbb="No";
                        }
                        else if(bbbb.equalsIgnoreCase("No")&&aaaa.equalsIgnoreCase("No"))
                        {
                            Toast.makeText(getApplicationContext(),"Dates Not Available to compare!",Toast.LENGTH_SHORT).show();
                            aaaa="No";
                            bbbb="No";
                        }
                        else
                        {
                            Log.e("mmmmmmmmm: ","mmmmmm" );
                            String dataa= String.valueOf(sumValuesA(DataListA));
                            edt_totalA.setText(dataa);
                            String datab=String.valueOf(sumValuesA(DataListB));
                            edt_totalB.setText(datab);
                            double i,j,diff;
                            i= Double.parseDouble(dataa);
                            j= Double.parseDouble(datab);
                            diff=j-i;
                            double roundOff = Math.round(diff*100)/100;
                            String result= String.valueOf(roundOff);
                            edt_result.setText(result);
                        }
                }

                }

                break;

            case R.id.img_back:
                Intent intent=new Intent(getApplicationContext(),ShowDataActivity.class);
                startActivity(intent);
                break;
        }
    }

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

                            //month = "0" + (monthOfYear);
                            month = String.valueOf(monthOfYear);
                            Log.e("monthOfYear: ", month);
                        }
                        else
                        {
                            month= String.valueOf(monthOfYear);
                        }
                        if(dayOfMonth < 10){

//                            day  ="0" + dayOfMonth;
                            day  = String.valueOf(dayOfMonth);
                            Log.e("dayOfMonth: ",day);
                        }
                        else
                        {
                            day= String.valueOf(dayOfMonth);
                        }
//                        mnth= Integer.parseInt(month);
                        date = day+"/"+ (month )+ "/"+year;

                        //  date = "0" + (monthOfYear + 1)+"/"+dayOfMonth+ "/"+year;

                        if(type.equalsIgnoreCase("fromA"))
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar1 = Calendar.getInstance();
                            try {
                                datestart=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");

                            // Calendar calendar2 = Calendar.getInstance();

//                            try {
                            fromdateA = formatter.format(datestart);
                            //  startdate= String.valueOf(datestart);
                            Log.e("startdate: ",fromdateA );
                            edt_from_dateA.setText(fromdateA);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar1.setTime(datestart);

                            Log.e("Compare Result start : " , String.valueOf(datestart));
                        }
                        else if(type.equalsIgnoreCase("toA"))
                        {
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar2 = Calendar.getInstance();
                            try {
                                dateend=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            try
//                            {
                            toDateA = formatter.format(dateend);
                            // enddate= String.valueOf(dateend);
                            edt_to_dateA.setText(toDateA);
                            Log.e("enddate: ",toDateA );
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar2.setTime(dateend);
                            // calendar2.setTime(date2);

                            Log.e("Compare Result end: " , String.valueOf(dateend));
                        }

                        else if(type.equalsIgnoreCase("fromB"))
                        {
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar2 = Calendar.getInstance();
                            try {
                                dateend=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            try
//                            {
                            fromDateB = formatter.format(dateend);
                            // enddate= String.valueOf(dateend);
                            edt_from_dateB.setText(fromDateB);
                            Log.e("enddate: ",fromDateB );
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar2.setTime(dateend);
                            // calendar2.setTime(date2);

                            Log.e("Compare Result end: " , String.valueOf(dateend));
                        }
                        else if(type.equalsIgnoreCase("toB"))
                        {
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar2 = Calendar.getInstance();
                            try {
                                dateend=dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            try
//                            {
                            toDAteB = formatter.format(dateend);
                            // enddate= String.valueOf(dateend);
                            edt_to_dateB.setText(toDAteB);
                            Log.e("enddate: ",toDAteB );
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar2.setTime(dateend);
                            // calendar2.setTime(date2);

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
            havestart="yes";
            data=productList.get(i).getStockstart();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Stock at end of day (in KL)"))
        {
            havestart="yes";
            data=productList.get(i).getStockend();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Receipt on date (in KL)"))
        {
            havestart="yes";
            data=productList.get(i).getReceipt();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("MS Price (in Rs)"))
        {
            havestart="yes";
            data=productList.get(i).getMsprice().toString();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("HSD Price"))
        {
            havestart="yes";
            data=productList.get(i).getHsdprice();
            DataListA.add(data);
        }
//        else if(colmnname.equalsIgnoreCase("Last Price change date-time"))
//        {
//            selectedcolumnend=productList.get(i).getLastpricechange();
//        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-1"))
        {
            havestart="yes";
            data=productList.get(i).getMs_du1();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-2"))
        {
            havestart="yes";
            data=productList.get(i).getMs_du2();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-1"))
        {
            havestart="yes";
            data=productList.get(i).getHsd_du1();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-2"))
        {
            havestart="yes";
            data=productList.get(i).getHsd_du2();
            DataListA.add(data);
        }
        else if(colmnname.equalsIgnoreCase("All"))
        {
            Toast.makeText(getApplicationContext(),"Please select one column",Toast.LENGTH_SHORT).show();
        }
    }

    public void columnselectend(String colmnname,int i)
    {
        Log.e( "columnselectend: ","columnselectend" );
        if(colmnname.equalsIgnoreCase("Stock at start of day (in KL)"))
        {
            haveend="yes";
            data=productList.get(i).getStockstart();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Stock at end of day (in KL)"))
        {
            haveend="yes";
            data=productList.get(i).getStockend();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Receipt on date (in KL)"))
        {
            haveend="yes";
            data=productList.get(i).getReceipt();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("MS Price (in Rs)"))
        {
            haveend="yes";
            data=productList.get(i).getMsprice();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("HSD Price"))
        {
            haveend="yes";
            data=productList.get(i).getHsdprice();
            DataListB.add(data);
        }
//        else if(colmnname.equalsIgnoreCase("Last Price change date-time"))
//        {
//            selectedcolumnend=productList.get(i).getLastpricechange();
//        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-1"))
        {
            haveend="yes";
            data=productList.get(i).getMs_du1();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from MS from DU-2"))
        {
            haveend="yes";
            data=productList.get(i).getMs_du2();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-1"))
        {
            haveend="yes";
            data=productList.get(i).getHsd_du1();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("Sale from HSD from DU-2"))
        {
            haveend="yes";
            data=productList.get(i).getHsd_du2();
            DataListB.add(data);
        }
        else if(colmnname.equalsIgnoreCase("All"))
        {
            Toast.makeText(getApplicationContext(),"Please select one column",Toast.LENGTH_SHORT).show();
        }
    }


    public double sumValuesA(ArrayList data)
    {
        double sum = 0;

        for(int i = 0; i < data.size(); i++)
        {
            double j=Double.parseDouble(String.valueOf(data.get(i)));
            sum = sum + j;
        }        double roundOff = Math.round(sum*100)/100;
        return roundOff;
    }
}
