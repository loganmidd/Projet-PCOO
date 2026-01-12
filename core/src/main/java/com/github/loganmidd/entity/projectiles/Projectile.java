package com.github.loganmidd.entity.projectiles;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.world.World;

/**
 * Represents a projectile entity that moves through the world and can collide with combattants.
 * 
 * @param <T> The specific type of Combattant this projectile interacts with.
 * 
 * @author Logan Middendorf
 */
public abstract class Projectile extends Combattant {
    private int t; // Used for getting position of projectile in parametric curve
    private Entity sender;

    /**
     * Constructs a new Projectile at the specified position moving in the given direction.
     * 
     * @param sender The entity that launched this projectile.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param angle The angle of movement in radians.
     * @param dr The speed or delta radius.
     */
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
    
    /**
     * Constructs a new Projectile with a default speed of 1.
     * 
     * @param sender The entity that launched this projectile.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param angle The angle of movement in radians.
     */
    public Projectile(Entity sender, float x, float y, float angle) {
        this(sender, x, y, angle, 1); // default dr of 1
    }

    /**
     * Executes the projectile's logic for a single game tick.
     * Handles collision detection with combattants, updates position based on parametric calculations,
     * and updates rotation.
     */
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
    
    /**
     * Calculates the x-coordinate of the projectile at a specific time instance.
     * Override to implement parametric or non-linear curves.
     * 
     * @param t The time instance.
     * @return The calculated x-coordinate.
     */
    protected float calculateX(int t) {
        return this.getX() + this.getDx();
    } 

    /**
     * Calculates the y-coordinate of the projectile at a specific time instance.
     * Override to implement parametric or non-linear curves.
     * 
     * @param t The time instance.
     * @return The calculated y-coordinate.
     */
    protected float calculateY(int t) {
        return this.getY() + this.getDy();
    }


    /**
     * Checks if this projectile is an enemy.
     * 
     * @return false, as projectiles are generally not considered enemies.
     */
    @Override
    public boolean isEnemy() {
        return false;
    }

    /**
     * Handles the collision logic with a specific combattant.
     * 
     * @param combattant The combattant being collided with.
     * @return true if the projectile should disappear as an after effect, false otherwise.
     */
    public abstract boolean collisionWith(Combattant combattant); // Returns true if projectile dissapears as an after effect

    /**
     * Retrieves the entity that sent this projectile.
     * 
     * @return The sender entity.
     */
    public Entity getSender() {
        return sender;
    }

    /**
     * Sets the entity that sent this projectile.
     * 
     * @param sender The sender entity.
     */
    public void setSender(Entity sender) {
        this.sender = sender;
    }

    

} 