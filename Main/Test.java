package Main;

import java.util.ArrayList;
import Behavior.Entity;
import Graphics.Sprite;
import Graphics.SpriteSheet;
/**
 * Write a description of class Test here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/1/2019)
 */
public class Test implements Game
{
    Kernel kernel;
    ArrayList<Entity> e;
    //Sprite s;
    SpriteSheet ss;
    public Test(){
        this.kernel = new Kernel("Test");
        e = new ArrayList<Entity>();
    }
    public static void main(String[] args){
        Test test = new Test();
        //test.s = Sprite.random(64, 1);
        //test.a.setSprite(test.s);
        //test.b.setSprite(Sprite.random(64, 1));
        test.ss = SpriteSheet.create("Resources" + IO.Text.FILE_SEPARATOR + "TestSheet.bmp", 128, -1, 0, true);
        //test.a.setSprite(test.ss.get(0, 0));
        for(int i = 0; i < 250; i++){
            test.e.add(new Entity());
            test.e.get(i).setSprite(test.ss.get(0, 0));
        }
        //test.b.setSprite(test.ss.get(1, 1));
        test.kernel.loop(test);
    }
    public boolean update(double delta){
        for(int i = 0; i < e.size(); i++)
        e.get(i).tick(delta);
        //s.noise(5);
        //b.tick(delta);
        return kernel.getKeyboard().alerted();
    }
}
