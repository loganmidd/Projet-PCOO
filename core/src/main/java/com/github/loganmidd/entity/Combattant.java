package com.github.loganmidd.entity;

import com.badlogic.gdx.graphics.Color;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.world.World;

/**
 * Represents a combat-capable entity with health and damage.
 * <p>
 * This class serves as a base for characters that can engage in combat, managing health,
 * damage output, invulnerability states, and taking damage. When a combatant is destroyed,
 * it may drop an experience orb if it is an enemy.
 * </p>
 *
 * @author Logan Middendorf
 */
public abstract class Combattant extends Entity {
    /**
     * The maximum health of the combatant.
     */
    private int maxHealth;

    /**
     * The current health of the combatant.
     */
    private int currentHealth;

    /**
     * The base attack damage dealt by the combatant.
     */
    private int attackDamage;

    /**
     * Determines if the combatant is currently immune to all damage.
     */
    private boolean isImmortal;

    /**
     * Timer used to handle the damage feedback visual effect (tinting red).
     */
    private CooldownTimer timer;

    /**
     * Determines if the combatant can be targeted by attacks.
     */
    private boolean isTargetable;

    /**
     * Creates a new Combattant at the specified position with defined health and attack power.
     *
     * @param x          The initial x-coordinate.
     * @param y          The initial y-coordinate.
     * @param maxHealth  The maximum health value.
     * @param attackDamage The base attack damage value.
     */
    public Combattant(float x, float y, int maxHealth, int attackDamage) {
        super(x, y);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.isImmortal = false;
        this.timer = new CooldownTimer(80, false);
        this.isTargetable = true;
    }

    /**
     * Creates a new Combattant at the specified position with default health (100) and damage (10).
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Combattant(float x, float y) {
        this(x, y, 100, 10);
    }

    /**
     * Applies damage to this combatant received from a sender.
     * <p>
     * If the combatant is not immortal, the damage is subtracted from current health.
     * If health drops to 0 or below, the combatant is disposed of. If the combatant is an enemy,
     * an experience orb is spawned at its center upon death. Taking damage resets the visual damage feedback timer.
     * </p>
     *
     * @param sender The combatant initiating the attack.
     */
    public void takeDamage(Combattant sender) {
        int damage = sender.getAttackDamage();

        if (!this.isImmortal) {
            if (damage >= this.getCurrentHealth()) {
                if (this.isEnemy()) {
                    World.getWorld().addEntity(new ExpOrb(this.getCenterX(), this.getCenterY()));
                }
                this.dispose();
            } else {
                this.currentHealth -= damage;
                this.timer.resetCooldown();
            }
        }
    }

    /**
     * Executes the combatant's logic loop.
     * <p>
     * Updates the base entity logic and manages the visual tint. If the damage timer is active,
     * the combatant is tinted red; otherwise, it returns to the default white tint.
     * </p>
     */
    public void logic() {
        super.logic();
        // Change tint to red while taking damage
        if (!this.timer.isCooldownOver()) {
            this.getTextureRenderer().setTint(new Color(0.8f, 0.3f, 0.3f, 1));
        } else {
            this.getTextureRenderer().setTint(new Color(1, 1, 1, 1));
        }
    }

    /**
     * Retrieves the maximum health.
     *
     * @return The maximum health value.
     */
    public int getMaxHealth() { return maxHealth; }

    /**
     * Retrieves the current health.
     *
     * @return The current health value.
     */
    public int getCurrentHealth() { return currentHealth; }

    /**
     * Retrieves the base attack damage.
     *
     * @return The base attack damage value.
     */
    public int getAttackDamage() { return attackDamage; }

    /**
     * Checks if the combatant is currently immortal.
     *
     * @return True if immortal, false otherwise.
     */
    public boolean isImmortal() { return this.isImmortal; }

    /**
     * Checks if this entity is a combatant.
     *
     * @return Always returns true.
     */
    public boolean isCombattant() { return true; }

    /**
     * Checks if the combatant can be targeted.
     *
     * @return True if targetable, false otherwise.
     */
    public boolean isTargetable() { return this.isTargetable; }

    /**
     * Sets the maximum health value.
     *
     * @param maxHealth The new maximum health.
     */
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    /**
     * Sets the current health value.
     *
     * @param currentHealth The new current health.
     */
    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth; }

    /**
     * Sets the base attack damage.
     *
     * @param baseDamage The new base damage value.
     */
    public void setAttackDamage(int baseDamage) { this.attackDamage = baseDamage; }

    /**
     * Sets the immortal state.
     *
     * @param state The new immortal state.
     */
    public void setImmortal(boolean state) { this.isImmortal = state; }

    /**
     * Sets the targetable state.
     *
     * @param state The new targetable state.
     */
    public void setTargetable(boolean state) { this.isTargetable = state; }

    
}

