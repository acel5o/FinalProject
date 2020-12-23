package org.example.restapi.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RestApiSend {

    public static void getSaldo(String idString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("getdataUser", false, false, false, null);
            channel.basicPublish("", "getdataUser", null, idString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + idString + "'");
        }
    }

    //mutasi
    public static void getMutasi(String mutasi) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("mutasi", false, false, false, null);
            channel.basicPublish("", "mutasi", null, mutasi.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + mutasi + "'");
        }
    }

    //nomor
    public static void nomor(String tagihan) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("nomor", false, false, false, null);
            channel.basicPublish("", "nomor", null, tagihan.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + tagihan + "'");
        }
    }

    //bayar
    public static void bayar(String bayar) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("bayar", false, false, false, null);
            channel.basicPublish("", "bayar", null, bayar.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + bayar + "'");
        }
    }

    //login
    public static void login(String userString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("loginUser", false, false, false, null);
            channel.basicPublish("", "loginUser", null, userString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + userString + "'");
        }
    }

    //logout
    public static void logout(String userString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("logoutUser", false, false, false, null);
            channel.basicPublish("", "logoutUser", null, userString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + userString + "'");
        }
    }

    //register
    public static void register(String userString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare("registerUser", false, false, false, null);
            channel.basicPublish("", "registerUser", null, userString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + userString + "'");
        }
    }

}

