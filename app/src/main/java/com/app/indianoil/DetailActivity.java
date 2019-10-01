package com.app.indianoil;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.indianoil.util.UtilClass;

public class DetailActivity extends AppCompatActivity
{
    RelativeLayout rel_date,rel_time,relrocode,relstartday,relendday,relreceipt,relmsprice,relhsdprice,rellastpricechange,relsalemsdu1,
            relsalemsdu2,relsalehsddu1,relsalehsddu2;
    TextView txt_date,txt_time,txt_rocode,txt_stockstartday,txt_stockendday,txt_receipt,txt_msprice,txt_hsdprice,txt_lastprice,txt_salemsdu1,
            txt_salemsdu2,txt_salehsddu1,txt_salehsddu2;
    String columnname,date,time,rocode,stockstart,stockend,receipt,msprice,hsdprice,lastprice,msdu1,msdu2,hsddu1,hsddu2;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        columnname= UtilClass.getallsharedpref(getApplicationContext(),UtilClass.columnname);
        Intent intent = getIntent();
        date=intent.getStringExtra("date");
        time=intent.getStringExtra("time");
        rocode=intent.getStringExtra("rocode");
        stockstart=intent.getStringExtra("stockstart");
        stockend=intent.getStringExtra("stockend");
        receipt=intent.getStringExtra("receipt");
        msprice=intent.getStringExtra("msprice");
        hsdprice=intent.getStringExtra("hsdprice");
        lastprice=intent.getStringExtra("lastprice");
        msdu1=intent.getStringExtra("msdu1");
        msdu2=intent.getStringExtra("msdu2");
        hsddu1=intent.getStringExtra("hsddu1");
        hsddu2=intent.getStringExtra("hsddu2");

        init();
        setData();
    }

    public void init()
    {
        img_back=findViewById(R.id.img_back);
        rel_date=findViewById(R.id.rel_date);
        rel_time=findViewById(R.id.rel_time);
        relrocode=findViewById(R.id.relrocode);
        relstartday=findViewById(R.id.relstartday);
        relendday=findViewById(R.id.relendday);
        relreceipt=findViewById(R.id.relreceipt);
        relmsprice=findViewById(R.id.relmsprice);
        relhsdprice=findViewById(R.id.relhsdprice);
        rellastpricechange=findViewById(R.id.rellastpricechange);
        relsalemsdu1=findViewById(R.id.relsalemsdu1);
        relsalemsdu2=findViewById(R.id.relsalemsdu2);
        relsalehsddu1=findViewById(R.id.relsalehsddu1);
        relsalehsddu2=findViewById(R.id.relsalehsddu2);
        txt_date=findViewById(R.id.txt_date);
        txt_time=findViewById(R.id.txt_time);
        txt_rocode=findViewById(R.id.txt_rocode);
        txt_stockstartday=findViewById(R.id.txt_stockstartday);
        txt_stockendday=findViewById(R.id.txt_stockendday);
        txt_receipt=findViewById(R.id.txt_receipt);
        txt_msprice=findViewById(R.id.txt_msprice);
        txt_hsdprice=findViewById(R.id.txt_hsdprice);
        txt_lastprice=findViewById(R.id.txt_lastprice);
        txt_salemsdu1=findViewById(R.id.txt_salemsdu1);
        txt_salemsdu2=findViewById(R.id.txt_salemsdu2);
        txt_salehsddu1=findViewById(R.id.txt_salehsddu1);
        txt_salehsddu2=findViewById(R.id.txt_salehsddu2);

        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DetailActivity.super.onBackPressed();
            }
        });
    }

    public void setData()
    {
        txt_date.setText(date);
        txt_time.setText(time);
        txt_rocode.setText(rocode);

        if(columnname.equalsIgnoreCase("All"))
        {
            txt_stockstartday.setText(stockstart);
            txt_stockendday.setText(stockend);
            txt_receipt.setText(receipt);
            txt_msprice.setText(msprice);
            txt_hsdprice.setText(hsdprice);
            txt_lastprice.setText(lastprice);
            txt_salemsdu1.setText(msdu1);
            txt_salemsdu2.setText(msdu2);
            txt_salehsddu1.setText(hsddu1);
            txt_salehsddu2.setText(hsddu2);
        }
        else if(columnname.equalsIgnoreCase("Stock at start of day (in KL)"))
        {
            txt_stockstartday.setText(stockstart);

            relstartday.setVisibility(View.VISIBLE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Stock at end of day (in KL)"))
        {
            txt_stockendday.setText(stockend);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.VISIBLE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Receipt on date (in KL)"))
        {
            txt_receipt.setText(receipt);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.VISIBLE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("MS Price (in Rs)"))
        {
            txt_msprice.setText(msprice);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.VISIBLE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("HSD Price"))
        {
            txt_hsdprice.setText(hsdprice);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.VISIBLE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Last Price change date-time"))
        {
            txt_lastprice.setText(lastprice);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.VISIBLE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Sale from MS from DU-1"))
        {
            txt_salemsdu1.setText(msdu1);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.VISIBLE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Sale from MS from DU-2"))
        {
            txt_salemsdu2.setText(msdu2);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.VISIBLE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Sale from HSD from DU-1"))
        {
            txt_salehsddu1.setText(hsddu1);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.VISIBLE);
            relsalehsddu2.setVisibility(View.GONE);
        }
        else if(columnname.equalsIgnoreCase("Sale from HSD from DU-2"))
        {
            txt_salehsddu2.setText(hsddu2);

            relstartday.setVisibility(View.GONE);
            relendday.setVisibility(View.GONE);
            relreceipt.setVisibility(View.GONE);
            relmsprice.setVisibility(View.GONE);
            relhsdprice.setVisibility(View.GONE);
            rellastpricechange.setVisibility(View.GONE);
            relsalemsdu1.setVisibility(View.GONE);
            relsalemsdu2.setVisibility(View.GONE);
            relsalehsddu1.setVisibility(View.GONE);
            relsalehsddu2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed()
    {
    }
}
