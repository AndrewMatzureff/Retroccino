package Unit_Tests;
/**
 * Write a description of class c here.
 * 
 * @author (Andrew Matzureff) 
 * @version (1/23/2017)
 */
public class RowIndex
{
    /*
     * CONCLUSION:
     * A tricky problem (mapping linear indices
	 * to a rectangular region (linear to
	 * bilinear, not the other way around) and
	 * resolving the y components) made
	 * solvable by trivial algebra. Silly, me.
     */
    public static void main(String[] args)
    {
        int xsprites = 9;
        int ysprites = 81;
        int mat = matrix(xsprites, ysprites);
        for(int i = 0; i < mat; i++)
        {
            int x = i % xsprites;
            int y = ((i - x) / xsprites);
            //System.out.println("X("+i+") is in column: " + x);
            System.out.println("Y("+i+") is in row: " + y);
        }
    }
    public static int matrix(int w, int h)
    {
        int k = 0;
        for(int i = 0; i < w; i++)
            System.out.print(String.format("\t%03d", i));
        System.out.println("\n");
        for(int i = 0; i < h; i++)
        {
            System.out.print(i + "\t");
            for(int j = 0; j < w; j++)
            {
                System.out.print(String.format("%03d\t", k++));
            }
            System.out.println();
        }
        return w * h;
    }
}
