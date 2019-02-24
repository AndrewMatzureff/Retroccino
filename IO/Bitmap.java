package IO;

import java.awt.Color;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Rectangle2D;
import Graphics.SpriteSheet;

public class Bitmap
{
    public static int[] dummy()
    {
        int[] pixels = new int[64 * 64];
        java.util.Arrays.fill(pixels, 255 << 24);
        return pixels;
    }
    public static int[][] read(String file) throws IOException{
        return read(file, -1);
    }
    public static int[][] read(String file, int transparency) throws IOException{
        return read(file, transparency, 0);
    }
    public static int[][] read(String file, int transparency, int threshold) throws IOException
    {
        BufferedImage image = formatARGB(ImageIO.read(new File(file)));
        if((transparency & 0xff000000) != 0xff000000)
        {return new int[][]{{image.getWidth()}, processTransparency(image, transparency, threshold)};}
        else
        {return new int[][]{{image.getWidth()}, processImage(image)};}
    }
    public static int[][] read(String file, int transparency, int threshold, int fWidth, int fHeight) throws IOException
    {
        BufferedImage image = formatARGB(ImageIO.read(new File(file)).getScaledInstance(fWidth,fHeight,java.awt.Image.SCALE_FAST));
        if((transparency & 0xff000000) != 0xff000000)
        {return new int[][]{{image.getWidth()}, processTransparency(image, transparency, threshold)};}
        else
        {return new int[][]{{image.getWidth()}, processImage(image)};}
    }
    private static BufferedImage formatARGB(Image image)
    {
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        java.awt.Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
    private static int[] processImage(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        return pixels;
    }
    private static int[] processTransparency(BufferedImage image, int transparency, int threshold)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        int a = 0xff000000;
        int r = 0x00ff0000;
        int g = 0x0000ff00;
        int b = 0x000000ff;
        int rgb = r | g | b;
        for(int i = 0; i < pixels.length; i++)
        {            
            pixels[i] = (pixels[i] & rgb) | lerp(((transparency & r) - (pixels[i] & r)) >> 16, threshold, transparency & a);
            pixels[i] = (pixels[i] & rgb) | lerp(((transparency & g) - (pixels[i] & g)) >> 8, threshold, transparency & a);
            pixels[i] = (pixels[i] & rgb) | lerp(((transparency & b) - (pixels[i] & b)), threshold, transparency & a);
        }
        return pixels;
    }
    public static int lerp(float dif, float threshold, float alpha){
        if(dif > threshold)
            return 255;
        else if(threshold == 0)
            return dif == threshold? (int)alpha: 255;
        float m = dif / threshold;
        return (int)(alpha + (254 - alpha) * m);
    }
}