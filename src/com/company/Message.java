package com.company;

/**
 * Created by morti on 23/02/2017.
 */
public class Message {

    int sender;
    int[] message;
    private int length;

    public Message(int sender, int[] message, int length) {
        this.sender = sender;
        this.message = message;
        this.length = length;
    }

    public int getSender() {
        return sender;
    }

    public int[] getMessage() {
        return message;
    }

    public int getLength() {
        return length;
    }
}
