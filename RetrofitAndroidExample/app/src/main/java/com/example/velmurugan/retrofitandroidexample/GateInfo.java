
package com.example.velmurugan.retrofitandroidexample;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GateInfo {

    @SerializedName("gateList")
    @Expose
    private List<GateList> gateList = null;
    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;

    public List<GateList> getGateList() {
        return gateList;
    }

    public void setGateList(List<GateList> gateList) {
        this.gateList = gateList;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
