
package com.example.android.bloodbank.data.model.myFavourites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFavourites {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyFavouritesPagination data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyFavouritesPagination getData() {
        return data;
    }

    public void setData(MyFavouritesPagination data) {
        this.data = data;
    }

}
