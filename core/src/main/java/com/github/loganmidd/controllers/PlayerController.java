package com.github.loganmidd.controllers;

public interface PlayerController {
    public float getYMovement();
    public float getXMovement();
    public boolean isPrimaryTowerKeyJustPressed();
    public boolean isPrimaryTowerKeyPressed();
    public boolean isPrimaryAttackKeyJustPressed();
    public boolean isPrimaryAttackKeyPressed();
    public boolean isSecondaryTowerKeyJustPressed();
    public boolean isSecondaryTowerKeyPressed();
    public boolean isSecondaryAttackKeyJustPressed();
    public boolean isSecondaryAttackKeyPressed();
    public boolean isCancelButtonPressed(); 
    public boolean isStartWaveButtonPressed();
    public boolean isStartWaveButtonJustPressed();
}
