package Graphics;
/**
 * Write a description of class Sprite here.
 * 
 * @author (Andrew Matzureff) 
 * @version (11/17/2016)
 */
public class Sprite
{
    public static final int UNDEFINED = 0, HORIZONTAL = 1, VERTICAL = 2;
    int[] pixels;
    int[] transform;
    public int tilesize;
    public static Sprite get(SpriteSheet sheet, int i)//TODO: 2/6/19 - move this method to SpriteSheet
    {
        int tilesize = sheet.tilesize;
        int scansize = sheet.scansize;
        int[] pixels = new int[tilesize * tilesize];
        int xsprites, ysprites, format, x = 0, y = 0;
        System.out.println("Scansize: " + scansize + ", Tilesize: " + tilesize);
        xsprites = scansize / tilesize;
        ysprites = sheet.pixels.length / scansize / tilesize;
        format = i < xsprites * ysprites? sheet.format: UNDEFINED;
        switch(format)
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
        }
        System.out.println(
        "\n\nSprite.get(" + i + ")\n{\n\txsprites = " + xsprites + "\n\tysprites = " + ysprites + "\n\tx = " + x + "\n\ty = " + y + "\n}\n");
        for(int r = 0; r < tilesize; r++)
        {
        	int sheetOffset = (r + y) * scansize;
        	int spriteOffset = r * tilesize;// + y;
        	//System.out.println("\n\tSource Row: " + sheetOffset + "\tDestination Row: " + spriteOffset);
        	for(int c = 0; c < tilesize; c++)
			{
				//System.out.println("\n\tDestination Column: " + (c) + " = Source Column: " + (c + x));
				pixels[spriteOffset + c] = sheet.pixels[sheetOffset + c + x];
			}
		}
        return new Sprite(pixels, tilesize);
    }
    private Sprite(int[] pixels, int tilesize)
    {
        this.pixels = pixels;
        this.tilesize = tilesize;
        this.transform = new int[pixels.length];
        //*
         for(int r = 0; r < this.tilesize; r++)
        {
            for(int c = r; c < this.tilesize; c++)
            {
                int i0 = c + r * this.tilesize;
                int i1 = r + c * this.tilesize;
                //int temp = this.pixels[i0];
                this.transform[i0] = this.pixels[i1];
                //this.pixels[i1] = temp;
                this.transform[i1] = this.pixels[i0];
            }
        }//*/
    }
    public void render(Screen screen)
    {
        
    }
}
