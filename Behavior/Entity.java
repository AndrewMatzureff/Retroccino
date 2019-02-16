package Behavior;
import Graphics.Sprite;


/**
 * Write a description of class Entity here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/15/2019)
 */
public class Entity
{
    private static int COUNT = 0;
    private float x, y, z;
    private final int id;
    private Sprite sprite;
    public Entity(){//2/16/2019-TODO: make private, enforce factory create() method to obtain pooled object when available
        id = COUNT++;
    }
}
