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
import com.badlogic.gdx.utils.Disposable;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.utils.MapPoint;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

/**
 * Represents a map loaded from a Tiled file.
 * <p>
 * This class handles loading the map, parsing its layers for collision data
 * (blocks), spawn points, and enemy path points, and managing the rendering process.
 * </p>
 *
 * @author Logan Middendorf
 */
public class TMap implements Disposable {
    private String path;
    private TiledMap map;
    private TMapRenderer renderer;
    private List<Block> blocks;
    private List<MapPoint> traversableCases;
    private TiledMapTileLayer firstLayer;

    private Point playerSpawnPoint;
    private List<Point> crystalSpawnPoints;
    private List<Point> enemySpawnPoints;
    private List<Point> enemyPathPoints;

    /**
     * Constructs a TMap and initializes it with the specified file path and camera.
     *
     * @param filePath The path to the Tiled map file to load.
     * @param cam      The OrthographicCamera used for rendering the map.
     */
    public TMap(String filePath, OrthographicCamera cam) {
        init(filePath, cam); // To be able to re-initialize later
    }

    /**
     * Initializes or re-initializes the map with the specified file path and camera.
     *
     * @param filePath The path to the Tiled map file to load.
     * @param cam      The OrthographicCamera used for rendering the map.
     */
    public void init(String filePath, OrthographicCamera cam) {
        this.path = filePath;
        this.map = new TmxMapLoader().load(filePath);
        this.renderer = new TMapRenderer(map, cam);
        this.blocks = new ArrayList<>();  
        this.traversableCases = new ArrayList<>();

        this.firstLayer = TMapUtils.getFirstTileLayer(this.map);

        this.crystalSpawnPoints = new ArrayList<>();
        this.enemySpawnPoints = new ArrayList<>();
        this.enemyPathPoints = new ArrayList<>();
        this.loadTraversabilityWalls();
        this.loadSpawnPoints();
        this.loadEnemyPathPoints();

    }

    /**
     * Loads traversability data from the map.
     * <p>
     * Iterates through tile layers to identify non-traversable tiles, creating blocks for them.
     * It also checks object layers for rectangles defined as non-traversable and adds those as blocks.
     * </p>
     */
    private void loadTraversabilityWalls() {
        int width = this.firstLayer.getTileWidth();
        int height = this.firstLayer.getTileHeight();
        // Tile Layers
        for (int x=0; x<this.firstLayer.getWidth(); x++) {
            for (int y=0; y<this.firstLayer.getHeight(); y++) {
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

    /**
     * Loads spawn points from the map.
     * <p>
     * Searches for layers with a "spawn" property and identifies specific spawn points
     * for the player, crystals, and enemies based on the object name.
     * </p>
     */
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

    /**
     * Loads enemy path points from the map.
     * <p>
     * Searches for layers with an "enemypath" property and extracts coordinates
     * from rectangle objects within those layers.
     * </p>
     */
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

    /**
     * Calculates the world coordinates of a specific tile based on its grid coordinates.
     *
     * @param x The x-coordinate of the tile in the grid.
     * @param y The y-coordinate of the tile in the grid.
     * @return A Point representing the world coordinates of the tile.
     */
    public Point getCoordinatesOfTile(int x, int y) {
        float topRightX = this.renderer.getTopLeftCornerX();
        float topRightY = this.renderer.getTopLeftCornerY();
        float unitScale = this.renderer.getUnitScale();
        
        Point point = new Point (
            topRightX + unitScale*x*this.firstLayer.getTileWidth(),
            topRightY + unitScale*y*this.firstLayer.getTileHeight()
        );
        return point;
    } 


    /**
     * Renders the map.
     */
    public void render() {
        this.renderer.render();
    }

    /**
     * Gets the file path of the map.
     *
     * @return The file path string.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets the spawn point for the player.
     *
     * @return The player spawn Point.
     */
    public Point getPlayerSpawnPoint() {
        return this.playerSpawnPoint;
    }

    /**
     * Gets the list of spawn points for crystals.
     *
     * @return A List of Points representing crystal spawn locations.
     */
    public List<Point> getCrystalSpawnPoints() {
        return this.crystalSpawnPoints;
    }

    /**
     * Gets the list of spawn points for enemies.
     *
     * @return A List of Points representing enemy spawn locations.
     */
    public List<Point> getEnemySpawnPoints() {
        return this.enemySpawnPoints;
    }

    /**
     * Gets the list of traversable cases (grid coordinates).
     *
     * @return A List of MapPoints representing traversable grid locations.
     */
    public List<MapPoint> getTraversableCases() {
        return this.traversableCases;
    }

    /**
     * Gets the list of path points for enemies.
     *
     * @return A List of Points representing the enemy patrol path.
     */
    public List<Point> getEnemyPathPoints() {
        return this.enemyPathPoints;
    }

    /**
     * Disposes of resources used by the map.
     */
    public void dispose() {
        this.renderer.dispose();
    }

    /**
     * Gets the width of the map in tiles.
     *
     * @return The map width in tiles.
     */
    public int getWidth() {
        return this.firstLayer.getWidth();
    }

    /**
     * Gets the height of the map in tiles.
     *
     * @return The map height in tiles.
     */
    public int getHeight() {
        return this.firstLayer.getHeight();
    }
    
}