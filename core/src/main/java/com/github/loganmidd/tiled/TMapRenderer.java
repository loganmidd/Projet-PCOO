package com.github.loganmidd.tiled;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TMapRenderer {
    private TiledMap map;
    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer renderer;
    private float unitScale;

    public TMapRenderer(TiledMap map, OrthographicCamera cam) {
        this.map = map; 
        this.cam = cam;    
        this.unitScale = 2f; // Arbitrary, change to (de)zoom map
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);  
    }

    public void render() {
        this.renderer.setView(this.cam); 
        this.renderer.render();
    }

    public float getTopLeftCornerX() {
        return this.renderer.getViewBounds().x;
    }
    
    public float getTopLeftCornerY() {
        return this.renderer.getViewBounds().y + this.renderer.getViewBounds().height;
    }

    public float getUnitScale() {
        return this.unitScale;
    }
}
