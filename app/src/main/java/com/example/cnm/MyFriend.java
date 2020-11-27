package com.example.cnm;

import java.util.ArrayList;
import java.util.List;

public class MyFriend {
    String user;
    List<String> friend;
    List<String> send;
    List<String> receive;

    public MyFriend(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getFriend() {
        return friend;
    }

    public void setFriend(List<String> friend) {
        this.friend = friend;
    }

    public List<String> getSend() {
        return send;
    }

    public void setSend(ArrayList<String> send) {
        this.send = send;
    }

    public List<String> getReceive() {
        return receive;
    }

    public void setReceive(List<String> receive) {
        this.receive = receive;
    }
}
