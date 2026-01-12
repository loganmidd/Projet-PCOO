package com.github.loganmidd.entity.projectiles.towerprojectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.Projectile;

/**
 * Represents a bullet projectile fired by a tower. Specifically designed for the DST tower type.
 * <p>
 * This projectile deals damage to enemy {@link Combattant}s upon collision and is then disposed of.
 */
public class DSTBullet extends Projectile {

    /**
     * Constructs a new DSTBullet.
     *
     * @param sender The entity that fired this bullet.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param angle The angle of travel in radians.
     */
    public DSTBullet(Entity sender, float x, float y, float angle) {
        super(sender, x, y, angle, 50);
        this.setWidth(25);
        this.setHeight(61);
    }

    /**
     * Handles collision with a combatant.
     * <p>
     * If the combatant is an enemy, deals damage to them and disposes of this bullet.
     *
     * @param combattant The combatant colliding with this bullet.
     * @return {@code true} if the collision resulted in damage (i.e., the combatant was an enemy); {@code false} otherwise.
     */
    @Override
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);
            this.dispose();
            return true;
        }
        return false;
    }

    /**
     * Returns the texture path for this bullet.
     *
     * @return The file path string for the bullet texture ("dstBullet.png").
     */
    @Override
    public String getTexturePath() {
        return "dstBullet.png";
    }
    
}