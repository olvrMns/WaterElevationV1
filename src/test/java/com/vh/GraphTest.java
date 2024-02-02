package com.vh;

import java.util.ArrayList;

public class GraphTest {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5};
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(4); integers.add(4); integers.add(4);
        for (int elem = 0; elem < integers.size() + 1; elem++) {
            System.out.println(integers.get(elem));
        }

    }
}
