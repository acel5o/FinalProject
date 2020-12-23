package org.example.database.model;

import java.util.ArrayList;
import java.util.List;

public class Responses {
    private List<Mutasi> respon;

    public Responses(List<Mutasi> respon){
        setRespon(respon);
    }

    public List<Mutasi> getRespon() {
        return respon;
    }

    public void setRespon(List<Mutasi> respon) {
        this.respon = respon;
    }
}
