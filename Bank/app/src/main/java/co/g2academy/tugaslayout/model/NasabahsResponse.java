package co.g2academy.tugaslayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NasabahsResponse {
    @SerializedName("respon")
    @Expose
    private List<Mutasi> respon = null;

    public List<Mutasi> getRespon() {
        return respon;
    }

    public void setRespon(List<Mutasi> respon) {
        this.respon = respon;
    }
}
