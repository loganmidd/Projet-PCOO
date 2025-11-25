package com.github.loganmidd.tiled;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TMapUtils {

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

    public static TiledMapTileLayer getFirstTileLayer(TiledMap map) {
        for (MapLayer layer : map.getLayers()) {
            if (layer.getClass().equals(TiledMapTileLayer.class)) {
                return (TiledMapTileLayer) layer;
            }
        }
        return null;
    }

    public static List<MapLayer> getMapLayersWithProperty(MapLayers layers, String property) {
        List<MapLayer> filtered = new ArrayList<>();
        for (MapLayer layer : flattenTiledMapLayers(layers)) {
            if (layer.getProperties().containsKey(property)) {
                filtered.add(layer);
            }
        }
        return filtered;
    }

    public static List<MapLayer> getMapLayersWithoutProperty(MapLayers layers, String property) {
        List<MapLayer> filtered = new ArrayList<>();
        for (MapLayer layer : flattenTiledMapLayers(layers)) {
            if (!layer.getProperties().containsKey(property)) {
                filtered.add(layer);
            }
        }
        return filtered;
    }

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
