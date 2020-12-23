package co.g2academy.tugaslayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NasabahResponse {
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
