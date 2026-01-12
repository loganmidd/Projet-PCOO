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

public class Player extends Combattant {
    private PlayerType type;
    private boolean isSecondaryCharging;
    private int expCount;
    private List<PlayerEffect> effects;
    private TowerPlacer placer;
    private PlayerController controller;
    private int level;

    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
        this.setWidth(128);
        this.setHeight(128);
        
        this.type = new Wizard(this);
        this.isSecondaryCharging = false;
        this.setImmortal(true);
        
        this.expCount = 0;
        this.level = 0;
        this.effects = new ArrayList<>();

        this.controller = new KeyboardController();
    }

    public Player(Point p) {
        this(p.getX(), p.getY());
    } 

    @Override
    public String getTexturePath() {
        return this.type.getTexturePath();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public void addExp(int xp) { 
        this.expCount += xp; 
        int level = this.getLevel();
        if (level != this.level) {
            this.level = level;
            World.getWorld().createLevelUpUI(this);
        }
    }
    public void setExp(int xp) { this.expCount = xp; this.level = this.getLevel(); }
    public int getExp()        { return this.expCount; }

    public int getLevel() {
        return this.getLevel(this.expCount);
    }

    private int getLevel(int exp) {
        return (int) Math.floor(Math.log(this.expCount + 1));
    }

    public int getExpToNextLevel() {
        int i = 0;
        while (this.getLevel() != this.getLevel(this.expCount + i++)) {}
        return i;

    }

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

    public void render() {
        super.render();

        if (this.placer != null) {
            this.placer.render();
        }
    }

    public void addEffect(PlayerEffect playerEffect) {
        this.effects.add(playerEffect);
        playerEffect.applyEffect(this);
    }

    public void removeEffect(PlayerEffect playerEffect) {
        this.effects.remove(playerEffect);
    }

    public List<PlayerEffect> getEffects() {
        return this.effects;
    }
    
}
