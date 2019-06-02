package VectorDesignTool;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.javafx.geom.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class ShapesTest {
    public GraphicsContext g;
    Shapes shapes;

    @BeforeEach
    public void setUpShapes() throws InstantiationError {
        shapes = null;
    }



    /*
     * Test 1: Testing the shape coordinates being greater than 0
     */
    @Test
    public void testCoordinates() {
        // Tester should input coordinates to be tested
        double x1 =100;
        double x2 =100;
        double y1 =100;
        double y2 =100;

        // Put into array for testing methods
        double[][] coords = {{x1,y1},{x2,y2}};

        double[][] zero = {{0,0},{0,0}};

        // Intialize Shapes Method
        shapes = new Shapes("",g,coords);


        // Test that the values are greater than zero
        assertTrue(coords[0][0] > 0,"The value of x1 (" + coords[0][0] + ") should be greater than zero");
        assertTrue(coords[0][1] > 0,"The value of y2 (" + coords[0][1] + ") should be greater than zero");
        assertTrue(coords[1][0] > 0,"The value of x1 (" + coords[1][0] + ") should be greater than zero");
        assertTrue(coords[1][1] > 0,"The value of y2 (" + coords[1][1] + ") should be greater than zero");
    }




}