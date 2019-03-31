package Main;

import java.util.ArrayList;
import Behavior.Entity;
import Graphics.Sprite;
import Graphics.SpriteSheet;
import Input.Keyboard;
import Input.Command;
import static java.awt.event.KeyEvent.*;
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
    boolean quit;
    public Test(){
        this.kernel = new Kernel("Test");
        quit = false;
        e = new ArrayList<Entity>();
        Command u = new Command(){
            protected void down(float delta){
                Behavior.Entity.Y -= delta;
            }
        };
        Command d = new Command(){
            protected void down(float delta){
                Behavior.Entity.Y += delta;
            }
        };
        Command l = new Command(){
            protected void down(float delta){
                Behavior.Entity.X -= delta;
            }
        };
        Command r = new Command(){
            protected void down(float delta){
                Behavior.Entity.X += delta;
            }
        };
        Command q = new Command(){
            protected void pressed(float delta){
                quit = true;
            }
        };
        u.bind(kernel.getKeyboard(), VK_UP);
        d.bind(kernel.getKeyboard(), VK_DOWN);
        l.bind(kernel.getKeyboard(), VK_LEFT);
        r.bind(kernel.getKeyboard(), VK_RIGHT);
        q.bind(kernel.getKeyboard(), VK_Q);
    }
    public static void main(String[] args){
        Test test = new Test();
        //test.s = Sprite.random(64, 1);
        //test.a.setSprite(test.s);
        //test.b.setSprite(Sprite.random(64, 1));
        test.ss = SpriteSheet.create("Resources" + IO.Text.FILE_SEPARATOR + "mono32pad.bmp", 32, -1, 0, true);
        //test.a.setSprite(test.ss.get(0, 0));
        for(int i = 0; i < 1; i++){
            test.e.add(new Entity(100, 100));
            test.e.get(i).setSprite(test.ss.get(i % 13, i % 2));
        }
        //test.b.setSprite(test.ss.get(1, 1));
        test.kernel.loop(test);
    }
    public boolean update(double delta){
        //System.out.println(Behavior.Entity.SCALE);
        for(int i = 0; i < e.size(); i++)
        e.get(i).tick(delta);
        //s.noise(5);
        //b.tick(delta);
        return quit;
    }
}
