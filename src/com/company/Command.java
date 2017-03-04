package com.company;

import java.util.Arrays;

/**
 * Created by 2030449c on 04/03/17.
 */
public class Command {

    private int iter;
    private String name;
    private int[] params;

    public Command(int iter, String name, int... params){
        this.iter = iter;
        this.name = name;
        this.params = params;
    }

    public int getIter() {
        return  iter;
    }

    public String getName() {
        return name;
    }

    public int[] getParams() {
        return params;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Iteration: ");
        sb.append(iter).append("\n");

        sb.append("Name: ");
        sb.append(name).append("\n");

        sb.append("Params: ");
        sb.append(Arrays.toString(params)).append("\n");

        return sb.toString();
    }
}
