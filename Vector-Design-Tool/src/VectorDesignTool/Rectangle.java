package VectorDesignTool;

public class Rectangle {

    private int length;
    private int width;
    private String colour;
    private int[] coords;

    /**
     * When the rectangle button is pressed, the object is created.
     * @param length - length of rectangle
     * @param width - width of rectangle
     * @param colour - colour of line
     * @param coords - coordinate of first point of rectangle
     */
    public Rectangle(int length, int width, String colour, int[] coords){
        this.length = length;
        this.width = width;
        this.colour = colour;
        this.coords = coords;
    }


}
