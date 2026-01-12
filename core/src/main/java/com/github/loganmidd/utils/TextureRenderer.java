package com.github.loganmidd.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.loganmidd.world.World;

/**
 * Renders a texture at a specific position with configurable dimensions, rotation, flipping, and tint.
 * <p>
 * This class manages a {@link Texture} and {@link SpriteBatch} to draw graphics. It supports
 * basic properties like width, height, and color adjustments. It also includes a specific rendering
 * behavior for a "bobbing" effect based on the global frame number.
 *
 * @author Logan Middendorf
 */
public class TextureRenderer {
    private String path;
    private SpriteBatch spriteBatch;
    private int width;
    private int height;
    private Texture texture;
    private float rotation;
    private float rotationX;
    private float rotationY;
    private boolean flipX;
    private boolean flipY;
    private Color tint;

    /**
     * Creates a TextureRenderer using a default path for missing textures.
     * <p>
     * The default path is "spriteNotFound.png".
     *
     * @param spriteBatch The SpriteBatch used for drawing
     */
    public TextureRenderer(SpriteBatch spriteBatch) {
        this("spriteNotFound.png", spriteBatch);
    }

    /**
     * Creates a TextureRenderer with a specific texture path and default dimensions (64x64).
     *
     * @param path The file path of the texture to load
     * @param spriteBatch The SpriteBatch used for drawing
     */
    public TextureRenderer(String path, SpriteBatch spriteBatch) {
        this(path, spriteBatch, 64, 64); // Initialise with default size of 64x64
    }
    
    /**
     * Creates a TextureRenderer with a specific texture path, dimensions, and SpriteBatch.
     * <p>
     * Initializes default rotation to 0, flip state to false, and sets the rotation pivot
     * to the center of the specified dimensions. The default tint is opaque white.
     *
     * @param path The file path of the texture to load
     * @param spriteBatch The SpriteBatch used for drawing
     * @param width The rendering width in pixels
     * @param height The rendering height in pixels
     */
    public TextureRenderer (String path, SpriteBatch spriteBatch, int width, int height) {
        this.path = path;
        this.spriteBatch = spriteBatch;
        this.width = width;
        this.height = height;
        this.texture = new Texture(Gdx.files.internal(path));
        this.rotation = 0;
        this.flipX = false;
        this.flipY = false;
        this.rotationX = this.width/2f;  
        this.rotationY = this.height/2f; 
        this.tint = new Color(1, 1,1, 1);
    }

    /**
     * Returns the rendering height.
     *
     * @return The height in pixels
     */
    public int getHeight()              { return this.height; }

    /**
     * Returns the rendering width.
     *
     * @return The width in pixels
     */
    public int getWidth()               { return this.width; }

    /**
     * Returns the file path of the texture.
     *
     * @return The texture path string
     */
    public String getPath()             { return this.path; }

    /**
     * Returns the SpriteBatch used for rendering.
     *
     * @return The SpriteBatch instance
     */
    public SpriteBatch getSpriteBatch() { return this.spriteBatch; }

    /**
     * Returns the Texture being rendered.
     *
     * @return The Texture instance
     */
    public Texture getTexture()         { return this.texture; }

    /**
     * Returns the rotation angle in degrees.
     *
     * @return The rotation angle
     */
    public float getRotation()          { return this.rotation; }

    /**
     * Returns the horizontal flip state.
     *
     * @return True if flipped horizontally
     */
    public boolean getFlipX()           { return this.flipX; }

    /**
     * Returns the vertical flip state.
     *
     * @return True if flipped vertically
     */
    public boolean getFlipY()           { return this.flipY; }

    /**
     * Returns the X coordinate of the rotation pivot.
     *
     * @return The pivot X coordinate
     */
    public float getRotationX()         { return this.rotationX; }

    /**
     * Returns the Y coordinate of the rotation pivot.
     *
     * @return The pivot Y coordinate
     */
    public float getRotationY()         { return this.rotationY; }

    /**
     * Returns the color tint applied during rendering.
     *
     * @return The tint Color
     */
    public Color getTint()              { return this.tint; }

    /**
     * Returns the opacity (alpha channel) of the current tint.
     *
     * @return The opacity value (0.0 to 1.0)
     */
    public float getOpacity()           { return this.tint.a; }

    /**
     * Sets the texture path and loads the new texture.
     *
     * @param path The new file path
     */
    public void setPath(String path)                    { 
        this.path = path; 
        this.texture = new Texture(Gdx.files.internal(path)); // Update texture to render as well
    }

    /**
     * Sets the SpriteBatch used for rendering.
     *
     * @param spriteBatch The SpriteBatch instance
     */
    public void setSpriteBatch(SpriteBatch spriteBatch) { this.spriteBatch = spriteBatch; }

    /**
     * Sets the rendering height.
     *
     * @param height The new height in pixels
     */
    public void setHeight(int height)                   { this.height = height; }

    /**
     * Sets the rendering width.
     *
     * @param width The new width in pixels
     */
    public void setWidth(int width)                     { this.width = width; }

    /**
     * Sets the rotation angle in degrees.
     *
     * @param rotation The rotation angle
     */
    public void setRotation(float rotation)             { this.rotation = rotation;     }

    /**
     * Sets the horizontal flip state.
     *
     * @param flip True to flip horizontally
     */
    public void setFlipX(boolean flip)                  { this.flipX = flip; }

    /**
     * Sets the vertical flip state.
     *
     * @param flip True to flip vertically
     */
    public void setFlipY(boolean flip)                  { this.flipY = flip; }

    /**
     * Sets the X coordinate of the rotation pivot.
     *
     * @param x The pivot X coordinate
     */
    public void setRotationX(float x)                   { this.rotationX = x; }

    /**
     * Sets the Y coordinate of the rotation pivot.
     *
     * @param y The pivot Y coordinate
     */
    public void setRotationY(float y)                   { this.rotationY = y; }

    /**
     * Sets the color tint.
     *
     * @param color The new tint Color
     */
    public void setTint(Color color)                    { this.tint = color; }

    /**
     * Sets the opacity (alpha channel) of the tint.
     *
     * @param opacity The opacity value (0.0 to 1.0)
     */
    public void setOpacity(float opacity)               { this.tint.a = opacity; }

    /**
     * Renders the texture at the specified coordinates with a vertical bobbing effect.
     * <p>
     * The rendering applies the configured rotation, scaling, flipping, and tint. The texture
     * is drawn with a vertical oscillation calculated based on the current frame number from
     * the {@link World} instance.
     *
     * @param x The x-coordinate for rendering
     * @param y The y-coordinate for rendering (base position for bobbing)
     */
    public void renderAt(float x, float y) {
        Color baseColor = this.spriteBatch.getColor();
        this.spriteBatch.setColor(this.tint);
        this.spriteBatch.draw(
            this.texture, 
            x,
            y + 4*(float) Math.sin((float)Math.PI*2f*(float) World.getWorld().getFrameNumber()/120f),
            this.rotationX,
            this.rotationY,
            this.width, 
            this.height, 
            1f,     // Scale x
            1f,     // Scale y
            this.rotation,
            0,          // srcX (texels)
            0,          // srcY (texels)
            this.texture.getWidth(),    // srcWidth (texels)
            this.texture.getHeight(),   // srcHeight (texels)
            this.flipX,
            this.flipY
        );
        this.spriteBatch.setColor(baseColor);
    }

    
}