package co.g2academy.telkom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TelkomResponse {
    @SerializedName("respon")
    @Expose
    private String respon;

    public String getRespon() {
        return respon;
    }

    public void setRespon(String status) {
        this.respon = status;
    }
}
