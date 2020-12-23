package org.example.database.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class DatabaseSend {
    public void sendUser(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendUser", false, false, false, null);
            channel.basicPublish("", "sendUser", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendNomor(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("nomor", false, false, false, null);
            channel.basicPublish("", "nomor", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendTagihan(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("tagihan1", false, false, false, null);
            channel.basicPublish("", "tagihan1", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendMutasi(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendMutasi", false, false, false, null);
            channel.basicPublish("", "sendMutasi", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendBayar(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("bayar1", false, false, false, null);
            channel.basicPublish("", "bayar1", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendtoUser(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sukses1", false, false, false, null);
            channel.basicPublish("", "sukses1", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendLogin(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
             channel.queueDeclare("sendLogin", false, false, false, null);
             channel.basicPublish("", "sendLogin", null, message.getBytes(StandardCharsets.UTF_8));
             System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendLogout(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendLogout", false, false, false, null);
            channel.basicPublish("", "sendLogout", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendRegister(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendRegister", false, false, false, null);
            channel.basicPublish("", "sendRegister", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }
}
