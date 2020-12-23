package org.example.dummy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "telkom")
public class Telkom {
    @Id
    protected String nomor;
    protected Integer tagihan;

    public Telkom(){}
    public Telkom(String nomor, Integer tagihan){
        setNomor(nomor);
        setTagihan(tagihan);
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
