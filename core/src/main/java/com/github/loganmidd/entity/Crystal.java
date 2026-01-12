package com.github.loganmidd.entity;

import com.github.loganmidd.utils.Point;

public class Crystal extends Combattant {
    public Crystal(float x, float y) {
        super(x, y);
        this.setMovable(false);
        
        this.setMaxHealth(200);
        this.setCurrentHealth(200);
        this.setAttackDamage(0);
    }

    public Crystal(Point point) {
        this(point.getX(), point.getY());
    }

    public String getTexturePath() {
        return "crystal.png";
    }

    @Override 
    public boolean isCrystal() {
        return true;
    }

}
