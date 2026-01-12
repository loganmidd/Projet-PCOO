package com.github.loganmidd.ui;

import com.badlogic.gdx.utils.Disposable;

/**
 * Defines the lifecycle for a UI component.
 * <p>
 * Responsibilities include rendering the UI and handling resize events to adjust layout.
 */
public interface GameUI extends Disposable {

    /**
     * Renders the UI to the screen.
     * <p>
     * This method is typically called once per frame by the application loop.
     */
    public void render();

    /**
     * Notifies the UI that the window size has changed.
     * <p>
     * Implementations should adjust internal layout and rendering dimensions to fit the new size.
     *
     * @param width  The new width in pixels.
     * @param height The new height in pixels.
     */
    public void resize(int width, int height);
}