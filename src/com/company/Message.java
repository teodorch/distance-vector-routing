package com.company;

import java.util.List;

/**
 * Created by morti on 23/02/2017.
 */
public class Message {

    private int sender;
    private List<Link> message;
    private int length;

    public Message(int sender, List<Link> message, int length) {
        this.sender = sender;
        this.message = message;
        this.length = length;
    }

    public int getSender() {
        return sender;
    }

    public List<Link> getMessage() {
        return message;
    }

    public int getLength() {
        return length;
    }
}
