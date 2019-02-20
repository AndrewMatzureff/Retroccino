package Behavior;
import Graphics.Sprite;
import Graphics.Render;


/**
 * Write a description of class Entity here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/15/2019)
 */
public class Entity
{
    private static int COUNT = 0;
    private float x = 0, y = 0, z = 0;
    private int transform = 0;
    private boolean queued = false;
    private final int id;
    private Sprite sprite;
    public Entity(){//2/16/2019-TODO: make private, enforce factory create() method to obtain pooled object when available
        id = COUNT++;
    }
    public void setX(float n){x = n;}
    public void setY(float n){y = n;}
    public void setZ(float n){z = n;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getZ(){return z;}
    public void setSprite(Sprite s){sprite = s;}
    public Sprite getSprite(){return sprite;}
    //public void setQueued(boolean q){queued = q;}
    public boolean isQueued(){return queued;}
    public void setTransform(int t){transform = t;}
    public int getTransform(){return transform;}
    public void tick(double delta){
        if(!isQueued()){
            Render.add(this);
            queued = true;
        }
        x = (x + 1) % 320;
        y = (y + 1) % 200;
    }
}
