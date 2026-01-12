package com.github.loganmidd.entity.projectiles.towerprojectiles;

import java.util.HashSet;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.Projectile;

/**
 * Represents a projectile fired by a tower that can pierce through multiple enemies.
 * <p>
 * This projectile deals damage to enemy Combattants it collides with. It keeps track
 * of the number of enemies pierced and self-destructs after piercing a certain
 * threshold.
 * 
 * @author Logan Middendorf
 */
public class HarpoonTowerProjectile extends Projectile {
    private HashSet<Combattant> pierced;

    /**
     * Constructs a new HarpoonTowerProjectile.
     * 
     * @param sender The Entity that fired this projectile.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param angle The direction of travel in radians.
     */
    public HarpoonTowerProjectile(Entity sender, float x, float y, float angle) {
        super(sender, x, y, angle);
        this.pierced = new HashSet<Combattant>();
    }

    /**
     * Handles collision with a Combattant.
     * <p>
     * If the target is an enemy, it deals damage to them and increments the pierce
     * count. If the pierce count reaches or exceeds 10, the projectile is disposed
     * of.
     * 
     * @param combattant The Combattant collided with.
     * @return true if the projectile was disposed of due to piercing too many enemies,
     *         false otherwise.
     */
    @Override
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy() && !this.pierced.contains(combattant)) {
            combattant.takeDamage(this);
            this.pierced.add(combattant);
            if (this.pierced.size() >= 10) {
                this.dispose();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the path to the texture asset for this projectile.
     * 
     * @return The file path of the texture.
     */
    @Override
    public String getTexturePath() {
        return "spriteNotFound.png";
    }
    
}