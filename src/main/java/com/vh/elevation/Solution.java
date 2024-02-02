package com.vh.elevation;

import com.vh.graphPlane.ElevationGraph;
import com.vh.graphPlane.util.GraphDefaultType;
import com.vh.graphPlane.util.GraphWaterType;

public class Solution {

    ElevationGraph graph, graph2, graph3, graph4, graph5, graph6;
    int[] initial_array;

    public Solution() throws Exception {
        //initial_array = new int[]{3,1,7,8,3,10,2,3,4,6,6,2,4,10,3,4,5,6,3}; //initial_array = new int[]{3,1,7,8,3,10,2,3,4,6,6,2,4,10,3};
        //this.graph = ElevationGraph.initialize(20, 30, 35, 30, null);
        //this.graph2 = ElevationGraph.initialize(10, 30, 50, initial_array, null);
        //this.graph3 = ElevationGraph.initialize(GraphDefaultType.DEFAULT);
        //this.graph4 = ElevationGraph.initialize(GraphDefaultType.DEFAULT);
        //this.graph5 = ElevationGraph.initialize(GraphDefaultType.DEFAULT);
        //this.graph6 = ElevationGraph.initialize(GraphDefaultType.DEFAULT);
    }

    public void results() throws Exception {
        try {
            //graph.start(false, true, false, GraphWaterType.SINGLE_LINE);
            //graph2.start(false, true, true, GraphWaterType.RECTANGLE);
            //graph3.start(false, true, false, GraphWaterType.RECTANGLE);
            //graph4.start(false, true, false, GraphWaterType.RECTANGLE);
            //graph5.start(false, true, false, GraphWaterType.SINGLE_LINE);
            //graph6.start(false, true, false, GraphWaterType.RECTANGLE);
            for (int elem = 0; elem < 5; elem++) {
                ElevationGraph e = ElevationGraph.initialize(GraphDefaultType.DEFAULT);
                e.start(false, true, false, GraphWaterType.RECTANGLE);
                System.out.println(e.printPanelAttributes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //graph.close(); graph2.close(); graph3.close(); graph4.close(); graph5.close(); graph6.close();
        }

    }

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.results();
    }
}
