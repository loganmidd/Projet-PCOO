package com.github.loganmidd.utils;

/**
 * Represents a point in a 2D coordinate system with integer x and y coordinates.
 * Provides methods for accessing and modifying coordinates, calculating Euclidean distance
 * to another point, and implementing value-based equality.
 */
public class MapPoint {
    private int x;
    private int y;

    /**
     * Constructs a MapPoint with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return The x-coordinate.
     */
    public int getX() { return x; }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return The y-coordinate.
     */
    public int getY() { return y; }

    /**
     * Sets the x-coordinate of this point.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) { this.x = x; }

    /**
     * Sets the y-coordinate of this point.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) { this.y = y; }

    /**
     * Calculates the Euclidean distance from this point to another point.
     *
     * @param p The target point.
     * @return The distance between this point and the target point.
     */
    public float distanceTo(MapPoint p) {
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) Math.sqrt(dx + dy);
    }

    /**
     * Returns a hash code value for this point.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    
    
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapPoint other = (MapPoint) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}