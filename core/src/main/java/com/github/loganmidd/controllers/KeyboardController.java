package com.github.loganmidd.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * {@code KeyboardController} is an implementation of the {@link PlayerController} interface
 * that translates keyboard inputs into player control actions using the libGDX framework.
 * <p>
 * This class maps specific keyboard keys to movement, tower placement, attack actions, and game flow controls.
 * 
 * @author Logan Middendorf
 */
public class KeyboardController implements PlayerController {

    /**
     * Retrieves the vertical movement input.
     * <p>
     * Maps the UP key to positive movement (1.0f) and the DOWN key to negative movement (-1.0f).
     * 
     * @return A float representing vertical movement: 1.0f (up), -1.0f (down), or 0.0f (no input).
     */
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

    /**
     * Retrieves the horizontal movement input.
     * <p>
     * Maps the RIGHT key to positive movement (1.0f) and the LEFT key to negative movement (-1.0f).
     * 
     * @return A float representing horizontal movement: 1.0f (right), -1.0f (left), or 0.0f (no input).
     */
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

    /**
     * Checks if the primary tower placement key was just pressed.
     * 
     * @return {@code true} if the D key was just pressed; {@code false} otherwise.
     */
    @Override
    public boolean isPrimaryTowerKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.D);
    }

    /**
     * Checks if the primary tower placement key is currently being held down.
     * 
     * @return {@code true} if the D key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isPrimaryTowerKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.D);
    }

    /**
     * Checks if the primary attack key is currently being held down.
     * 
     * @return {@code true} if the F key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isPrimaryAttackKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.F);
    }

    /**
     * Checks if the primary attack key was just pressed.
     * 
     * @return {@code true} if the F key was just pressed; {@code false} otherwise.
     */
    @Override
    public boolean isPrimaryAttackKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.F);
    }

    /**
     * Checks if the secondary tower placement key is currently being held down.
     * 
     * @return {@code true} if the X key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isSecondaryTowerKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.X);
    }

    /**
     * Checks if the secondary tower placement key was just pressed.
     * 
     * @return {@code true} if the X key was just pressed; {@code false} otherwise.
     */
    @Override
    public boolean isSecondaryTowerKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.X);
    }

    /**
     * Checks if the secondary attack key was just pressed.
     * 
     * @return {@code true} if the C key was just pressed; {@code false} otherwise.
     */
    @Override
    public boolean isSecondaryAttackKeyJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.C);
    }
    
    /**
     * Checks if the secondary attack key is currently being held down.
     * 
     * @return {@code true} if the C key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isSecondaryAttackKeyPressed() {
        return Gdx.input.isKeyPressed(Keys.C);
    }

    /**
     * Checks if the cancel button is currently being held down.
     * 
     * @return {@code true} if the ESCAPE key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isCancelButtonPressed() {
        return Gdx.input.isKeyPressed(Keys.ESCAPE);
    }

    /**
     * Checks if the start wave button was just pressed.
     * 
     * @return {@code true} if the G key was just pressed; {@code false} otherwise.
     */
    @Override
    public boolean isStartWaveButtonJustPressed() {
        return Gdx.input.isKeyJustPressed(Keys.G);
    }

    /**
     * Checks if the start wave button is currently being held down.
     * 
     * @return {@code true} if the G key is currently pressed; {@code false} otherwise.
     */
    @Override
    public boolean isStartWaveButtonPressed() {
        return Gdx.input.isKeyPressed(Keys.G);
    }
    
}