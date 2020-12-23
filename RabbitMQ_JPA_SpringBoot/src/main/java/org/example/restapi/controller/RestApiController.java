package org.example.restapi.controller;

import com.google.gson.Gson;
import org.example.database.model.User;
import org.example.dummy.model.Telkom;
import org.example.dummy.model.TelkomBayar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.restapi.rabbitmq.*;
import org.example.database.model.*;

@RestController
@RequestMapping("/api")
public class RestApiController {
    public final RestApiRecv restApiReceive = new RestApiRecv();
    public final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    // -------------------Get an Saldo-------------------------------------------
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("username") String username) throws InterruptedException {
        try {
            RestApiSend.getSaldo(username);
            Thread.sleep(1000);
            restApiReceive.RecvSaldo();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally {
            while(restApiReceive.getDatamessage()==null) {
                Thread.sleep(2300);
            }
            return new ResponseEntity<>(restApiReceive.getDatamessage(), HttpStatus.OK);
        }
    }

    // -------------------Login-------------------------------------------
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody User user) throws InterruptedException {
        try {
            RestApiSend.login(new Gson().toJson(user));
            restApiReceive.RecvLoginMsg();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally{
                Thread.sleep(2300);
            return new ResponseEntity<>(restApiReceive.getLoginmessage(), HttpStatus.OK);
        }
    }

    // -------------------Logout-------------------------------------------
    @RequestMapping(value = "/logout/", method = RequestMethod.POST)
    public ResponseEntity<?> logoutUser(@RequestBody User username) throws InterruptedException {
        try {
            RestApiSend.logout(new Gson().toJson(username));
            restApiReceive.RecvLogoutMsg();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally{
                Thread.sleep(2300);
            return new ResponseEntity<>(restApiReceive.getLogoutmessage(), HttpStatus.OK);
        }
    }

    // -------------------Register-------------------------------------------
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody User user) throws InterruptedException {
        try {
            RestApiSend.register(new Gson().toJson(user));
            Thread.sleep(1000);
            restApiReceive.RecvRegMsg();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally {
                Thread.sleep(2300);
            return new ResponseEntity<>(restApiReceive.getRegistermessage(), HttpStatus.OK);
        }
    }

    // -------------------TagihanBank-------------------------------------------
    @RequestMapping(value = "/tagihan/", method = RequestMethod.POST)
    public ResponseEntity<?> tagihan(@RequestBody Telkom telkom) throws InterruptedException {
        try {
            RestApiSend.nomor(new Gson().toJson(telkom));
            restApiReceive.RecvTagihan();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally{

                Thread.sleep(2300);

            return new ResponseEntity<>(restApiReceive.getTagihan(), HttpStatus.OK);
        }
    }

    // -------------------Bayar-------------------------------------------
    @RequestMapping(value = "/bayar/", method = RequestMethod.POST)
    public ResponseEntity<?> bayar(@RequestBody TelkomBayar telkom) throws InterruptedException {
        try {
            RestApiSend.bayar(new Gson().toJson(telkom));
            restApiReceive.RecvBayar();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally{
                Thread.sleep(2300);

            return new ResponseEntity<>(restApiReceive.getTagihan(), HttpStatus.OK);
        }
    }

    // -------------------Get Mutasi-------------------------------------------
    @RequestMapping(value = "/mutasi/", method = RequestMethod.POST)
    public ResponseEntity<?> getmutasi(@RequestBody CekMutasi cekMutasi) throws InterruptedException {
        try {
            RestApiSend.getMutasi(new Gson().toJson(cekMutasi));
            Thread.sleep(1000);
            restApiReceive.RecvMutasi();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        finally {
            while(restApiReceive.getMutasi()==null) {
                Thread.sleep(2500);
            }
            return new ResponseEntity<>(restApiReceive.getMutasi(), HttpStatus.OK);
        }
    }
}