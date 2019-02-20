package Main;

import Behavior.Entity;
import Graphics.Sprite;
/**
 * Write a description of class Test here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/1/2019)
 */
public class Test implements Game
{
    Kernel kernel;
    Entity a = new Entity();
    Entity b = new Entity();
    Sprite s;
    public Test(){
        this.kernel = new Kernel("Test");
    }
    public static void main(String[] args){
        Test test = new Test();
        test.s = Sprite.random(64, 1);
        test.a.setSprite(test.s);
        //test.b.setSprite(Sprite.random(64, 1));
        test.kernel.loop(test);
    }
    public boolean update(double delta){
        a.tick(delta);
        s.noise(5);
        //b.tick(delta);
        return kernel.getKeyboard().alerted();
    }
}
