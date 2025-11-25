package com.github.loganmidd.tiled;

import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.github.loganmidd.world.World;

public class TMapRenderer {
    private TiledMap map;
    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer renderer;
    private float unitScale;

    private List<MapLayer> aboveLayers;
    private List<MapLayer> underLayers;


    public TMapRenderer(TiledMap map, OrthographicCamera cam) {
        this.map = map; 
        this.cam = cam;    
        this.unitScale = 2.5f; // Arbitrary, change to (de)zoom map
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);  

        this.aboveLayers = TMapUtils.getMapLayersWithProperty(this.map.getLayers(), "above");
        this.underLayers = TMapUtils.getMapLayersWithoutProperty(this.map.getLayers(), "above");

    }

    public void render() {
        this.renderer.setView(this.cam);
        this.renderer.getBatch().begin();
        
        for (MapLayer layer : this.underLayers) {
            // Render Layer
            if (layer.getClass().equals(TiledMapImageLayer.class)) {
                this.renderer.renderImageLayer((TiledMapImageLayer) layer);
            } else if (layer.getClass().equals(TiledMapTileLayer.class)) {
                this.renderer.renderTileLayer((TiledMapTileLayer) layer);
            }
        }

        this.renderer.getBatch().end();
        World.getWorld().renderEntities();
        this.renderer.getBatch().begin();
        
        for (MapLayer layer : this.aboveLayers) {
            // Render layer
            if (layer.getClass().equals(TiledMapImageLayer.class)) {
                this.renderer.renderImageLayer((TiledMapImageLayer) layer);
            } else if (layer.getClass().equals(TiledMapTileLayer.class)) {
                this.renderer.renderTileLayer((TiledMapTileLayer) layer);
            }
        }
       this.renderer.getBatch().end();
    }

    public float getTopLeftCornerX() {
        return this.renderer.getViewBounds().getX();
    }
    
    public float getTopLeftCornerY() {
        return this.renderer.getViewBounds().getY() + this.renderer.getViewBounds().height;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public void dispose() {
        this.renderer.dispose();
        this.map.dispose();
    }
}
