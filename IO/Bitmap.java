package IO;

import java.awt.Color;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Rectangle2D;
import Constants.C;
import Graphics.SpriteSheet;

public class Bitmap
{
    public static int TRANSPARENCY = 0;//Alpha: replacement alpha, Red/ Green/ Blue: query color
    public static int THRESHOLD = 0;
    private static int SCANSIZE = 0;
    public static int[] dummy()
    {
        SCANSIZE = 64;
        int[] pixels = new int[64 * 64];
        java.util.Arrays.fill(pixels, 255 << 24);
        return pixels;
    }
    public static int getScanSize()
    {
        return SCANSIZE;
    }
    public static int[] read(String file) throws IOException
    {
        BufferedImage image = formatARGB(ImageIO.read(new File(file)));
        SCANSIZE = image.getWidth();
        if(TRANSPARENCY != 0)
        {return processTransparency(image);}
        else
        {return processImage(image);}
    }
    public static int[] read(String file, int fWidth, int fHeight) throws IOException
    {
        BufferedImage image = formatARGB(ImageIO.read(new File(file)).getScaledInstance(fWidth,fHeight,java.awt.Image.SCALE_FAST));
        SCANSIZE = image.getWidth();
        if(TRANSPARENCY != 0)
        {return processTransparency(image);}
        else
        {return processImage(image);}
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
    private static int[] processTransparency(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        int ffffff = 0xFFFFFF;
        int rgb = TRANSPARENCY & ffffff;
        int alpha = TRANSPARENCY & 0xFF000000;
        for(int i = 0; i < pixels.length; i++)
        {
            if((rgb - (pixels[i] & ffffff)) <= THRESHOLD)
            {
                pixels[i] = pixels[i] | alpha;
            }
        }
        return pixels;
    }
}