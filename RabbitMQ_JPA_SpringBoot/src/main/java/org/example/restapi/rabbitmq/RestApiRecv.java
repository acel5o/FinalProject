package org.example.restapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RestApiRecv {
    protected String Loginmessage;
    protected String Registermessage;
    protected String Logoutmessage;
    protected String Datamessage;
    protected String Tagihan;
    protected String Mutasi;

    public void RecvMutasi() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendMutasi", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setMutasi(message);
        };
        channel.basicConsume("sendMutasi", true, deliverCallback, consumerTag -> { });
    }

    public void RecvRegMsg() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendRegister", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setRegistermessage(message);
        };
        channel.basicConsume("sendRegister", true, deliverCallback, consumerTag -> { });
    }

    public void RecvTagihan() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("tagihan1", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setTagihan(message);
        };
        channel.basicConsume("tagihan1", true, deliverCallback, consumerTag -> { });
    }


    public void RecvBayar() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sukses1", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setTagihan(message);
        };
        channel.basicConsume("sukses1", true, deliverCallback, consumerTag -> { });
    }

    public void RecvLoginMsg() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendLogin", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setLoginmessage(message);
        };
        channel.basicConsume("sendLogin", true, deliverCallback, consumerTag -> { });

    }

    public void RecvLogoutMsg() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendLogout", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setLogoutmessage(message);
        };
        channel.basicConsume("sendLogout", true, deliverCallback, consumerTag -> { });

    }

    public void RecvSaldo() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendUser", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setDatamessage(message);
        };
        channel.basicConsume("sendUser", true, deliverCallback, consumerTag -> { });

    }

    public String getLoginmessage() {
        return Loginmessage;
    }

    public void setLoginmessage(String loginmessage) {
        Loginmessage = loginmessage;
    }

    public String getRegistermessage() {
        return Registermessage;
    }

    public void setRegistermessage(String registermessage) {
        Registermessage = registermessage;
    }

    public String getLogoutmessage() {
        return Logoutmessage;
    }

    public void setLogoutmessage(String logoutmessage) {
        Logoutmessage = logoutmessage;
    }

    public String getDatamessage() {
        return Datamessage;
    }

    public void setDatamessage(String getDatamessage) {
        this.Datamessage = getDatamessage;
    }

    public String getTagihan() {
        return Tagihan;
    }

    public void setTagihan(String tagihan) {
        Tagihan = tagihan;
    }

    public String getMutasi() {
        return Mutasi;
    }

    public void setMutasi(String mutasi) {
        Mutasi = mutasi;
    }
}
