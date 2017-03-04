package com.company;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by 2030449c on 04/03/17.
 */
public class UserInterface {

    private Graph g;
    private static UserInterface ui = null;

    private UserInterface(Graph g) {
        this.g = g;
        startUI();
    }

    public static UserInterface getUserInterface(Graph g) {
        if (ui == null) {
            ui = new UserInterface(g);
        }
        return ui;
    }

    private void startUI() {
        help();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("quit")) {
            int[] params;
            int when = 0;
            String[] tokens;
            switch (input) {
                case "help":
                    help();
                    break;

                case "iter":
                    System.out.println("Enter number of iterations");
                    String iterations = sc.nextLine();
                    int iter = Integer.parseInt(iterations);
                    setIteration(iter);
                    break;

                case "run":
                    g.run();
                    break;

                case "split":
                    System.out.println("Set split horizon: true/false?");
                    String split = sc.nextLine();
                    g.setSplitHorizon(Boolean.parseBoolean(split));
                    break;

                case "change":
                    System.out.println("Set link cost to change in format");
                    System.out.println("int   int   int  int");
                    System.out.println("node1 node2 cost when");
                    tokens = sc.nextLine().split(" ");
                    params = new int[3];

                    params[0] = Integer.parseInt(tokens[0]);
                    params[1] = Integer.parseInt(tokens[1]);
                    params[2] = Integer.parseInt(tokens[2]);
                    if (tokens.length == 4) {
                        when = Integer.parseInt(tokens[3]);
                    }

                    if (when == 0) {
                        g.changeCost(params[0], params[1], params[2]);
                    } else {
                        g.queueCommand(new Command(when, "path", params));
                    }
                    break;

                case "fail":
                    System.out.println("Set link cost to change in format");
                    System.out.println("int   int   int");
                    System.out.println("node1 node2 when");

                    tokens = sc.nextLine().split(" ");
                    params = new int[2];

                    params[0] = Integer.parseInt(tokens[0]);
                    params[1] = Integer.parseInt(tokens[1]);

                    if (tokens.length == 3) {
                        when = Integer.parseInt(tokens[2]);
                    }
                    if (when == 0) {
                        g.failLink(params[0], params[1]);
                    } else {
                        g.queueCommand(new Command(when, "path", params));
                    }

                    break;

                case "path":
                    System.out.println("Select nodes to display path");
                    System.out.println("int   int   int");
                    System.out.println("node1 node2 when");

                    tokens = sc.nextLine().split(" ");
                    params = new int[2];

                    params[0] = Integer.parseInt(tokens[0]);
                    params[1] = Integer.parseInt(tokens[1]);

                    if (tokens.length == 3) {
                        when = Integer.parseInt(tokens[2]);
                    }
                    if (when == 0) {
                        System.out.println(g.getPath(params[0], params[1]));
                    } else {
                        g.queueCommand(new Command(when, "path", params));
                    }
                    break;

                case "once":
                    g.setIterations(1);
                    g.run();
                    break;

                default:
                    help();
                    break;
            }
            System.out.println("Enter command:");
            input = sc.nextLine();

        }
    }

    public void help() {
        System.out.println("Commands");
        System.out.println("\t help -- Display the Help menu");
        System.out.println("\t iter -- Number of iterations (Default set to 10)");
        System.out.println("\t split -- set split horizon to true/false");
        System.out.println("\t change -- change cost between nodes.\n" +
                            "\t\t\t - default when is next iteration");
        System.out.println("\t fail -- fail a link between nodes.\n" +
                            "\t\t\t - default when is next iteration");
        System.out.println("\t path -- Displays the path between two nodes\n" +
                            "\t\t\t - default when is next iteration");
        System.out.println("\t path -- Displays the path between two nodes\n" +
                            "\t\t\t - default when is next iteration");
        System.out.println("\t once -- Runs one iteration");
        System.out.println("\t run");
        System.out.println("\t quit");
    }

    public void setIteration(int iter) {
        g.setIterations(iter);
    }

}
