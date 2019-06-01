//package VectorDesignTool;
//
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.stage.FileChooser;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.text.DecimalFormat;
//
//public class SaveBMP {
//    //public static StringBuilder saveBMPFile = new StringBuilder();
//    public static DecimalFormat df2 = new DecimalFormat("0.000000");
//    private GraphicsContext g;
//
//    /**
//     * Saves file to chosen directory based on user selection, of file type 'VEC'.
//     *
//     * @param g - Graphics Context of canvas
//     */
//    public SaveBMP(GraphicsContext g) {
//        this.g = g;
//    }
//
//    public void saveBMPFile() {
//        //saveBMPFile.deleteCharAt(0);
//        //String saveBMPfile = saveBMPFile.toString();
//        Image snapshot = canvas.snapshot(null, null)
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save Resource File");
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter =
//                new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
//        fileChooser.getExtensionFilters().add(extFilter);
//        File file = fileChooser.showSaveDialog(null);
//        if (file != null) {
//            saveToFile(file, saveBMPfile);
//        }
//    }
//
//    /**
//     * Writes the file using FileWriter class.
//     *
//     * @param file   - file being saved to
//     * @param result - string of contents being written to file
//     */
//    private void saveToFile(File file, String result) {
//        try {
//            FileWriter bmpFile;
//            bmpFile = new FileWriter(file);
//            bmpFile.write(result);
//            bmpFile.close();
//        } catch (Exception e) {
//            System.out.println("Failed to save file: " + e);
//        }
//    }
//
//}
