package com.github.loganmidd.entity.enemies;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.utils.EnemyPathVertex;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

/**
 * Represents an enemy entity in the game world.
 * <p>
 * Enemies follow a path or track combatants, and attack when within range.
 * They extend {@link Combattant} to participate in combat.
 */
public abstract class Enemy extends Combattant {
    private EnemyPathVertex currentNode;
    private float speed;
    private CooldownTimer attackTimer;
    
    private float attackDistance;

    /**
     * Constructs an Enemy at the specified coordinates.
     *
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     */
    public Enemy(float x, float y) {
        super(x, y);
        this.speed = 3f;
        this.attackTimer = new CooldownTimer(1000);
        this.attackDistance = 100f; 
    } 

    /**
     * Constructs an Enemy at the origin (0, 0).
     */
    public Enemy() {
        this(0, 0); 
    }

    /**
     * Initializes the enemy's path by finding the closest path vertex to its current location.
     */
    public void initEnemyPath() {
        this.currentNode = World.getWorld().getEnemyPaths().getClosestVertex(this.getCenterPoint());
    }

    /**
     * Executes the enemy's logic for movement and attacking.
     * <p>
     * This method checks for nearby combatants to target, attacks if in range,
     * updates the path to follow, and moves towards the target node.
     */
    public void logic() {
        super.logic();
        // If the enemy is not following a non crystal entity, check if it should
        float maxDistance = 200;
        float bestDistance = maxDistance;
        for (Entity e : World.getWorld().getEntities()) {
            if (e.isCombattant() && !e.isEnemy()) {
                Combattant combattant = (Combattant) e;
                if (combattant.isTargetable()) {
                    float distance = e.getCenterPoint().distanceTo(this.getCenterPoint());
                    if (bestDistance > distance) {
                        this.currentNode = new EnemyPathVertex(e.getCenterPoint(), null, false);
                        this.currentNode.setTargetCombattant((Combattant) e);
                        bestDistance = distance;
                    }
                }
            } 
        }
        // If close enough, attack
        if (bestDistance < this.attackDistance && this.attackTimer.isCooldownOver()) {
            Combattant combattant = this.currentNode.getTargetCombattant();
            this.attack(combattant);
            this.attackTimer.resetCooldown();
        }

        // If following an entity
        if (maxDistance - bestDistance < 0.1  && this.currentNode.isEntityNode() && !this.currentNode.isCrystal()) {
            this.currentNode = World.getWorld().getEnemyPaths().getClosestVertex(this.getCenterPoint());
        }

        // To follow predefined enemy path
        if (this.getCenterPoint().distanceTo(this.currentNode.getPoint()) < 10 && this.currentNode.getNext() != null) {
            this.currentNode = this.currentNode.getNext();
        }
        Point p = this.currentNode.getPoint();
        float x = p.getX();
        float y = p.getY();
        float angle = (float) Math.atan2(this.getCenterY() - y, this.getCenterX() - x);
        float distance = this.getCenterPoint().distanceTo(this.currentNode.getPoint());
        float dr = Math.min(this.speed, distance);
        this.setDx((float) (-dr * Math.cos(angle)));
        this.setDy((float) (-dr * Math.sin(angle))); 
    }

    /**
     * Attacks the specified combatant.
     * <p>
     * This deals damage to the target, originating from this enemy.
     *
     * @param combattant the target to attack
     */
    public void attack(Combattant combattant) {
        combattant.takeDamage(this);
    }

    /**
     * Checks if this entity is an enemy.
     *
     * @return true, indicating this is an enemy
     */
    public boolean isEnemy() {
        return true;
    }

    /**
     * Gets the current movement speed of the enemy.
     *
     * @return the movement speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed of the enemy.
     *
     * @param speed the new movement speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the attack distance threshold.
     *
     * @return the distance within which the enemy will attack
     */
    public float getAttackDistance() {
        return attackDistance;
    }

    /**
     * Sets the attack distance threshold.
     *
     * @param attackDistance the distance within which the enemy will attack
     */
    public void setAttackDistance(float attackDistance) {
        this.attackDistance = attackDistance;
    }
}