/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 4 (Implement QuickHull Algorithm)
 */

/** The Point class contains an X and Y double coordinate, used as part
 *  of implementing the QuickHull algorithm.
 */
public class Point {
    /** x is the x-coordinate of this Point.
     */
    private final double x;
    /** y is the y-coordinate of this Point.
     */
    private final double y;
    
    /** Constructor that takes in an x-y coordinate in double format.
     * 
     * @param newX The new X coordinate to store in this Point.
     * @param newY The new Y coordinate to store in this Point.
     */
    public Point(double newX, double newY) {
        x = newX;
        y = newY;
    }
    
    /** getX returns the x-coordinate of this Point.
     * 
     * @return The x attribute.
     */
    public double getX() {
        return x;
    }
    
    /** getY returns the y-coordinate of this Point.
     * 
     * @return The y attribute.
     */
    public double getY() {
        return y;
    }
    
    /** toString() outputs the coordinates of this Point in space-separated format.
     * 
     * @return The x attribute, followed by a space, followed by the y attribute.
     */
    @Override
    public String toString() {
        return x + " " + y;
    }
    
    /** equals determines whether the point parameter is the same as this Point.
     * 
     * @param point The Point to compare to this Point.
     * @return True if the passed-in Point's x and y coordinate values are 
     * identical to this Point.
     * @throws IllegalArgumentException If the point parameter is null.
     */
    public boolean equals(Point point) {
        // First check that the point parameter is not null.
        if (point == null)
            throw new IllegalArgumentException("Error while executing equals(Point) in Point: Parameter point is null!");
        
        return Double.compare(x, point.getX()) == 0 && Double.compare(y, point.getY()) == 0;
    }
}