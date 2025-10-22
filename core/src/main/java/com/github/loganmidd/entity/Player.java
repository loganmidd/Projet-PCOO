package com.github.loganmidd.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Entity {


    public Player(float x, float y) {
        super(x, y); // (x, y) coordinates for bottom-left corner of hitbox
    }

    public void input() {
        float a = 6f;
        // Movement
        // Right arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.setDx(a*0.8f);
        }
        // Left arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.setDx(-a*0.8f);
        }
        // Up arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.setDy(a*0.8f);
        }
        // Down arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.setDy(-a*0.8f);
        }

    }
    
}
