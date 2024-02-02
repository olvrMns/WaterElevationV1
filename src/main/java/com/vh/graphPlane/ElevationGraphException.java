package com.vh.graphPlane;

public class ElevationGraphException
        extends Exception {

    public ElevationGraphException(String message) {
        super(message);
    }

    public static Exception incorrectSpacing() {
        return new ElevationGraphException("SPACING ERROR");
    }

    public static Exception incorrectHeight() {
        return new ElevationGraphException("HEIGHT ERROR");
    }

    public static Exception incorrectElevation() {
        return new ElevationGraphException("ELEVATION ERROR");
    }

    public static Exception incorrectPercentage() {
        return new ElevationGraphException("PERCENTAGE ERROR");
    }
}
