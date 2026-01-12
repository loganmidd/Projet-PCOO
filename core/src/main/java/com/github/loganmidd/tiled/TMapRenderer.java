package com.github.loganmidd.tiled;

import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.github.loganmidd.world.World;

/**
 * Renders a TiledMap with a specific order, separating layers with an 'above' property.
 * <p>
 * This renderer divides the map layers into two groups: those with an 'above' property
 * and those without. It renders the "under" layers first, then the world entities,
 * and finally the "above" layers to ensure entities appear between the two sets of layers.
 */
public class TMapRenderer {
    private TiledMap map;
    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer renderer;
    private float unitScale;

    private List<MapLayer> aboveLayers;
    private List<MapLayer> underLayers;

    /**
     * Creates a new TMapRenderer.
     *
     * @param map The TiledMap to render.
     * @param cam The camera used for viewing the map.
     */
    public TMapRenderer(TiledMap map, OrthographicCamera cam) {
        this.map = map; 
        this.cam = cam;    
        this.unitScale = 2.5f; // Arbitrary, change to (de)zoom map
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);  

        this.aboveLayers = TMapUtils.getMapLayersWithProperty(this.map.getLayers(), "above");
        this.underLayers = TMapUtils.getMapLayersWithoutProperty(this.map.getLayers(), "above");

    }

    /**
     * Renders the map and entities.
     * <p>
     * The render order is:
     * <ol>
     *     <li>Under layers (layers without 'above' property)</li>
     *     <li>World entities</li>
     *     <li>Above layers (layers with 'above' property)</li>
     * </ol>
     */
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

    /**
     * Gets the X coordinate of the top-left corner of the current view.
     *
     * @return The x-coordinate of the top-left corner.
     */
    public float getTopLeftCornerX() {
        return this.renderer.getViewBounds().getX();
    }
    
    /**
     * Gets the Y coordinate of the top-left corner of the current view.
     *
     * @return The y-coordinate of the top-left corner.
     */
    public float getTopLeftCornerY() {
        return this.renderer.getViewBounds().getY() + this.renderer.getViewBounds().height;
    }

    /**
     * Gets the unit scale used for rendering.
     *
     * @return The unit scale.
     */
    public float getUnitScale() {
        return this.unitScale;
    }

    /**
     * Disposes of the renderer and the map to release resources.
     */
    public void dispose() {
        this.renderer.dispose();
        this.map.dispose();
    }
}
