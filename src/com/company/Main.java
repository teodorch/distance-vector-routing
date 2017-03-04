package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

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
                        token = Graph.infinity;
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
        g.setSplitHorizon(true);
        UserInterface ui = UserInterface.getUserInterface(g);

        //g.run();
    }
}
