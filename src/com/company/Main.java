package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static int infinity = 20;

    public static void main(String[] args) {
        Graph g = new Graph();
        try {
            List<String> file = Files.readAllLines(Paths.get("network.txt"));
            for (String line : file) {
                String[] tokens = line.split(" ");
                if (tokens.length != file.size()) {
                    System.err.println(" wrong file");
                }
                int line_length = tokens.length;
                int[] nodes = new int[line_length];
                int node_num = -1;
                int token;
                for (int j = 0; j < line_length; j++) {
                    token = Integer.parseInt(tokens[j]);
                    if (token == 0) {
                        node_num = j;
                    }
                    if (token == -1) {
                        token = infinity;
                    }
                    nodes[j] = token;

                }
                if (node_num == -1) {
                    System.err.println("wrong file, node num error");
                }
                Node n = new Node(nodes, node_num);
                g.add(n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setup();
        List<Node> nodes = g.getNodes();
        System.out.println(g.toString());
        System.out.println();
        nodes.forEach(Node::broadcast);
        nodes.forEach(Node::update);
        System.out.println(g.toString());
    }
}
