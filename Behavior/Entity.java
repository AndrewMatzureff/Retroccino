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
        x = (int)(Math.random() * 100000) - 50000;
        y = (int)(Math.random() * 100000) - 50000;
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
        int r = (int)(Math.sin(System.nanoTime() * 0.00000000000000000000000001) + 1);
        //x = (x + 1320 + r) % 2640 - 1320;//(x + 33) % 352 - 32;//96;
        //y = (y + 1200 + r) % 2400 - 1200;//(y + 33) % 232 - 32;//36;
        x = (x + r) % 192;//(x + 33) % 352 - 32;//96;
        y = (y + r) % 72;//(y + 33) % 232 - 32;//36;
    }
}
