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
    boolean value;
    @Test
    public void testBrushInput() throws InstantiationError {
        try {
            controller = new Controller();
            boolean value = controller.brushSize.getText().matches("[0-9]*");

        } catch (Exception expected) {
            assertEquals(null, expected.getMessage());
            assertEquals(true, value);
        }
    }


}