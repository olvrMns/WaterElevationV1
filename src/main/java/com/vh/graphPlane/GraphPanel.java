package com.vh.graphPlane;

import com.vh.graphPlane.util.GraphDefaultType;
import com.vh.graphPlane.util.GraphVariables;
import com.vh.graphPlane.util.GraphWaterType;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GraphPanel
        extends JPanel
        implements GraphVariables {

    //https://www.baeldung.com/java-final -- final = initialise/instancie une seule fois
    private Integer max_elevation;
    private Integer number_of_values_x_axis;
    private Integer x_spacing;
    private Integer y_spacing;
    private Integer height = 0;
    private Integer width = 0;
    private final LinkedHashMap<Integer, Integer> elevation_map;
    private final ArrayList<Integer> x_axis_values;
    private final Color line_color;
    private boolean dotted_line;
    private boolean showWater;
    private boolean show_index;
    private int water_type;
    private final int[] initial_array;
    private final HashMap<Integer, Integer> solution_map;

    public ArrayList<Integer> getX_axis_values() {
        return x_axis_values;
    }

    public void setDotted_line(boolean dotted_line) {
        this.dotted_line = dotted_line;
    }

    public HashMap<Integer, Integer> getSolution_map() {
        return solution_map;
    }

    public void setShowWater(boolean showWater) {
        this.showWater = showWater;
    }

    public void setShow_index(boolean show_index) {
        this.show_index = show_index;
    }

    public void setWater_type(GraphWaterType water_type) {
        this.water_type = water_type.getValue();
    }

    private void setGraphProperties() throws Exception {
        setDimensions();
        setElevationMap();
        setXValues();
        resolve();
    }

    private GraphPanel(Integer max_elevation, Integer y_spacing, Integer x_spacing, Integer number_of_values_x_axis, Color line_color) throws Exception {
        this.max_elevation = checkMaxElevation(max_elevation);
        this.x_spacing = checkXSpacing(x_spacing);
        this.y_spacing = checkYSpacing(y_spacing);
        this.number_of_values_x_axis = number_of_values_x_axis;
        this.elevation_map = new LinkedHashMap<>();
        this.x_axis_values = new ArrayList<>();
        if (line_color == null) this.line_color = DEFAULT_COLOR; else this.line_color = line_color;
        this.initial_array = null;
        this.solution_map = new HashMap<>();
    }

    private GraphPanel(Integer max_elevation, Integer y_spacing, Integer x_spacing, int[] initial_array, Color line_color) throws Exception {
        this.max_elevation = checkMaxElevation(max_elevation);
        this.x_spacing = checkXSpacing(x_spacing);
        this.y_spacing = checkYSpacing(y_spacing);
        this.number_of_values_x_axis = initial_array.length;
        this.elevation_map = new LinkedHashMap<>();
        this.x_axis_values = new ArrayList<>();
        if (line_color == null) this.line_color = DEFAULT_COLOR; else this.line_color = line_color;
        this.initial_array = initial_array;
        this.solution_map = new HashMap<>();
    }

    private GraphPanel() {
        this.max_elevation = 20;
        this.x_spacing = 30;
        this.y_spacing = 30;
        this.number_of_values_x_axis = 30;
        this.elevation_map = new LinkedHashMap<>();
        this.x_axis_values = new ArrayList<>();
        this.line_color = DEFAULT_COLOR;
        this.initial_array = null;
        this.solution_map = new HashMap<>();
    }

    private void randomizeAttributes() {
        Random random = new Random();
        this.max_elevation = random.nextInt(MIN_ELEVATION, MAX_ELEVATION);
        this.x_spacing = random.nextInt(MIN_X_SPACING, MAX_X_SPACING);
        this.y_spacing = random.nextInt(MIN_Y_SPACING, MAX_Y_SPACING/2);
        this.number_of_values_x_axis = random.nextInt(MIN_NUMBER_OF_VALUES_X_AXIS, MAX_NUMBER_OF_VALUES_X_AXIS);
    }

    public static GraphPanel initialize(Integer max_elevation, Integer y_spacing, Integer x_spacing, Integer number_of_values_x_axis, Color line_color) throws Exception {
        GraphPanel gp = new GraphPanel(max_elevation, y_spacing, x_spacing, number_of_values_x_axis, line_color);
        gp.setGraphProperties();
        return gp;
    }

    public static GraphPanel initialize(Integer max_elevation, Integer y_spacing, Integer x_spacing, int[] initial_array, Color line_color) throws Exception {
        GraphPanel gp = new GraphPanel(max_elevation, y_spacing, x_spacing, initial_array, line_color);
        gp.setGraphProperties();
        return gp;
    }

    public static GraphPanel initialize(GraphDefaultType gdt) throws Exception {
        GraphPanel gp = new GraphPanel();
        if (gdt.equals(GraphDefaultType.RANDOM)) {
            gp.randomizeAttributes();
        }
        gp.setGraphProperties();
        return gp;
    }

    private Integer checkMaxElevation(Integer max_elevation) throws Exception {
        if (max_elevation > MAX_ELEVATION || max_elevation < MIN_ELEVATION) throw ElevationGraphException.incorrectElevation();
        return max_elevation;
    }

    private Integer checkXSpacing(Integer x_spacing) throws Exception {
        if (x_spacing > MAX_X_SPACING || x_spacing < MIN_X_SPACING) throw ElevationGraphException.incorrectSpacing();
        return x_spacing;
    }

    private Integer checkYSpacing(Integer y_spacing) throws Exception {
        if (y_spacing > MAX_Y_SPACING || y_spacing < MIN_Y_SPACING) throw ElevationGraphException.incorrectSpacing();
        return y_spacing;
    }

    private Integer getPercentageOf(Integer initial_value, Integer percentage) throws Exception {
        if (percentage > MAX_PERCENTAGE || percentage < MIN_PERCENTAGE) throw ElevationGraphException.incorrectPercentage();
        return ((initial_value * percentage)/MAX_PERCENTAGE);
    }

    private void setDimensions() throws Exception {
        this.width = 0;
        this.height = 0;
        for (int elem = 0; elem < number_of_values_x_axis; elem++) width = width + (int)(x_spacing*WIDTH_MULTIPLIER);
        for (int elem = 0; elem < max_elevation; elem++) height = height + (int)(y_spacing*HEIGHT_MULTIPLIER);
        //width+=getPercentageOf(width, WIDTH_REDUCTION_PERCENTAGE);
        //height+=getPercentageOf(height, HEIGHT_REDUCTION_PERCENTAGE);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * @Note les paliers d'élévation sont calculés à partir de pourcentage d'une hauteur réduite afin de laisser une marge
     */
    private void setElevationMap() throws Exception {
        int reduced_height = height - getPercentageOf(height, MAP_REDUCTION_PERCENTAGE);
        int increment = reduced_height/max_elevation; //on divise la hauteur réduite par l'elevation
        int value_of_elevation = 0;
        for (int elem = 1; elem < max_elevation + 1; elem++) {
            value_of_elevation = value_of_elevation + increment;
            elevation_map.put(elem, value_of_elevation);
        }
    }

    private void setXValues() throws Exception {
        if (initial_array == null) {
            int rand_int;
            Random random = new Random();
            for (int elem = 0; elem < number_of_values_x_axis; elem++) {
                rand_int = random.nextInt(1, max_elevation + 1);
                x_axis_values.add(rand_int);
            }
        } else for (int integer : initial_array) x_axis_values.add(integer);

    }

    private void resolve() throws Exception {
        if (ascendantSolver()) {
            System.out.println(reversedAscendantSolver());
        }
    }

    /**
     * @Note marche seulement pour la partie gauche (avant la plus grande elevation)
     * @return (...)
     * @throws Exception (...)
     */
    private boolean ascendantSolver() throws Exception {
        int current_value;
        int size = x_axis_values.size();
        ArrayList<Integer> sub_x_values = new ArrayList<>();
        for (int elem = 0; elem < size - 1; elem++) { //elem = index de la x axis list [0,1,2,3,...n]
            current_value = x_axis_values.get(elem); //current value in x axis list
            if (current_value > x_axis_values.get(elem + 1)) { //si l'elevation presente est plus grande que sa prochaine
                for (int sub_elem = elem + 1; x_axis_values.get(sub_elem) < current_value; sub_elem++) { //ajoute dans la liste sub_x_value les elements x_axis_value a partir de l'index du for() principale jusqu'au moment ou current value est plus petit
                    sub_x_values.add(sub_elem);
                    if (sub_elem == size - 1) return true; //si la haute élévation a été trouvé
                }
                for (Integer index : sub_x_values) solution_map.put(index, elevation_map.get(current_value));
                elem = sub_x_values.get(sub_x_values.size() - 1); //elem recommence à partir l'element ex:7(6) de la liste --> [0,1,2,3,4,5,6]
                sub_x_values.clear();
            }
        }
        return false;
    }

    /**
     * @Note from right to left (même méthode, mais cette fois-ci on va de 18 -> à la plus grande elevation)
     * @return ()
     * @throws Exception ()
     */
    private boolean reversedAscendantSolver() throws Exception {
        int current_value;
        ArrayList<Integer> sub_x_values = new ArrayList<>();
        for (int elem = x_axis_values.size() - 1; elem > 1; elem--) {
            current_value = x_axis_values.get(elem);
            if (current_value > x_axis_values.get(elem - 1)) {
                for (int sub_elem = elem - 1; x_axis_values.get(sub_elem) < current_value; sub_elem--) {
                    sub_x_values.add(sub_elem);
                    if (sub_elem == 0) return true;
                }
                for (Integer i : sub_x_values) solution_map.put(i, elevation_map.get(current_value));
                elem = sub_x_values.get(sub_x_values.size() - 1);
                sub_x_values.clear();
            }
        }
        return false;
    }



    // ==========================================================================================================================================

    private Graphics2D getGraph2d(Graphics graphics) {
        return (Graphics2D) graphics;
    }

    private void setColorToDefault(Graphics graphics) {
        graphics.setColor(DEFAULT_COLOR);
    }

    private void drawLineWithColor(Graphics copy, int x1, int y1, int x2, int y2, Color color) {
        Graphics2D graphics2D = getGraph2d(copy);
        graphics2D.setColor(color);
        graphics2D.drawLine(x1, y1, x2, y2);
        setColorToDefault(graphics2D);
    }

    private void drawYAxisNumbers(Graphics copy, int current_x_position, int min_drawing_y_value) {
        Graphics2D graphics2D = getGraph2d(copy);
        setColorToDefault(graphics2D);
        int y_axis_values_offset = 4;
        for (Map.Entry<Integer, Integer> entry : elevation_map.entrySet()) {
            graphics2D.drawString(String.valueOf(entry.getKey()), current_x_position - SMALL_SPACING, min_drawing_y_value - entry.getValue() + y_axis_values_offset);
        }
    }

    private void drawXAxisNumbers(Graphics copy, int current_x_position, int min_drawing_y_value) {
        Graphics2D graphics2D = getGraph2d(copy);
        String showed_value;
        for (int elem = 0; elem < x_axis_values.size(); elem++) {
            if (show_index) showed_value = x_axis_values.get(elem) + LEFT_PARENTHESES + elem + RIGHT_PARENTHESES; else showed_value = String.valueOf(x_axis_values.get(elem));
            graphics2D.drawString(showed_value, current_x_position + x_spacing/2, min_drawing_y_value + SMALL_SPACING);
            current_x_position+=x_spacing;
        }
    }

    private void drawDottedLines(Graphics copy, int current_x_position, int min_drawing_y_value) {
        Graphics2D graphics2D = getGraph2d(copy);
        Stroke dotted = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{DASHING_PATTERN_VALUE}, 0);
        graphics2D.setColor(DOTTED_LINE_COLOR);
        graphics2D.setStroke(dotted);
        for (Map.Entry<Integer, Integer> entry : elevation_map.entrySet()) {
            graphics2D.drawLine(current_x_position, min_drawing_y_value - entry.getValue(), width ,min_drawing_y_value - entry.getValue());
        }
    }

    private void drawWater(Graphics graphics, int current_x_position, int min_drawing_y_value) {
        Graphics2D g2d = getGraph2d(graphics);
        g2d.setColor(WATER_COLOR);
        int water_elevation;
        for (int elem = 0; elem < x_axis_values.size(); elem++) {
            if (solution_map.containsKey(elem)) { //après que x se déplace par x-spacing
                water_elevation = min_drawing_y_value - solution_map.get(elem);
                drawLineWithColor(graphics, current_x_position, water_elevation, current_x_position + x_spacing, water_elevation, WATER_COLOR);
            }
            current_x_position+=x_spacing;
        }
    }

    public void drawWaterRectangles(Graphics graphics, int current_x_position, int min_drawing_y_value) {
        Graphics2D g2d = getGraph2d(graphics);
        g2d.setColor(WATER_COLOR);
        int water_elevation;
        for (int elem = 0; elem < x_axis_values.size(); elem++) {
            if (solution_map.containsKey(elem)) {
                water_elevation = min_drawing_y_value - solution_map.get(elem);
                Point point = new Point(current_x_position, water_elevation);
                Dimension dimension = new Dimension(x_spacing, solution_map.get(elem) - elevation_map.get(x_axis_values.get(elem)));
                Rectangle rectangle = new Rectangle(point, dimension);
                g2d.draw(rectangle);
                g2d.fill(rectangle);
            }
            current_x_position+=x_spacing;
        }
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2d = getGraph2d(graphics);
        g2d.setFont(new Font(VALUES_FONT_STYLE, VALUES_FONT_TYPE, VALUES_FONT_SIZE));
        setColorToDefault(graphics);
        int min_drawing_y_value = height - ((height*REDUCTION_PERCENTAGE)/MAX_PERCENTAGE);
        int current_x_position = ((width*WIDTH_REDUCTION_PERCENTAGE)/MAX_PERCENTAGE);
        int current_y_position;

        if (dotted_line) drawDottedLines(graphics, current_x_position, min_drawing_y_value);
        if (showWater && water_type == GraphWaterType.SINGLE_LINE.getValue()) drawWater(graphics, current_x_position, min_drawing_y_value);
        else if (water_type == GraphWaterType.RECTANGLE.getValue()) drawWaterRectangles(graphics, current_x_position, min_drawing_y_value);
        drawLineWithColor(graphics, 0, min_drawing_y_value, width, min_drawing_y_value, DEFAULT_COLOR); //draws line on x-axis
        drawYAxisNumbers(graphics, current_x_position, min_drawing_y_value);
        drawXAxisNumbers(graphics, current_x_position, min_drawing_y_value);

        for (int elem = 0; elem < x_axis_values.size(); elem++) {
            current_y_position = min_drawing_y_value - elevation_map.get(x_axis_values.get(elem)); //set du positionnement y
            if (elem == 0) g2d.drawLine(current_x_position, current_y_position, current_x_position, min_drawing_y_value);
            g2d.drawLine(current_x_position, current_y_position, current_x_position + x_spacing, current_y_position); //draws x-axis line
            current_x_position = current_x_position + x_spacing;
            if (elem != x_axis_values.size() - 1) {
                g2d.drawLine(current_x_position, current_y_position, current_x_position, min_drawing_y_value - elevation_map.get(x_axis_values.get(elem + 1))); //draws y-axis line
            } else {
                g2d.drawLine(current_x_position, current_y_position,current_x_position,min_drawing_y_value);
            }
        }
    }

    @Override
    public String toString() {
        return "GraphPanel{" +
                "max_elevation=" + max_elevation +
                ", number_of_values_x_axis=" + number_of_values_x_axis +
                ", x_spacing=" + x_spacing +
                ", y_spacing=" + y_spacing +
                ", height=" + height +
                ", width=" + width +
                ", elevation_map=" + elevation_map +
                ", x_axis_values=" + x_axis_values +
                ", line_color=" + line_color +
                ", dotted_line=" + dotted_line +
                ", showWater=" + showWater +
                ", show_index=" + show_index +
                ", initial_array=" + Arrays.toString(initial_array) +
                ", solution_map=" + solution_map +
                "} " + super.toString();
    }
}
