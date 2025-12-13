package com.github.loganmidd.entity;

import com.github.loganmidd.utils.Point;

public class Crystal extends Entity {
    public Crystal(float x, float y) {
        super(x, y);
        this.setMovable(false);
    }

    public boolean isEnemy() {
        return false;
    }

    public Crystal(Point point) {
        this(point.getX(), point.getY());
    }

    public String getTexturePath() {
        return "spriteNotFound.png";
    }

}
