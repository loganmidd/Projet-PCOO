package com.github.loganmidd.imageutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureRenderer {
    private String path;
    private SpriteBatch spriteBatch;
    private int width;
    private int height;
    private Texture texture;

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
    }

    public int getHeight()              { return this.height; }
    public int getWidth()               { return this.width; }
    public String getPath()             { return path; }
    public SpriteBatch getSpriteBatch() { return spriteBatch; }

    public void setPath(String path)                    { 
        this.path = path; 
        this.texture = new Texture(Gdx.files.internal(path)); // Update texture to render as well
    }
    public void setSpriteBatch(SpriteBatch spriteBatch) { this.spriteBatch = spriteBatch; }
    public void setHeight(int height)                   { this.height = height; }
    public void setWidth(int width)                     { this.width = width; }

    public void renderAt(float x, float y) {
        this.spriteBatch.draw(this.texture, x, y, this.width, this.height);
    }

    
}
