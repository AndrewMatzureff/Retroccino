package Unit_Tests;


/**
 * Write a description of class BitClip here.
 * 
 * @author (Andrew Matzureff) 
 * @version (1/28/2017)
 */
public class BitClip
{
    static int SIGN_INT = 31;
    public static void main(String[] args)
    {
        int screensize = 320;
        int tilesize = 32;
        int offset = 0;
        
        draw(32, 0, 4);
        /*
        for(int i = -10; i < 11; i++)
        {
            int screen = i & (-i >> SIGN_INT);//equivalent to "if(x < 0)x = 0"
            int sprite = -(i & (i >> SIGN_INT));//equivalent to "if(x < 0)x = -x else x = 0"
            int sprited = i + tilesize - 1;
            sprited = sprited > width? tilesize - (sprited - width): tilesize;
            System.out.println("Screen(" + i + "): " + screenClip(i) + "\tSprite(" + i + "): " + spriteClip(i));
        }//*/
    }
    public static void draw(int screensize, int offset, int tilesize)
    {
        
        for(int i = 0; i < offset; i++)//offset the screen to visualize the overlap
            System.out.print(" ");
        for(int i = 0; i < screensize; i++)//draw screen region
            System.out.print("0");
        System.out.println();
        for(int i = 0; i < -offset; i++)//draw sprite
            System.out.print("1");
            
    }
    public static int screenClip(int n)
    {
        return n & (-n >> SIGN_INT);
    }
    public static int spriteClip(int n)
    {
        return -(n & (n >> SIGN_INT));
    }
}
