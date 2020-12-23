package org.example.database.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "mutasi")
public class Mutasi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String tanggal;
    protected String tipe;
    protected Integer dana;
    protected BigInteger user_id;

    public Mutasi(){}

    public Mutasi(String tanggal, String tipe, Integer dana, BigInteger user_id){
        setTanggal(tanggal);
        setTipe(tipe);
        setDana(dana);
        setUser_id(user_id);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getDana() {
        return dana;
    }

    public void setDana(Integer dana) {
        this.dana = dana;
    }

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }
}
