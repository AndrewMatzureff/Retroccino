         
package Graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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
    
    public static final long BLEND = 0x1_00000000L;
    public int width, height;
    private int[] read;//final
    private int[] write;//wip
    private BufferedImage preblend;
    private BufferedImage postblend;
    public Font font;
    Sprite clear;
    static int ALPHAP = 200;
    private Screen(BufferedImage pre, BufferedImage post)
    {
        width = post.getWidth();
        height = pre.getHeight();//:)
        preblend = pre;
        postblend = post;
        write = ((DataBufferInt)pre.getRaster().getDataBuffer()).getData();
        read = ((DataBufferInt)post.getRaster().getDataBuffer()).getData();
        setOpacity(200);
        //clear = Sprite.get(SpriteSheet.create("Resources/barn.png", 800, 0), 0);
    }
    public static Screen create(int w, int h){
        BufferedImage pre = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        BufferedImage post = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if(pre != null && post != null)
            return new Screen(pre, post);
        return null;
    }
    public int[] getPixels(){return read;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public void and(int n){
        for(int i = 0; i < read.length; i++)
            read[i] &= n;
    }
    public void or(int n){
        for(int i = 0; i < read.length; i++)
            read[i] |= n;
    }
    public void xor(int n){
        for(int i = 0; i < read.length; i++)
            read[i] ^= n;
    }
    public void blast(String ops){//just for fun; happy birthday, me! -3/7/19
        //  ^^, **, ++, --, ~, *, /, %, +, -, <<, >>, >>>, &, |, ^
        //  Exponentiation          0
        //  Square                  1
        //  Increment               2
        //  Decrement               3
        //  Not                     4
        
        //  Multiplication          5
        //  Division                6
        //  Modulo                  7
        
        //  Addition                8
        //  Subtraction             9
        
        //  Left Shift              10
        //  Right Shift             11
        //  Unsigned Right Shift    12
        
        //  And                     13
        
        //  Xor                     14
        
        //  Or                      15
        
        //4 & 7 << 3 + 3 | 16 / 3 + 3 ^^ 5
    }
    public void setOpacity(int alpha){
        for(int i = 0; i < write.length; i++)
            write[i] = write[i] & 0x00ffffff | alpha << 24;
    }
    public BufferedImage refresh(long op)//, boolean blend)
    {
        int clear = (int)(op & 0xffffffff);
        //if(blend)
        if((op & BLEND) == BLEND){
            for(int i = 0; i < read.length; i++){
                read[i] = compose(read[i], write[i], write[i] >>> 24) | (clear & 0xff000000);
                write[i] = compose(write[i], clear, read[i] >>> 24) | (write[i] & 0xff000000);
            }
            return postblend;
        }
        else{
            return preblend;
        }
        /*else
            for(int i = 0; i < read.length; i++){
                read[i] = compose(read[i], write[i], ALPHAC);
                write[i] = clear;
            }*/
    }
    public void clear()
    {
        //int c = (int)(System.currentTimeMillis() / 30 % width);
        double c = Math.sin(System.currentTimeMillis() * 0.001) * Math.cos(System.currentTimeMillis() * 0.001);
        //for(int i = 0; i < read.length; i++)
        //read[i] = 0xffccaaff + i * width / (c + 1);
        //read[i] = 0xffccaaff + (int)(i * c * 0.01);
        //drawSprite(clear, -300, -400, MIRROR_Y);
    }
    public void drawRect(int x, int y, int w, int h, float s, int color)
    {
        x = (int)(x - (w * s - w) / 2);
        y = (int)(y - (h * s - h) / 2);
        w = (int)(w * s);
        h = (int)(h * s);
        if(x >= width || y >= height || x + w < 0 || y + h < 0 || w * h == 0)
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
            read[c + ul] = color;
            read[c + ll] = color;
        }
        for(int r = 0; r < h - 1; r++)
        {
            read[ul += width] = color;
            read[ur += width] = color;
        }
    }
    public void drawRect(int x, int y, int w, int h, int color)
    {
        if(x >= width || y >= height || x + w < 0 || y + h < 0 || w * h == 0)
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
            read[c + ul] = color;
            read[c + ll] = color;
        }
        for(int r = 0; r < h - 1; r++)
        {
            read[ul += width] = color;
            read[ur += width] = color;
        }
    }
    public void drawSprite(Sprite sprite, int x, int y, int transform)//NOTE: 3/22/19- variable aspect ratio Sprites are technically possible in these draw methods, it was just a matter of making the transform generation easier; ultimately, the transforms can be generated then trimmed to the proper aspect ratio.
    {
        double start = System.nanoTime();
        int
        screenx = x,//draw position of
        screeny = y,//sprite onscreen
        spritex = 0,//clip offset in sprite
        spritey = 0,//to start drawing from
        spritew = sprite.tilesize,
        spriteh = sprite.tilesize;
        int[] spritePixels = sprite.pixels;
        byte[] spriteHeight = sprite.height;
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
            spritePixels = sprite.ptransform;
            spriteHeight = sprite.htransform;
        }
        //*
        //switch(transform & MIRROR_MASK)//these mirror transforms were Hell in their own right to figure out, but they're so worth the work
        //{
            /*case MIRROR_Y:
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter = (r + spritey) * sprite.tilesize;
                    //int spriter = (sprite.tilesize - 1 - (r + spritey)) * sprite.tilesize;//y flip
                    int screenr = (r + screeny) * width;
                    for(int c = 0; c < spritew; c++)
                    {
                        //this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
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
                        this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        //this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
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
                        //this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            default://*
            */int time = (int)((Math.sin((System.currentTimeMillis() + x - y) * 0.001) + 1) / 2 * 255);
            //System.out.println("Appearence: " + (255 - (time + 1)) + " = 255 - (" + time + " + 1)");
            //System.out.println("Equivalent:\t" + time);
            time = 0xff - (time + 1);
                for(int r = 0; r < spriteh; r++)//fixed by ADDING sprite position in offscreen condition since it's a negative offset
                {
                    int spriter;// = (r + spritey) * sprite.tilesize;
                    int screenr;// = (r + screeny) * width;
                    switch(transform & MIRROR_MASK)//3/19/19-NOTE: low priority, but maybe implement a lookup here later...
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
                    {
                        int alpha = time << 24;
                        alpha = 255;//(alpha >>> 8) | (alpha >>> 16) | (alpha >>> 24);
                        
                        int spriteIndex = spriter + c + spritex;
                        int spritePixel = spritePixels[spriteIndex];
                        
                        int screenIndex = screenr + c + screenx;
                        int screenPixel = write[screenIndex];
                        
                        
                        //NOTE: 3/22/19- add an alpha channel override scalar to the Sprite class that will be used here to scale the intensity of the instance persistent Sprite alpha values
                        int blend = compose(screenPixel, spritePixel, ALPHAP);
                        //System.out.println("Alpha: " + (alpha & 0xff));
                        int depth = 0xff000000;
                        //gotta continue this
                        //if((depth < (screenPixel & 0xff000000) || (screenPixel & 0xff000000) == 0))//depth buffer check
                            this.write[screenIndex] = (blend & 0x00ffffff) | (screenPixel & 0xff000000);//((screenPixel - spritePixel) * alpha) / 255;
                        
                        //int sign = 
                    }
                }//*/
                return;
        //}//*/
        /*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.read[xi + x + (yi + y) * width] = spritePixels[xi + yi * sprite.tilesize];//*/
        
        //clipLog(sprite, x, y, screenx, screeny, spritex, spritey, spritew, spriteh);
        //TOTAL++;
    }
    /*public int compose(int screen, int sprite, int alpha){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr - pr) * alpha) >> 8);
        
        int g = (((cg - pg) * alpha) >> 8);
        
        int b = (((cb - pb) * alpha) >> 8);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }*/
    public int compose(int src, int dst, int a){//Calls to this method will be replaced by inline copies during finalization; this method will be kept for now for debugging convenience.
        int dr = (dst & 0xff0000) >> 16, dg = (dst & 0x00ff00) >> 8, db = dst & 0x0000ff;
        int sr = (src & 0xff0000) >> 16, sg = (src & 0x00ff00) >> 8, sb = src & 0x0000ff;
        
        int r = (((dr - sr) * a) >> 8);
        
        int g = (((dg - sg) * a) >> 8);
        
        int b = (((db - sb) * a) >> 8);
        
        return ((sr + r) << 16) | ((sg + g) << 8) | (sb + b);
    }
    public static int sod(int screen, int sprite, int alphac, int alphap){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int a = alphac + ((alphap * (255 - alphac)) >> 8);
        
        int r = ((cr * alphac + pr * alphap * (255 - alphac)) * a) >>> 24;
        
        int g = ((cg * alphac + pg * alphap * (255 - alphac)) * a) >>> 24;
        
        int b = ((cb * alphac + pb * alphap * (255 - alphac)) * a) >>> 24;
        
        return ((r) << 16) | ((g) << 8) | (b);
    }
    public static int distribute(int screen, int sprite, int alphac, int alphap){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr * alphac - pr * alphap)) >> 8);
        
        int g = (((cg * alphac - pg * alphap)) >> 8);
        
        int b = (((cb * alphac - pb * alphap)) >> 8);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }
    public static int simplify(int screen, int sprite, int alphac, int alphap){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr - pr) * alphac * alphap) >> 16);
        
        int g = (((cg - pg) * alphac * alphap) >> 16);
        
        int b = (((cb - pb) * alphac * alphap) >> 16);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }
    public int nest(int screen, int sprite, int alphac, int alphap){
        int prepass = compose(screen, sprite, alphap);
        return compose(screen, prepass, alphac);
    }
    public void drawSprite(Sprite sprite, int x, int y, int transform, float s)
    {
        //x = (int)(x - (w * s - w) / 2);
        //y = (int)(y - (h * s - h) / 2);
        //w = (int)(w * s);
        //h = (int)(h * s);
        
            s = (float)((Math.sin(System.currentTimeMillis() * 0.0005) * 0.999 + 1));
        float tilescale = (int)(sprite.tilesize * s);
        float tiledif = tilescale - sprite.tilesize;
        int tilesize = sprite.tilesize;
        int
        screenx = (int)(x - tiledif / 2),//draw position of
        screeny = (int)(y - tiledif / 2),//sprite onscreen
        spritex = 0,//clip offset in sprite
        spritey = 0,//to start drawing from
        spritew = (int)(tilescale),
        spriteh = (int)(tilescale);//maybe these should eventually be the regular tilesize since the sreen coordinate will get scaled while drawing
        int[] spritePixels = sprite.pixels;
        byte[] spriteHeight = sprite.height;
        if(screenx < 0)
        {//start index offscreen
            if(screenx + tilescale <= 0)//<= because not subtracting 1
            {//whole sprite offscreen
                return;//trivial case
            }
            spritex = -screenx;//drawing position in sprite space begins on clip offset; remember to divide out scale when drawing!
            spritew += screenx;//total drawn width is now x pixels less due to clip
            screenx = 0;//drawing position in screen space begins on left edge
            //^^Last 2 values are virtual and must have scale divided back out when accessing pixels in Sprite space.
        }
        else
        if(screenx >= width)
        {//whole sprite offscreen
            return;//trivial case
        }
                    //System.out.println("spritex: " + spritex);
                    //System.out.println("spritew: " + spritew);
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
            spritey = -screeny;
            spriteh += screeny;
            screeny = 0;
            //See x branches above.
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
            spritePixels = sprite.ptransform;
            spriteHeight = sprite.htransform;
        }
        /*
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
                        //this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
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
                        this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        //this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
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
                        //this.read[screenr + c + screenx] = spritePixels[spriter + c + spritex];
                        this.read[screenr + c + screenx] = spritePixels[spriter + sprite.tilesize - 1 - (c + spritex)];//x flip
                    }
                }
                break;
            default:*///*
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
                            spriter = (int)((r + spritey) / s * sprite.tilesize);
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
                            //spriter = (int)(((r + spritey) / (tilescale - 1) ) * (tilesize - 1));
                             //System.out.println("((" + r + " + " + spritey + " = " + (r + spritey) + ") / " + s + " = " + ((r + spritey) / s) + ") * " + tilesize + " =\t" + (((r + spritey) / s) * tilesize));
                            spriter = ((int)((r + spritey) / s) * tilesize);
                            screenr = (r + screeny) * width;
                            break;
                    }
                    for(int c = 0; c < spritew; c++)
                    {
                        int alpha = time << 24;
                        alpha = 255;//(alpha >>> 8) | (alpha >>> 16) | (alpha >>> 24);
                        
                        int spriteIndex = (int)(spriter + (((c + spritex) / s)));
                        //if(spriteIndex >= tilesize * tilesize){
                             //System.out.println("spriteIndex(spriter: " + spriter + ", spritec: " + (int)((spritex + c) / s) + "): " + spriteIndex + "\terror: " + (1 + spriteIndex - tilesize * tilesize));
                        //     continue;}
                        int spritePixel = spritePixels[spriteIndex];
                        
                        int screenIndex = screenr + c + screenx;
                        int screenPixel = write[screenIndex];
                        
                        
                        //NOTE: 3/22/19- add an alpha channel override scalar to the Sprite class that will be used here to scale the intensity of the instance persistent Sprite alpha values
                        int blend = compose(screenPixel, spritePixel, ALPHAP);
                        //System.out.println("Alpha: " + (alpha & 0xff));
                        int depth = 0xff000000;
                        //gotta continue this
                        //if((depth < (screenPixel & 0xff000000) || (screenPixel & 0xff000000) == 0))//depth buffer check
                            this.write[screenIndex] = (blend & 0x00ffffff) | (screenPixel & 0xff000000);//((screenPixel - spritePixel) * alpha) / 255;
                        
                        //int sign = 
                    }
                }//*/
                return;
        //}//*/
        /*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.read[xi + x + (yi + y) * width] = spritePixels[xi + yi * sprite.tilesize];//*/
        
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
            "Sprite (tilesize = " + sprite.tilesize + ", read.length = " + sprite.pixels.length + ")" + newline +
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
                this.read[screenr + c] = sprite.pixels[spriter + c];
            }
        }//*/
        //*
        for(int xi = 0; xi < sprite.tilesize; xi++)
        for(int yi = 0; yi < sprite.tilesize; yi++)
        if(xi + x >= 0 && xi + x < width && yi + y >= 0 && yi + y < height)
        this.read[xi + x + (yi + y) * width] = sprite.pixels[xi + yi * sprite.tilesize];//*/
    }
}
