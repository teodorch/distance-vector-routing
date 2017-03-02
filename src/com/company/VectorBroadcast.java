package com.company;

/**
 * Created by morti on 23/02/2017.
 */
public class VectorBroadcast imp{

    private Graph g;

    public VectorBroadcast(Graph g){
        this.g = g;
    }

    public void broadcast() {
        for (Node n : g.getNodes()) {
            Thread t = new Thread();
        }
    }
}
