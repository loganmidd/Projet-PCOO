package com.github.loganmidd.entity.projectiles;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.world.World;

public abstract class Projectile extends Entity {
    private int t; // Used for getting position of projectile in parametric curve

    public Projectile(float x, float y, float angle, float dr) {
        super(x, y);
        this.setCenterX(x);
        this.setCenterY(y);
        this.t = 0;
        this.setCollision(false);
        this.setSlowDownFactor(1);
        // Using polar coordinates to get direction
        this.setDx((float) (dr * Math.cos(angle)));
        this.setDy((float) (dr * Math.sin(angle)));
    }   
    
    public Projectile(float x, float y, float angle) {
        this(x, y, angle, 1); // default dr of 1
    }

    @Override
    public void logic() {
        super.logic();
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : World.getWorld().getEntities()) {
            if (this.collidesWith(entity)) {
                entities.add(entity);
            }
        }

        for (Entity entity : entities) {
            if (this.collisionWith(entity)) {
                return;
            }
        }
        this.setX(this.calculateX(t));
        this.setY(this.calculateY(t));
        t++;
    }
    
    // Used to calculate (x, y) coordinates of projectile at
    // an instant t. Override to code parametric, non linear curves
    protected float calculateX(int t) {
        return this.getX();
    } 
    protected float calculateY(int t) {
        return this.getY();
    }


    public boolean isEnemy() {
        return false;
    }

    public abstract boolean collisionWith(Entity entity); // Returns true if projectile dissapears as an after effect

} 