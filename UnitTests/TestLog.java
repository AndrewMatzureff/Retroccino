package UnitTests;


/**
 * Write a description of class TestLog here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/22/2017)
 */
public class TestLog
{
    public static void main(String[] args)
    {
        IO.Log.append("Appended");
        IO.Log.append("Appended0", "Appended1", "Appended2", "Appended3", "Appended4");
        IO.Log.append(new String[]{"Appended0", "Appended1", "Appended2", "Appended3", "Appended4"});
    }
}
