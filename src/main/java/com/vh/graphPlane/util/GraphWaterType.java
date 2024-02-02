package com.vh.graphPlane.util;

public enum GraphWaterType {
    SINGLE_LINE(0),
    RECTANGLE(1)
    ;

    private int value;

    public int getValue() {
        return this.value;
    }

    GraphWaterType(int value) {
        this.value = value;
    }
}
