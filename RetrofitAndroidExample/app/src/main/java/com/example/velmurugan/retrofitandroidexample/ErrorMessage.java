
package com.example.velmurugan.retrofitandroidexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("developerMessage")
    @Expose
    private String developerMessage;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
