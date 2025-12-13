package com.github.loganmidd.entity;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.utils.TextureRenderer;
import com.github.loganmidd.world.World;

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

    public Entity(Point p) {
        this(p.getX(), p.getY());
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    public float getX()      { return x; }
    public float getY()      { return y; }
    public float getDx()     { return dx; }
    public float getDy()     { return dy; } 
    public float getWidth()  { return this.width; }
    public float getHeight() { return this.height; }
    public TextureRenderer getTextureRenderer() { return this.renderer; }
    public boolean hasCollision()    { return this.hasCollision; }
    public float getSlowDownFactor() { return this.slowDownFactor; }
    public abstract String getTexturePath();
    public abstract boolean isEnemy();
    public float getCenterX() { return this.x + (this.width /2); }
    public float getCenterY() { return this.y + (this.height/2); }
    public Point getPoint()   { return new Point(this.x, this.y); }
    public Point getCenterPoint() { return new Point(this.getCenterX(), this.getCenterY()); }
    public Rectangle getHitbox() { return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()); }
    public Rectangle getNextHitbox() { return new Rectangle(this.x - Math.abs(this.dx), this.y - Math.abs(this.dy), this.getWidth() + Math.abs(this.dx), this.getHeight() + Math.abs(this.dy)); }
    public boolean isDisposed() { return this.isDisposed; }
    public boolean isMovable() { return this.isMovable; }

    public void setX(float x)   { this.x = x; }
    public void setY(float y)   { this.y = y; }
    public void setDx(float dx) { this.dx = dx; }
    public void setDy(float dy) { this.dy = dy; }
    public void setCollision(boolean collision) { this.hasCollision = collision; }
    public void setSlowDownFactor(float factor) { this.slowDownFactor = factor;  }
    public void setCenterX(float x) { this.x = x - this.width /2f; }
    public void setCenterY(float y) { this.y = y - this.height/2f; }
    public void setMovable(boolean isMovable) { this.isMovable = isMovable; }
    
    public void setWidth(float width)   { 
        this.width = width; 
        if (this.renderer != null) {
            this.renderer.setWidth((int) width);
        }
    }

    public void setHeight(float height) { 
        this.height = height; 
        if (this.renderer != null) {
            this.renderer.setHeight((int) height);
        }
    }

    public void setRenderer(TextureRenderer textureRenderer) {
        this.renderer = textureRenderer;
        this.renderer.setHeight((int) this.getHeight());
        this.renderer.setWidth((int) this.getWidth());
    }

    public void addDx(float dx) { this.dx += dx; }
    public void addDy(float dy) { this.dy += dy; }

///////////////////////////////////////////////////////////
///                       Logic                         ///
///////////////////////////////////////////////////////////

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
            // | 0 <= t <= 2PI
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

    public float getCollisionHeightForBlock(Block block) {
        if (block.hasTrueCollision()) {
            return this.height;
        } else {
            return this.height * this.collisionHeightFactor;
        }
    }
    
    public void dispose() {
        this.isDisposed = true;
    } 

    public boolean collidesWith(Entity other) {
        return other.getHitbox().overlaps(this.getHitbox()) && !this.isDisposed;
    }

    public void render() {
        if (!this.isDisposed) {
            this.renderer.renderAt(x, y);
        }
    }


}
