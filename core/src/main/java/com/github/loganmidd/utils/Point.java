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
    
}
