package Graphics;



import IO.Bitmap;
import java.io.IOException;
import Constants.C;

/**
 * Write a description of class SpriteSheet here.
 * 
 * @author (Andrew Matzureff) 
 * @version (11/12/2016)
 */
public class SpriteSheet
{
    //non-static class since different SpriteSheets may have different characteristics
    //such as transparency and tile sizes
    final int[] pixels;
    final int scansize;
    final int tilesize;
    final int format;
    public static SpriteSheet create(String file, int tilesize, int format)
    {
        try{
            int[] pixels = Bitmap.read(file);
            int scansize = Bitmap.getScanSize();
            if(scansize < tilesize || pixels.length / scansize < tilesize)//dimensions of sprite(s) in sheet cannot exceed either sheet dimension
                return null;
            else
            {
                int[] clipped = clip(pixels, scansize, tilesize);//clips sheet to area of highest multiple of tilesize along each dimension
                scansize = pixels[0];//retrieve stored scansize
                pixels = null;
                return new SpriteSheet(clipped, scansize, tilesize, format);
            }
        }catch(IOException ioe){return new SpriteSheet(new int[tilesize * tilesize], tilesize, tilesize, format);}
    }
    public static SpriteSheet create(String file, int tilesize, int format, int transparency, int threshold)//gotta fix this trash
    {
            Bitmap.TRANSPARENCY = transparency;
            Bitmap.THRESHOLD = threshold;
            return create(file, tilesize, format);
    }
    public int tiles(){return pixels.length / (tilesize * tilesize);}
    private SpriteSheet(int[] pixels, int scansize, int tilesize, int format)
    {
        this.tilesize = tilesize;
        this.scansize = scansize;
        this.format = format;
        this.pixels = pixels;
    }
    /**
     * Clips the specified 1D pixel array to an upper-left
     * rectangular region tilesize * tilesize * h * w long,
     * where w & h are the number of whole tiles able to fit
     * within the original area along the horizontal and
     * vertical dimensions of the sheet respectively. The
     * algorithm used in this method should work with
     * arbitrary sheet sizes and clip regions as long as
     * tilesize is guarenteed to be the same along both axes
     * which, in this case, is the only possibility. Only
     * slight modification is necessary to facilitate the
     * use of separate tile dimensions as the only place in
     * the algorithm at which the vertical tile dimension is
     * referenced is in determining the length of the clipped
     * array. Though, this has not been tested.
     */
    private static int[] clip(int[] pixels, int scansize, int tilesize)
    {
        //By this point pixels should already be guarenteed to be a minimum of tilesize entries long.
        int height = pixels.length / scansize;
        int clipx = (scansize / tilesize) * tilesize, clipy = (height / tilesize) * tilesize; //number of pixels along each dimension (as a multiple of tilesize)
        int[] clipped = new int[clipx * clipy];
        for(int y = 0; y < clipy; y++)//rectangular clipping algorithm in one line (a few additional lines required for offset clipping)
        {
            int cy = y * clipx;
            int sy = y * scansize;
            for(int x = 0; x < clipx; x++)
            {
                clipped[x + cy] = pixels[x + sy];
            }
        }
        pixels[0] = clipx;//pack the new scansize into pixels so it can be retrieved after this method exits
        return clipped;
    }
}