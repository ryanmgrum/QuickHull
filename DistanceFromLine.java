/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 4 (Implement QuickHull Algorithm)
 */

import java.util.Comparator;


/** DistanceFromLine sorts a list of Points by their distance from the given line
 *  created by the Points AB.
 */
public class DistanceFromLine implements Comparator<Point> {
    /** a is the starting point that makes up the line from which to find the
     *  distance for the other Points.
     */
    private final Point a;
    /** b is the end point that makes up the line from which to find the
     *  distance for the other Points.
     */
    private final Point b;
    
    /** Constructor that takes in Points a and b that make up the line for which
     *  we want to find the distance of all the other points.
     * 
     * @param newA The Point that will be the starting Point of the AB line.
     * @param newB The Point that will be the ending Point of the AB line.
     * @throws IllegalArgumentException If either newA or newB is null or are the same point.
     */
    public DistanceFromLine(Point newA, Point newB) throws IllegalArgumentException {
        // First verify that neither newA nor newB is null.
        if (newA == null)
            throw new IllegalArgumentException("Error while constructing a new DistanceFromLine(Point, Point): Parameter newA is null!");
        else if (newB == null)
            throw new IllegalArgumentException("Error while constructing a new DistanceFromLine(Point, Point): Parameter newB is null!");
        // Next, check that newA and newB are not the same Point (either by address or by coordinates).
        else if (newA == newB)
            throw new IllegalArgumentException("Error while constructing a new DistanceFromLine(Point, Point): Parameters newA and newB are the same address!");
        else if (newA.equals(newB))
            throw new IllegalArgumentException("Error while constructing a new DistanceFromLine(Point, Point): Parameters newA and newB are the same coordinate!");
        
        a = newA;
        b = newB;
    }
    
    /** compare compares two points to see which is further from the AB line.
     * 
     * @param p1 The first Point to compare.
     * @param p2 The second point to compare.
     * @return {@literal <} 0 If p1 is closer to the line, 0 if equidistant, and
     * {@literal >} 0 if p1 is further from the line compared to p2.
     * @throws IllegalArgumentException If either p1 or p2 is null, or they are the same Point
     * (either in terms of address or coordinate).
     */
    @Override
    public int compare(Point p1, Point p2) {
        // First check that neither p1 nor p2 are null.
        if (p1 == null)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in DistanceFromLine: Parameter p1 is null!");
        else if (p2 == null)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in DistanceFromLine: Parameter p2 is null!");
        // Next check that p1 and p2 are not the same address or coordinate.
        else if (p1 == p2)
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in DistanceFromLine: Parameters p1 and p2 are the same address!");
        else if (p1.equals(p2))
            throw new IllegalArgumentException("Error while executing compare(Point, Point) in DistanceFromLine: Parameters p1 and p2 are the same coordinate!");
        
        // Now, return the order in which these two Points are distant from the line.
        double d1 = distanceFromLine(p1, slope(a, b), intercept(a, slope(a, b)));
        double d2 = distanceFromLine(p2, slope(a, b), intercept(a, slope(a, b)));
        int compare = Double.compare(d1, d2);
        if (d1 < 0 && d2 < 0) {
            if (compare > 0)
                return -1;
            else if (compare < 0)
                return 1;
            else
                return 0;
        } else
          return compare;
    }
    
    /** slope calculates the slope of the line resulting between two Points.
     * 
     * @param p1 The first point of the line to calculate slope.
     * @param p2 The second point of the line to calculate slope.
     * @return A double containing the slope of the line created by the two Points.
     * @throws IllegalArgumentException If either p1 or p2 are null or the same Point (address or coordinate).
     */
    private double slope(Point p1, Point p2) throws IllegalArgumentException {
        // First check that p1 and p2 are not null.
        if (p1 == null)
            throw new IllegalArgumentException("Error while executing slope(Point, Point) in QuickHull: Parameter p1 is null!");
        else if (p2 == null)
            throw new IllegalArgumentException("Error while executing slope(Point, Point) in QuickHull: Parameter p2 is null!");
        // Next check that p1 and p2 are not the same variable or coordinate.
        else if (p1 == p2)
            throw new IllegalArgumentException("Error while executing slope(Point, Point) in QuickHull: Parameters p1 and p2 are the same address!");
        else if (p1.equals(p2))
            throw new IllegalArgumentException("Error while executing slope(Point, Point) in QuickHull: Parameters p1 and p2 are the same coordinate!");
        
        // Return the slope of the two points ((y2 - y1)/(x2 - x1)).
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }

    /** intercept finds the y-intercept of a point's line given its slope with another point.
     * 
     * @param point The Point used to find the y-intercept.
     * @param slope The slope used in the calculation to find the y-intercept.
     * @return A double containing the location of the point's y-intercept.
     * @throws IllegalArgumentException If the point parameter is null.
     */
    private double intercept(Point point, double slope) throws IllegalArgumentException {
        // First verify that point is not null.
        if (point == null)
            throw new IllegalArgumentException("Error while executing intercept(Point, double) in QuickHull: Parameter point is null!");
        
        // Return the y-intercept of the line based on the given point and slope.
        return point.getY() - slope * point.getX();
    }

    /** distanceFromLine returns the distance a Point is from an AB line
     *  (slope and intercept parameters should come from the other two A, B Points).
     * 
     * @param point The Point from which to find its distance from a given line.
     * @param slope The slope of the line formed by separate AB Points.
     * @param intercept The y-intercept of the line formed by separate AB Points.
     * @return A double containing the distance the point is from the line.
     * @throws IllegalArgumentException If the point parameter is null.
     */
    private double distanceFromLine(Point point, double slope, double intercept) throws IllegalArgumentException {
        // First check that the point parameter is not null.
        if (point == null)
            throw new IllegalArgumentException("Error while executing distanceFromLine(Point, double, double) in QuickHull: Parameter point is null!");
        
        // Return the distance the Point is from the line.
        // ((-m * P.x) + P.y - b)/sqrt(m^2 + 1)
        return (((-1 * slope) * point.getX()) + point.getY() - intercept) /
                Math.sqrt(Math.pow(slope, 2.0) + 1);
    }
}