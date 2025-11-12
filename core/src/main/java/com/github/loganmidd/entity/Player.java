package com.github.loganmidd.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Entity {

    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
        this.setWidth(128);
        this.setHeight(128);
    }

    public void input() {
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

    }

    public void render() {
        super.render();
    }
    
}
