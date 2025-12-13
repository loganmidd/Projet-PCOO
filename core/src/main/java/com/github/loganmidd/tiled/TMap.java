package com.github.loganmidd.tiled;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.utils.MapPoint;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class TMap {
    private String path;
    private TiledMap map;
    private TMapRenderer renderer;
    private List<Block> blocks;
    private List<MapPoint> traversableCases;

    private Point playerSpawnPoint;
    private List<Point> crystalSpawnPoints;
    private List<Point> enemySpawnPoints;
    private List<Point> enemyPathPoints;

    public TMap(String filePath, OrthographicCamera cam) {
        init(filePath, cam); // To be able to re-initialize later
    }

    public void init(String filePath, OrthographicCamera cam) {
        this.path = filePath;
        this.map = new TmxMapLoader().load(filePath);
        this.renderer = new TMapRenderer(map, cam);
        this.blocks = new ArrayList<>();  
        this.traversableCases = new ArrayList<>();


        this.crystalSpawnPoints = new ArrayList<>();
        this.enemySpawnPoints = new ArrayList<>();
        this.enemyPathPoints = new ArrayList<>();
        this.loadTraversabilityWalls();
        this.loadSpawnPoints();
        this.loadEnemyPathPoints();
    }

    private void loadTraversabilityWalls() {
        TiledMapTileLayer firstLayer = TMapUtils.getFirstTileLayer(this.map);
        int width = firstLayer.getTileWidth();
        int height = firstLayer.getTileHeight();
        // Tile Layers
        for (int x=0; x<firstLayer.getWidth(); x++) {
            for (int y=0; y<firstLayer.getHeight(); y++) {
                if (!TMapUtils.getTraversability(this.map, x, y)) {
                    Point point = this.getCoordinatesOfTile(x, y);
                    float unitScale = this.renderer.getUnitScale();
                    Block b = new Block(point.getX(), point.getY(), unitScale*height, unitScale*width);
                    this.blocks.add(b);
                    World.getWorld().addBlock(b);
                } else {
                    this.traversableCases.add(new MapPoint(x, y));
                }
            }
        }
        // Object Layers
        for (MapLayer layer : TMapUtils.getMapLayersWithProperty(this.map.getLayers(), "isTraversable")) {
            if (!((boolean) layer.getProperties().get("isTraversable"))) {
                for (MapObject obj : layer.getObjects()) {
                    if (obj.getClass().equals(RectangleMapObject.class)) {
                        Rectangle rec = ((RectangleMapObject) obj).getRectangle();
                        float unitScale = this.renderer.getUnitScale();
                        Block block = new Block(rec.getX()*unitScale, rec.getY()*unitScale, rec.getHeight()*unitScale, rec.getWidth()*unitScale);
                        
                        if (layer.getProperties().containsKey("hasTrueCollision")) {
                            block.setTrueCollision((boolean) layer.getProperties().get("hasTrueCollision"));
                        }

                        this.blocks.add(block);
                        World.getWorld().addBlock(block);
                    }
                }
            }
        }
    }

    private void loadSpawnPoints() {
        float unitScale = this.renderer.getUnitScale();
        MapLayers layers = this.map.getLayers();
        for (MapLayer layer : TMapUtils.flattenTiledMapLayers(layers)) {
            MapProperties props = layer.getProperties();
            
            if (props.containsKey("spawn")) {
                for (MapObject obj : layer.getObjects()) {
                    if (obj.getClass().equals(RectangleMapObject.class)) {
                        RectangleMapObject rectObj = (RectangleMapObject) obj;
                        float x = rectObj.getRectangle().getX() * unitScale;
                        float y = rectObj.getRectangle().getY() * unitScale;

                        switch (rectObj.getName()) {
                            case "player":
                                this.playerSpawnPoint = new Point(x, y);
                                break;
                            case "crystal":
                                this.crystalSpawnPoints.add(new Point(x,y));
                                break;
                            case "enemy":
                                this.enemySpawnPoints.add(new Point(x,y));
                                break;
                        }

                    }
                }

            }
        }
    }

    private void loadEnemyPathPoints() {
        for (MapLayer layer : TMapUtils.getMapLayersWithProperty(this.map.getLayers(), "enemypath")) {
            for (MapObject obj : layer.getObjects()) {
                if (obj.getClass().equals(RectangleMapObject.class)) {
                        Rectangle rec = ((RectangleMapObject) obj).getRectangle();
                        float unitScale = this.renderer.getUnitScale();
                        Point point = new Point(rec.getX() * unitScale, rec.getY() * unitScale);
                        this.enemyPathPoints.add(point);
                }
            }
        }
    }

    public Point getCoordinatesOfTile(int x, int y) {
        float topRightX = this.renderer.getTopLeftCornerX();
        float topRightY = this.renderer.getTopLeftCornerY();
        float unitScale = this.renderer.getUnitScale();
        TiledMapTileLayer firstLayer = TMapUtils.getFirstTileLayer(this.map);
        
        Point point = new Point (
            topRightX + unitScale*x*firstLayer.getTileWidth(),
            topRightY + unitScale*y*firstLayer.getTileHeight()
        );
        return point;
    } 


    public void render() {
        this.renderer.render();
    }

    public String getPath() {
        return this.path;
    }

    public Point getPlayerSpawnPoint() {
        return this.playerSpawnPoint;
    }

    public List<Point> getCrystalSpawnPoints() {
        return this.crystalSpawnPoints;
    }

    public List<Point> getEnemySpawnPoints() {
        return this.enemySpawnPoints;
    }

    public List<MapPoint> getTraversableCases() {
        return this.traversableCases;
    }

    public List<Point> getEnemyPathPoints() {
        return this.enemyPathPoints;
    }

    public void dispose() {
        this.renderer.dispose();
    }
    
}