package com.company;

/**
 * Created by 2030449c on 02/03/17.
 */
public class Link {

    private int distance;
    private int destination;
    private int route;

    public Link(int distance, int destination, int route) {
        this.distance = distance;
        this.destination = destination;
        this.route = route;
    }


    public int getDistance() {
        return distance;
    }

    public int getDestination() {
        return destination;
    }

    public int getRoute() {
        return route;
    }
}
