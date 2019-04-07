package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import Input.Mouse;
import Input.Keyboard;
import Graphics.Screen;
import Graphics.Sprite;
import Graphics.Render;
/**
 * Write a description of class Kernel here.
 * 
 * @author (Andrew Matzureff) 
 * @version (10/22/2018)
 */
public class Kernel extends Canvas implements Runnable
{
    public static final GraphicsConfiguration G_CONFIG = initGraphics();
    public static final double SECONDS = 1 / 1_000_000_000d;
    private JFrame frame;
    private Keyboard keyboard;
    private Mouse mouse;
    private boolean running;
    //BufferedImage image;
    //int[] pixels;
    Screen screen;
    Thread renderer;
    public Kernel(String title){
        //Rolled steel for making boilers...
        super(G_CONFIG);
        init(320, 200);
        frame = new JFrame(title, G_CONFIG);
        setIgnoreRepaint(true);
        keyboard = new Keyboard();
        addKeyListener(keyboard);
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        createBufferStrategy(3);
        running = true;
    }
    public Mouse getMouse(){
        return mouse;
    }
    public Keyboard getKeyboard(){
        return keyboard;
    }
    public void init(int w, int h){
        screen = Screen.create(w, h);
        if(screen == null)
            running = false;
    }
    private static GraphicsConfiguration initGraphics(){//NOTE: 2/6/19 - support multi monitor environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        return gc;
    }
    public boolean isRunning(){return running;}
    public void setRunning(boolean running){this.running = running;}
    static Sprite TEST = Sprite.random(128, 10);
    public static int TOTAL = 0;
    public static double TIME = 0;
    public static int frames = 0;
    public void refresh(){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        randomRect();
        double start = System.nanoTime();
        Render.refresh(screen);
        TIME += System.nanoTime() - start;
        TOTAL++;
        g.drawImage(screen.refresh(Screen.BLEND | 0xffffffffL & frames | (0xff << 24 & 0xff000000)), 0, 0, getWidth(), getHeight(), null);
        //screen.clear(0x10ff0000);//0x00ffffff & frames);
        nextColor(5);
        g.dispose();
        bs.show();
    }
    public void nextColor(double step){
        int r = (frames & 0x00ff0000) >> 16;
        int g = (frames & 0x0000ff00) >> 8;
        int b = frames & 0x000000ff;
        frames = ((int)(r + Math.random() * step) << 16) | ((int)(g + Math.random() * step) << 8) | ((int)(b + Math.random() * step));
    }
    public void randomRect(){
        int x = (int)(Math.random() * screen.getWidth());
        int y = (int)(Math.random() * screen.getHeight());
        int w = (int)(Math.random() * screen.getWidth() * 0.1);
        int h = (int)(Math.random() * screen.getHeight() * 0.1);
        
        int t = TEST.tilesize;
        //try{
        //screen.drawSprite(TEST, x - t / 2, y - t / 2, 0);
        screen.drawRect(x - w / 2, y - h / 2, w, h, (int)System.nanoTime());//}
        //catch(ArrayIndexOutOfBoundsException e){System.out.printf("%d %d %d %d\n", x - w / 2, y - h / 2, w, h);}
        TEST.noise(1);
    }
   public void run(){//render
        long oneSecond = 1_000_000_000;
        long startTimer = System.nanoTime();
        double seconds = SECONDS;

        long previousTime = System.nanoTime();
        long currentTime;
        int updates = 0;
        double accumulator = 0;
        double targetDelta = 1 / 60d;
                //System.out.println("render");
        while (running) {//1/26/2019-NOTE: delegate render condition to auxiliary variable
            BufferStrategy bs = this.getBufferStrategy();
            //setRunning(!keyboard.alerted());
            currentTime = System.nanoTime();
            long frameTime = currentTime - previousTime;
            previousTime = currentTime;
            accumulator += frameTime * SECONDS;
            do{
                //while (accumulator >= targetDelta){
                    accumulator -= targetDelta;
                    do
                       refresh();
                    while(bs.contentsRestored());
                    updates++;
    
                
                    //refresh();
                //}
            }while(bs.contentsLost());
            if(System.nanoTime() - startTimer >= oneSecond){
                //System.out.println(updates + " frames in " + ((System.nanoTime() - startTimer) * seconds) + " seconds...");
                updates = 0;
                startTimer = System.nanoTime(); 
            }
        }
        System.out.println("Rendering ended.");
        
        IO.Log.append("Double Division: " + (TIME / TOTAL));
    }
    
    public void loop(Game game){
        if(renderer != null)
            return;
        long previousTime = System.nanoTime();
        long currentTime;
        int updates = 0;
        double accumulator = 0;
        double targetDelta = 1 / 60d;
        renderer = new Thread(this, "Renderer");
        renderer.start();
        while (running) {
            requestFocus();//frame must have focus for input...
                //System.out.print(running + " -> ");
            //running = (!keyboard.alerted());
                //System.out.println(running);
            currentTime = System.nanoTime();
            long frameTime = currentTime - previousTime;
            previousTime = currentTime;
            accumulator += frameTime * SECONDS;
            //FTS//
            while (accumulator >= targetDelta){
                //FTS//
                accumulator -= targetDelta;
                
                //FTS//
                updates++;
                Input.Command.update((float)targetDelta);
                boolean quit = game.update(targetDelta);
                if(quit){running = false; break;}
                
                //testRender("" + updates);
                  //  refresh();
            }
        }
        System.out.println("Loop ended.");
    }
}
