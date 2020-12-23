package org.example.dummy;

import org.example.database.rabbitmq.DatabaseRecv;
import org.example.dummy.rabbitmq.DatabaseReceive;

public class TelkomRun {
    public static DatabaseReceive receive = new DatabaseReceive();

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receive.getNomor();
            receive.getBayar();
        }catch (Exception e){
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}
