package com.github.loganmidd.utils;

public class EnemyPathVertex {
    private Point point;
    private EnemyPathVertex next;
    private boolean isCrystal;

    public EnemyPathVertex(Point p, EnemyPathVertex next, boolean isCrystal) {
        this.point = p;
        this.next = next;
        this.isCrystal = isCrystal;
    }

    public EnemyPathVertex(Point p, EnemyPathVertex next) {
        this(p, next, false); // Not a crystal by default
    }

    public EnemyPathVertex(Point p) {
        this(p, null, false); // Allow initialization without next vertex
    }

    public boolean isEntityNode() {
        return this.next == null;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public EnemyPathVertex getNext() {
        return next;
    }

    public void setNext(EnemyPathVertex next) {
        this.next = next;
    }

    public boolean isCrystal() {
        return isCrystal;
    }

    public void setCrystal(boolean isCrystal) {
        this.isCrystal = isCrystal;
    }

}
