package org.example.dummy.service;

import com.google.gson.Gson;
import org.example.database.model.User;
import org.example.dummy.model.Telkom;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.lang.reflect.GenericSignatureFormatError;

public class TelkomDAO {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    Telkom telkom;

    public TelkomDAO() {
    }

    public TelkomDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public Telkom findNomor(String nomorString) {
        try {
            Telkom telkoms = new Telkom();
            telkoms.setNomor(nomorString);
            System.out.println("coba"+telkoms.getNomor());
            telkom = entityManager.createQuery("SELECT a FROM Telkom a where nomor='"+telkoms.getNomor()+"'", Telkom.class).getSingleResult();
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("debug" + telkom);
        return telkom;
    }

    public Telkom findIdBayar(String nomorString) {
        try {
            Telkom telkoms = new Gson().fromJson(nomorString, Telkom.class);
            telkom = entityManager.createQuery("SELECT a FROM Telkom a where nomor='" + telkoms.getNomor() + "'", Telkom.class).getSingleResult();
        }catch (Exception e) {
            System.out.println("debug" + telkom + "--> " + e);
        }
        return telkom;
    }
}
