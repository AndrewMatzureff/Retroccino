package Main;


/**
 * Write a description of class Test here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/1/2019)
 */
public class Test implements Game
{
    Kernel kernel;
    public Test(){
        this.kernel = new Kernel("Test");
    }
    public static void main(String[] args){
        Test test = new Test();
        test.kernel.loop(test);
    }
    public boolean update(double delta){System.out.println("update");
        return kernel.getKeyboard().alerted();
    }
}
