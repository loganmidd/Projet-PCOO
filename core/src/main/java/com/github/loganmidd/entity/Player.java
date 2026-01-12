package com.github.loganmidd.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.controllers.KeyboardController;
import com.github.loganmidd.controllers.PlayerController;
import com.github.loganmidd.controllers.TowerPlacer;
import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.entity.playertypes.PlayerType;
import com.github.loganmidd.entity.playertypes.Wizard;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

/**
 * Represents the player character in the game.
 * <p>
 * The Player extends {@link Combattant} and manages player-specific logic such as
 * leveling up, experience points, active effects, and controlling the character
 * through a {@link PlayerController}. It delegates specific abilities and
 * appearance to a {@link PlayerType}.
 * 
 * @author Logan Middendorf
 */
public class Player extends Combattant {
    /** The type of player, defining abilities and appearance. */
    private PlayerType type;
    /** Indicates if the secondary attack is currently charging. */
    private boolean isSecondaryCharging;
    /** The total accumulated experience points. */
    private int expCount;
    /** The list of active effects applied to the player. */
    private List<PlayerEffect> effects;
    /** The tower placer used for building towers. */
    private TowerPlacer placer;
    /** The controller handling player input. */
    private PlayerController controller;
    /** The current level of the player. */
    private int level;

    /**
     * Constructs a Player at the specified coordinates.
     *
     * @param x The x-coordinate for the bottom-left corner of the hitbox.
     * @param y The y-coordinate for the bottom-left corner of the hitbox.
     */
    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
        this.setWidth(128);
        this.setHeight(128);
        
        this.type = new Wizard(this);
        this.isSecondaryCharging = false;
        
        this.expCount = 0;
        this.level = 0;
        this.effects = new ArrayList<>();

        this.controller = new KeyboardController();
    }

    /**
     * Constructs a Player at the specified Point.
     *
     * @param p The Point defining the starting position.
     */
    public Player(Point p) {
        this(p.getX(), p.getY());
    } 

    /**
     * Returns the texture path based on the current player type.
     *
     * @return The path to the texture file.
     */
    @Override
    public String getTexturePath() {
        return this.type.getTexturePath();
    }

    /**
     * Determines if this entity is a player.
     *
     * @return true, as this is a Player instance.
     */
    @Override
    public boolean isPlayer() {
        return true;
    }

    /**
     * Adds experience points to the player and triggers level up if applicable.
     *
     * @param xp The amount of experience points to add.
     */
    public void addExp(int xp) { 
        this.expCount += xp; 
        int level = this.getLevel();
        if (level != this.level) {
            this.level = level;
            World.getWorld().createLevelUpUI(this);
        }
    }

    /**
     * Sets the total experience points and updates the level.
     *
     * @param xp The total experience points to set.
     */
    public void setExp(int xp) { this.expCount = xp; this.level = this.getLevel(); }

    /**
     * Returns the current total experience points.
     *
     * @return The experience count.
     */
    public int getExp()        { return this.expCount; }

    /**
     * Calculates the current level based on the experience count.
     *
     * @return The current level.
     */
    public int getLevel() {
        return this.getLevel(this.expCount);
    }

    /**
     * Calculates the level corresponding to a specific amount of experience.
     *
     * @param exp The experience amount.
     * @return The calculated level.
     */
    private int getLevel(int exp) {
        return (int) Math.floor(Math.log(this.expCount + 1));
    }

    /**
     * Calculates the experience points required to reach the next level.
     *
     * @return The amount of XP needed for the next level.
     */
    public int getExpToNextLevel() {
        int i = 0;
        while (this.getLevel() != this.getLevel(this.expCount + i++)) {}
        return i;

    }

    /**
     * Processes input, updates state, and handles actions like movement, attacks, and tower placement.
     */
    public void input() {
        if (this.getTextureRenderer().getPath() == "spriteNotFound.png") {
            return;
        }

        float a = 6f; // Arbitrary
        // Movement
        this.addDx(this.controller.getXMovement() * a);
        this.addDy(this.controller.getYMovement() * a);

        if (this.controller.isPrimaryTowerKeyJustPressed()) {
            this.placer = new TowerPlacer(this.type.getPrimaryTower());
        }

        else if (this.controller.isSecondaryTowerKeyJustPressed()) {
            this.placer = new TowerPlacer(this.type.getSecondaryTower());
        }

        else if (this.controller.isCancelButtonPressed()) {
            this.placer = null;
        }


        if (this.controller.isPrimaryAttackKeyJustPressed()) {
            this.type.primaryAttack();
        }

        if (this.controller.isSecondaryAttackKeyPressed()) {
            if (!this.isSecondaryCharging) {
                this.type.startSecondaryAttack();
            }
            this.isSecondaryCharging = true;
        } else if (this.isSecondaryCharging) {
            this.isSecondaryCharging = false;
            this.type.endSecondaryAttack();
        }

        if (this.controller.isStartWaveButtonJustPressed() && World.getWorld().isWaveOver()) {
            World.getWorld().getWaveManager().startNextWave();
        }

        // DEBUGGING

        if (this.placer != null) {
            this.placer.logic();
        }

    }

    /**
     * Renders the player and the active tower placer if one exists.
     */
    public void render() {
        super.render();

        if (this.placer != null) {
            this.placer.render();
        }
    }

    /**
     * Adds a player effect to the player and applies it.
     *
     * @param playerEffect The {@link PlayerEffect} to add and apply.
     */
    public void addEffect(PlayerEffect playerEffect) {
        this.effects.add(playerEffect);
        playerEffect.applyEffect(this);
    }

    /**
     * Removes a player effect from the player.
     *
     * @param playerEffect The {@link PlayerEffect} to remove.
     */
    public void removeEffect(PlayerEffect playerEffect) {
        this.effects.remove(playerEffect);
    }

    /**
     * Returns the list of active player effects.
     *
     * @return A list of {@link PlayerEffect} objects.
     */
    public List<PlayerEffect> getEffects() {
        return this.effects;
    }
    
}