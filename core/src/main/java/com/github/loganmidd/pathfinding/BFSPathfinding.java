package com.github.loganmidd.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.github.loganmidd.entity.Player;
import com.github.loganmidd.utils.MapPoint;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class BFSPathfinding implements EnemyPathfinding {
    private MapPoint start;
    private List<MapPoint> destinations;
    private List<MapPoint> mapPoints;
    private List<MapPoint> path;

    public BFSPathfinding(List<MapPoint> mapPoints, MapPoint start, List<MapPoint> destinations) {
        this.start = start;
        this.destinations = destinations;
        this.mapPoints = mapPoints;

        this.path = this.calculatePath();

        for (MapPoint point : this.path) {
            Point p = World.getWorld().getTMap().getCoordinatesOfTile(point.getX(), point.getY());
            Player pl = new Player(p);
            World.getWorld().addEntity(pl);
            pl.getTextureRenderer().setPath("spriteNotFound.png");
            pl.getTextureRenderer().setHeight(80);
            pl.getTextureRenderer().setWidth(80);
        }
    }

    private List<MapPoint> getNeighbors(MapPoint p) {
        int x0 = p.getX();
        int y0 = p.getY();
        List<MapPoint> result = new ArrayList<>();
        int[][] testOffsets = {{1,0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] testOffset : testOffsets) {
            int x = testOffset[0];
            int y = testOffset[1];
            if (x == 0 && y == 0) {
                continue;
            }   
            MapPoint test = new MapPoint(x0 + x, y0 + y);
            if (this.mapPoints.contains(test)) {
                result.add(test);
            }
        }
        return result;
    }

    private List<MapPoint> calculatePath() {
        MapPoint destination = this.start;
        HashMap<MapPoint, MapPoint> parents = new HashMap<>();
        List<MapPoint> visited = new ArrayList<>();
        Queue<MapPoint> queue = new LinkedList<>();
        queue.add(this.start);
        parents.put(this.start, null);

        while (!queue.isEmpty()) {
            MapPoint point = queue.poll();
            if (destination == this.start && this.destinations.contains(point)) {
                destination = point;
            }

            if (!visited.contains(point)) {
                visited.add(point);
            }
            for (MapPoint neighbor : this.getNeighbors(point)) {
                if (!visited.contains(neighbor) && !queue.contains(neighbor)) {
                    queue.offer(neighbor);
                    parents.put(neighbor, point);
                }
            }
        }

        List<MapPoint> path = new ArrayList<>();
        MapPoint current = destination;
        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        // To clean up diagonals
        int i = 1;
        while (i < path.size() - 1) {
            MapPoint before = path.get(i-1);
            MapPoint after = path.get(i+1);
            int dx = Math.abs(before.getX() - after.getX());
            int dy = Math.abs(before.getY() - after.getY());
            // Testing if the points are a diagonal away (instead of straight across)
            if (dx == 1 && dy == 1) {
                path.remove(i);
            } else {
                i++;
            }
        }

        return path;
    }

    public MapPoint getStart() {
        return this.start;
    }

    public List<MapPoint> getDestinations() {
        return this.destinations;
    }

    public MapPoint getNextPoint() {
        return new MapPoint(0,0);
    }

    public List<MapPoint> getPath() {
        return this.path;
    }
}
