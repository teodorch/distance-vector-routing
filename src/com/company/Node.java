package com.company;

import java.util.*;

import static com.company.Graph.infinity;

/**
 * Created by morti on 23/02/2017.
 */
public class Node {

    private int[] distance;
    private int[] routing;
    private int number;
    private List<Node> neighbours;
    private Queue<Message> messages;

    public Node(int[] table, int number){
        this.distance = table;
        this.routing = new int[table.length];
        setUp(table);
        this.number = number;
        this.neighbours = new ArrayList<>();
        this.messages = new LinkedList<>();
    }

    public void setUp(int[] table) {
        for (int i =0; i< table.length; i++) {
            if (distance[i] > 0) {
                routing[i] = i;
            } else if (distance[i] < 0) {
                routing[i] = -1;
            } else {
                routing[i] = i;
            }
        }
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public void removeNeighbour(Node n) {
        int neighbour = n.getNumber();
        neighbours.remove(n);
        distance[neighbour] = infinity;
        routing[neighbour] = -1;
        for (int i = 0; i < distance.length; i++) {
            if (routing[i] == neighbour) {
                distance[i] = infinity;
            }
        }
        broadcast();
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int[] getDistance() {
        return distance;
    }

    public void broadcast(){
        for (Node neighbour : neighbours) {
            int n = neighbour.getNumber();
            List<Link> links = new LinkedList<>();
            for (int i = 0; i < routing.length; i++) {

                if (n != routing[i]) {
                    links.add(new Link(distance[i], i, routing[i]));
                }
            }
            if (!links.isEmpty()) {
                neighbour.receive(new Message(number, links, links.size()));
            }

        }
    }

    public void receive(Message message) {
        messages.add(message);
    }

    public void update() {
        while (!messages.isEmpty()) {
            Message message = messages.poll();
            List<Link> links = message.getMessage();
            int sender = message.getSender();

            for (Link link : links) {
                int new_distance = link.getDistance() + distance[sender];
                int route = link.getRoute();
                int destination = link.getDestination();

                if (number == 1 && destination == 2 && sender == 4) {
                    System.out.println();
                }

                if (route == number) {
                    continue;
                }
                if (routing[destination] == sender && link.getDistance() == infinity) {
                    distance[destination] = infinity;
                    routing[destination] = -1;
                }
                else if (distance[destination] > new_distance) {
                    distance[destination] = new_distance;
                    routing[destination] = routing[sender];
                }

            }
        }
    }

    private int getNumber() {
        return number;
    }

    public String toString() {
        return Arrays.toString(distance);
    }

}
