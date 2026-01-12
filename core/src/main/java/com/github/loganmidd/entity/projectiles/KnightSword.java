package com.github.loganmidd.entity.projectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;

/**
 * Represents a projectile that behaves as a sword swung around the host entity.
 * <p>
 * This projectile rotates around the host entity in an arc rather than traveling
 * in a straight line. It deals damage to enemies upon collision and is disposed
 * of after completing its swing arc.
 *
 * @author Logan Middendorf
 */
public class KnightSword extends Projectile {
    private float current;
    private float start;
    private Entity host;
    private float distanceToHost;

    // For this projectile, we will not follow
    // a strict line

    /**
     * Constructs a new KnightSword projectile attached to the specified host.
     * <p>
     * The projectile is initialized at the host's center with zero velocity.
     * It is configured with a specific width, height, and swing distance.
     * The starting rotation is calculated based on the host's current direction.
     *
     * @param host The Entity that owns and centers this sword projectile
     */
    public KnightSword(Entity host) {
        super(host, host.getCenterX(), host.getCenterY(), 0, 0);
        this.setSlowDownFactor(0);
        float factor = 2f;
        this.setWidth(17*factor);
        this.setHeight(45*factor);

        this.host = host;
        this.distanceToHost = 50f;
        this.start = (float) Math.atan2(this.host.getDy(), this.host.getDx());
        this.current = -45f;
    }

    /**
     * Executes the projectile's logic cycle.
     * <p>
     * Updates the projectile's rotation angle and determines if the projectile
     * should be disposed of because the swing arc is complete.
     */
    public void logic() {
        super.logic();
        // Update rotation
        if (current > 45) {
            this.dispose();
        }

        this.current += 5;
        this.getTextureRenderer().setRotation(180*this.start/((float) Math.PI) + this.current - 90);
    }

    /**
     * Calculates the X position of the projectile based on the host's position
     * and the current swing angle.
     *
     * @param t The time parameter (unused in calculation)
     * @return The calculated X coordinate
     */
    protected float calculateX(int t) {
        float x = this.host.getX() + this.host.getWidth()/4f;
        return x + distanceToHost*((float) Math.cos(this.start + this.current * (float)Math.PI/180));
    }

    /**
     * Calculates the Y position of the projectile based on the host's position
     * and the current swing angle.
     *
     * @param t The time parameter (unused in calculation)
     * @return The calculated Y coordinate
     */
    protected float calculateY(int t) {
        float y = this.host.getY() + this.host.getHeight()/4f;
        return y + distanceToHost*((float) Math.sin(this.start + this.current * (float)Math.PI/180));
    }

    /**
     * Handles collision detection with a Combattant.
     * <p>
     * If the combatant is an enemy, it deals damage to them and disposes of this
     * projectile.
     *
     * @param combattant The Combattant being checked for collision
     * @return true if the collision occurred with an enemy and damage was dealt, false otherwise
     */
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);;
            this.dispose();
            return true;
        }
        return false;
    }

    /**
     * Returns the file path for the projectile's texture asset.
     *
     * @return The relative path to the texture image ("sword.png")
     */
    public String getTexturePath() {
        return "sword.png";
    }

}