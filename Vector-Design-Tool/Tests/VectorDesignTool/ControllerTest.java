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

        // Input a Test Input Value
        String testVariable = "9";

        // Example Inputs
        String testInput2 = "Nine";
        String testInput3 = "/?$#";

        // Call method to find boolean
        boolean testBoolean = controller.inputBrushValue(testVariable);
        boolean testBoolean2 = controller.inputBrushValue(testInput2);
        boolean testBoolean3 = controller.inputBrushValue(testInput3);

        // Test for both a numerical input, character input or string input
        //assertEquals(true, testBoolean);
        assertTrue(testBoolean == true, testVariable + " is not a number between 1-1000");
        assertTrue(testBoolean2 == false, testInput2 + " is not a number between 1-1000");
        assertTrue(testBoolean3 == false, testInput3 + " is a number between 1-1000");

    }

    /*
     * Test 2: Testing the canvas history being cleared
     */
    @Test
    public void testCanvasHistory() {
        controller = new Controller();

        // Tester inputs a PLOT CHOICE
        String result = "ELLIPSE";


        // StringBuilder is initialised assertion test
        StringBuilder saved2 = new StringBuilder("PLOT ");

        String option = "PLOT POLYGON";
        String option1 = "PLOT ELLIPSE";
        String option2 = "PLOT RECTANGLE";


        //result = "PLOT " + result;
        saved2.append(result);


        assertTrue(saved2.toString().equals(option)||saved2.toString().equals(option1)||saved2.toString().equals(option2), result + " is not an option");


    }


    /*
     * Test 3: An error message is created when saving an empty screen.
     *
     */
    @Test
    public void testEmptySaves() {
        controller = new Controller();

        // Input a value for a testing number of changes made.
        int numberofEdits = 1;

        // Use Controller value for number of edits
        long EditsOnFile = controller.savefile.toString().chars().filter(line -> line == '\n').count();

        // Test if more than zero changes have been made
        assertTrue(numberofEdits > EditsOnFile,"Test Edits Variable (" + numberofEdits + ") should be greater than  Edits Made (" + EditsOnFile + ") in order to save the file.");
    }

}
