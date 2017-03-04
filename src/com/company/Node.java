package com.company;

import java.util.*;

import static com.company.Graph.infinity;

/**
 * Created by morti on 23/02/2017.
 */
public class Node {

    private int[] neighbours_table;
    private int[] distance;
    private int[] routing;
    private int number;
    private List<Node> neighbours;
    private Queue<Message> messages;
    private boolean changed;
    private boolean splitHorizon;

    public Node(int[] table, int number) {
        this.neighbours_table = table;
        this.distance = new int[table.length];
        this.routing = new int[table.length];
        setUp(table);
        this.number = number;
        this.neighbours = new ArrayList<>();
        this.messages = new LinkedList<>();
        this.changed = true;
        this.splitHorizon = false;
    }

    public void setUp(int[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] > 0) {
                distance[i] = table[i];
                routing[i] = i;
            } else if (table[i] < 0) {
                distance[i] = infinity;
                routing[i] = -1;
            } else {
                distance[i] = 0;
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
        neighbours_table[neighbour] = infinity;
        distance[neighbour] = infinity;
        routing[neighbour] = -1;
        for (int i = 0; i < distance.length; i++) {
            if (routing[i] == neighbour) {
                distance[i] = infinity;
            }
        }
        changed = true;
        //broadcast();
    }

    public void changeTable(int node, int cost) {
        int old_cost = neighbours_table[node];
        int diff = cost - old_cost;
        neighbours_table[node] = cost;
        for (int i = 0; i < routing.length; i++) {
            // If route to destination goes through node
            if (routing[i] == node) {
                // check if destination is neighbour
                // and cost to destination is better than new_cost
                if (neighbours_table[i] < distance[i] + diff) {
                    distance[i] = neighbours_table[i];
                    routing[i] = i;
                } else {
                    distance[i] += diff;
                }
            }
        }
        changed = true;
        //broadcast();
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int[] getDistance() {
        return distance;
    }

    public int[] getRouting() {
        return routing;
    }

    public void broadcast() {
        for (Node neighbour : neighbours) {
            int n = neighbour.getNumber();
            List<Link> links = new LinkedList<>();
            for (int i = 0; i < routing.length; i++) {
                // If route to destination is NOT through n
                // OR splitHorizon is NOT enabled
                if (n != routing[i]) {
                    // add the link to the message
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
        changed = false;
        while (!messages.isEmpty()) {
            Message message = messages.poll();
            List<Link> links = message.getMessage();
            int sender = message.getSender();

            for (Link link : links) {
                int new_distance = link.getDistance() + distance[sender];
                int route = link.getRoute();
                int destination = link.getDestination();

                // Check if the route is going through this node
                if (route == number && splitHorizon) {
                    continue;
                }

                // If this.node is using the sender to reach destination
                if (routing[destination] == sender) {
                    // If sender is broadcasting broken link
                    if (link.getDistance() == infinity) {
                        distance[destination] = infinity;
                        routing[destination] = -1;
                        changed = true;
                    }
                    // If sender is broadcasting changed link
                    else if (distance[destination] < new_distance) {
                        // Check if this.node is neighbours with destination
                        // change to that link if better
                        if (neighbours_table[destination] > 0
                                && neighbours_table[destination] < infinity) {
                            distance[destination] = neighbours_table[destination];
                            routing[destination] = destination;
                            changed = true;
                        } else {
                            distance[destination] = new_distance;
                            changed = true;
                        }

                    // General case
                    } else if (distance[destination] > new_distance){
                        distance[destination] = new_distance;
                        routing[destination] = routing[sender];
                        changed = true;
                    }
                }

                // General case
                else if (distance[destination] > new_distance) {
                    distance[destination] = new_distance;
                    routing[destination] = routing[sender];
                    changed = true;
                }
            }
        }
    }

    private int getNumber() {
        return number;
    }

    public boolean isChanged() {
        return changed;
    }

    public boolean getSplitHorizon() {
        return splitHorizon;
    }

    public void setSplitHorizon(boolean splitHorizon) {
        this.splitHorizon = splitHorizon;
    }

    public String toString() {
        return Arrays.toString(distance);
    }

}
