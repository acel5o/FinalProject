package co.g2academy.telkom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CekTagihan {
    @SerializedName("nomor")
    @Expose
    private String nomor;

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }
}
