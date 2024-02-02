package com.vh.graphPlane;

import com.vh.graphPlane.util.GraphDefaultType;
import com.vh.graphPlane.util.GraphWaterType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ElevationGraph
        extends JFrame {

    GraphPanel graphPanel;

    private void setElevationGraphProperties() throws Exception {
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(graphPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("./src/main/resources/images/ori1.jpg").getImage());
    }

    private ElevationGraph(Integer max_elevation, Integer y_spacing, Integer x_spacing, Integer number_of_values_x_axis, Color line_color) throws Exception {
        graphPanel = GraphPanel.initialize(max_elevation, y_spacing, x_spacing, number_of_values_x_axis, line_color);
        setElevationGraphProperties();
    }

    private ElevationGraph(Integer max_elevation, Integer y_spacing, Integer x_spacing, int[] initial_array, Color line_color) throws Exception {
        graphPanel = GraphPanel.initialize(max_elevation, y_spacing, x_spacing, initial_array, line_color);
        setElevationGraphProperties();
    }

    private ElevationGraph() throws Exception {
        graphPanel = GraphPanel.initialize(GraphDefaultType.DEFAULT);
        setElevationGraphProperties();
    }

    private ElevationGraph(GraphDefaultType gdt) throws Exception {
        graphPanel = GraphPanel.initialize(gdt);
        setElevationGraphProperties();
    }

    public static ElevationGraph initialize(Integer max_elevation, Integer y_spacing, Integer x_spacing, Integer number_of_values_x_axis, Color line_color) throws Exception {
        return new ElevationGraph(max_elevation, y_spacing, x_spacing, number_of_values_x_axis, line_color);
    }

    public static ElevationGraph initialize(Integer max_elevation, Integer y_spacing, Integer x_spacing, int[] initial_array, Color line_color) throws Exception {
        return new ElevationGraph(max_elevation, y_spacing, x_spacing, initial_array, line_color);
    }

    public static ElevationGraph initialize() throws Exception {
        return new ElevationGraph();
    }

    public static ElevationGraph initialize(GraphDefaultType gdt) throws Exception {
        return new ElevationGraph(gdt);
    }

    /**
     * @Note turns the visibility of window on
     * @throws Exception (...)
     */
    public void start(boolean dotted_line, boolean showWater, boolean show_index, GraphWaterType gwt) throws Exception {
        graphPanel.setDotted_line(dotted_line);
        graphPanel.setShowWater(showWater);
        graphPanel.setShow_index(show_index);
        graphPanel.setWater_type(gwt);
        this.setVisible(true);
    }

    /**
     * @Note turns the visibility of window on with default parameters
     * @throws Exception (...)
     */
    public void start() throws Exception {
        graphPanel.setDotted_line(false);
        graphPanel.setShowWater(false);
        graphPanel.setShow_index(false);
        graphPanel.setWater_type(GraphWaterType.SINGLE_LINE);
        this.setVisible(true);
    }

    /**
     * @Note deletes the object
     * @throws Exception (...)
     */
    public void close() throws Exception {
        dispose();
    }

    public ArrayList<Integer> getXAxisValues() {
        return graphPanel.getX_axis_values();
    }

    public HashMap<Integer, Integer> getSolution() {
        return graphPanel.getSolution_map();
    }

    public String printPanelAttributes() {
        return graphPanel.toString();
    }


}
