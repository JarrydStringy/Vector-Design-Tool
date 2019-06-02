package VectorDesignTool;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class ControllerTest {

    /*
     * Test 0: Declaring Controller objects.
     */
    Controller controller;

    @BeforeEach
    public void setUpController() throws InstantiationError {
        controller = null;
    }

    /*
     * Test 1: Choosing the Brush size - must be an integer.
     */
    @Test
    public void testBrushInput() {
        controller = new Controller();

        // Read an input value
        String input = controller.inputBrushValue("One");

        // Use a boolean to determine if the input is a number or not
        boolean value = input.matches("[0-9]*");
        boolean value2 = ("9").matches("[0-9]*");
        boolean value3 = ("/?$#").matches("[0-9]*");

        // Test for both a numerical input, character input or string input
        assertEquals(false, value);
        assertEquals(true, value2);
        assertEquals(false, value3);
    }

    /*
     * Test 1: Choosing the Brush size - must be an integer.
     */
    @Test
    public void testReadFile() {
//        controller = new Controller();
//
//        // Read an input value
//        String input = controller.inputBrushValue("One");
//
//        // Use a boolean to determine if the input is a number or not
//        boolean value = input.matches("[0-9]*");
//        boolean value2 = ("9").matches("[0-9]*");

//        // Test for both a numerical input, character input or string input
//        assertEquals(false, value);
//        assertEquals(true, value2);


    }


}