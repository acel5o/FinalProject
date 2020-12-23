package org.example.database.service;

import com.google.gson.Gson;
import org.example.database.model.User;

import java.util.List;

public class Session {
    private boolean LoggedIn;
    private String username;
    private String password;

    public Session(String username, String password){
        setLoggedIn(true);
        setUsername(username);
        setPassword(password);
    }

    public boolean isLoggedIn() {
        return LoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        LoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
