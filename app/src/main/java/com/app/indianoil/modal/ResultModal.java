package com.app.indianoil.modal;

public class ResultModal
{
    String date,time,rocode,roname,stockstart,stockend,receipt,msprice,hsdprice,lastpricechange,ms_du1,ms_du2,hsd_du1,hsd_du2;

    public ResultModal(String date, String time, String rocode,String roname, String stockstart, String stockend, String receipt, String msprice, String hsdprice, String lastpricechange, String ms_du1, String ms_du2, String hsd_du1, String hsd_du2) {
        this.date = date;
        this.time = time;
        this.rocode = rocode;
        this.roname=roname;
        this.stockstart = stockstart;
        this.stockend = stockend;
        this.receipt = receipt;
        this.msprice = msprice;
        this.hsdprice = hsdprice;
        this.lastpricechange = lastpricechange;
        this.ms_du1 = ms_du1;
        this.ms_du2 = ms_du2;
        this.hsd_du1 = hsd_du1;
        this.hsd_du2 = hsd_du2;
    }

    public String getRoname() {
        return roname;
    }

    public void setRoname(String roname) {
        this.roname = roname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRocode() {
        return rocode;
    }

    public void setRocode(String rocode) {
        this.rocode = rocode;
    }

    public String getStockstart() {
        return stockstart;
    }

    public void setStockstart(String stockstart) {
        this.stockstart = stockstart;
    }

    public String getStockend() {
        return stockend;
    }

    public void setStockend(String stockend) {
        this.stockend = stockend;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getMsprice() {
        return msprice;
    }

    public void setMsprice(String msprice) {
        this.msprice = msprice;
    }

    public String getHsdprice() {
        return hsdprice;
    }

    public void setHsdprice(String hsdprice) {
        this.hsdprice = hsdprice;
    }

    public String getLastpricechange() {
        return lastpricechange;
    }

    public void setLastpricechange(String lastpricechange) {
        this.lastpricechange = lastpricechange;
    }

    public String getMs_du1() {
        return ms_du1;
    }

    public void setMs_du1(String ms_du1) {
        this.ms_du1 = ms_du1;
    }

    public String getMs_du2() {
        return ms_du2;
    }

    public void setMs_du2(String ms_du2) {
        this.ms_du2 = ms_du2;
    }

    public String getHsd_du1() {
        return hsd_du1;
    }

    public void setHsd_du1(String hsd_du1) {
        this.hsd_du1 = hsd_du1;
    }

    public String getHsd_du2() {
        return hsd_du2;
    }

    public void setHsd_du2(String hsd_du2) {
        this.hsd_du2 = hsd_du2;
    }
}
