package Graphics;

import Constants.C;
import java.util.List;
import java.util.Iterator;
import IO.Text;
import IO.Log;
import java.util.ArrayList;
/**
 * Write a description of class Text here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/6/2017)
 */
public class Font//Needs work...
{
    public Sprite[] glyphs;
    public final char offset;
    public int point;
    public int xpad = 0;
    public int ypad = 8;
    //NOTE: perhaps differentiate between modulo fonts and and fonts
    private Font(SpriteSheet sprites, char offset)
    {
        int xsprites = sprites.scansize / sprites.tilesize;
        int ysprites = sprites.pixels.length / sprites.scansize / sprites.tilesize;
        this.glyphs = new Sprite[xsprites * ysprites];
        this.offset = offset;
        this.point = sprites.tilesize;
        Sprite glyph;
        int i = 0;
        System.out.println("Glyphs: " + this.glyphs.length);
        while((glyph = Sprite.get(sprites, i)) != null)
        {
            glyphs[i++] = glyph;
        }
    }
    private static char decase(int letter)//lol does this even work? I barely remember writing this...
    {
        return (char)(C.LOWER_BOUND + (letter % C.UPPER_BOUND) % C.RANGE);
    }
    public static Font read(String formatPath)
    {
        List<String> format = Text.readLines(formatPath);
        String filename = null;
        int orientation = 0;
        char offset = 0;
        int i = 0;
        int tilesize = 1;
        for(String line: format)
        {
            switch(decase((int)line.charAt(0)))
            {
                case C.F:
                    filename = line.substring(1);
                    break;
                case C.O:
                    switch(decase((int)line.charAt(1)))
                    {
                        case C.H:
                            orientation = C.HORIZONTAL;
                            break;
                        case C.V:
                            orientation = C.VERTICAL;
                            break;
                        default:
                            Log.append("Invalid SpriteSheet Orientation on Line " + i + " in " + filename + ".");
                            break;
                    }
                    break;
                case C.R:
                    offset = line.charAt(1);/*//deprecated
                    try
                    {
                        range = Integer.parseInt(line.substring(2));
                    }
                    catch(IndexOutOfBoundsException ioobe){continue;}
                    catch(NumberFormatException nfe){continue;}*/
                    break;
                case C.T:
                    try{
                        tilesize = Integer.parseInt(line.substring(1));
                        //System.out.println("Parsed " + tilesize);
                    }catch(NumberFormatException nfe)
                    {
                        Log.append("Cannot parse value as Integer on Line " + i + " in " + filename + ".");
                        break;
                    }
                    break;
                default:
                    break;
            }
            i++;
        }
        return new Font(SpriteSheet.create(filename, tilesize, orientation), offset);
    }
}
