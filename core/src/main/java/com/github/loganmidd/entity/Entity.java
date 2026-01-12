package com.github.loganmidd.entity;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.utils.TextureRenderer;
import com.github.loganmidd.world.World;

/**
 * Represents an entity in the game world.
 * <p>
 * This class serves as the base for all dynamic objects, handling position, movement,
 * collision detection, and rendering responsibilities.
 * </p>
 *
 * @author Logan Middendorf
 */
public abstract class Entity {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float width;
    private float height;
    private float collisionHeightFactor;
    private TextureRenderer renderer;
    private boolean hasCollision;
    private float slowDownFactor;
    private boolean isDisposed;
    private boolean isMovable;
    
    /**
     * Constructs an Entity at the specified coordinates.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Entity(float x, float y) {
        this.width = 50f;  // Arbitrary but
        this.height = 50f; // default value
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.collisionHeightFactor = 1f/3f;
        this.hasCollision = true;    // Default
        this.slowDownFactor = 0.05f; //  ''
        this.isMovable = true;
    }

    /**
     * Constructs an Entity at the location specified by a Point.
     *
     * @param p The point representing the initial position.
     */
    public Entity(Point p) {
        this(p.getX(), p.getY());
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    /**
     * Returns the x-coordinate of the entity.
     *
     * @return The x position.
     */
    public float getX()      { return x; }

    /**
     * Returns the y-coordinate of the entity.
     *
     * @return The y position.
     */
    public float getY()      { return y; }

    /**
     * Returns the x-axis velocity of the entity.
     *
     * @return The delta x value.
     */
    public float getDx()     { return dx; }

    /**
     * Returns the y-axis velocity of the entity.
     *
     * @return The delta y value.
     */
    public float getDy()     { return dy; } 

    /**
     * Returns the width of the entity.
     *
     * @return The width.
     */
    public float getWidth()  { return this.width; }

    /**
     * Returns the height of the entity.
     *
     * @return The height.
     */
    public float getHeight() { return this.height; }

    /**
     * Checks if the entity participates in collision detection.
     *
     * @return True if collision is enabled, false otherwise.
     */
    public boolean hasCollision()    { return this.hasCollision; }

    /**
     * Returns the factor by which velocity decreases each tick.
     *
     * @return The slow down factor.
     */
    public float getSlowDownFactor() { return this.slowDownFactor; }

    /**
     * Returns the file path to the texture used by this entity.
     *
     * @return The texture path string.
     */
    public abstract String getTexturePath();
    
    /**
     * Calculates the center x-coordinate of the entity.
     *
     * @return The x-coordinate of the center.
     */
    public float getCenterX() { return this.x + (this.width /2); }

    /**
     * Calculates the center y-coordinate of the entity.
     *
     * @return The y-coordinate of the center.
     */
    public float getCenterY() { return this.y + (this.height/2); }

    /**
     * Returns a Point object representing the entity's top-left position.
     *
     * @return A new Point at the entity's position.
     */
    public Point getPoint()   { return new Point(this.x, this.y); }

    /**
     * Returns a Point object representing the entity's geometric center.
     *
     * @return A new Point at the entity's center.
     */
    public Point getCenterPoint() { return new Point(this.getCenterX(), this.getCenterY()); }

    /**
     * Returns the current hitbox of the entity.
     *
     * @return A Rectangle representing the current collision area.
     */
    public Rectangle getHitbox() { return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()); }

    /**
     * Returns the projected hitbox of the entity based on its current velocity.
     *
     * @return A Rectangle representing the potential future collision area.
     */
    public Rectangle getNextHitbox() { return new Rectangle(this.x - Math.abs(this.dx), this.y - Math.abs(this.dy), this.getWidth() + Math.abs(this.dx), this.getHeight() + Math.abs(this.dy)); }

    /**
     * Checks if the entity has been disposed.
     *
     * @return True if disposed, false otherwise.
     */
    public boolean isDisposed() { return this.isDisposed; }

    /**
     * Checks if the entity is movable.
     *
     * @return True if movable, false otherwise.
     */
    public boolean isMovable() { return this.isMovable; }

    /**
     * Checks if the entity is a combattant.
     *
     * @return Always returns false for the base class.
     */
    public boolean isCombattant() { return false; }

    /**
     * Checks if the entity is the player.
     *
     * @return Always returns false for the base class.
     */
    public boolean isPlayer() { return false; }

    /**
     * Checks if the entity is a tower.
     *
     * @return Always returns false for the base class.
     */
    public boolean isTower() { return false; }

    /**
     * Checks if the entity is an enemy.
     *
     * @return Always returns false for the base class.
     */
    public boolean isEnemy() { return false;}

    /**
     * Checks if the entity is a crystal.
     *
     * @return Always returns false for the base class.
     */
    public boolean isCrystal() { return false; }

    /**
     * Returns the TextureRenderer used to draw this entity.
     * <p>
     * Lazily initializes the renderer if it does not exist.
     * </p>
     *
     * @return The TextureRenderer instance.
     */
    public TextureRenderer getTextureRenderer() { 
        if (this.renderer == null) {
            TextureRenderer t = new TextureRenderer(World.getWorld().getSpriteBatch());
            t.setPath(this.getTexturePath());
            this.setRenderer(t);
        }         
        return this.renderer;
    }

    /**
     * Sets the x-coordinate of the entity.
     *
     * @param x The new x position.
     */
    public void setX(float x)   { this.x = x; }

    /**
     * Sets the y-coordinate of the entity.
     *
     * @param y The new y position.
     */
    public void setY(float y)   { this.y = y; }

    /**
     * Sets the x-axis velocity of the entity.
     *
     * @param dx The new delta x.
     */
    public void setDx(float dx) { this.dx = dx; }

    /**
     * Sets the y-axis velocity of the entity.
     *
     * @param dy The new delta y.
     */
    public void setDy(float dy) { this.dy = dy; }

    /**
     * Enables or disables collision detection for this entity.
     *
     * @param collision True to enable collision, false to disable.
     */
    public void setCollision(boolean collision) { this.hasCollision = collision; }

    /**
     * Sets the slow down factor for movement.
     *
     * @param factor The new slow down factor.
     */
    public void setSlowDownFactor(float factor) { this.slowDownFactor = factor;  }

    /**
     * Centers the entity horizontally at the specified x-coordinate.
     *
     * @param x The x-coordinate to center on.
     */
    public void setCenterX(float x) { this.x = x - this.width /2f; }

    /**
     * Centers the entity vertically at the specified y-coordinate.
     *
     * @param y The y-coordinate to center on.
     */
    public void setCenterY(float y) { this.y = y - this.height/2f; }

    /**
     * Sets the movable status of the entity.
     *
     * @param isMovable True to make the entity movable, false otherwise.
     */
    public void setMovable(boolean isMovable) { this.isMovable = isMovable; }
    
    /**
     * Sets the width of the entity and updates the renderer if present.
     *
     * @param width The new width.
     */
    public void setWidth(float width)   { 
        this.width = width; 
        if (this.renderer != null) {
            this.renderer.setWidth((int) width);
        }
    }

    /**
     * Sets the height of the entity and updates the renderer if present.
     *
     * @param height The new height.
     */
    public void setHeight(float height) { 
        this.height = height; 
        if (this.renderer != null) {
            this.renderer.setHeight((int) height);
        }
    }

    /**
     * Sets the TextureRenderer for this entity and syncs its dimensions.
     *
     * @param textureRenderer The renderer to use.
     */
    public void setRenderer(TextureRenderer textureRenderer) {
        this.renderer = textureRenderer;
        this.renderer.setHeight((int) this.getHeight());
        this.renderer.setWidth((int) this.getWidth());
    }

    /**
     * Adds value to the x-axis velocity.
     *
     * @param dx The amount to add to dx.
     */
    public void addDx(float dx) { this.dx += dx; }

    /**
     * Adds value to the y-axis velocity.
     *
     * @param dy The amount to add to dy.
     */
    public void addDy(float dy) { this.dy += dy; }

///////////////////////////////////////////////////////////
///                       Logic                         ///
///////////////////////////////////////////////////////////

    /**
     * Executes the entity's logic for the current tick.
     * <p>
     * Handles collision detection with blocks and other entities, calculates repulsion forces,
     * resolves physical collisions, and updates position based on velocity.
     * </p>
     */
    public void logic() {
        if (this.isDisposed) {
            return;
        }
        // In case of collision with Block
        List<Block> blocks = this.blockCollision(World.getWorld().getBlocks());

        List<Block> unmovableEntityBlocks = new ArrayList<>();
        for (Entity entity : World.getWorld().getEntities()) {
            if (!entity.isMovable() && entity != this) {
                unmovableEntityBlocks.add(new Block(entity.getHitbox()));
            }
        }

        blocks.addAll(this.blockCollision(unmovableEntityBlocks));
        
        List<Block> entities = new ArrayList<>();
        if (blocks.isEmpty() && this.isMovable) {
            for (Entity e : World.getWorld().getEntities()) {
                if (e != this && e.hasCollision() && e.isEnemy()) {
                    Block block = new Block(e.getNextHitbox());
                    block.setTrueCollision(true);
                    entities.add(block);
                }
            }
       }
        List<Block> entityBlocks = this.blockCollision(entities);
        // Here we will just "push" the incoming entites away
        // To do this we will use the distance between the centers of 
        // the ellipses covering the entity hitbox
        // For ellipse collision calculations
        float a = this.width/2f;
        float b = this.height/2f;
        float xc = this.getCenterX();
        float yc = this.getCenterY();

        for (Block block : entityBlocks) {
            float aEnt = block.getLength()/2f;
            float bEnt = block.getHeight()/2f;
            float xcEnt = block.getX() + aEnt;
            float ycEnt = block.getY() + bEnt;

            List<Float> dxChange = new ArrayList<>();
            List<Float> dyChange = new ArrayList<>();
            // We will test point by point, with a small step
            // To check if the ellipses overlap
            // An ellipse can be parametrised as:
            // | x(t) = xc + acos(t)
            // | y(t) = yc + bsin(t)
            // | 0 <= t < 2PI
            // Where (xc, yc) is the center of the ellipse,
            // and if the origin were moved to (xc, yc),
            // the equation of the ellipse would become (x/a)^2 + (y/b)^2 = 1

            float step = 0.01f;
            for (float t=0f; t<2*Math.PI; t += step) {
                float x = (float) (xc + 2*a*Math.cos(t));
                float y = (float) (yc + 2*b*Math.sin(t));
                if (Math.pow((x - xcEnt) / aEnt, 2f) + Math.pow((y - ycEnt) / bEnt, 2f) <= 1) {
                    // If the numbers happen to collide, just change it a small bit
                    if (x == xcEnt) {
                        x += 0.01;
                    }
                     if (y == ycEnt) {
                        y += 0.01;
                    }
                    dxChange.add(xcEnt-x);
                    dyChange.add(ycEnt-y);

                }
            }
            // In case entities stack
            if (Math.pow((xc - xcEnt)/a, 2f) + Math.pow((yc - ycEnt)/b, 2f) <= 1) {
                dxChange.add((float) Math.cos(this.hashCode())); // Effectively random
                dyChange.add((float) Math.sin(this.hashCode()));
            }
            
            if (dxChange.size() != 0) {
                // Get average of dxChange and dyChange and add it
                float xAvg = 0f;
                float yAvg = 0f;
                for (int i=0; i<dxChange.size(); i++) {
                    xAvg += dxChange.get(i);
                    yAvg += dyChange.get(i);
                }    

                float distance = this.getCenterPoint().distance2To(new Point(xcEnt, ycEnt));
                
                float multiplier = 500f/(distance + 1f);
                //float sign = () / (); // V1 = ()
                this.addDx(multiplier*xAvg/dxChange.size());
                this.addDy(multiplier*yAvg/dyChange.size());
                if (!this.blockCollision(World.getWorld().getBlocks()).isEmpty()) {
                    
                    this.addDx(-multiplier*xAvg/dxChange.size());
                    this.addDy(-multiplier*yAvg/dyChange.size());

                }
            }  
        }
        
        int tries = 0;
        int maxTries = 100;
        while (!blocks.isEmpty() && tries++ < maxTries) {
            Block block = blocks.get(0);
            float blockX = block.getX();
            float blockY = block.getY();
            float blockH = block.getHeight();
            float blockL = block.getLength();
            float collisionHeight = this.getCollisionHeightForBlock(block);
            // Collision on the side
            if (blockY - collisionHeight < this.y && this.y < blockY + blockH) {
                
                // If the entity if moving to the right
                if (this.dx > 0) {
                    this.x = blockX - this.width;
                }
                // To the left
                else if (this.dx < 0) {
                    this.x = blockX + blockL;
                }
                this.dx = 0;
            }
            // Else, the collision is on the top or bottom
            else {
                // If the entity is moving downwards
                if (this.dy < 0 ) {
                    this.y = blockY + blockH;
                } 
                // The entity is moving upwards 
                else {
                    this.y = blockY - collisionHeight;
                }
                this.dy = 0;
            }
            blocks = this.blockCollision(World.getWorld().getBlocks());
        }
        
        if (tries == maxTries) {
           //throw new ExceptionInInitializerError("infinite while loop");
        }

        if (this.dx > 1) {
            this.getTextureRenderer().setFlipX(false);
        } else if (this.dx < -1) {
            this.getTextureRenderer().setFlipX(true);;
        }

        // Movement
        this.x += this.dx;
        this.y += this.dy;
        this.dx *= this.slowDownFactor;
        this.dy *= this.slowDownFactor;
    }

    /**
     * Checks for collisions between this entity and a list of blocks.
     *
     * @param blocks The list of blocks to check against.
     * @return A list of blocks that are colliding with this entity.
     */
    public List<Block> blockCollision(List<Block> blocks) {
        List<Block> collisions = new ArrayList<>();

        if (!this.hasCollision) {
            return collisions; // If the Entity does not have collision.
        }

        Rectangle rec = new Rectangle(this.x + this.dx, this.y + this.dy, this.width, 0);
        
        for (Block block : blocks) {
            // To use Rectangle.overlaps() method from LibGDX   
            float collisionHeight = this.getCollisionHeightForBlock(block);
            rec.setHeight(collisionHeight);
            float blockX = block.getX();
            float blockY = block.getY();
            float blockWidth = block.getLength();
            float blockHeight = block.getHeight();
            Rectangle blockRectangle = new Rectangle(blockX, blockY, blockWidth, blockHeight);

            if (rec.overlaps(blockRectangle)) {
                collisions.add(block);
            }

        }
        return collisions;
    }

    /**
     * Determines the effective collision height for a specific block.
     * <p>
     * Allows for partial collision (e.g., for terrain) if the block does not have true collision.
     * </p>
     *
     * @param block The block to check.
     * @return The height to use for collision detection.
     */
    public float getCollisionHeightForBlock(Block block) {
        if (block.hasTrueCollision()) {
            return this.height;
        } else {
            return this.height * this.collisionHeightFactor;
        }
    }
    
    /**
     * Marks the entity as disposed.
     * <p>
     * A disposed entity will be skipped in logic updates and rendering.
     * </p>
     */
    public void dispose() {
        this.isDisposed = true;
    } 

    /**
     * Checks if this entity overlaps with another entity.
     *
     * @param other The other entity to check.
     * @return True if hitboxes overlap and neither is disposed, false otherwise.
     */
    public boolean collidesWith(Entity other) {
        return other.getHitbox().overlaps(this.getHitbox()) && !this.isDisposed;
    }

    /**
     * Renders the entity using its TextureRenderer.
     * <p>
     * Does nothing if the entity is disposed.
     * </p>
     */
    public void render() {
        if (!this.isDisposed) {
            this.getTextureRenderer().renderAt(x, y);
        }
    }


}