package com.github.loganmidd.entity;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.loganmidd.entity.enemies.Goblin;
import com.github.loganmidd.entity.playertypes.Knight;
import com.github.loganmidd.entity.playertypes.PlayerType;
import com.github.loganmidd.entity.playertypes.Wizard;
import com.github.loganmidd.entity.towers.WizardBlueTower;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class Player extends Entity {
    private PlayerType type;
    private boolean isSecondaryCharging;

    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
        this.setWidth(128);
        this.setHeight(128);
        this.type = new Wizard(this);
        this.isSecondaryCharging = false;
    }

    public Player(Point p) {
        this(p.getX(), p.getY());
    } 

    public String getTexturePath() {
        return this.type.getTexturePath();
    }

    public boolean isEnemy() {
        return false;
    }


    public void input() {
        if (this.getTextureRenderer().getPath() == "spriteNotFound.png") {
            return;
        }

        float a = 6f; // Arbitrary
        // Movement
        // Right arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.setDx(a);
        }
        // Left arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.setDx(-a);
        }
        // Up arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.setDy(a);
        }
        // Down arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.setDy(-a);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            List<Point> points = World.getWorld().getTMap().getEnemySpawnPoints();
            for (Point point : points) {
                World.getWorld().addEntity(new Goblin(point.getX(), point.getY()));
            }
            
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            World.getWorld().addEntity(new WizardBlueTower(this.getX(), this.getY()));
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            this.type.primaryAttack();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (!this.isSecondaryCharging) {
                this.type.startSecondaryAttack();
            }
            this.isSecondaryCharging = true;
        } else if (this.isSecondaryCharging) {
            this.isSecondaryCharging = false;
            this.type.endSecondaryAttack();
        }

    }

    public void render() {
        super.render();
    }
    
}
