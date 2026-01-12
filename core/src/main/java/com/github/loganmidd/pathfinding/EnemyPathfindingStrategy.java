package com.github.loganmidd.pathfinding;

import java.util.List;

import com.github.loganmidd.utils.MapPoint;

/**
 * Defines the contract for enemy pathfinding behavior.
 * 
 * @author Logan Middendorf
 */
public interface EnemyPathfindingStrategy {
    /**
     * Retrieves the starting point of the path.
     * 
     * @return The start MapPoint.
     */
    public MapPoint getStart();

    /**
     * Retrieves the list of destination points for the path.
     * 
     * @return A list of destination MapPoints.
     */
    public List<MapPoint> getDestinations();

    /**
     * Retrieves the calculated path from start to destinations.
     * 
     * @return A list of MapPoints representing the path.
     */
    public List<MapPoint> getPath();

    /**
     * Retrieves the next point in the path sequence.
     * 
     * @return The next MapPoint in the path.
     */
    public MapPoint getNextPoint();
}