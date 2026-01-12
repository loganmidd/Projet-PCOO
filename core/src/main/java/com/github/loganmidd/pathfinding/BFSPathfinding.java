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

/**
 * Implements the EnemyPathfinding interface using the Breadth-First Search (BFS)
 * algorithm to calculate paths on a grid.
 * <p>
 * This class computes a path from a start point to one of several destination
 * points, visualizes the path by adding entities to the world, and provides
 * access to the calculated path and initial parameters.
 */
public class BFSPathfinding implements EnemyPathfindingStrategy {

    /** The starting point for the pathfinding. */
    private MapPoint start;

    /** The list of valid destination points. */
    private List<MapPoint> destinations;

    /** The list of all traversable map points in the environment. */
    private List<MapPoint> mapPoints;

    /** The calculated list of points representing the path from start to destination. */
    private List<MapPoint> path;

    /**
     * Constructs a BFSPathfinding instance, calculates the path, and visualizes it.
     *
     * @param mapPoints The list of all traversable map points.
     * @param start The starting point for pathfinding.
     * @param destinations The list of potential destination points.
     */
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

    /**
     * Identifies traversable neighboring points for a given point.
     * <p>
     * Checks the four cardinal directions (up, down, left, right) and returns
     * only those neighbors that exist within the provided {@code mapPoints} list.
     *
     * @param p The point for which to find neighbors.
     * @return A list of neighboring points.
     */
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

    /**
     * Calculates the path from the start point to the nearest destination
     * using Breadth-First Search.
     * <p>
     * After finding the path via BFS parent tracking, it performs a cleanup
     * step to remove intermediate points that create diagonals, simplifying the path.
     *
     * @return A list of map points representing the calculated path.
     */
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

    /**
     * Retrieves the start point.
     *
     * @return The start MapPoint.
     */
    public MapPoint getStart() {
        return this.start;
    }

    /**
     * Retrieves the list of destination points.
     *
     * @return The list of destination MapPoints.
     */
    public List<MapPoint> getDestinations() {
        return this.destinations;
    }

    /**
     * Retrieves the next point in the path.
     * <p>
     * Note: This implementation returns a new MapPoint(0,0) regardless of the current path state.
     *
     * @return A new MapPoint at coordinates (0,0).
     */
    public MapPoint getNextPoint() {
        return new MapPoint(0,0);
    }

    /**
     * Retrieves the calculated path.
     *
     * @return The list of MapPoints representing the path.
     */
    public List<MapPoint> getPath() {
        return this.path;
    }
}