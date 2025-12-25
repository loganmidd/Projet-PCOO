package com.github.loganmidd.entity;

import com.badlogic.gdx.graphics.Color;
import com.github.loganmidd.utils.CooldownTimer;

public abstract class Combattant extends Entity {
    private int maxHealth;
    private int currentHealth;
    private int attackDamage;
    private boolean isImmortal;
    private CooldownTimer timer;
    private boolean isTargetable;

    public Combattant(float x, float y, int maxHealth, int attackDamage) {
        super(x, y);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.isImmortal = false;
        this.timer = new CooldownTimer(80, false);
        this.isTargetable = true;
    }

    public Combattant(float x, float y) {
        this(x, y, 100, 10);
    }

    public void takeDamage(Combattant sender) {
        int damage = sender.getAttackDamage();

        if (!this.isImmortal) {
            if (damage >= this.getCurrentHealth()) {
                this.dispose();
            } else {
                this.currentHealth -= damage;
                this.timer.resetCooldown();
            }
        }
    }

    public void logic() {
        super.logic();
        // Change tint to red while taking damage
        if (!this.timer.isCooldownOver()) {
            this.getTextureRenderer().setTint(new Color(0.8f, 0.3f, 0.3f, 1));
        } else {
            this.getTextureRenderer().setTint(new Color(1, 1, 1, 1));
        }
    }

    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public int getAttackDamage() { return attackDamage; }
    public boolean isImmortal() { return this.isImmortal; }
    public boolean isCombattant() { return true; }
    public boolean isTargetable() { return this.isTargetable; }

    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth; }
    public void setAttackDamage(int baseDamage) { this.attackDamage = baseDamage; }
    public void setImmortal(boolean state) { this.isImmortal = state; }
    public void setTargetable(boolean state) { this.isTargetable = state; }

    
}
