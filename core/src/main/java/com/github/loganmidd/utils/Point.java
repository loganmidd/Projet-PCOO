package com.github.loganmidd.utils;

/**
 * Represents a point in 2D space with floating-point coordinates.
 * Provides methods for distance calculations and scalar products.
 *
 * @author Logan Middendorf
 */
public class Point {
    /** The x-coordinate of the point. */
    private float x;
    /** The y-coordinate of the point. */
    private float y;

    /**
     * Constructs a new Point with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return The x-coordinate.
     */
    public float getX() { return x; }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return The y-coordinate.
     */
    public float getY() { return y; }

    /**
     * Sets the x-coordinate of this point.
     *
     * @param x The new x-coordinate.
     */
    public void setX(float x) { this.x = x; }

    /**
     * Sets the y-coordinate of this point.
     *
     * @param y The new y-coordinate.
     */
    public void setY(float y) { this.y = y; }

    /**
     * Calculates the Euclidean distance to another point.
     *
     * @param p The point to calculate the distance to.
     * @return The Euclidean distance between this point and the specified point.
     */
    public float distanceTo(Point p) {
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) Math.sqrt(dx + dy);
    }

    /**
     * Calculates the squared Euclidean distance to another point.
     * This method is faster than {@code distanceTo} as it avoids the square root operation.
     *
     * @param p The point to calculate the squared distance to.
     * @return The squared Euclidean distance between this point and the specified point.
     */
    public float distance2To(Point p) {
        // Returns the distance squared, faster because not square root is involved.
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) (dx + dy);
    }

    /**
     * Calculates the scalar product (dot product) of this point and another point.
     *
     * @param p The point to compute the scalar product with.
     * @return The scalar product of the two points.
     */
    public float scalarProduct(Point p) {
        return this.x * p.getX() + this.y * p.getY();
    }
    
}