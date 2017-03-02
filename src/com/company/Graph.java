package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by morti on 23/02/2017.
 */
public class Graph {
    public List<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    public void add(Node n){
        nodes.add(n);
    }

    public void setup() {
        for (Node n : nodes) {
            int[] table =  n.getTable();
            for (int i = 0; i < table.length; i++) {
                if (table[i] > 0) {
                    n.addNeighbour(nodes.get(i));
                }
            }
        }
    }

    public List<Node> getNodes() {
        return nodes;
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
