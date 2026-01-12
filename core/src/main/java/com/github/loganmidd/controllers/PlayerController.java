package com.github.loganmidd.controllers;

/**
 * Defines the interface for a player controller that retrieves input state for movement and actions.
 * 
 * @author Logan Middendorf
 */
public interface PlayerController {
    /**
     * Retrieves the vertical movement component.
     * 
     * @return The Y-axis movement value.
     */
    public float getYMovement();
    
    /**
     * Retrieves the horizontal movement component.
     * 
     * @return The X-axis movement value.
     */
    public float getXMovement();
    
    /**
     * Checks if the primary tower selection key was pressed in the current frame.
     * 
     * @return True if the key was just pressed, false otherwise.
     */
    public boolean isPrimaryTowerKeyJustPressed();
    
    /**
     * Checks if the primary tower selection key is currently held down.
     * 
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isPrimaryTowerKeyPressed();
    
    /**
     * Checks if the primary attack key was pressed in the current frame.
     * 
     * @return True if the key was just pressed, false otherwise.
     */
    public boolean isPrimaryAttackKeyJustPressed();
    
    /**
     * Checks if the primary attack key is currently held down.
     * 
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isPrimaryAttackKeyPressed();
    
    /**
     * Checks if the secondary tower selection key was pressed in the current frame.
     * 
     * @return True if the key was just pressed, false otherwise.
     */
    public boolean isSecondaryTowerKeyJustPressed();
    
    /**
     * Checks if the secondary tower selection key is currently held down.
     * 
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isSecondaryTowerKeyPressed();
    
    /**
     * Checks if the secondary attack key was pressed in the current frame.
     * 
     * @return True if the key was just pressed, false otherwise.
     */
    public boolean isSecondaryAttackKeyJustPressed();
    
    /**
     * Checks if the secondary attack key is currently held down.
     * 
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isSecondaryAttackKeyPressed();
    
    /**
     * Checks if the cancel action button is currently pressed.
     * 
     * @return True if the button is pressed, false otherwise.
     */
    public boolean isCancelButtonPressed(); 
    
    /**
     * Checks if the start wave button is currently pressed.
     * 
     * @return True if the button is pressed, false otherwise.
     */
    public boolean isStartWaveButtonPressed();
    
    /**
     * Checks if the start wave button was pressed in the current frame.
     * 
     * @return True if the button was just pressed, false otherwise.
     */
    public boolean isStartWaveButtonJustPressed();
}