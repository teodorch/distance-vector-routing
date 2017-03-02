package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by morti on 23/02/2017.
 */
public class Graph {
    public List<Node> nodes;
    public static int infinity = 20;

    public Graph() {
        nodes = new ArrayList<>();
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

    public List<Node> getNodes() {
        return nodes;
    }

    public void cutLink(int node1, int node2) {
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);

        if (n1.getNeighbours().contains(n2) && n2.getNeighbours().contains(n1)) {
            n1.removeNeighbour(n2);
            n2.removeNeighbour(n1);
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node n : nodes) {
            sb.append(n.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
