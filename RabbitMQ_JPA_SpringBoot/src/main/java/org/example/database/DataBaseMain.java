package org.example.database;

import org.example.database.rabbitmq.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

//Receive (RabbitMQ) -> MyBatisService -> Database -> Send (RabbitMQ)

class DataBaseMain {

    public static DatabaseRecv receive = new DatabaseRecv();

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receive.getSaldo();
            receive.login();
            receive.register();
            receive.logout();
            receive.nomor();
            receive.RecvTagihan();
            receive.Mutasi();
            receive.Bayar();
            receive.getSukses();
        }catch (Exception e){
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}