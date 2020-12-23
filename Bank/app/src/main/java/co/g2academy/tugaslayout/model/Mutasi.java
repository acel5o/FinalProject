package co.g2academy.tugaslayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mutasi {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    @SerializedName("dana")
    @Expose
    private String dana;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getDana() {
        return dana;
    }

    public void setDana(String dana) {
        this.dana = dana;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
