package com.github.loganmidd.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyboardController implements PlayerController {

    @Override
    public float getYMovement() {
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            return 1;
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public float getXMovement() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            return 1;
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isPrimaryTowerKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.D);
    }

    @Override
    public boolean isPrimaryTowerKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.D);
    }


    @Override
    public boolean isPrimaryAttackKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.F);
    }

    @Override
    public boolean isPrimaryAttackKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.F);
    }


    @Override
    public boolean isSecondaryTowerKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.X);
    }

    @Override
    public boolean isSecondaryTowerKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.X);
    }


    @Override
    public boolean isSecondaryAttackKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.C);
    }
    
    @Override
    public boolean isSecondaryAttackKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.C);
    }

    @Override
    public boolean isCancelButtonPressed() {
        return Gdx.input.isKeyPressed(Keys.ESCAPE);
    }

    @Override
    public boolean isStartWaveButtonJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.G);
    }

    @Override
    public boolean isStartWaveButtonPressed() {
        return Gdx.input.isKeyPressed(Keys.G);
    }
    
}
