package Graphics;
/**
 * Write a description of class Sprite here.
 * 
 * @author (Andrew Matzureff) 
 * @version (11/17/2016)
 */
public final class Sprite
{
    int[] pixels;
    int[] ptransform;
    byte[] height;
    byte[] htransform;
    public final int tilesize;
    //NOTE: 2/16/19 - plans for height-mapped sprites have been dropped, though not for
    //obvious reasons (difficulty, complexity, performance being the obvious ones). The
    //usefulness comes into question when one considers the frequency of graphical
    //artifacts at target sprite resolutions. Any significant difference in depth between
    //adjacent pixels would result in noticeable visual gaps, the only remedy being a
    //drastic increase in sprite resolution allowing smoother, more gradient height maps
    //to be drawn.
    //NOTE: 2/21/2019 - height maps can still be! Just no per pixel parallax...
    private Sprite(int[] pixels, byte[] height, int tilesize)
    {
        this.pixels = new int[pixels.length];
        this.height = new byte[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
        if(height != null)
            System.arraycopy(height, 0, this.height, 0, pixels.length);
        this.tilesize = tilesize;
        this.ptransform = new int[pixels.length];
        this.htransform = new byte[pixels.length];
        //*
         for(int r = 0; r < tilesize; r++)
        {
            for(int c = r; c < tilesize; c++)
            {
                int i0 = c + r * tilesize;
                int i1 = r + c * tilesize;
                //int temp = this.pixels[i0];
                this.ptransform[i0] = this.pixels[i1];
                //this.pixels[i1] = temp;
                this.ptransform[i1] = this.pixels[i0];
                
                this.htransform[i0] = this.height[i1];
                //this.pixels[i1] = temp;
                this.htransform[i1] = this.height[i0];
            }
        }//*/
    }
    public static Sprite create(int[] pixels, int tilesize){
        if(pixels != null && pixels.length == tilesize * tilesize)
            return new Sprite(pixels, null, tilesize);
        return null;
    }
    public static Sprite random(int t, int n){
        Sprite rs = new Sprite(new int[t * t], null, t);
        rs.noise(n);
        return rs;
    }
    public void noise(int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < pixels.length; j++){
                pixels[j] = (pixels[j] ^ (int)(Math.random() * 0x01000000)) | 0xff000000;
            }
        }
    }
    
    /*public static Sprite get(SpriteSheet sheet, int i)//TODO: 2/6/19 - move this method to SpriteSheet
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
    }*/
}
