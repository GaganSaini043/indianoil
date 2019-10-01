package com.app.indianoil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.indianoil.adapter.ResultAdapter;
import com.app.indianoil.modal.ResultModal;
import com.app.indianoil.util.UtilClass;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<ResultModal> showdata= new ArrayList<>();
    //the recyclerview
    RecyclerView recyclerView;
    ResultAdapter adapter;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        init();
    }

    public void init()
    {
        img_back=findViewById(R.id.img_back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showdata=UtilClass.getArrayListrocode(UtilClass.showdatalist,getApplicationContext());

        setdata();
        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),ShowDataActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setdata()
    {
        adapter = new ResultAdapter(ResultActivity.this, showdata);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

    }

}
