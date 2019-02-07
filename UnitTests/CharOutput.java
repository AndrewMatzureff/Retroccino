package Unit_Tests;


/**
 * Write a description of class sa here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/6/2017)
 */
public class CharOutput//Aha! I guess it does work for my purposes.
{
    //Conclusion:
    //  Arbitrary sets of characters are able to be restricted to the range '`' - ' ' (96 - 127)
    //OR "a - z" (97 - 122) if elements of the input set are in the range "a - z" or "A - Z".
    //This is useful because I can now make case insensitive decisions on basic Latin characters
    //used as attribute flags in character set format files. Whether or not characters outside
    //both the aforementioned case domains produce the correct results is beyond the scope of
    //the requirements described by this file system.
    //LowerCase = 96 + (letter % 123) % 32
    public static void main(String[] args)
    {
        int range = 32;
        System.out.println("Literal:");
        for(int i = 0; i < 26; i++)
        {
            System.out.print((char)(i + 97));
        }
        System.out.println("\n\nUpper Case Transformed:");
        for(int i = 0; i < 26; i++)
        {
            int j = i + 65;
            j %= 123;
            System.out.print((char)(96 + j % range));
        }
        System.out.println("\n\nLower Case Transformed:");
        for(int i = 0; i < 26; i++)
        {
            int j = i + 97;
            j %= 123;
            System.out.print((char)(96 + j % range));
        }
        System.out.println("\n\nArbitrary Transformed:");
        for(int i = 0; i < 65536; i++)
        {
            int j = i;// + 97;
            j %= 123;
            System.out.print((char)(96 + j % range));
        }
    }
}
