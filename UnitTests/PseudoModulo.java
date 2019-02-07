package Unit_Tests;


/**
 * Write a description of class PseudoModulo here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/20/2017)
 */
public class PseudoModulo
{
    int size = 1024;
    char[] charset = new char[size];
    int mod = charset.length - 1;
    int tests = 10;
    int samples = 100000000;
    double millis = 1e-6;
    //*Memory*
    //  Best Case: O(n)
    //  Worst Case: O(2n - 2)
    //*Lookup*
    //  Every Case: O(1)
    /**
     * Constructor for objects of class PseudoModulo
     */
    public PseudoModulo()
    {
        int moda = 32;
        int modb = 31;
        /*for(int i = 0; i < mod * 4; i++)
        {
            System.out.println(i + " % " + moda + " =\t" + (i & modb) + "\t:\t" + (i & (moda - 1)));
            
        }*/
        for(char i = (char)0; i < size; i++)
        {
            charset[i] = (char)(i + 'a');
        }/*
        for(int i = 0; i < size * 4; i++)
        {
            System.out.println("charset[(" + i + " % " + size + " = " + (i & mod) + ")] : " + charset[i & mod]);
        }*/
        double modt = 0d;
        double andt = 0d;
        for(int i = 0; i < tests; i++)
        {
            modt += mod();
            andt += and();
            System.out.println(".");
        }
        System.out.printf("Domain Samples: %d\nTests: %d\nCharset: %d\n", samples, tests, size);
        System.out.println(".\nMOD Time: " + (modt / tests * millis) + "mS\t" + "\nAND Time: " + (andt / tests * millis) + "mS");
    }
    public long mod()
    {
        long start = System.nanoTime();
        for(int i = 0; i < samples; i++)
        {
            charset[i % size] = charset[i % size];
        }
        return System.nanoTime() - start;
    }
    public long and()
    {
        long start = System.nanoTime();
        for(int i = 0; i < samples; i++)
        {
            charset[i & mod] = charset[i & mod];
        }
        return System.nanoTime() - start;
    }
}
