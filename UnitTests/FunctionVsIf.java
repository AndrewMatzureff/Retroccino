package UnitTests;


/**
 * Write a description of class FunctionVsIf here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/4/2019)
 */
public class FunctionVsIf
{
    public static void main(String[] args){
        int samples = 1000;
        long start = System.nanoTime();
        for(int i = 0; i < samples; i++){
            for(int j = 0; j < samples; j++){
                int b = compose(-i * samples, -j * samples, 125) + 1;
            }
        }
        int draws = samples * samples;
        int passes = 0;
        for(int i = 0; i < samples; i++){
            for(int j = 0; j < samples; j++){
                if(passes < draws){
                    int b = compose(-i * samples, -j * samples, 125) + 1;
                    passes++;
                }
            }
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
}
