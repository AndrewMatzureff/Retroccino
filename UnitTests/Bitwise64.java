package UnitTests;
/**
 * Write a description of class c here.
 * 
 * @author (Andrew Matzureff) 
 * @version (1/23/2017)
 */
public class Bitwise64
{
    /*
     *           1000 0000
     *           |
     * 0000 0000 0000 0000
     *           =
     * 1111 1111 1000 0000 <-SIGN EXTENSION
     * 
     * CONCLUSION:
     * Bitwise manipulation between different sized datatypes
     * seems pretty safe; the biggest thing to watch out for
     * seems to be sign extensions, so using a mask afterwards
     * would be wise.
     */
    public static void main(String[] args)
    {
        //command._Bits = command._Bits - (int)(command._States >>> (C.BITS_INT - 1)) + key;
        int bits = 0;//bits on in states
        long states = 0;//key press states
        int size = 64;//size of integer
        int key = 0;//most recent key press
        
        int  i =                                 0b00000000000000000000000000010100;
        long l = 0b1000000000000000000000000000000000000000000000000000000000000000L;
        System.out.println
        (
            "Long: " + Long   .toBinaryString(l)
            + "\t\t>>>\t" +
            "Int : " + Integer.toBinaryString(i)
            + "\t\t=\tLong: " +
            Long.toBinaryString(l >>> i)
        );
    }
}
