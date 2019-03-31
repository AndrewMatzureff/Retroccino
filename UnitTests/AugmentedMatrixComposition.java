package UnitTests;


/**
 * Write a description of class AugmentedMatrixComposition here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/15/2019)
 */
public class AugmentedMatrixComposition
{
    public static void main(int samples){
        double   s00 = 0, s01 = 0,
                 s10 = 0, s11 = 1,
                 s20 = 1, s21 = 0,
                 s30 = 1, s31 = 1;
        String t = "\n\n\n\t%-10d\t%-10d\t\t->\t\t%-10d\t%-10d\t\t->\t\tResult\n\n\n";
        String m = "\t%-10.8f\t%-10.8f\t\t\t";
        for(int i = 0; i < samples; i++){
            double  e00 = s00 + s10,
                    e01 = s01 + s11,
                    e30 = s30 + s20 * -2,
                    e31 = s31 + s21 * -2;
            
            System.out.printf(t, 0, 1, 0, 1);//"\n\n\n\t%-10d\t%-10d\n\n\n", 0, 1);
            System.out.printf  ("0" + m + m + "\t%-10.8f\n", s00, s01, e00, e01, e00 + e01);//"0\t%-10.8f\t%-10.8f\n", s00, s01);
            System.out.printf  ("1" + m + m + "\t%-10.8f\n", s10, s11, s10, s11, s10 + s11);//"1\t%-10.8f\t%-10.8f\n", s10, s11);
            System.out.printf  ("2" + m + m + "\t%-10.8f\n", s20, s21, s20, s21, s20 + s21);//"2\t%-10.8f\t%-10.8f\n", s20, s21);
            System.out.printf  ("3" + m + m + "\t%-10.8f\n", s30, s31, e30, e31, e30 + e31);//"3\t%-10.8f\t%-10.8f\n", s30, s31);
            s00 = Math.random();
            s01 = Math.random();
            s10 = Math.random();
            s11 = Math.random();
            s20 = Math.random();
            s21 = Math.random();
            s30 = Math.random();
            s31 = Math.random();
        }
    }
}
