package co.g2academy.tugaslayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MutasiReq {
    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("fromDate")
    @Expose
    String fromDate;

    @SerializedName("toDate")
    @Expose
    String toDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
