package com.github.loganmidd.tiled;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Utility methods for handling Tiled maps.
 *
 * @author Logan Middendorf
 */
public class TMapUtils {

    /**
     * Determines if a specific tile is traversable based on the "isTraversable" property.
     * <p>
     * Checks layers from top to bottom. If a tile layer has a cell at the specified
     * coordinates and defines the "isTraversable" property, that value is returned.
     * </p>
     *
     * @param map The tiled map to check.
     * @param x The x coordinate of the tile.
     * @param y The y coordinate of the tile.
     * @return {@code true} if the tile is traversable, {@code false} otherwise.
     */
    public static boolean getTraversability(TiledMap map, int x, int y) {
        List<MapLayer> layers = flattenTiledMapLayers(map.getLayers());
        for (int index=layers.size()-1; index>=0; index--) {

            MapLayer layer = layers.get(index);
            if (layer.getClass().equals(TiledMapTileLayer.class)) { // Check if the layer is a tile layer
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
        }
        return false;
    }

    /**
     * Retrieves the first tile layer found in the map.
     *
     * @param map The tiled map to search.
     * @return The first {@link TiledMapTileLayer} found, or {@code null} if none exists.
     */
    public static TiledMapTileLayer getFirstTileLayer(TiledMap map) {
        for (MapLayer layer : map.getLayers()) {
            if (layer.getClass().equals(TiledMapTileLayer.class)) {
                return (TiledMapTileLayer) layer;
            }
        }
        return null;
    }

    /**
     * Retrieves all layers that contain a specific property key.
     * <p>
     * Includes layers inside {@link MapGroupLayer}s.
     * </p>
     *
     * @param layers The collection of layers to filter.
     * @param property The property key to search for.
     * @return A list of layers that have the specified property.
     */
    public static List<MapLayer> getMapLayersWithProperty(MapLayers layers, String property) {
        List<MapLayer> filtered = new ArrayList<>();
        for (MapLayer layer : flattenTiledMapLayers(layers)) {
            if (layer.getProperties().containsKey(property)) {
                filtered.add(layer);
            }
        }
        return filtered;
    }

    /**
     * Retrieves all layers that do not contain a specific property key.
     * <p>
     * Includes layers inside {@link MapGroupLayer}s.
     * </p>
     *
     * @param layers The collection of layers to filter.
     * @param property The property key to search for.
     * @return A list of layers that do not have the specified property.
     */
    public static List<MapLayer> getMapLayersWithoutProperty(MapLayers layers, String property) {
        List<MapLayer> filtered = new ArrayList<>();
        for (MapLayer layer : flattenTiledMapLayers(layers)) {
            if (!layer.getProperties().containsKey(property)) {
                filtered.add(layer);
            }
        }
        return filtered;
    }

    /**
     * Flattens a collection of map layers, unwrapping any {@link MapGroupLayer}s.
     * <p>
     * This method recursively traverses group layers to produce a flat list of non-group layers.
     * </p>
     *
     * @param layers The collection of layers to flatten.
     * @return A flat list of map layers, excluding any {@link MapGroupLayer} instances.
     */
    public static List<MapLayer> flattenTiledMapLayers(MapLayers layers) {
        // To flatten MapLayer object that might have group layers instead of only TileLayers, ObjectLayers, etc.
        List<MapLayer> flattened = new ArrayList<>();
        for (MapLayer layer : layers) {
            if (layer.getClass().equals(MapGroupLayer.class)) {
                MapGroupLayer group = (MapGroupLayer) layer;
                for (MapLayer l : flattenTiledMapLayers(group.getLayers())) {
                    flattened.add(l);
                }
            } else {
                flattened.add(layer);
            }
        }
        return flattened;
    }


}