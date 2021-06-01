/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 4 (Implement QuickHull Algorithm)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** Runner executes a command-line interface for testing the QuickHull algorithm.
 */
public class Runner {
    /** welcomeMessage returns a String containing this program's welcome message.
     * 
     * @return A String containing the welcome message for this program.
     */
    private static String welcomeMessage() {
        return "Welcome to Ryan McAllister-Grum's Assignment 4 QuickHull Algorithm Testing Program!";
    }
    
    /** main is the static method that Java executes to test the QuickHull algorithm
     *  through the command-line/terminal.
     * 
     * @param args The String array of arguments passed to the JVM.
     */
    public static void main(String[] args) {
        String input = ""; // input captures the user's command-line input.
        
        System.out.println(welcomeMessage()); // Output our welcome message.
        
        try (InputStreamReader reader = new InputStreamReader(System.in)) {
            char[] buff = new char[1000];
            int length;
            while(!input.equalsIgnoreCase("q")) {
                input = ""; // Reset the input buffer.
                System.out.println(); // Output a newline to ease reading error output.
                System.out.println("Please enter in a .txt file of x,y coordinates for which to find the hull, or enter 'q' to quit:");
                
                
                length = reader.read(buff, 0, buff.length);
                if (length != 0)
                    input = String.copyValueOf(buff, 0, length).replace(System.lineSeparator(), "").replace("\n", "").replace("\"", "");
                
                
                if (!input.isBlank() && !input.isEmpty()) {
                    if (!input.equalsIgnoreCase("q")) {
                        ArrayList<Point> points = null;
                        try {
                            points = readFile(input);
                        } catch(IllegalArgumentException | SecurityException | IOException e) {
                            e.printStackTrace();
                        }
                        
                        if (points != null) {
                            try {
                                System.out.println("Your list of hull points:");
                                System.out.println(new QuickHull(points));
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** readFile parses a text file containing a list of space-separated x,y coordinates into a list.
     * 
     * @param filePath The file to read.
     * @return An ArrayList<Point> containing the list of coordinates.
     * @throws IllegalArgumentException If the filePath parameter is null, empty, blank, a directory, or is not a .txt file.
     * @throws IOException If there is an error while reading filePath.
     * @throws SecurityException If the program is unable to read the file.
     */
    private static ArrayList<Point> readFile(String filePath) throws IllegalArgumentException, IOException, SecurityException {
        // First check that the filePath parameter is not null.
        if (filePath == null)
            throw new IllegalArgumentException("Error while executing readFile(String) in Runner: Parameter filePath is null!");
        // Next check that filePath is not empty or blank.
        else if (filePath.isEmpty())
            throw new IllegalArgumentException("Error while executing readFile(String) in Runner: Parameter filePath is empty!");
        else if (filePath.isBlank())
            throw new IllegalArgumentException("Error while executing readFile(String) in Runner: Parameter filePath is blank!");

        
        // Create a new File object to further verify that filePath is actually a .txt file.
        File file = new File(filePath);
        
        if (!file.canRead())
            throw new SecurityException("Error while executing readFile(String) in Runner: Parameter filePath \"" + filePath + "\" cannot be opened for reading!");
        else if (file.isDirectory())
            throw new IllegalArgumentException("Error while executing readFile(String) in Runner: Parameter filePath \"" + filePath + "\" is a directory, not a .txt file!");
        else if (file.isFile()) { // Verify that filePath is actually a .txt file.
            String extension = "";
            int i = filePath.lastIndexOf(".");
            if (i >= 0 && i < filePath.length() - 1)
                extension = filePath.substring(filePath.lastIndexOf(".") + 1);
            
            if (!extension.toLowerCase().equals("txt"))
                throw new IllegalArgumentException("Error while executing readFile(String) in Runner: Parameter filePath \"" + filePath + "\" is not a .txt file!");
        }
        
        // Read the .txt file line-by-line.
        ArrayList<Point> points = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for(String line = reader.readLine(); ; line = reader.readLine()) {
                if (!line.isEmpty() && !line.isBlank()) { // Read the list of x,y coordinates into the points list.
                    String[] lines = line.split(" ");
                    points.add(new Point(Double.parseDouble(lines[0]), Double.parseDouble(lines[1])));
                }
                
                if (!reader.ready())
                    break;
            }
        }
        
        // Return the list of Points.
        return points;
    }
}