package UnitTests;


/**
 * Write a description of class OffBy1Division here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/1/2019)
 */
public class OffBy1Division
{
    public static void main(String[] args){
        for(int dif = -255; dif < 256; dif++){;
            System.out.printf("dif=%d\n", dif);
            for(int alpha = 0; alpha < 256; alpha++){
                int lrp = dif * alpha;
                int div = (lrp / 255);
                int shf = (lrp >> 8);
                int err = div - shf;
                if(Math.abs(err) > 1)
                    System.out.printf("\t\talpha=%d\tlrp=%d\tdiv=%d\tshf=%d\t\terr=%d\n\n", alpha, lrp, div, shf, err);
            }
        }
    }
}
