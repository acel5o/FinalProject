package org.example.database.service;
import com.google.gson.Gson;
import org.example.database.model.Bayar;
import org.example.database.model.CekMutasi;
import org.example.database.model.Mutasi;
import org.example.database.model.User;
import org.example.database.rabbitmq.DatabaseSend;


import javax.persistence.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class UserDAO {
    private final DatabaseSend send = new DatabaseSend();
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    User user,update;
    List<Mutasi> mutasii;

    public UserDAO() {
    }

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void registrasi(String userString) throws InterruptedException {
        User usernew = new Gson().fromJson(userString, User.class);
        User userKirim = new User(usernew.getNama(), usernew.getKtp(), usernew.getAlamat(),
                usernew.getUsername(), usernew.getPassword());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        userKirim.setSaldo(1250000);
        entityManager.persist(userKirim);
        Query q = entityManager.createNativeQuery("select next_val from hibernate_sequence");
        Mutasi mutasi = new Mutasi(dateFormat.format(date),"Tabungan",userKirim.getSaldo(),(BigInteger)q.getSingleResult());
        entityManager.persist(mutasi);
    }


    public User find(String userString) {
        try {
            User user1 = new Gson().fromJson(userString, User.class);
            user = entityManager.createQuery("SELECT a FROM User a where username ='" + user1.getUsername() + "'", User.class).getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public List<User> findAll() {
        List<User> listUser = entityManager.createQuery("SELECT a FROM User a", User.class).getResultList();
        return listUser;
    }


    public User findId(String userString) {
        try {
            user = entityManager.createQuery("SELECT a FROM User a where username='"+userString+"'", User.class).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
        }
        System.out.println("debug" + user);
        return user;
    }

    public User findIdBayar(String userString) {
        try {
            Bayar bayar = new Gson().fromJson(userString,Bayar.class);
            System.out.println("nomor: "+bayar.getUsername());
            user = entityManager.createQuery("SELECT a FROM User a where username='"+bayar.getUsername()+"'", User.class).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
        }
        System.out.println("debug" + user);
        return user;
    }

    public User findIdMutasi(String userString) {
        try {
            CekMutasi mutasi = new Gson().fromJson(userString,CekMutasi.class);
            user = entityManager.createQuery("SELECT a FROM User a where username='"+mutasi.getUsername()+"'", User.class).getSingleResult();


        } catch (NoResultException e) {
            System.out.println(e);
        }
        System.out.println("debug" + user);
        return user;
    }

    public List<Mutasi> findMutasi(String userString) {
        try {
            CekMutasi mutasi = new Gson().fromJson(userString, CekMutasi.class);
            user = entityManager.createQuery("SELECT a FROM User a where username='" + mutasi.getUsername() + "'", User.class).getSingleResult();

            mutasii = entityManager.createQuery("SELECT a FROM Mutasi a where user_id='" + user.getId() + "' and tanggal between '" + mutasi.getFromDate() + "' and '" + mutasi.getToDate() + "'", Mutasi.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("debug" + mutasii);
        return mutasii;
    }

    public Integer findBayar(String username){
        Integer teruskan = 0;
        try {
            Bayar bayar = new Gson().fromJson(username, Bayar.class);
            Integer bayars = Integer.valueOf(bayar.getTagihan());
            user = entityManager.createQuery("SELECT a FROM User a where username='" + bayar.getUsername() + "'", User.class).getSingleResult();
            User users = user;
            Integer saldo = users.getSaldo();
            Integer finalSaldo;
            if(saldo>=bayars) {
                teruskan = 1;
                if(bayars!=0) {
                    finalSaldo = saldo - bayars;
                    User userss = entityManager.find(User.class, user.getId());
                    userss.setSaldo(finalSaldo);
                    entityManager.merge(userss);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();

                    Mutasi mutasi = new Mutasi(dateFormat.format(date), "Pembayaran", bayars, BigInteger.valueOf(user.getId()));
                    entityManager.persist(mutasi);
                }
            }else{
                teruskan=0;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("debug" + mutasii);
        return teruskan;
    }

    public int checkAccNumPin(String userString) {
        User userGet = find(userString);
        User user = new Gson().fromJson(userString, User.class);
        int canLogin = 0;
        try {
            if (!userGet.equals(null)) {
                if (userGet.getUsername().equals(user.getUsername()) && userGet.getPassword().equals(user.getPassword())) {
                    canLogin = 1;
                } else if (userGet.getUsername().equals(user.getUsername()) && !userGet.getPassword().equals(user.getPassword())) {
                    canLogin = 3;
                } else {
                    canLogin = 0;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return canLogin;
    }
    public int isRegistered(String userString) {
        List<User> listAllUser = findAll();
        User user = new Gson().fromJson(userString, User.class);
        int registered = 0;
        for (User obj : listAllUser){
            if(obj.getKtp().equals(user.getKtp())){
                registered = 1;
            }
        }
        return registered;
    }
}
