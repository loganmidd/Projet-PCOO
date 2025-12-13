package com.github.loganmidd.utils;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public float distanceTo(Point p) {
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) Math.sqrt(dx + dy);
    }

    public float distance2To(Point p) {
        // Returns the distance squared, faster because not square root is involved.
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) (dx + dy);
    }

    public float scalarProduct(Point p) {
        return this.x * p.getX() + this.y * p.getY();
    }
    
}
