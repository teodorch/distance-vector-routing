package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by morti on 23/02/2017.
 */
public class Node {

    private int[] table;
    private int number;
    private List<Node> neighbours;
    private Stack<Message> messages;
    private int size;

    public Node(int[] table, int number){
        this.table = table;
        this.number = number;
        this.neighbours = new ArrayList<>();
        this.messages = new Stack<>();
        this.size = table.length;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int[] getTable() {
        return table;
    }

    public void broadcast(){
        for (Node n : neighbours) {
            n.receive(new Message(number, table, size));
        }
    }

    public void receive(Message message) {
        messages.add(message);
    }

    public void update() {
        for (Message message : messages) {
            int[] links = message.getMessage();
            int sender = message.getSender();
            int length = message.getLength();
            for (int i = 0; i < length; i++) {
                if (table[i] == 0 || links[i] == 0 || links[i] == -1) {
                    continue;
                }
                if (table[i] > links[i] + table[sender] ) {
                    table[i] = links[i] + table[sender];
                }
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return Arrays.toString(table);
    }

}
