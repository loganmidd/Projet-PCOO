package com.github.loganmidd.tiled;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.github.loganmidd.structures.Block;
import com.github.loganmidd.world.World;

public class TMap {
    private String path;
    private TiledMap map;
    private TMapRenderer renderer;
    private List<Block> blocks;

    public TMap(String filePath, OrthographicCamera cam) {
        init(filePath, cam); // To be able to re-initialize later
    }

    public void init(String filePath, OrthographicCamera cam) {
        this.path = filePath;
        this.map = new TmxMapLoader().load(filePath);
        this.renderer = new TMapRenderer(map, cam);
        this.blocks = new ArrayList<>();

        TiledMapTileLayer firstLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int width = firstLayer.getTileWidth();
        int height = firstLayer.getTileHeight();
        for (int x=0; x<firstLayer.getWidth(); x++) {
            for (int y=0; y<firstLayer.getHeight(); y++) {
                if (!this.getTraversability(x, y)) {
                    float[] coords = this.getCoordinatesOfTile(x, y);
                    float unitScale = this.renderer.getUnitScale();
                    Block b = new Block(coords[0], coords[1], unitScale*height, unitScale*width);
                    this.blocks.add(b);
                    World.getWorld().addBlock(b);
                }
            }
        }
    }

    public boolean getTraversability(int x, int y) {
        MapLayers layers = this.map.getLayers();
        for (int index=layers.getCount()-1; index>=0; index--) {
            TiledMapTileLayer tLayer = (TiledMapTileLayer) layers.get(index);
            Cell cell = tLayer.getCell(x, y);
            if (cell != null) {
                Object state = tLayer.getProperties().get("isTraversable");
                if (state == null) { // If the property "isTraversable" is undefined
                    continue;        // Ignore layer
                } else {
                    return (boolean) state;
                }
            }
        }
        return false;
    }

    public float[] getCoordinatesOfTile(int x, int y) {
        float topRightX = this.renderer.getTopLeftCornerX();
        float topRightY = this.renderer.getTopLeftCornerY();
        float unitScale = this.renderer.getUnitScale();
        TiledMapTileLayer firstLayer = (TiledMapTileLayer) map.getLayers().get(0);
        float[] coords =  {
            topRightX + unitScale*x*firstLayer.getTileWidth(),
            topRightY + unitScale*y*firstLayer.getTileHeight()
        };
        return coords;
    } 

    public void render() {
        this.renderer.render();
    }

    public String getPath() {
        return this.path;
    }
}