package com.github.loganmidd.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector3;
import com.github.loganmidd.entity.towers.Tower;
import com.github.loganmidd.world.World;

public class TowerPlacer {
    private Tower towerToPlace;
    private States state;
    private float baseSpanAngle;

    private static enum States {
        PLACING, ROTATING, DONE
    };

    public TowerPlacer(Tower tower) {
        this.towerToPlace = tower;
        this.baseSpanAngle = tower.getSpanAngle();
        this.towerToPlace.setSpanAngle((float) Math.PI*4f);
        this.towerToPlace.getTextureRenderer().setOpacity(0.7f);
        this.state = States.PLACING;
    }

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
            this.towerToPlace.setX(vector3.x);
            this.towerToPlace.setY(vector3.y);
        } else if (this.state.equals(States.ROTATING)) {
            this.towerToPlace.setStartingAngle(angle);
            this.towerToPlace.setCurrentAngle(angle);
        }


    }

    public void render() {  
        if (this.state != States.DONE) {
            towerToPlace.render();
        }
    }

    private Vector3 getMouseCoordinates() {
        return World.getWorld().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); 
    }



}
