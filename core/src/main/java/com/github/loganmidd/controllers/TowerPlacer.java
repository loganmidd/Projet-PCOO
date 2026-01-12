package com.github.loganmidd.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector3;
import com.github.loganmidd.entity.towers.Tower;
import com.github.loganmidd.world.World;

/**
 * Manages the process of placing a tower in the world.
 * <p>
 * This class facilitates a two-step interaction for placing a tower:
 * first positioning it via mouse click, then orienting it via a second click.
 * It handles visual feedback, such as transparency, during the placement process.
 */
public class TowerPlacer {
    private Tower towerToPlace;
    private States state;
    private float baseSpanAngle;

    /**
     * Defines the possible states of the tower placement process.
     */
    private static enum States {
        PLACING, ROTATING, DONE
    };

    /**
     * Constructs a new TowerPlacer for the specified tower.
     * <p>
     * This initializes the placement state, sets the tower's initial position to be invisible
     * until moved, and prepares the tower's visual properties for placement feedback.
     *
     * @param tower The tower instance to be placed.
     */
    public TowerPlacer(Tower tower) {
        this.towerToPlace = tower;
        this.baseSpanAngle = tower.getSpanAngle();
        this.towerToPlace.setSpanAngle((float) Math.PI*4f);
        this.towerToPlace.getTextureRenderer().setOpacity(0.7f);
        this.state = States.PLACING;
    }

    /**
     * Updates the logic for tower placement.
     * <p>
     * This method processes user input to handle the placement and rotation steps.
     * It updates the tower's position or rotation based on the current state and mouse input.
     */
    public void logic() {

        Vector3 vector3 = this.getMouseCoordinates();
        float angle = (float) Math.atan2(vector3.y - this.towerToPlace.getCenterY(), vector3.x - this.towerToPlace.getCenterX());

        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            if (this.state.equals(States.PLACING)) {
                this.state = States.ROTATING;
                this.towerToPlace.getTextureRenderer().setOpacity(1);
            } else if (this.state.equals(States.ROTATING)) {
                this.towerToPlace.setSpanAngle(this.baseSpanAngle);
                World.getWorld().addEntity(this.towerToPlace);        
                this.state = States.DONE;

            }          
        }

        if (this.state.equals(States.PLACING)) {
            this.towerToPlace.setCenterX(vector3.x);
            this.towerToPlace.setCenterY(vector3.y);
        } else if (this.state.equals(States.ROTATING)) {
            this.towerToPlace.setStartingAngle(angle);
            this.towerToPlace.setCurrentAngle(angle);
        }
    }

    /**
     * Renders the tower if it is not yet placed.
     * <p>
     * This ensures the tower is visible during the placement and rotation phases
     * but disappears once the placement is finalized.
     */
    public void render() {  
        if (this.state != States.DONE) {
            towerToPlace.render();
        }
    }

    /**
     * Retrieves the current mouse coordinates projected into the game world.
     *
     * @return A Vector3 containing the x, y, and z coordinates of the mouse in the world.
     */
    private Vector3 getMouseCoordinates() {
        return World.getWorld().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); 
    }
}