package com.example.rup.halisahakiralama.client;

import java.util.ArrayList;
import java.util.List;

public class ListStringRequest {
    private List<String> dataList;
    //constrctor
    public ListStringRequest(){
        //empty constructor  for Gson
    }
    //setter for dataList
    public void setDataList(List<String> dataList){
        this.dataList = dataList;
    }
}
