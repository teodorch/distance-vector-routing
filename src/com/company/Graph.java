package com.company;

import java.util.*;

/**
 * Created by morti on 23/02/2017.
 */
public class Graph {

    public PriorityQueue<Command> queue;
    public int iterations;
    public int counter;
    public List<Node> nodes;
    public static int infinity = 20;

    public Graph() {
        nodes = new ArrayList<>();
        iterations = 10;
        counter = 0;
        queue = new PriorityQueue<>(new Comparator<Command>() {
            @Override
            public int compare(Command o1, Command o2) {
                return o1.getIter() - o2.getIter();
            }
        });
    }

    public void add(Node n){
        nodes.add(n);
    }

    public void setup() {
        for (Node n : nodes) {
            int[] distance =  n.getDistance();
            for (int i = 0; i < distance.length; i++) {
                if (distance[i] < infinity && distance[i] > 0) {
                    n.addNeighbour(nodes.get(i));
                }
            }
        }
    }

    public void run() {
        if (counter == 0) {
            System.out.println("Initial network:");
            System.out.println(toString());
        }
        for (int i = 0; i < iterations; i ++) {
            System.out.println("Iteration : " + counter++);
            executeCommands();


            nodes.forEach(Node::broadcast);
            nodes.forEach(Node::update);
            System.out.println(toString());


            if (isStable()) {
                System.out.println("Network is now stable!");
                System.out.println("Clearing remaining commands");
                queue.clear();
                break;
            }
        }
    }

    private void executeCommands() {
        if (queue.isEmpty()) {
            return;
        }
        Command c = queue.peek();

        while (!queue.isEmpty() &&
                (c.getIter() == counter)) {
            c = queue.poll();

            String name = c.getName();
            int[] params = c.getParams();

            switch (name) {
                case "change":
                    changeCost(params[0], params[1], params[2]);
                    System.out.println(toString());
                    //nodes.forEach(Node::update);
                    break;
                case "fail":
                    failLink(params[0], params[1]);
                    System.out.println(toString());
                    //nodes.forEach(Node::update);
                    break;
                case "path":
                    System.out.println(getPath(params[0], params[1]));
                    break;
            }
            c = queue.peek();
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void failLink(int node1, int node2) {
        System.out.println("Failing link: " + node1 + " - " + node2);
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);

        if (n1.getNeighbours().contains(n2) && n2.getNeighbours().contains(n1)) {
            n1.removeNeighbour(n2);
            n2.removeNeighbour(n1);
        }
    }

    public void createLink(int node1, int node2, int cost) {
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);

        n1.addNeighbour(n2);
        n2.addNeighbour(n1);

        n1.getDistance()[node2] = cost;
        n2.getDistance()[node1] = cost;
    }

    public void changeCost(int node1, int node2, int cost) {
        System.out.println("Changing cost:" + node1 + "-" + node2 + " to " + cost);
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);

        if (n1.getNeighbours().contains(n2) && n2.getNeighbours().contains(n1)) {
            n1.changeTable(node2, cost);
            n2.changeTable(node1, cost);
        } else {
            System.out.println("Invalid input!");
            System.out.println("Nodes are not neighbours!");
        }
    }

    public boolean isStable() {
        boolean stable = true;
        for (Node n: nodes) {
            stable = !n.isChanged();
            if (!stable) {
                break;
            }
        }
        return stable;
    }

    public void setSplitHorizon(boolean splitHorizon) {
        for (Node n : nodes) {
            n.setSplitHorizon(splitHorizon);
        }
    }

    public boolean getSplitHorizon() {
        boolean splitHorizon = false;
        for (Node n : nodes) {
            splitHorizon = n.getSplitHorizon();
            if (!splitHorizon) {
                break;
            }
        }
        return splitHorizon;
    }

    public String getPath(int node1, int node2) {
        StringBuilder sb = new StringBuilder();
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);

        Node current_node = n1;
        int current_number = node1;

        sb.append("Path: [");

        while (current_node != n2) {
            sb.append(current_number);
            sb.append(", ");
            current_number = current_node.getRouting()[node2];
            current_node = nodes.get(current_number);
        }

        sb.append(current_number);
        sb.append("] Distance: ");
        sb.append(n1.getDistance()[node2]);

        return sb.toString();
    }

    public void setIterations(int i) {
        this.iterations = i;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node n : nodes) {
            sb.append(n.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void queueCommand(Command c) {
        if (c.getIter() < 0) {
            return;
        }
        queue.add(c);
    }
}
