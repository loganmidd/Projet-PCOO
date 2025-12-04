package com.github.loganmidd.utils;

public class MapPoint {
    private int x;
    private int y;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public float distanceTo(MapPoint p) {
        double dx = Math.pow((this.x - p.getX()), 2);
        double dy = Math.pow((this.y - p.getY()), 2);
        return (float) Math.sqrt(dx + dy);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    
    
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
