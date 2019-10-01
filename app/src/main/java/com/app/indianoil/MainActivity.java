package com.app.indianoil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.indianoil.modal.ResultModal;
import com.app.indianoil.util.UtilClass;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_next;
    EditText edt_ro_code;
    ArrayList<ResultModal> productList= new ArrayList<>();
    ArrayList<ResultModal> productListrocode= new ArrayList<>();
    ImageView img_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListener();
    }

    public void init()
    {
        img_refresh=findViewById(R.id.img_refresh);
        edt_ro_code=findViewById(R.id.edt_ro_code);
        btn_next=findViewById(R.id.btn_next);
        Languageapi2();
    }

    public void setListener()
    {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                catch (Exception e)
                {

                }
                String rocode=edt_ro_code.getText().toString();

                if(TextUtils.isEmpty(rocode))
                {
                    Toast.makeText(getApplicationContext(),"Ro Code is required!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(isGoogleSheetContains(rocode))
                    {
                        updateRoCodeList(rocode);
                        UtilClass.saveArrayListrocode(productListrocode,UtilClass.resultlistrocode,getApplicationContext());
                        productListrocode=UtilClass.getArrayListrocode(UtilClass.resultlistrocode,getApplicationContext());
                        Log.e("productListrocode: ", String.valueOf(productListrocode.size()));
                        Intent intent=new Intent(getApplicationContext(),ShowDataActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Ro Code is wrong!",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        img_refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Languageapi2();
            }
        });
    }

    public Boolean isGoogleSheetContains(String roCode)
    {
        Boolean contains = false;

        for(int i=0;i<=productList.size();i++)
        {
            if(roCode.equalsIgnoreCase(productList.get(i).getRocode()))
            {
                contains=true;

            }
            break;
        }

        return contains;
    }

    public void updateRoCodeList(String rocode){

        for (int i = 0; i < productList.size(); i++) {

            if (rocode.equalsIgnoreCase(productList.get(i).getRocode())) {
                String date = productList.get(i).getDate();
                String time = productList.get(i).getTime();
                String roname = productList.get(i).getRoname();
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
                UtilClass.saveallsharedpref(getApplicationContext(), UtilClass.roname, roname);
                ResultModal resultModal = new ResultModal(date, time, rocode, roname, stockatstartofdayinkl, stockatendofdayinkl, receiptondateinkl, mspriceinrs,
                        hsdprice, lastpricechangedatetime, salefrommsfromdu1, salefrommsfromdu2, salefromhsdfromdu1, salefromhsdfromdu2);
                productListrocode.add(resultModal);

            }
        }
    }


    public  void Languageapi()
    {
        final KProgressHUD kud=KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Fetching data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        AndroidNetworking.get("http://gsx2json.com/api?id=1YVDKTuSuR-FHdWaAS3w5tiaNpL8MnrzzDYWmN7-ITo4")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener()
                {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        productList=new ArrayList<ResultModal>();
                        Log.e("onResponse: ", response.toString());
                        try {
                            JSONArray jsonArray=response.getJSONArray("rows");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String date=jsonObject.getString("date");
                                String time=jsonObject.getString("time");
                                String rocode=jsonObject.getString("rocode");
                                String roname=jsonObject.getString("roname");
                                String stockatstartofdayinkl=jsonObject.getString("stockatstartofdayinkl");
                                String stockatendofdayinkl=jsonObject.getString("stockatendofdayinkl");
                                String receiptondateinkl=jsonObject.getString("receiptondateinkl");
                                String mspriceinrs=jsonObject.getString("mspriceinrs");
                                String hsdprice=jsonObject.getString("hsdprice");
                                String lastpricechangedatetime=jsonObject.getString("lastpricechangedate-time");
                                String salefrommsfromdu1=jsonObject.getString("salefrommsfromdu-1");
                                String salefrommsfromdu2=jsonObject.getString("salefrommsfromdu-2");
                                String salefromhsdfromdu1=jsonObject.getString("salefromhsdfromdu-1");
                                String salefromhsdfromdu2=jsonObject.getString("salefromhsdfromdu-2");
                                ResultModal resultModal=new ResultModal(date,time,rocode,roname,stockatstartofdayinkl,stockatendofdayinkl,receiptondateinkl,mspriceinrs,hsdprice,lastpricechangedatetime,salefrommsfromdu1
                                        ,salefrommsfromdu2,salefromhsdfromdu1,salefromhsdfromdu2);
                                productList.add(resultModal);

                                UtilClass.saveArrayList(productList, UtilClass.resultlist,getApplicationContext());
                                productList=UtilClass.getArrayList(UtilClass.resultlist,getApplicationContext());
                                kud.dismiss();
                            }
                            Log.e( "size: ", String.valueOf(productList.size()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        kud.dismiss();
                        Log.e("onError: ",anError.getErrorDetail() );
                        Toast.makeText(getApplicationContext(),"Server Error Please Refresh",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public  void Languageapi2()
    {
        final KProgressHUD kud=KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Fetching data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        AndroidNetworking.get("https://spreadsheets.google.com/feeds/list/1YVDKTuSuR-FHdWaAS3w5tiaNpL8MnrzzDYWmN7-ITo4/od6/public/values?alt=json")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener()
                {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        productList=new ArrayList<ResultModal>();
                        Log.e("onResponse: ", response.toString());
                        try {
                            JSONObject js1=response.getJSONObject("feed");
                            JSONArray jsonArray=js1.getJSONArray("entry");

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                JSONObject jsd=jsonObject.getJSONObject("gsx$date");
                                String date=jsd.getString("$t");
                                JSONObject jst=jsonObject.getJSONObject("gsx$time");
                                String time=jst.getString("$t");
                                JSONObject jsro=jsonObject.getJSONObject("gsx$rocode");
                                String rocode=jsro.getString("$t");
                                JSONObject jsrn=jsonObject.getJSONObject("gsx$roname");
                                String roname=jsrn.getString("$t");
                                JSONObject jsss=jsonObject.getJSONObject("gsx$stockatstartofdayinkl");
                                String stockatstartofdayinkl=jsss.getString("$t");
                                JSONObject jsse=jsonObject.getJSONObject("gsx$stockatendofdayinkl");
                                String stockatendofdayinkl=jsse.getString("$t");
                                JSONObject jsr=jsonObject.getJSONObject("gsx$receiptondateinkl");
                                String receiptondateinkl=jsr.getString("$t");
                                JSONObject jsms=jsonObject.getJSONObject("gsx$mspriceinrs");
                                String mspriceinrs=jsms.getString("$t");
                                JSONObject jshs=jsonObject.getJSONObject("gsx$hsdprice");
                                String hsdprice=jshs.getString("$t");
                                JSONObject jsl=jsonObject.getJSONObject("gsx$lastpricechangedate-time");
                                String lastpricechangedatetime=jsl.getString("$t");
                                JSONObject jsmd1=jsonObject.getJSONObject("gsx$salefrommsfromdu-1");
                                String salefrommsfromdu1=jsmd1.getString("$t");
                                JSONObject jsmd2=jsonObject.getJSONObject("gsx$salefrommsfromdu-2");
                                String salefrommsfromdu2=jsmd2.getString("$t");
                                JSONObject jshd1=jsonObject.getJSONObject("gsx$salefromhsdfromdu-1");
                                String salefromhsdfromdu1=jshd1.getString("$t");
                                JSONObject jshd2=jsonObject.getJSONObject("gsx$salefromhsdfromdu-2");
                                String salefromhsdfromdu2=jshd2.getString("$t");
//
                                ResultModal resultModal=new ResultModal(date,time,rocode,roname,stockatstartofdayinkl,stockatendofdayinkl,receiptondateinkl,mspriceinrs,hsdprice,lastpricechangedatetime,salefrommsfromdu1
                                        ,salefrommsfromdu2,salefromhsdfromdu1,salefromhsdfromdu2);
                                productList.add(resultModal);

                                UtilClass.saveArrayList(productList, UtilClass.resultlist,getApplicationContext());
                                productList=UtilClass.getArrayList(UtilClass.resultlist,getApplicationContext());

                                Log.e( "date---: ", date);
                               // Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
                            }
                            Log.e( "size: ", String.valueOf(productList.size()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        kud.dismiss();
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        kud.dismiss();
                        Log.e("onError: ",anError.getErrorDetail() );
                        Toast.makeText(getApplicationContext(),"Server Error Please Refresh",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
