package com.github.loganmidd.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.loganmidd.world.World;

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

    public TextureRenderer(SpriteBatch spriteBatch) {
        this("spriteNotFound.png", spriteBatch);
    }

    public TextureRenderer(String path, SpriteBatch spriteBatch) {
        this(path, spriteBatch, 64, 64); // Initialise with default size of 64x64
    }
    
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

    public int getHeight()              { return this.height; }
    public int getWidth()               { return this.width; }
    public String getPath()             { return this.path; }
    public SpriteBatch getSpriteBatch() { return this.spriteBatch; }
    public Texture getTexture()         { return this.texture; }
    public float getRotation()          { return this.rotation; }
    public boolean getFlipX()           { return this.flipX; }
    public boolean getFlipY()           { return this.flipY; }
    public float getRotationX()         { return this.rotationX; }
    public float getRotationY()         { return this.rotationY; }
    public Color getTint()              { return this.tint; }
    public float getOpacity()           { return this.tint.a; }

    public void setPath(String path)                    { 
        this.path = path; 
        this.texture = new Texture(Gdx.files.internal(path)); // Update texture to render as well
    }
    public void setSpriteBatch(SpriteBatch spriteBatch) { this.spriteBatch = spriteBatch; }
    public void setHeight(int height)                   { this.height = height; }
    public void setWidth(int width)                     { this.width = width; }
    public void setRotation(float rotation)             { this.rotation = rotation;     }
    public void setFlipX(boolean flip)                  { this.flipX = flip; }
    public void setFlipY(boolean flip)                  { this.flipY = flip; }
    public void setRotationX(float x)                   { this.rotationX = x; }
    public void setRotationY(float y)                   { this.rotationY = y; }
    public void setTint(Color color)                    { this.tint = color; }
    public void setOpacity(float opacity)               { this.tint.a = opacity; }

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
