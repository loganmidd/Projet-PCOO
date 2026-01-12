package com.github.loganmidd.entity.projectiles;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.world.World;

public abstract class Projectile extends Combattant {
    private int t; // Used for getting position of projectile in parametric curve
    private Entity sender;

    public Projectile(Entity sender, float x, float y, float angle, float dr) {
        super(x, y);
        this.setCenterX(x);
        this.setCenterY(y);
        this.t = 0;
        this.setCollision(false);
        this.setSlowDownFactor(1);
        this.setTargetable(false);
        // Using polar coordinates to get direction
        this.setDx((float) (dr * Math.cos(angle)));
        this.setDy((float) (dr * Math.sin(angle)));
        this.sender = sender;
    }   
    
    public Projectile(Entity sender, float x, float y, float angle) {
        this(sender, x, y, angle, 1); // default dr of 1
    }

    @Override
    public void logic() {
        super.logic();
        List<Combattant> combattants = new ArrayList<>();
        for (Entity entity : World.getWorld().getEntities()) {
            if (this.collidesWith(entity) && entity.isCombattant()) {
                combattants.add((Combattant) entity);
            }
        }

        for (Combattant combattant : combattants) {
            if (this.collisionWith(combattant)) {
                return;
            }
        }
        float nextX = this.calculateX(t);
        float nextY = this.calculateY(t);
        float angle = (float) (180*Math.atan2(nextY - this.getY(), nextX - this.getX())/Math.PI);
        this.getTextureRenderer().setRotation(angle - 90);

        this.setX(nextX);
        this.setY(nextY);
        t++; 
        
    }
    
    // Used to calculate (x, y) coordinates of projectile at
    // an instant t. Override to code parametric, non linear curves
    protected float calculateX(int t) {
        return this.getX() + this.getDx();
    } 
    protected float calculateY(int t) {
        return this.getY() + this.getDy();
    }


    public boolean isEnemy() {
        return false;
    }

    public abstract boolean collisionWith(Combattant combattant); // Returns true if projectile dissapears as an after effect

    public Entity getSender() {
        return sender;
    }

    public void setSender(Entity sender) {
        this.sender = sender;
    }

    

} 