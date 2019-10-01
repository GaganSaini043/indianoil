package com.app.indianoil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.indianoil.modal.ResultModal;
import com.app.indianoil.util.UtilClass;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowDataActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_back, img_iconresult;
    EditText edt_from_date, edt_to_date;
    TextView edt_absolute, edt_percent;
    Button btn_showdata, btn_result, btn_compare, btn_showfulldata,btn_analysis;
    RelativeLayout rel_absolute;
    String date, selectedcolumnstart, selectedcolumnend;
    int mYear, mMonth, mDay;
    Float percentage;
    Date datestart, dateend;
    Calendar calendar2, calendar1;
    String today, startdate, enddate, spinnervalue;
    Spinner spin_columnname;
    TextView txt_roname;
    ArrayList<ResultModal> productList = new ArrayList<>();
    ArrayList<ResultModal> showdatalist = new ArrayList<>();
    ArrayList<String> dateslist = new ArrayList<>();
    ArrayList<String> datesavailable = new ArrayList<>();
    String columnname[] = {"All", "Stock at start of day (in KL)", "Stock at end of day (in KL)", "Receipt on date (in KL)",
            "MS Price (in Rs)", "HSD Price", "Last Price change date-time", "Sale from MS from DU-1", "Sale from MS from DU-2", "Sale from HSD from DU-1",
            "Sale from HSD from DU-2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        init();
        setListener();
    }

    public void init() {
        //initialization
        img_back = findViewById(R.id.img_back);
        img_iconresult = findViewById(R.id.img_iconresult);
        txt_roname = findViewById(R.id.txt_roname);
        spin_columnname = findViewById(R.id.spin_columnname);
        edt_from_date = findViewById(R.id.edt_from_date);
        edt_to_date = findViewById(R.id.edt_to_date);
        edt_absolute = findViewById(R.id.edt_absolute);
//        edt_percent=findViewById(R.id.edt_percent);
        btn_showdata = findViewById(R.id.btn_showdata);
        btn_compare = findViewById(R.id.btn_compare);
        btn_result = findViewById(R.id.btn_result);
        btn_showfulldata = findViewById(R.id.btn_showfulldata);
        btn_analysis= findViewById(R.id.btn_analysis);
        rel_absolute = findViewById(R.id.rel_absolute);
//        rel_percent=findViewById(R.id.rel_percent);
        txt_roname.setText(UtilClass.getallsharedpref(getApplicationContext(), UtilClass.roname));
        productList = UtilClass.getArrayListrocode(UtilClass.resultlistrocode, this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item_layout, columnname);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//android.R.layout.simple_spinner_dropdown_item
        spin_columnname.setAdapter(dataAdapter);
    }

    public void setListener() {
        btn_showdata.setOnClickListener(this);
        edt_from_date.setOnClickListener(this);
        edt_to_date.setOnClickListener(this);
        btn_result.setOnClickListener(this);
        btn_compare.setOnClickListener(this);
        btn_showfulldata.setOnClickListener(this);
        btn_analysis.setOnClickListener(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        spin_columnname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnervalue = adapterView.getItemAtPosition(i).toString();
                UtilClass.saveallsharedpref(getApplicationContext(), UtilClass.columnname, spinnervalue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_showdata:
                rel_absolute.setVisibility(View.GONE);
                String edtstartdate = edt_from_date.getText().toString();
                String edtenddate = edt_to_date.getText().toString();
                if (TextUtils.isEmpty(edtstartdate) || TextUtils.isEmpty(edtenddate)) {
                    Toast.makeText(getApplicationContext(), "Please select date first", Toast.LENGTH_SHORT).show();
                } else {
                    showdatalist = new ArrayList<ResultModal>();
                    Log.e("spinner: ", spinnervalue);
                    List<Date> dates = getDates(startdate, enddate);

                    DateFormat formatter = new SimpleDateFormat("d/M/yy");

                    if (!(dates.size() == 0)) {
                        if (dates.size() <= 5) {
                            Log.e("dateslistsize: ", String.valueOf(dates.size()));

                            for (Date date : dates) {
                                Log.e("productListsize: ", String.valueOf(productList.size()));
                                today = formatter.format(date);
                                // Log.e("todaydates: ", today);
                                for (int i = 0; i < productList.size(); i++) {
                                    // Log.e("today1: ", String.valueOf(today)+"..productList11.."+productList.get(i).getDate());
                                    if (today.equalsIgnoreCase(productList.get(i).getDate())) {
                                        String rocode = productList.get(i).getRocode();
                                        String roname = productList.get(i).getRoname();
                                        String date1 = productList.get(i).getDate();
                                        String time = productList.get(i).getTime();
                                        String stockatstartofdayinkl = productList.get(i).getStockstart();
                                        String stockatendofdayinkl = productList.get(i).getStockend();
                                        String receiptondateinkl = productList.get(i).getReceipt();
                                        String mspriceinrs = productList.get(i).getMsprice();
                                        String hsdprice = productList.get(i).getHsdprice();
                                        String lastpricechangedatetime = productList.get(i).getLastpricechange();
                                        String salefrommsfromdu1 = productList.get(i).getMs_du1();
                                        String salefrommsfromdu2 = productList.get(i).getMs_du2();
                                        String salefromhsdfromdu1 = productList.get(i).getHsd_du1();
                                        String salefromhsdfromdu2 = productList.get(i).getHsd_du2();
                                        ResultModal resultModal = new ResultModal(date1, time, rocode, roname, stockatstartofdayinkl, stockatendofdayinkl, receiptondateinkl, mspriceinrs,
                                                hsdprice, lastpricechangedatetime, salefrommsfromdu1, salefrommsfromdu2, salefromhsdfromdu1, salefromhsdfromdu2);
                                        showdatalist.add(resultModal);
                                        Log.e("today: ", String.valueOf(today) + "..productList.." + productList.get(i).getDate());
                                    }
                                }
                                UtilClass.saveArrayListrocode(showdatalist, UtilClass.showdatalist, getApplicationContext());
                                showdatalist = UtilClass.getArrayListrocode(UtilClass.showdatalist, getApplicationContext());
                                Log.e("showdatalist: ", String.valueOf(showdatalist.size()));
                            }

                            if (showdatalist.size() == 0) {
                                Toast.makeText(getApplicationContext(), "Data of these dates is not available!", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "you can select maximum 5 dates!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (dates.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Please select valid dates!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.btn_result:
                Log.e("onClick: ", "start: " + startdate + " end : " + enddate + " column: " + spinnervalue);
                if (spinnervalue.equalsIgnoreCase("Last Price change date-time")) {
                    rel_absolute.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please select valid column", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(startdate) && !(TextUtils.isEmpty(enddate))) {
                        Toast.makeText(getApplicationContext(), "Please select From Date!", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(enddate) && !(TextUtils.isEmpty(startdate))) {
                        Toast.makeText(getApplicationContext(), "Please select To Date!", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(startdate) && TextUtils.isEmpty(enddate)) {
                        Toast.makeText(getApplicationContext(), "Please select Dates!", Toast.LENGTH_SHORT).show();
                    } else {
                        comparisonByDate(startdate, enddate, spinnervalue);

                        if (!(TextUtils.isEmpty(selectedcolumnstart)) && !(TextUtils.isEmpty(selectedcolumnend))) {
                           calculatedata();
                        }
                        else if (!(TextUtils.isEmpty(selectedcolumnend)) && (TextUtils.isEmpty(selectedcolumnstart))) {
                            rel_absolute.setVisibility(View.GONE);
                            String date = edt_from_date.getText().toString();
                            GetDatesAvailable(date, "Start date not available!", "start");
                        } else if (!(TextUtils.isEmpty(selectedcolumnstart)) && (TextUtils.isEmpty(selectedcolumnend))) {
                            rel_absolute.setVisibility(View.GONE);
                            String date = edt_to_date.getText().toString();
                            GetDatesAvailable(date, "End date not available!", "end");
                        } else if ((TextUtils.isEmpty(selectedcolumnstart)) && (TextUtils.isEmpty(selectedcolumnend))) {
                            rel_absolute.setVisibility(View.GONE);
                            String date = edt_from_date.getText().toString();
                            GetDatesAvailable(date, "Dates not available!", "no");
                        } else {
                            String date = edt_to_date.getText().toString();
                            GetDatesAvailable(date, "date not available!", "no");
                            rel_absolute.setVisibility(View.GONE);
                        }
                        selectedcolumnend = "";
                        selectedcolumnstart = "";
                    }
                }

                break;

            case R.id.edt_from_date:
                datePicker("start");
                break;

            case R.id.edt_to_date:
                datePicker("end");
                break;

            case R.id.btn_compare:
                Intent intent = new Intent(getApplicationContext(), PeriodCompareActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_showfulldata:
                showAllData();
                break;

            case R.id.btn_analysis:
                Intent intent1 = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //method to show Calender
    public void datePicker(final String type) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = null, yearr, day = null;
                        int mnth;
                        monthOfYear = (monthOfYear + 1);
                        if (monthOfYear < 10) {

                            //month = "0" + (monthOfYear);
                            month = String.valueOf(monthOfYear);
                            Log.e("monthOfYear: ", month);
                        } else {
                            month = String.valueOf(monthOfYear);
                        }
                        if (dayOfMonth < 10) {

//                            day  ="0" + dayOfMonth;
                            day = String.valueOf(dayOfMonth);
                            Log.e("dayOfMonth: ", day);
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }
//                        mnth= Integer.parseInt(month);
                        date = day + "/" + (month) + "/" + year;

                        //  date = "0" + (monthOfYear + 1)+"/"+dayOfMonth+ "/"+year;

                        if (type.equalsIgnoreCase("start")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar1 = Calendar.getInstance();
                            try {
                                datestart = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");

                            // Calendar calendar2 = Calendar.getInstance();

//                            try {
                            startdate = formatter.format(datestart);
                            //  startdate= String.valueOf(datestart);
                            Log.e("startdate: ", startdate);
                            edt_from_date.setText(startdate);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar1.setTime(datestart);

                            Log.e("Compare Result start : ", String.valueOf(datestart));
                        } else if (type.equalsIgnoreCase("end")) {
                            DateFormat formatter = new SimpleDateFormat("d/M/yy");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                            calendar2 = Calendar.getInstance();
                            try {
                                dateend = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            try
//                            {
                            enddate = formatter.format(dateend);
                            // enddate= String.valueOf(dateend);
                            edt_to_date.setText(enddate);
                            Log.e("enddate: ", enddate);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            calendar2.setTime(dateend);
                            // calendar2.setTime(date2);

                            Log.e("Compare Result end: ", String.valueOf(dateend));
                        }
                    }

                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    //method for date difference

    public void datediff(Date datestart, Date enddate) {
        long diff = datestart.getTime() - enddate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24 + 1;

        Log.e("datediff: ", String.valueOf(days));
    }

    private static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd/MM/yy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @Override
    public void onBackPressed() {

    }

    public void comparisonByDate(String start, String end, String column) {
        for (int i = 0; i < productList.size(); i++) {
            if (start.equalsIgnoreCase(productList.get(i).getDate().toString())) {
                Log.e("comparisonByDate: ", "start" + start);
                columnselectstart(column, i);
            }
            if (end.equalsIgnoreCase(productList.get(i).getDate().toString())) {
                Log.e("comparisonByDate: ", "end" + end);
                columnselectend(column, i);
            }
        }
    }

    public void columnselectstart(String colmnname, int i) {
        Log.e("columnselectstart: ", "columnselectstart");
        if (colmnname.equalsIgnoreCase("Stock at start of day (in KL)")) {
            selectedcolumnstart = productList.get(i).getStockstart();
        } else if (colmnname.equalsIgnoreCase("Stock at end of day (in KL)")) {
            selectedcolumnstart = productList.get(i).getStockend();
        } else if (colmnname.equalsIgnoreCase("Receipt on date (in KL)")) {
            selectedcolumnstart = productList.get(i).getReceipt();
        } else if (colmnname.equalsIgnoreCase("MS Price (in Rs)")) {
            selectedcolumnstart = productList.get(i).getMsprice();
        } else if (colmnname.equalsIgnoreCase("HSD Price")) {
            selectedcolumnstart = productList.get(i).getHsdprice();
        }
//        else if(colmnname.equalsIgnoreCase("Last Price change date-time"))
//        {
//            //selectedcolumnstart=productList.get(i).getLastpricechange();
//
//        }
        else if (colmnname.equalsIgnoreCase("Sale from MS from DU-1")) {
            selectedcolumnstart = productList.get(i).getMs_du1();
        } else if (colmnname.equalsIgnoreCase("Sale from MS from DU-2")) {
            selectedcolumnstart = productList.get(i).getMs_du2();
        } else if (colmnname.equalsIgnoreCase("Sale from HSD from DU-1")) {
            selectedcolumnstart = productList.get(i).getHsd_du1();
        } else if (colmnname.equalsIgnoreCase("Sale from HSD from DU-2")) {
            selectedcolumnstart = productList.get(i).getHsd_du2();
        } else if (colmnname.equalsIgnoreCase("All")) {
            Toast.makeText(getApplicationContext(), "Please select one column", Toast.LENGTH_SHORT).show();
        }
    }

    public void columnselectend(String colmnname, int i) {
        Log.e("columnselectend: ", "columnselectend");
        if (colmnname.equalsIgnoreCase("Stock at start of day (in KL)")) {
            selectedcolumnend = productList.get(i).getStockstart();
        } else if (colmnname.equalsIgnoreCase("Stock at end of day (in KL)")) {
            selectedcolumnend = productList.get(i).getStockend();
        } else if (colmnname.equalsIgnoreCase("Receipt on date (in KL)")) {
            selectedcolumnend = productList.get(i).getReceipt();
        } else if (colmnname.equalsIgnoreCase("MS Price (in Rs)")) {
            selectedcolumnend = productList.get(i).getMsprice();
        } else if (colmnname.equalsIgnoreCase("HSD Price")) {
            selectedcolumnend = productList.get(i).getHsdprice();
        }
//        else if(colmnname.equalsIgnoreCase("Last Price change date-time"))
//        {
//            selectedcolumnend=productList.get(i).getLastpricechange();
//        }
        else if (colmnname.equalsIgnoreCase("Sale from MS from DU-1")) {
            selectedcolumnend = productList.get(i).getMs_du1();
        } else if (colmnname.equalsIgnoreCase("Sale from MS from DU-2")) {
            selectedcolumnend = productList.get(i).getMs_du2();
        } else if (colmnname.equalsIgnoreCase("Sale from HSD from DU-1")) {
            selectedcolumnend = productList.get(i).getHsd_du1();
        } else if (colmnname.equalsIgnoreCase("Sale from HSD from DU-2")) {
            selectedcolumnend = productList.get(i).getHsd_du2();
        } else if (colmnname.equalsIgnoreCase("All")) {
            Toast.makeText(getApplicationContext(), "Please select one column", Toast.LENGTH_SHORT).show();
        }
    }

    public void showAllData() {
        UtilClass.saveArrayListrocode(productList, UtilClass.showdatalist, getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        startActivity(intent);
    }

    public void GetDatesAvailable(String start, String text1, String type) {
        datesavailable = new ArrayList<>();
        Date date = null;
        Date date1 = null;
        Date date2 = null;

        SimpleDateFormat format = new SimpleDateFormat("d/M/yy");
        try {
            date = format.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date1 = date;
        date2 = date;
        Date increment, decrement;
//        = DateUtils.addDays(date, 1);
//        String dateTime = format.format(increment);
        increment = DateUtils.addDays(date1, 1);
        String dateTime = format.format(increment);
        for (int i = 0; i <= 90; i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (dateTime.equalsIgnoreCase(productList.get(j).getDate())) {
                    if (datesavailable.size() < 2) {
                        datesavailable.add(dateTime);
                    }
                }
            }
            increment = DateUtils.addDays(increment, 1);
            dateTime = format.format(increment);
        }

        decrement = DateUtils.addDays(date2, -1);
        String dateTime1 = format.format(decrement);
        for (int i = 0; i <= 90; i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (dateTime1.equalsIgnoreCase(productList.get(j).getDate())) {
                    if (datesavailable.size() < 4) {
                        datesavailable.add(dateTime1);
                    }
                }
            }
            decrement = DateUtils.addDays(decrement, -1);
            dateTime1 = format.format(decrement);
            // date2=increment;
        }

        if (type.equalsIgnoreCase("start")) {
            showresultnext(datesavailable, "start");
        }
        if (type.equalsIgnoreCase("end")) {
            showresultnext(datesavailable, "end");
        }


        Log.e("GetDatesAvailable: ", String.valueOf(datesavailable.size()));
        String text = "", textfinal = "";
        for (int i = 0; i < datesavailable.size(); i++) {
            Log.e("GetDatesList: ", datesavailable.get(i).toString());
            text = datesavailable.get(i).toString() + " , ";
            textfinal = textfinal + text;
        }
        String a = textfinal.substring(0, textfinal.lastIndexOf(","));
        Ok_button_Dialog_Dismiss(ShowDataActivity.this, a, text1);
    }

    public void datesget(int i) {
        String date1, date2, date3, date4;
        if (!(datesavailable.size() == 0)) {
            int first, second, third, forth;
            first = i - 2;
            second = i - 1;
            third = i + 1;
            forth = i + 2;
            if (!(datesavailable.get(first).toString().equals(null))) {
                date1 = datesavailable.get(first).toString();
                Log.e("firstdsate: ", date1);
            }
            if (!(datesavailable.get(second).toString().equals(null))) {
                date2 = datesavailable.get(second).toString();
                Log.e("seconddsate: ", date2);
            }
            if (!(datesavailable.get(third).toString().equals(null))) {
                date3 = datesavailable.get(third).toString();
                Log.e("thirddsate: ", date3);
            }
            if (!(datesavailable.get(forth).toString().equals(null))) {
                date4 = datesavailable.get(forth).toString();
                Log.e("forthdsate: ", date4);
            }
        }
    }

    public static void Ok_button_Dialog_Dismiss(final Context context, final String text, final String toptext) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ok_button_dialog);
        final TextView title = (TextView) dialog.findViewById(R.id.title);
        final TextView textView = (TextView) dialog.findViewById(R.id.desc);
        final Button buttonok = (Button) dialog.findViewById(R.id.buttonOk);
        final ImageView iconimage = (ImageView) dialog.findViewById(R.id.imageView);
        iconimage.setVisibility(View.GONE);
        title.setText(toptext);
        buttonok.setText("Ok");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        textView.setText(text);
        buttonok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showresultnext(ArrayList list, String type) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (list.get(i).equals(productList.get(j).getDate())) {
                    Toast.makeText(getApplicationContext(), list.get(i).toString(), Toast.LENGTH_SHORT).show();
                    if (type.equalsIgnoreCase("start")) {
                        comparisonByDate(list.get(i).toString(), enddate, spinnervalue);
                        edt_from_date.setText(list.get(i).toString());
                        calculatedata();

                    }
                        else if (type.equalsIgnoreCase("end")) {
                            comparisonByDate(startdate, list.get(i).toString(), spinnervalue);
                            edt_to_date.setText(list.get(i).toString());
                            calculatedata();

                        }
                    break;
                    }
                }
            }
        }


    public void calculatedata()
    {
        if (!(TextUtils.isEmpty(selectedcolumnstart)) && !(TextUtils.isEmpty(selectedcolumnend)))
        {
            float start = Float.parseFloat(selectedcolumnstart);
            float end = Float.parseFloat(selectedcolumnend);
            float differnce = end - start;
            double roundOff = Math.round(differnce * 100) / 100;
            rel_absolute.setVisibility(View.VISIBLE);
            if (differnce > 0) {
                img_iconresult.setImageResource(0);
                img_iconresult.setImageDrawable(getResources().getDrawable(R.drawable.caret_arrow_up));
                img_iconresult.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.greenlime));
            } else if (differnce < 0) {
                img_iconresult.setImageResource(0);
                img_iconresult.setImageDrawable(getResources().getDrawable(R.drawable.sort_down));
                img_iconresult.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red));
            }
            else if(differnce == 0)
            {
                img_iconresult.setImageResource(0);
            }
            roundOff = Math.abs(roundOff);
            edt_absolute.setText(String.valueOf(roundOff));
        }
    }
}
