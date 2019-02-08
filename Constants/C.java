package Constants;
import java.awt.event.*;

/**
 * Write a description of class C here.
 * 
 * @author (Andrew Matzureff) 
 * @version (10/3/2016)
 */
public class C//NOTE: 2/6/19 - no. cheese this shit and move these constants to the classes in which they're used.
{
    //MASKS//
    public static final int MOUSE_MASK = -2147483648;//10...0; flag must exist within 2 most significant bytes
    public static final int KEYBOARD_MASK = 0;//0...0
    public static final int COMMAND_MASK = 254;//1111 1110; pre-accounts for byte to int sign extension
    public static final int STATE_MASK = 1;//0000 0001; will ALWAYS equal 1!!!
    public static final int TOGGLE_MASK = 3;//0000 0011
    public static final long RELEASE_MASK = ~3;//1111 1100
    public static final int X_MASK = 1;
    public static final int Y_MASK = 4;
    public static final int WIDTH_MASK = 2;
    public static final int HEIGHT_MASK = 8;
    
    public static final int MIRROR_X_MASK = 1;//Mirror X coordinates, not mirror across X-axis
    public static final int MIRROR_Y_MASK = 2;
    public static final int MIRROR_XY_MASK = 3;
    public static final int MIRROR_MASK = 3;
    public static final int ROTATE_MASK = 4;
    public static final int REFLECTION_MASK = 5;
    
    //Added  a test comment
    
    public static final int
    FLIP_HORZ = MIRROR_X_MASK,
    FLIP_VERT = MIRROR_Y_MASK,
    FLIP_BOTH = MIRROR_XY_MASK;
    
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
    
    public static final int FONT_COMPLETE = -16;
    public static final int FONT_N = 1;
    public static final int FONT_L = 2;
    public static final int FONT_T = 4;
    public static final int FONT_O = 8;
    public static final int CHAR_MASK = ((int)(Character.MAX_VALUE) << 1) | 1;
    //ENUMS//
    public static final int END_OF_FILE = -1;
    public static final int NULL = 0;
    public static final int MIRROR_X = 1;
    public static final int MIRROR_Y = 2;
    public static final int MIRROR_XY = 3;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int STATE = 1;
    public static final int COMMAND = 2;
    public static final int BOTH = 0;
    public static final int UNDEFINED = ~0;
    public static final int TOGGLE = 1;
    public static final int CONTINUOUS = 0;
    public static final int ASSIGN = 0;      //tier0
    public static final int OR  = 1;        //tier1
    public static final int AND = 2;       //tier1
    public static final int NOT = 3;      //tier1
    public static final int XOR = 4;     //tier1
    public static final int ADD = 5;    //tier2
    public static final int SUB = 6;   //tier3
    public static final int MUL = 7;  //tier4
    public static final int DIV = 8; //tier5
    public static final int MOD = 9;//tier5
    //staaaahp...
    //CLIPPING//
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
    //...//
    //OTHER//
    public static final double START_TIME = System.nanoTime();
    public static final int MAX_COMMANDS = 128;
    public static final int ESCAPE = KeyEvent.VK_ESCAPE;
    public static final int DELETE = KeyEvent.VK_DELETE;
    public static final int MAX_SCAN = 1 << 10;//1024
    public static final int BITS_INT = 32;
    public static final int BITS_LONG = 64;
    public static final int MAX_CHARS = (int)(Character.MAX_VALUE) + 1;
    public static final char NULL_CHAR = (char)0;
    public static final char F = 'f';
    public static final char R = 'r';
    public static final char C = 'c';
    public static final char T = 't';
    public static final char O = 'o';
    public static final char H = 'h';
    public static final char V = 'v';
    public static final int LOWER_BOUND = 96;
    public static final int UPPER_BOUND = 123;
    public static final int RANGE = 32;
    public static final char X = 'x';
    public static final char B = 'b';
    public static final char ONE = '6';
    public static final String TXT = "txt";
    public static final String TX8 = "tx8";
    public static final String T16 = "t16";
    public static final String B16 = "b16";
    public static final String FILE_SEPARATOR = java.io.File.separator;
}
