package co.g2academy.tugaslayout.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BayarTagihan {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nomor")
    @Expose
    private String nomor;

    @SerializedName("tagihan")
    @Expose
    private Integer tagihan;

    public BayarTagihan(String username, String nomor, Integer tagihan){
        setNomor(nomor);
        setTagihan(tagihan);
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public Integer getTagihan() {
        return tagihan;
    }

    public void setTagihan(Integer tagihan) {
        this.tagihan = tagihan;
    }
}
