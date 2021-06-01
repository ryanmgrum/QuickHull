/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 4 (Implement QuickHull Algorithm)
 */

import java.util.Comparator;

/** XThenY is a Comparator that compares a set of points by their x-coordinate,
 *  followed by their y-coordinate.
 */
public class XThenY implements Comparator<Point> {
    /** The compare method in XThenY first compares two Points for x-equality,
     *  and if so, then y-equality.
     * 
     * @param p1 The first Point to compare.
     * @param p2 the second Point to compare.
     * @return {@literal <} 0 If p1's x-, then y-coordinate, is smaller than p2's,
     * 0 if equal, and {@literal >} 0 if bigger.
     * @throws IllegalArgumentException If p1 or p2 are null or the same address or coordinate.
     */
    @Override
    public int compare(Point p1, Point p2) {
        // First check that neither p2 nor p2 are null.
        if (p1 == null)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in XThenY: Parameter p1 is null!");
        else if (p2 == null)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in XThenY: Parameter p2 is null!");
        // Next check that p1 and p2 are not the same.
        else if (p1 == p2)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in XThenY: Parameter p1 and p2 are the same variable!");
        else if (p1.equals(p2))
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in XThenY: Parameter p1 and p2 are the same coordinate (" + p1 + ")!");
        
        // Return whether p1's x- and y-coordinate is larger than p2's coordinates.-
        if (Double.compare(p1.getX(), p2.getX()) != 0)
            return Double.compare(p1.getX(), p2.getX());
        else
            return Double.compare(p1.getY(), p2.getY());
    }
}