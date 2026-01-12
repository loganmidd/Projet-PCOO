package com.github.loganmidd.entity.projectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;

/**
 * Represents a magical projectile fired by a wizard.
 * <p>
 * This projectile rotates as it travels and damages enemies upon collision.
 */
public class WizardProjectile extends Projectile {
    private float angle;

    /**
     * Constructs a new WizardProjectile.
     *
     * @param sender The entity that fired this projectile
     * @param x The initial x coordinate
     * @param y The initial y coordinate
     * @param angle The direction of travel in degrees
     */
    public WizardProjectile(Entity sender, float x, float y, float angle) {
        super(sender, x, y, angle, 10);
        this.angle = 0;
    }

    /**
     * Updates the projectile's state.
     * <p>
     * Calls the superclass logic and increments the rotation angle.
     */
    @Override
    public void logic() {
        super.logic();
        this.getTextureRenderer().setRotation(this.angle);
        this.angle += 20;
    }

    /**
     * Returns the file path to the texture image.
     *
     * @return The texture path
     */
    public String getTexturePath() {
        return "fireball.png";
    }

    /**
     * Handles collision with a combatant.
     * <p>
     * If the combatant is an enemy, deals damage and disposes of the projectile.
     *
     * @param combattant The combatant to check collision with
     * @return true if a collision occurred and the projectile should be destroyed, false otherwise
     */
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);;
            this.dispose();
            return true;
        }
        return false;
    }
    
}