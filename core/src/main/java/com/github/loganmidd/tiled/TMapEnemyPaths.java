package com.github.loganmidd.tiled;

import java.util.HashSet;

import com.github.loganmidd.entity.Crystal;
import com.github.loganmidd.utils.EnemyPathVertex;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

/**
 * Manages the creation and storage of enemy path nodes based on the world map configuration.
 * <p>
 * This class calculates a navigation graph for enemies by connecting crystal spawn points
 * to the set of designated enemy path points. It builds a linked structure of {@link EnemyPathVertex}
 * objects, allowing enemies to navigate from crystals to various points on the map.
 */
public class TMapEnemyPaths {
    /** The set of all path points defined in the map. */
    private HashSet<Point> vertices;
    /** The set of crystal objects used as path sources. */
    private HashSet<Crystal> crystals;
    /** The calculated set of navigation nodes connecting crystals and path points. */
    private HashSet<EnemyPathVertex> nodes;

    /**
     * Constructs a new path manager and calculates the navigation graph.
     * <p>
     * Initializes the path vertices from the world map and creates crystals
     * at their spawn points. Then, it triggers the calculation of the node graph.
     */
    public TMapEnemyPaths() {
        this.vertices = new HashSet<>();
        this.crystals = new HashSet<>();
        this.vertices.addAll(World.getWorld().getTMap().getEnemyPathPoints());
        for (Point crystalPoint : World.getWorld().getTMap().getCrystalSpawnPoints()) {
            this.crystals.add(new Crystal(crystalPoint));
        }
        this.calculate();

    }

    /**
     * Calculates the enemy navigation graph.
     * <p>
     * The algorithm proceeds in two phases:
     * <ol>
     * <li>Initializes nodes with crystal locations.</li>
     * <li>Iteratively adds remaining path points to the graph by finding the
     * nearest existing node.</li>
     * </ol>
     * This builds a set of connected vertices where each new point connects to its
     * closest existing neighbor.
     */
    private void calculate() {
        // First, add crystals.
        this.nodes = new HashSet<>();
        HashSet<Point> points = new HashSet<>(); // Points of placed points
        for (Crystal crystal : this.crystals) {
            EnemyPathVertex vertex = new EnemyPathVertex(crystal.getCenterPoint(), null, true);
            vertex.setTargetCombattant(crystal);
            this.nodes.add(vertex);
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

    /**
     * Retrieves the calculated set of navigation nodes.
     *
     * @return The set of {@link EnemyPathVertex} objects representing the navigation graph.
     */
    public HashSet<EnemyPathVertex> getNodes() {
        return this.nodes;
    }

    /**
     * Finds the closest navigation vertex to a given point.
     * <p>
     * If the closest vertex has a next node, and the angle between the target point,
     * the vertex, and the next vertex is acute (indicating the entity has passed the vertex),
     * this method returns the next vertex to avoid backtracking.
     *
     * @param point The point from which to find the closest vertex.
     * @return The optimal closest {@link EnemyPathVertex} to navigate towards.
     */
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