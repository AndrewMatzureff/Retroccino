package IO;

import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import Constants.C;

/**
 * Write a description of class Text here.
 * 
 * @author (Andrew Matzureff) 
 * @version (11/12/2016)
 */
public class Text
{
    public static List<String> readLines(String path)
    {
        try{
            Charset chars;
            int dot = path.indexOf(".");
            String ext = (path.substring(dot + 1)).toLowerCase();
            if(ext.equals(C.TX8))//before I knew 
                chars = StandardCharsets.UTF_8;
            else
            if(ext.equals(C.T16))
                chars = StandardCharsets.UTF_16LE;
            else
            if(ext.equals(C.B16))
                chars = StandardCharsets.UTF_16BE;
            else
            if(ext.equals(C.TXT))
                chars = Charset.defaultCharset();//NOTE: reading in ANSI .txt files as UTF_16 Charsets DOES produce an IO error!
            else
                chars = StandardCharsets.UTF_16;
            Log.append("Charset for: File \""+ path +"\" is \"" + chars.name() + "\".");
            return Files.readAllLines(new File(path).toPath(), chars);
        }catch(IOException ioe)
        {
            Log.append("IO Exception at Text.readLines(String): File \""+ path +"\" may not exist or is incomplete.");
            return null;
        }
        catch(SecurityException se)
        {
            Log.append("Security Exception at Text.readLines(String): Access to File \""+ path +"\" is denied.");
            return null;
        }
    }
}
