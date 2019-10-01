package com.app.indianoil.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.indianoil.DetailActivity;
import com.app.indianoil.R;
import com.app.indianoil.modal.ResultModal;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ProductViewHolder>
{
    private Context mCtx;

    //we are storing all the products in a list
    private List<ResultModal> productList;

    //getting the context and product list with constructor
    public ResultAdapter(Context mCtx, List<ResultModal> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_layout_cardview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final ResultModal product = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_date.setText(":  "+product.getDate());
        holder.txt_time.setText(":  "+product.getTime());
        holder.txt_rocode.setText(":  "+product.getRocode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(mCtx, DetailActivity.class);
                intent.putExtra("date",product.getDate());
                intent.putExtra("time",product.getTime());
                intent.putExtra("rocode",product.getRocode());
                intent.putExtra("stockstart",product.getStockstart());
                intent.putExtra("stockend",product.getStockend());
                intent.putExtra("receipt",product.getReceipt());
                intent.putExtra("msprice",product.getMsprice());
                intent.putExtra("hsdprice",product.getHsdprice());
                intent.putExtra("lastprice",product.getLastpricechange());
                intent.putExtra("msdu1",product.getMs_du1());
                intent.putExtra("msdu2",product.getMs_du2());
                intent.putExtra("hsddu1",product.getHsd_du1());
                intent.putExtra("hsddu2",product.getHsd_du2());
                mCtx.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date,txt_time,txt_rocode;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_rocode = itemView.findViewById(R.id.txt_rocode);
        }
    }
}
