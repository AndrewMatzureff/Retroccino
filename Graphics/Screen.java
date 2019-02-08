
package Graphics;

import java.awt.Rectangle;
/**
 * Write a description of class Screen here.
 * 
 * @author (Andrew Matzureff) 
 * @version (9/18/2016)
 */
public class Screen
{
    
    //Clipping
    public static final int IN = 0;
    public static final int OUT_ALL = 15;
    public static final int OUT_LEFT = 1;
    public static final int OUT_RIGHT = 2;
    public static final int OUT_UP = 4;
    public static final int OUT_DOWN = 8;
    public static final int OUT_X = OUT_LEFT | OUT_RIGHT;
    public static final int OUT_Y = OUT_UP | OUT_DOWN;
    public static final int OUT_UP_RIGHT = OUT_UP | OUT_RIGHT;
    public static final int OUT_UP_LEFT = OUT_UP | OUT_LEFT;
    public static final int OUT_DOWN_RIGHT = OUT_DOWN | OUT_RIGHT;
    public static final int OUT_DOWN_LEFT = OUT_DOWN | OUT_LEFT;
    public static final int OUT_Y_RIGHT = OUT_Y | OUT_RIGHT;
    public static final int OUT_Y_LEFT = OUT_Y | OUT_LEFT;
    public static final int OUT_X_UP = OUT_X | OUT_UP;
    public static final int OUT_X_DOWN = OUT_X | OUT_DOWN;
    public static final int X_SHIFT = 31;
    public static final int Y_SHIFT = 29;
    public static final int WIDTH_SHIFT = 30;
    public static final int HEIGHT_SHIFT = 28;
    public static final int SIGN_INT = 31;
    
    //Transform
    public static final int MIRROR_XY_MASK = 3;
    public static final int MIRROR_MASK = 3;
    public static final int ROTATE_MASK = 4;
    public static final int REFLECTION_MASK = 5;
    //
    public static final int MIRROR_Y = 1;
    public static final int MIRROR_X = 2;
    public static final int MIRROR_XY = 3;
    
    //Clockwise & Anti-Clockwise
    public static final int
    CW_0 = 0,
    CW_90 = 4,
    CW_180 = 3,
    CW_270 = 7,
    AC_0 = 1,
    AC_90 = 5,
    AC_180 = 2,
    AC_270 = 6;
    
    public static int TINT_OP;
    public int width, height;
    public int[] pixels;
    public Font font;
    Sprite clear;
    public Screen(int w, int h)
    {
        width = w;
        height = h;
        pixels = new int[width * height];
        clear = Sprite.get(SpriteSheet.create("Resources/barn.png", 800, 0), 0);
    }
    public void set(int[] pixels, int width, int height){
        if(pixels != null){
            this.pixels = pixels;
            this.width = width & 0xffff;
            this.height = height & 0xffff;
        }
        
        
    }
    public int[] getPixels(){return pixels;}
    public void clear()
    {
        //int c = (int)(System.currentTimeMillis() / 30 % width);
        double c = Math.sin(System.currentTimeMillis() * 0.001) * Math.cos(System.currentTimeMillis() * 0.001);
        //for(int i = 0; i < pixels.length; i++)
        //pixels[i] = 0xffccaaff + i * width / (c + 1);
        //pixels[i] = 0xffccaaff + (int)(i * c * 0.01);
        //drawSprite(clear, -300, -400, MIRROR_Y);
    }
    public void drawRect(int x, int y, int w, int h, float s, int color)
    {
        x = (int)(x - (w * s - w) / 2);
        y = (int)(y - (h * s - h) / 2);
        w = (int)(w * s);
        h = (int)(h * s);
        if(x >= width || y >= height || x + w < 0 || y + h < 0)
        return;
        
        //int oL = x >>> 31;//negatve indicates left of screen; x must be left of screen
        //int oR = (((width - 1) - (x + w - 1)) >>> 30) & 2;//x + w - 1 must be right of screen
        //int oU = y >>> 29 & 4;
        //int oD = (((height - 1) - (y + h - 1)) >>> 28) & 8;
        
        int position = //oL | oR | oU| oD;
        x >>> 31
        |
        (((width - 1) - (x + w - 1)) >>> 30) & 2
        |
        y >>> 29 & 4
        |
        (((height - 1) - (y + h - 1)) >>> 28) & 8;
        
        if((position & OUT_LEFT) != 0)
        {
            w += x;//w must be offset to account for x amount offscreen
            if(w < 1)
                w = 1;
            x = 0;
        }
        else if((position & OUT_RIGHT) != 0)
        {
            w = width - x;
        }
        if((position & OUT_UP) != 0)
        {
            h += y;
            if(h < 1)
                h = 1;
            y = 0;
        }
        else if((position & OUT_DOWN) != 0)
        {
            h = height - y;
        }
        //↑↓→←
        int ul = x + y * width;//upper left corner
        int ll = x + (y + h - 1) * width;//lower left corner
        int ur = (x + w - 1) + y * width;//upper right corner
        int lr = (x + w - 1) + (y + h - 1) * width;//lower right corner
        for(int c = 0; c < w; c++)
        {
            pixels[c + ul] = color;
            pixels[c + ll] = color;
        }
        for(int r = 0; r < h - 1; r++)
        {
            pixels[ul += width] = color;
            pixels[ur += width] = color;
        }
    }
    public void drawRect(int x, int y, int w, int h, int color)
    {
        if(x >= width || y >= height || x + w < 0 || y + h < 0)
        return;
        
        //int oL = x >>> 31;//negatve indicates left of screen; x must be left of screen
        //int oR = (((width - 1) - (x + w - 1)) >>> 30) & 2;//x + w - 1 must be right of screen
        //int oU = y >>> 29 & 4;
        //int oD = (((height - 1) - (y + h - 1)) >>> 28) & 8;
        
        int position = //oL | oR | oU| oD;
        x >>> 31
        |
        (((width - 1) - (x + w - 1)) >>> 30) & 2
        |
        y >>> 29 & 4
        |
        (((height - 1) - (y + h - 1)) >>> 28) & 8;
        
        if((position & OUT_LEFT) != 0)
        {
            w += x;//w must be offset to account for x amount offscreen
            if(w < 1)
                w = 1;
            x = 0;
        }
        else if((position & OUT_RIGHT) != 0)
        {
            w = width - x;
        }
        if((position & OUT_UP) != 0)
        {
            h += y;
            if(h < 1)
                h = 1;
            y = 0;
        }
        else if((position & OUT_DOWN) != 0)
        {
            h = height - y;
        }
        //↑↓→←
        int ul = x + y * width;//upper left corner
        int ll = x + (y + h - 1) * width;//lower left corner
        int ur = (x + w - 1) + y * width;//upper right corner
        int lr = (x + w - 1) + (y + h - 1) * width;//lower right corner
        for(int c = 0; c < w; c++)
        {
            pixels[c + ul] = color;
            pixels[c + ll] = color;
        }
        for(int r = 0; r < h - 1; r++)
        {
            pixels[ul += width] = color;
            pixels[ur += width] = color;
        }
    }
    public void drawSprite(Sprite sprite, int x, int y, int transform)
    {
        int
        screenx = x,//draw position of
        screeny = y,//sprite onscreen
        spritex = 0,//clip offset in sprite
        spritey = 0,//to start drawing from
        spritew = sprite.tilesize,
        spriteh = sprite.tilesize;
        int[] spritePixels = sprite.pixels;
        if(x < 0)
        {//start index offscreen
            if(x + sprite.tilesize <= 0)//<= because not subtracting 1
            {//whole sprite offscreen
                return;//trivial case
            }
            screenx = 0;//drawing position in screen space begins on left edge
            spritex = -x;//drawing position in sprite space begins on clip offset
            spritew += x;//total drawn width is now x pixels less due to clip
        }
        else
        if(x >= width)
        {//whole sprite offscreen
            return;//trivial case
        }
        //0123456789       
        //      01234       
        if(x + sprite.tilesize > width)
        {//extends offscreen
            spritew -= (x + sprite.tilesize) - width;//sprite draw width reduced by difference
        }
        
        if(y < 0)
        {
            if(y + sprite.tilesize <= 0)//<= because not subtracting 1
            {
                return;
            }
            screeny = 0;
            spritey = -y;
            spriteh += y;
        }
        else
        if(y >= height)
        {
            return;
        }
        //0123456789       
        //      01234       
        if(y + sprite.tilesize > height)
        {
            spriteh -= (y + sprite.tilesize) - height;
        }
        
        if((transform & ROTATE_MASK) != 0)
        {//NOTE: 90 degree rotation to the right must also include a horizontal mirror transform for the rotation to be correct.
            //Because of this implicit reflection the MIRROR_Y flag must be inverted while the MIRROR_X remains unchanged.
            //100    101    110    111
            //101    101    101    101
            //001    000    011    010
            transform ^= REFLECTION_MASK;
            spritePixels = sprite.transform;
        }
        //*
        switch(transform & MIRROR_MASK)//these mirror transforms were Hell in their own right to figure out, but they're so worth the work
        {
            case MIRROR_Y:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter = (r + spritey) * sprite.tilesize;
                    //int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            case MIRROR_X:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    //int spriter = (r + spritey) * sprite.tilesize;
                    int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            case MIRROR_XY:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    //int spriter = (r + spritey) * sprite.tilesize;
                    int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            default://*
            int time = (int)((Math.sin(System.currentTimeMillis() * 0.005) + 1) / 2 * 256);
            //System.out.println("Appearence: " + (255 - (time + 1)) + " = 255 - (" + time + " + 1)");
            //System.out.println("Equivalent:\t" + time);
            time = 0xff - (time + 1);
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter;// = (r + spritey) * sprite.tilesize;
                    int screenr;// = (r + screeny) * width;
                    switch(transform & MIRROR_MASK)
                    {
                        case MIRROR_Y:
                            spriter = (r + spritey) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        case MIRROR_X:
                            spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        case MIRROR_XY:
                            spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        default:
                            spriter = (r + spritey) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                    }
                    for(int c = 0; c < spritew; c++)
                    {//NOTE: maybe look into reimplementing hardware translucency or omitting real translucency altogether...
                        int alpha = time << 24;//HOLY SHITE I FIGURED IT OUT!!!!!!!!!!!
                        alpha = (alpha >>> 8) | (alpha >>> 16) | (alpha >>> 24);
                        int spritePixel = spritePixels[spriter + c + spritex];
                        int lsb = 0x000000ff; //least significant byte; sign correction 
                        int flag = ((spritePixel >> 16) & (spritePixel >> 8) & spritePixel & lsb) + 1;
                        int mask = (flag << 23) >> 31;
                        if(lsb - (alpha & lsb) <= 0)//completely opaque
                        {
                            this.pixels[screenr + c + screenx] &= mask;
                            this.pixels[screenr + c + screenx] |= ~mask & spritePixel;
                        }
                        else//translucent
                            this.pixels[screenr + c + screenx] &= spritePixel | alpha;
                    }
                }//*/
                return;
        }//*/
        /*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.pixels[xi + x + (yi + y) * width] = spritePixels[xi + yi * sprite.tilesize];//*/
        
        //clipLog(sprite, x, y, screenx, screeny, spritex, spritey, spritew, spriteh);
    }
    public void drawSprite(Sprite sprite, int x, int y, float s, int transform)
    {
        //x = (int)(x - (w * s - w) / 2);
        //y = (int)(y - (h * s - h) / 2);
        //w = (int)(w * s);
        //h = (int)(h * s);
        
        float tilescale = (int)(sprite.tilesize * s);
        float tiledif = tilescale - sprite.tilesize;
        int tilesize = sprite.tilesize;
        int
        screenx = (int)(x - tiledif / 2),//draw position of
        screeny = (int)(y - tiledif / 2),//sprite onscreen
        spritex = 0,//clip offset in sprite
        spritey = 0,//to start drawing from
        spritew = (int)(tilescale),
        spriteh = (int)(tilescale);
        int[] spritePixels = sprite.pixels;
        if(screenx < 0)
        {//start index offscreen
            if(screenx + tilescale <= 0)//<= because not subtracting 1
            {//whole sprite offscreen
                return;//trivial case
            }
            screenx = 0;//drawing position in screen space begins on left edge
            spritex = -screenx;//drawing position in sprite space begins on clip offset; remember to divide out scale when drawing!
            spritew += screenx;//total drawn width is now x pixels less due to clip
        }
        else
        if(screenx >= width)
        {//whole sprite offscreen
            return;//trivial case
        }
        //0123456789       
        //      01234       
        if(screenx + spritew > width)
        {//extends offscreen
            spritew -= (screenx + spritew) - width;//sprite draw width reduced by difference
        }
        
        if(screeny < 0)
        {
            if(screeny + tilescale <= 0)//<= because not subtracting 1
            {
                return;
            }
            screeny = 0;
            spritey = -screeny;
            spriteh += screeny;
        }
        else
        if(screeny >= height)
        {
            return;
        }
        //0123456789       
        //      01234       
        if(screeny + spriteh > height)
        {
            spriteh -= (screeny + spriteh) - height;
        }
        
     if((transform & ROTATE_MASK) != 0)
        {//NOTE: 90 degree rotation to the right must also include a horizontal mirror transform for the rotation to be correct.
            //Because of this implicit reflection the MIRROR_Y flag must be inverted while the MIRROR_X remains unchanged.
            //100    101    110    111
            //101    101    101    101
            //001    000    011    010
            transform ^= REFLECTION_MASK;
            spritePixels = sprite.transform;
        }
        //*
        switch(transform & MIRROR_MASK)//these mirror transforms were Hell in their own right to figure out, but they're so worth the work
        {
            case MIRROR_Y:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter = (r + spritey) * tilesize;
                    //int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            case MIRROR_X:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    //int spriter = (r + spritey) * sprite.tilesize;
                    int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            case MIRROR_XY:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    //int spriter = (r + spritey) * sprite.tilesize;
                    int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        //this.pixels[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.pixels[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            default://*
            int time = (int)((Math.sin(System.currentTimeMillis() * 0.005) + 1) / 2 * 256);
            //System.out.println("Appearence: " + (255 - (time + 1)) + " = 255 - (" + time + " + 1)");
            //System.out.println("Equivalent:\t" + time);
            time = 0xff - (time + 1);
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter;// = (r + spritey) * sprite.tilesize;
                    int screenr;// = (r + screeny) * width;
                    switch(transform & MIRROR_MASK)
                    {
                        case MIRROR_Y:
                            spriter = (r + spritey) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        case MIRROR_X:
                            spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        case MIRROR_XY:
                            spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                        default:
                            spriter = (r + spritey) * sprite.tilesize;
                            screenr = (r + screeny) * width;
                            break;
                    }
                    for(int c = 0; c < spritew; c++)
                    {//NOTE: maybe look into reimplementing hardware translucency or omitting real translucency altogether...
                        int alpha = time << 24;//HOLY SHITE I FIGURED IT OUT!!!!!!!!!!!
                        alpha = (alpha >>> 8) | (alpha >>> 16) | (alpha >>> 24);
                        int spritePixel = spritePixels[spriter + c + spritex];
                        int lsb = 0x000000ff; //least significant byte; sign correction 
                        int flag = ((spritePixel >> 16) & (spritePixel >> 8) & spritePixel & lsb) + 1;
                        int mask = (flag << 23) >> 31;
                        if(lsb - (alpha & lsb) <= 0)//completely opaque
                        {
                            this.pixels[screenr + c + screenx] &= mask;
                            this.pixels[screenr + c + screenx] |= ~mask & spritePixel;
                        }
                        else//translucent
                            this.pixels[screenr + c + screenx] &= spritePixel | alpha;
                    }
                }//*/
                return;
        }//*/
        /*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.pixels[xi + x + (yi + y) * width] = spritePixels[xi + yi * sprite.tilesize];//*/
        
        //clipLog(sprite, x, y, screenx, screeny, spritex, spritey, spritew, spriteh);
    }
    public void message(String msg, int x, int y)
    {
        if(font == null || msg == null)
            return;
        for(int i = 0; i < msg.length(); i++)
        {
            drawSprite(font.glyphs[((msg.charAt(i) - font.offset) & 0xffff) % font.glyphs.length], x + i * font.point + font.xpad, y + font.ypad, 0);
        }
    }
    public void clipLog(Sprite sprite, int x, int y, int scx, int scy, int spx, int spy, int spw, int sph)
    {
        String padding = "\n\n\n";
        String newline = "\n";
        String tab = "\t";
        System.out.println
        (
            padding +
            "Sprite (tilesize = " + sprite.tilesize + ", pixels.length = " + sprite.pixels.length + ")" + newline +
            "{" + newline +
            tab + "Position on screen: (" + x + ", " + y + ")" + newline +
            tab + "Source coordinates of clipped region: (" + spx + ", " + spy + ")" + newline +
            tab + "Destination coordinates of clipped region: (" + scx + ", " + scy + ")" + newline +
            tab + "Dimensions of clipped region: (" + spw + ", " + sph + ")" + newline +
            "}" +
            padding
        );
    }
    public void drawSprite(Sprite sprite, int x, int y, int z, int a)
    {
        int screenx = x & (-x >> SIGN_INT);//equivalent to "if(x < 0)x = 0"
        int screeny = y & (-y >> SIGN_INT);
        int spritex = -(x & (x >> SIGN_INT));//equivalent to "if(x < 0)x = -x else x = 0"
        int spritey = -(y & (y >> SIGN_INT));
        int spritew = x + sprite.tilesize - 1;
        int spriteh = y + sprite.tilesize - 1;
        spritew = spritew > width? sprite.tilesize - (spritew - width): sprite.tilesize;
        spriteh = spriteh > height? sprite.tilesize - (spriteh - height): sprite.tilesize;
        /*
        for(int r = 0; r < spriteh; r++)//still not drawing correctly, check scribbling
        {
            int spriter = (r + spritey) * sprite.tilesize;
            int screenr = (r + screeny) * height;
            for(int c = 0; c < spritew; c++)
            {
                this.pixels[screenr + c] = sprite.pixels[spriter + c];
            }
        }//*/
        //*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.pixels[xi + x + (yi + y) * width] = sprite.pixels[xi + yi * sprite.tilesize];//*/
    }
}
