package UnitTests;


/**
 * Write a description of class CompoundComposition here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/11/2019)
 */
public class CompoundComposition
{
    public static void main(int samples){
        for(int i = 0; i < samples; i++){;
            int alphac = (int)(Math.random() * 256);
            int alphap = (int)(Math.random() * 256);
            int dif = (int)(Math.random() * 511 - 255);
            
            int col = (int)(Math.random() * 256);
            //dif+col-col=dif
            int dis0 = distributedBlend(dif + col, col, 0, alphap);
            int sim0 = simplifiedBlend(dif + col, col, 0, alphap);
            int dis1 = distributedBlend(dif + col, col, 1, alphap);
            int sim1 = simplifiedBlend(dif + col, col, 1, alphap);
            int dis = distributedBlend(dif + col, col, alphac, alphap);
            int sim = simplifiedBlend(dif + col, col, alphac, alphap);
            
            int com = compose(dif + col, col, alphap);
            
            System.out.printf("\ncol=% 4d,\tdif=% 4d,\tcom=% 4d\n\n", col, dif, com);
            System.out.print ("\t\tdis0\tsim0\tdis1\tsim1\tcom\t\tdis\tsim\n\n");
            System.out.printf("\t\t % 4d\t % 4d\t % 4d\t % 4d\t  % 4d\t\t  % 4d\t  % 4d\n\n", (byte)dis0, (byte)sim0, (byte)dis1, (byte)sim1, (byte)com, (byte)dis, (byte)sim);
        }
    }
    public static int compose(int screen, int sprite, int alpha){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr - pr) * alpha) >> 8);
        
        int g = (((cg - pg) * alpha) >> 8);
        
        int b = (((cb - pb) * alpha) >> 8);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }
    public static int distributedBlend(int screen, int sprite, int alphac, int alphap){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr * alphac - pr * alphap)) >> 8);
        
        int g = (((cg * alphac - pg * alphap)) >> 8);
        
        int b = (((cb * alphac - pb * alphap)) >> 8);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }
    public static int simplifiedBlend(int screen, int sprite, int alphac, int alphap){
        int cr = (screen & 0xff0000) >> 16, cg = (screen & 0x00ff00) >> 8, cb = screen & 0x0000ff;
        int pr = (sprite & 0xff0000) >> 16, pg = (sprite & 0x00ff00) >> 8, pb = sprite & 0x0000ff;
        
        int r = (((cr - pr) * alphac * alphap) >> 16);
        
        int g = (((cg - pg) * alphac * alphap) >> 16);
        
        int b = (((cb - pb) * alphac * alphap) >> 16);
        
        return ((cr - r) << 16) | ((cg - g) << 8) | (cb - b);
    }
    public int nestedBlend(int screen, int sprite, int alphac, int alphap){
        int prepass = compose(screen, sprite, alphap);
        return compose(screen, prepass, alphac);
    }
}
