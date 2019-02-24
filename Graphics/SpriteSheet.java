package Graphics;



import IO.Bitmap;
import java.io.IOException;

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
    final boolean format;
    public static SpriteSheet create(String file, int tilesize, int transparency, int threshold, boolean format)
    {
        try{
            int[][] data = Bitmap.read(file, transparency, threshold);            
            if(data[0][0] < tilesize || data[1].length / data[0][0] < tilesize)//dimensions of sprite(s) in sheet cannot exceed either sheet dimension
                return null;
            else
            {
                int[][] clipped = clip(data[1], data[0][0], tilesize);//clips sheet to area of highest multiple of tilesize along each dimension
                return new SpriteSheet(clipped[1], clipped[0][0], tilesize, format);
            }
        }catch(IOException ioe){return new SpriteSheet(new int[tilesize * tilesize], tilesize, tilesize, format);}
   }
    public int size(){return pixels.length / (tilesize * tilesize);}
    private SpriteSheet(int[] pixels, int scansize, int tilesize, boolean format)
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
    private static int[][] clip(int[] pixels, int scansize, int tilesize)
    {
        //By this point pixels should already be guarenteed to be a minimum of tilesize entries long.
        int height = pixels.length / scansize;
        int clipx = (scansize / tilesize) * tilesize;
        int clipy = (height / tilesize) * tilesize; //number of pixels along each dimension (as a multiple of tilesize)
        int[][] clipped = new int[][]{{clipx}, new int[clipx * clipy]};
        for(int y = 0; y < clipy; y++)//rectangular clipping algorithm in one line (a few additional lines required for offset clipping)
        {
            int cy = y * clipx;
            int sy = y * scansize;
            for(int x = 0; x < clipx; x++)
            {
                clipped[1][x + cy] = pixels[x + sy];
            }
        }
        return clipped;
    }
    public Sprite get(int x, int y)
    {
        int[] pixels = new int[tilesize * tilesize];
        //int xsprites, ysprites;//, x = 0, y = 0;
        //xsprites = scansize / tilesize;
        //ysprites = this.pixels.length / scansize / tilesize;
        /*switch(format)
        {
            case HORIZONTAL:
                x = i % xsprites * tilesize;//bilinear pixel component, top-left
                y = i / xsprites * tilesize;//bilinear pixel component, top-left
                break;
            case VERTICAL:
                x = i / xsprites * tilesize;//bilinear pixel component, top-left
                y = i % xsprites * tilesize;//bilinear pixel component, top-left
                break;
            default:
                return null;
        }*/
        x *= tilesize;
        y *= tilesize;
        //System.out.println("Sheet Length: " + this.pixels.length);
        for(int r = 0; r < tilesize; r++)
        {
        	int sheetOffset = (r + y) * scansize;
        	int spriteOffset = r * tilesize;// + y;
        	//System.out.println("\n\n\tSource Row: " + sheetOffset + "\tDestination Row: " + spriteOffset);
        	for(int c = 0; c < tilesize; c++)
			{
				//System.out.println("\n\t\tSource Column: " + (c + x) + " = Destination Column: " + (c));
				int source = this.pixels[sheetOffset + c + x];
				pixels[spriteOffset + c] = source;
			}
		}
        return Sprite.create(pixels, tilesize);
    }
}