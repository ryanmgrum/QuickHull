/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 4 (Implement QuickHull Algorithm)
 */

import java.util.ArrayList;

/** QuickHull contains the logic and data necessary to:
 *  1. Take in an ArrayList of Point objects,
 *  2. Determine the convex hull of the Points,
 *  3. Return the resulting Points that make up the surrounding hull.
 */
public class QuickHull {
    /** hull contains the resulting Points that make up the convex hull
     *  of the original list of Points.
     */
    private final ArrayList<Point> hull;
    
    /** Constructor that takes in a TreeSet of Points and finds their convex hull.
     * 
     * @param points The Points for which to find the convex hull.
     * @throws IllegalArgumentException If the list of Points is null or has less than 2 points.
     */
    public QuickHull(ArrayList<Point> points) throws IllegalArgumentException {
        // First check that the points parameter is not null.
        if (points == null)
            throw new IllegalArgumentException("Error while constructing a new QuickHull(ArrayList<Point>): The points parameter is null!");
        // Next check that the points parameter has 2 or more points.
        else if (points.size() < 2)
            throw new IllegalArgumentException("Error while constructing a new QuickHull(ArrayList<Point>): Cannot create a hull with less than 2 points (size: " + points.size() + ")!");
        
        // Initialize hull.
        hull = new ArrayList<>();
        
        // Call quickhull.
        quickhull(points);
    }
    
    /** quickhull(ArrayList<Point>) begins the Divide-and-Conquer Quickhull algorithm,
     *  which finds the convex hull for a set of xy-coordinate points.
     * 
     * @param points The initial list of Points of which to find the convex hull.
     */
    private void quickhull(ArrayList<Point> points) {
        /* First find the leftmost and rightmost points A and B, and add them to hull
         * (remove from points, as well).
         */
        points.sort(new XThenY());
        Point a = points.get(0);
        Point b = points.get(points.size()-1);
        hull.add(a);
        hull.add(b);
        points.remove(a);
        points.remove(b);
        
        /* Create segment AB.
         * Points in S1 are on the right side of the oriented line AB.
         * Points in S2 are on the right side of the oriented line BA.
         */
        ArrayList<Point> s1 = new ArrayList<>(), s2 = new ArrayList<>();
        for(Point point : points)
            if (lineSide(point, a, b) > 0)
                s1.add(point);
            /* Also compare instead of assuming for BA because Point
             * could be on line, in which case we can ignore it.
             */
            else if(lineSide(point, b, a) > 0)
                s2.add(point);
        
        // Now start the recursion to find the hull on each side of the AB segment.
        findHull(s1, a, b);
        findHull(s2, b, a);
    }
    
    /** findHull recursively searches each side of the initial AB line segment,
     *  and then resulting triangles that will make up the convex hull, for the
     *  furthest Points that make up the hull.
     * 
     * @param points The subset of Points of which to find the point that is part
     * of the convex hull.
     * @param p Point that makes up the line segment PQ.
     * @param q The other Point that makes up the line segment PQ.
     * @param bottom Used to determine the orientation of points from the original AB line.
     * @throws IllegalArgumentException If points, p, or q are null.
     */
    private void findHull(ArrayList<Point> points, Point p, Point q) throws IllegalArgumentException {
        // First check that points, p and q are not null.
        if (points == null)
            throw new IllegalArgumentException("Error while executing findHull (TreeSet<Point>, Point, Point) in QuickHull: Parameter points is null!");
        else if (p == null)
            throw new IllegalArgumentException("Error while executing findHull (TreeSet<Point>, Point, Point) in QuickHull: Parameter p is null!");
        else if (q == null)
            throw new IllegalArgumentException("Error while executing findHull (TreeSet<Point>, Point, Point) in QuickHull: Parameter q is null!");
        
        // Skip if points is empty.
        if (!points.isEmpty()) {
            // Find the Point farthest from segment PQ.
            points.sort(new DistanceFromLine(p, q));
            Point c = points.get(points.size()-1);
            points.remove(c);
            
            // Add to hull.
            hull.add(c);
            
            
            // Points P, Q, and C form a triangle; partition the remaining points into three subsets.
            
            
            // Set s0 are Points inside the triangle and can be ignored.
            ArrayList<Point> s0 = new ArrayList<>();
            for (Point point : points)
                if (pointWithinTriangle(point, p, q, c))
                    s0.add(point);
            points.removeAll(s0);
            
            // Set s1 are Points on the right side of the line PC.
            ArrayList<Point> s1 = new ArrayList<>();
            for(Point point : points)
                if (lineSide(point, p, c) > 0)
                    s1.add(point);
            
            // Set s2 are Points on the right side of the line QC.
            ArrayList<Point> s2 = new ArrayList<>();
            for(Point point : points)
                if (lineSide(point, c, q) > 0)
                    s2.add(point);
            
            // Recursively find the next Point that makes up the hull in s1 and s2.
            findHull(s1, p, c);
            findHull(s2, c, q);
        }
    }
    
    /** lineSide determines which side of a line a Point is on.
     * 
     * @param point The point to check.
     * @param a A Point on the line.
     * @param b The second Point on the line.
     * @return {@literal <} 0 if point is to the right of the line,
     * 0 if on it, and {@literal >} 0 if point is to the left of the line.
     * @throws IllegalArgumentExcption If point, a, or b is null or the same Point (address or coordinate).
     */
    private double lineSide(Point point, Point a, Point b) throws IllegalArgumentException {
        // First check that point, a, and b are not null.
        if (point == null)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameter point is null!");
        else if (a == null)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameter a is null!");
        else if (b == null)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameter b is null!");
        // Next, check that none of the points are identical.
        else if (point == a)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters point and a are the same address!");
        else if (point.equals(a))
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters point and a are the same coordinate!");
        else if (point == b)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters point and b are the same address!");
        else if (point.equals(b))
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters point and b are the same coordinate!");
        else if (a == b)
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters a and b are the same address!");
        else if (a.equals(b))
            throw new IllegalArgumentException("Error while executing lineSide(Point, Point, Point) in QuickHull: Parameters a and b are the same coordinate!");
        
        // Return which side point is on ((B.x - A.x)(P.y - A.y) - (B.y - A.y)(P.x - A.x)).
        return (b.getX() - a.getX()) * (point.getY() - a.getY()) - (b.getY() - a.getY()) * (point.getX() - a.getX());
    }

    /** pointWithinTriangle determines whether a given point is within a triangle
     *  formed by three other points (a, b, c). Using the three calculations within
     *  this method, if their results are all the same sign, then the point is within
     *  the triangle; otherwise, it is not.
     * 
     * @param point The Point to check whether it is in the triangle.
     * @param a The first of three Points that make up the triangle.
     * @param b The second of three Points that make up the triangle.
     * @param c The third Point that makes up the triangle.
     * @return True if point is inside the triangle, else false.
     * @throws IllegalArgumentException If point, a, b, or c is null or the same Point (address or coordinate).
     */
    private boolean pointWithinTriangle(Point point, Point a, Point b, Point c) throws IllegalArgumentException {
        // First check that point, a, b, and c are not null.
        if (point == null)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameter point is null!");
        else if (a == null)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameter a is null!");
        else if (b == null)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameter b is null!");
        else if (c == null)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameter c is null!");
        // Next check that none of the points are identical.
        else if (point == a)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and a are the same address!");
        else if (point.equals(a))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and a are the same coordinate!");
        else if (point == b)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and b are the same address!");
        else if (point.equals(b))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and b are the same coordinate!");
        else if (point == c)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and c are the same address!");
        else if (point.equals(c))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters point and c are the same coordinate!");
        else if (a == b)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters a and b are the same address!");
        else if (a.equals(b))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters a and b are the same coordinate!");
        else if (a == b)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters a and b are the same address!");
        else if (a.equals(b))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters a and b are the same coordinate!");
        else if (b == c)
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters b and c are the same address!");
        else if (b.equals(c))
            throw new IllegalArgumentException("Error while executing pointWithinTriangle(Point, Point, Point, Point) in QuickHull: Parameters b and c are the same coordinate!");
        
        /* Perform three calculations to compare point to a & b, b & c, and a & c:
         * (P.x - A.x)(B.y - A.y) - (P.y - A.y)(B.c - A.x)
         * (P.x - B.x)(C.y - B.y) - (P.y - B.y)(C.x - B.x)
         * (P.x - C.x)(A.y - C.y) - (P.y - C.y)(A.x - C.x)
         */
        double pointAB = (point.getX() - a.getX()) * (b.getY() - a.getY()) - (point.getY() - a.getY()) * (b.getX() - a.getX());
        double pointBC = (point.getX() - b.getX()) * (c.getY() - b.getY()) - (point.getY() - b.getY()) * (c.getX() - b.getX());
        double pointAC = (point.getX() - c.getX()) * (a.getY() - c.getY()) - (point.getY() - c.getY()) * (a.getX() - c.getX());
        
        // Return the result (in triangle if all have the same sign).
        return (pointAB > 0 && pointBC > 0 && pointAC > 0) || (pointAB < 0 && pointBC < 0 && pointAC < 0);
    }
    
    /** toString outputs this QuickHull's list of hull Points, one per line.
     * 
     * @return A String containing the Points that make up the hull for the originally passed-in list of Points.
     */
    @Override
    public String toString() {
        String result = "";
        for (Point point : hull)
            result += point + System.lineSeparator();
        return result;
    }
}