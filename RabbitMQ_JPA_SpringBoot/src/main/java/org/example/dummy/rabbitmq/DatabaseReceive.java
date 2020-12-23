package org.example.dummy.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.database.model.Response;
import org.example.dummy.model.Telkom;
import org.example.dummy.model.TelkomTagihan;
import org.example.dummy.service.TelkomDAO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DatabaseReceive {
    private final org.example.dummy.rabbitmq.DatabaseSend send = new DatabaseSend();
    private Connection connection;
    private Channel channel;
    private EntityManager entityManager;
    private TelkomDAO telkomDAO;

    public void connectJPA(){
        this.entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        telkomDAO = new TelkomDAO(entityManager);
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

    public void getNomor() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("nomor", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                Telkom telkom= telkomDAO.findNomor(userString);
                if(telkom==null){
                    Response response = new Response("Nomor tidak ditemukan");
                    send.sendTagihan(new Gson().toJson(response));
                }else {
                    Response response = new Response(telkom.getTagihan().toString());
                    send.sendTagihan(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("nomor", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getTagihan = " + e);
        }
    }

    public void getBayar() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("bayar1", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String userString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + userString + "'");
                connectJPA();
                Telkom telkom= telkomDAO.findIdBayar(userString);
                TelkomTagihan bayar = new Gson().fromJson(userString,TelkomTagihan.class);

                Integer tagihan = telkom.getTagihan();
                Integer bayar1 = bayar.getTagihan();
                if(tagihan!=0) {
                    Integer finalTagihan = tagihan - bayar1;
                    telkom.setTagihan(finalTagihan);
                    entityManager.merge(telkom);
                    Response response = new Response("Sukses");
                    send.sendSukses(new Gson().toJson(response));
                }else{
                    Response response = new Response("Tidak ada tagihan!");
                    send.sendSukses(new Gson().toJson(response));
                }
                commitJPA();
            };
            channel.basicConsume("bayar1", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getTagihan = " + e);
        }
    }
}
