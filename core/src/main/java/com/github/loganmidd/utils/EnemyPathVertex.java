package com.github.loganmidd.utils;

import com.github.loganmidd.entity.Combattant;

/**
 * Represents a single point in an enemy's path. A vertex may contain a point in
 * space, a reference to the next vertex in the path, a flag indicating if it is
 * a crystal point, and a specific target entity to interact with.
 *
 * @author Logan Middendorf
 */
public class EnemyPathVertex {
    private Point point;
    private EnemyPathVertex next;
    private boolean isCrystal;
    private Combattant target;

    /**
     * Constructs a new EnemyPathVertex with the specified point, next vertex, and
     * crystal status.
     *
     * @param p         the coordinate point for this vertex
     * @param next      the next vertex in the path sequence
     * @param isCrystal true if this vertex represents a crystal location
     */
    public EnemyPathVertex(Point p, EnemyPathVertex next, boolean isCrystal) {
        this.point = p;
        this.next = next;
        this.isCrystal = isCrystal;
        this.target = null;
    }

    /**
     * Constructs a new EnemyPathVertex with the specified point and next vertex,
     * defaulting to not being a crystal.
     *
     * @param p    the coordinate point for this vertex
     * @param next the next vertex in the path sequence
     */
    public EnemyPathVertex(Point p, EnemyPathVertex next) {
        this(p, next, false); // Not a crystal by default
    }

    /**
     * Constructs a new EnemyPathVertex with only a point, defaulting to no next
     * vertex and not being a crystal.
     *
     * @param p the coordinate point for this vertex
     */
    public EnemyPathVertex(Point p) {
        this(p, null, false); // Allow initialization without next vertex
    }

    /**
     * Checks if this vertex represents an entity node. An entity node is identified
     * by having no subsequent vertex in the path.
     *
     * @return true if this is the last node in the path (next is null)
     */
    public boolean isEntityNode() {
        return this.next == null;
    }

    /**
     * Retrieves the coordinate point associated with this vertex.
     *
     * @return the Point object for this vertex
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Sets the coordinate point for this vertex.
     *
     * @param point the Point object to set
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * Retrieves the next vertex in the path sequence.
     *
     * @return the next EnemyPathVertex, or null if this is the end of the path
     */
    public EnemyPathVertex getNext() {
        return next;
    }

    /**
     * Sets the next vertex in the path sequence.
     *
     * @param next the EnemyPathVertex to set as the next node
     */
    public void setNext(EnemyPathVertex next) {
        this.next = next;
    }

    /**
     * Checks if this vertex represents a crystal location.
     *
     * @return true if this vertex is a crystal node
     */
    public boolean isCrystal() {
        return isCrystal;
    }

    /**
     * Sets whether this vertex represents a crystal location.
     *
     * @param isCrystal true to mark this vertex as a crystal node
     */
    public void setCrystal(boolean isCrystal) {
        this.isCrystal = isCrystal;
    }

    /**
     * Sets the target combatant associated with this vertex.
     *
     * @param combattant the Combattant to set as the target
     */
    public void setTargetCombattant(Combattant combattant) {
        this.target = combattant;
    }

    /**
     * Retrieves the target combatant associated with this vertex.
     *
     * @return the target Combattant, or null if no target is set
     */
    public Combattant getTargetCombattant() {
        return this.target;
    }

}