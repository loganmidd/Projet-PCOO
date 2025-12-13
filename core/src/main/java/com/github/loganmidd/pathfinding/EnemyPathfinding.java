package com.github.loganmidd.pathfinding;

import java.util.List;

import com.github.loganmidd.utils.MapPoint;

public interface EnemyPathfinding {
    public MapPoint getStart();
    public List<MapPoint> getDestinations();
    public List<MapPoint> getPath();
    public MapPoint getNextPoint();
}
