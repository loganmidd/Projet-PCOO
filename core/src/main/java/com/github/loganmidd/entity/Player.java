package com.github.loganmidd.entity;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.loganmidd.defenders.controllers.TowerPlacer;
import com.github.loganmidd.entity.enemies.Goblin;
import com.github.loganmidd.entity.playertypes.Knight;
import com.github.loganmidd.entity.playertypes.PlayerType;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class Player extends Combattant {
    private PlayerType type;
    private boolean isSecondaryCharging;

    private TowerPlacer placer;

    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
        this.setWidth(128);
        this.setHeight(128);
        this.type = new Knight(this);
        this.isSecondaryCharging = false;
        this.setImmortal(true);
    }

    public Player(Point p) {
        this(p.getX(), p.getY());
    } 

    public String getTexturePath() {
        return this.type.getTexturePath();
    }

    public boolean isPlayer() {
        return true;
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
            this.placer = new TowerPlacer(this.type.getPrimaryTower());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            this.placer = new TowerPlacer(this.type.getSecondaryTower());
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
    
}
