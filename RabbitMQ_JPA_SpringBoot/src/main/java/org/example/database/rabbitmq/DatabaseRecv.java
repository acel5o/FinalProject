package org.example.database.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.example.database.model.*;
import org.example.database.service.*;
import org.example.dummy.model.Telkom;

public class DatabaseRecv {
    private final DatabaseSend send = new DatabaseSend();
    private Connection connection;
    private Channel channel;
    private EntityManager entityManager;
    private UserDAO userDao;
    private final List<Session> session = new ArrayList<>();

    public void connectJPA(){
        this.entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        userDao = new UserDAO(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    //    private Adapter adapter = new Adapter();
    public void commitJPA(){
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void connectRabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public void getSaldo() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getdataUser", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                User user= userDao.findId(userString);
                boolean statusLogin = false;
                if (user.getStatLogin()==1) {
                    statusLogin = true;
                }
                if (statusLogin) {
                    String usrString = new Gson().toJson(user);
                    try {
                        if(userDao.isRegistered(usrString)==1){
                            Response response = new Response(user.getSaldo().toString());
                            send.sendUser(new Gson().toJson(response));
                        } else {
                            Response response = new Response("User not found!");
                            send.sendUser(new Gson().toJson(response));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    Response response = new Response("Login is required, please Login first!");
                    send.sendUser(new Gson().toJson(response));
                }


                commitJPA();
            };
            channel.basicConsume("getdataUser", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getdataUser = " + e);
        }
    }

    public void Mutasi() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("mutasi", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                User user= userDao.findIdMutasi(userString);
                System.out.println(user);
                boolean statusLogin = false;
                if (user.getStatLogin()==1) {
                    statusLogin = true;
                }
                if (statusLogin) {
                    List<Mutasi> mutasi = userDao.findMutasi(userString);

                    try {
                        if(mutasi!=null){
                            Responses response = new Responses(mutasi);
                            send.sendMutasi(new Gson().toJson(response));
                        } else {
                            Response response = new Response("Mutasi not found!");
                            send.sendMutasi(new Gson().toJson(response));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    Response response = new Response("Login is required, please Login first!");
                    send.sendMutasi(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("mutasi", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getMutasi = " + e);
        }
    }

    public void Bayar() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("bayar", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                User userr = userDao.find(userString);
                boolean statusLogin = false;
                if (userr.getStatLogin()==1) {
                    statusLogin = true;
                }
                if (statusLogin) {
                    Integer teruskan = userDao.findBayar(userString);
                    try {
                        if(teruskan.equals(1)) {
                            Bayar bayar = new Gson().fromJson(userString, Bayar.class);
                            ToTelkom telkom = new ToTelkom();
                            telkom.setNomor(bayar.getNomor());
                            telkom.setTagihan(bayar.getTagihan());
                            send.sendBayar(new Gson().toJson(telkom));
                        }else {
                            Response response = new Response("Saldo anda tidak mencukupi!");
                            send.sendtoUser(new Gson().toJson(response));
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    Response response = new Response("Login is required, please Login first!");
                    send.sendMutasi(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("bayar", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getBayar = " + e);
        }
    }



    public void login() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("loginUser", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();

                User userr = userDao.find(userString);
                boolean statusLogin = false;
                    if (userr.getStatLogin()==1) {
                        statusLogin = true;
                    }

                if (statusLogin) {
                    User userss = entityManager.find(User.class, userr.getId());
                    userss.setStatLogin(0);
                    entityManager.merge(userss);

                    Response response = new Response("Sudah Login!");
                    send.sendLogin(new Gson().toJson(response));
                } else {
                    Integer cek= userDao.checkAccNumPin(userString);
                    if(cek.equals(1)) {
                        User userss = entityManager.find(User.class, userr.getId());
                        userss.setStatLogin(1);
                        entityManager.merge(userss);

                        Response response = new Response(userr.getUsername());
                        send.sendLogin(new Gson().toJson(response));
                    }else if(cek.equals(0)){
                        Response response = new Response("Wrong Username or Password!");
                        send.sendLogin(new Gson().toJson(response));
                    }else if(cek.equals(3)){
                        Response response = new Response("Wrong Password!");
                        send.sendLogin(new Gson().toJson(response));
                    }
                }
                commitJPA();
            };
            channel.basicConsume("loginUser", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error login = " + e);
        }
    }

    public void logout() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("logoutUser", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                User userr = userDao.find(userString);
                if (userr.getStatLogin()==1) {
                    User userss = entityManager.find(User.class, userr.getId());
                    userss.setStatLogin(0);
                    entityManager.merge(userss);

                    Response response = new Response("Logout success!");
                    send.sendLogout(new Gson().toJson(response));
                } else {
                    Response response = new Response("Logout fail! no session detected");
                    send.sendLogout(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("logoutUser", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error logoutUser = " + e);
        }
    }

    public void register() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("registerUser", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                if(userDao.isRegistered(userString)==1){
                    Response response = new Response("No.KTP sudah digunakan");
                    send.sendRegister(new Gson().toJson(response));
                }else{
                    try {
                        userDao.registrasi(userString);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    Response response = new Response("Sukses");
                    send.sendRegister(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("registerUser", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error registerUser = " + e);
        }
    }

    public void RecvTagihan() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("tagihan", false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            send.sendTagihan(message);
        };
        channel.basicConsume("tagihan", true, deliverCallback, consumerTag -> { });
    }

    public void nomor() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("nomor", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                Telkom telkom = new Gson().fromJson(userString,Telkom.class);
                send.sendNomor(telkom.getNomor());
                commitJPA();
            };
            channel.basicConsume("nomor", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getNomor = " + e);
        }
    }

    public void getSukses() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("sukses", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                send.sendtoUser(userString);
                commitJPA();
            };
            channel.basicConsume("sukses", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getRespon = " + e);
        }
    }
}
