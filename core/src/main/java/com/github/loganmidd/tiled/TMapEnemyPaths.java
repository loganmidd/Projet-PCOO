package com.github.loganmidd.tiled;

import java.util.HashSet;

import com.github.loganmidd.entity.Crystal;
import com.github.loganmidd.utils.EnemyPathVertex;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class TMapEnemyPaths {
    private HashSet<Point> vertices;
    private HashSet<Crystal> crystals;
    private HashSet<EnemyPathVertex> nodes;

    public TMapEnemyPaths() {
        this.vertices = new HashSet<>();
        this.crystals = new HashSet<>();
        this.vertices.addAll(World.getWorld().getTMap().getEnemyPathPoints());
        for (Point crystalPoint : World.getWorld().getTMap().getCrystalSpawnPoints()) {
            this.crystals.add(new Crystal(crystalPoint));
        }
        this.calculate();

    }

    private void calculate() {
        // First, add crystals.
        this.nodes = new HashSet<>();
        HashSet<Point> points = new HashSet<>(); // Points of placed points
        for (Crystal crystal : this.crystals) {
            this.nodes.add(new EnemyPathVertex(crystal.getCenterPoint(), null, true));
            points.add(crystal.getCenterPoint());
        }
        // This algorithm will build all the paths, point by point
        HashSet<Point> remaining = new HashSet<>();
        remaining.addAll(this.vertices);
        while (remaining.size() > 0) {
            // Find best next vertex
            
            Point best = remaining.iterator().next();
            EnemyPathVertex bestNextVertex = null;
            float bestDistance = Float.POSITIVE_INFINITY;

            for (Point candidate : remaining) {
                float closestDistance = Float.POSITIVE_INFINITY;
                Point closest = candidate;
                EnemyPathVertex closestNextVertex = null;
                for (EnemyPathVertex testVertex : this.nodes) {
                    float testDistance = candidate.distanceTo(testVertex.getPoint());
                    if (testDistance < closestDistance) {
                        closestDistance = testDistance;
                        closestNextVertex = testVertex;
                        closest = candidate;
                    }
                }
                if (closestDistance < bestDistance) {
                    best = closest;
                    bestDistance = closestDistance;
                    bestNextVertex = closestNextVertex;
                }
            }
            
            this.nodes.add(new EnemyPathVertex(best, bestNextVertex, false));
            remaining.remove(best);
            points.add(best);
        
        }
    }

    public HashSet<EnemyPathVertex> getNodes() {
        return this.nodes;
    }

    public EnemyPathVertex getClosestVertex(Point point) {
        float bestDistance = Float.POSITIVE_INFINITY;
        EnemyPathVertex best = null;
        for (EnemyPathVertex vertex : this.nodes) {
            float testDistance = point.distanceTo(vertex.getPoint());
            if (testDistance < bestDistance) {
                best = vertex;
                bestDistance = testDistance;
            }
        }
        // To avoid backtracking. If the angle between the entity, closest point and point after the
        // closest point is acute, go directly the next point.
        if (best.getNext() != null) {
            Point vPoint = best.getPoint();
            Point nextPoint = best.getNext().getPoint();
            Point p1 = new Point(vPoint.getX() - point.getX(), vPoint.getY() - point.getY());
            Point p2 = new Point(nextPoint.getX() - vPoint.getX(), nextPoint.getY() - vPoint.getY());
            if (p1.scalarProduct(p2) < 0) {
                best = best.getNext();
            }
        }
        return best;
    }
}
