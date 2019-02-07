package IO;

import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.File;

/**
 * Write a description of class Log here.
 * 
 * @author (Andrew Matzureff) 
 * @version (3/17/2017)
 */
public class Log
{
    private static String NULL = "\n";
    private static String BREAK = "\n\n\n";
    private static String INDENT = "\t";
    private static Date DATE = null;
    private static Path PATH = new File(Paths.get("").toAbsolutePath().toString() + File.separator + "ActionLog.txt").toPath();
    private static ArrayList<String> APPEND = new ArrayList<String>(2);
    public static boolean append(String message)
    {
        boolean logged;
        APPEND.clear();
        if(DATE == null)
        {
            DATE = new java.util.Date(System.currentTimeMillis());
            APPEND.add(BREAK);
        }
        else
        {
            DATE.setTime(System.currentTimeMillis());
        }
        if(message != null)
        {
            APPEND.add(NULL);
            APPEND.add(DATE.toString());
            APPEND.add(INDENT + message);
            logged = true;
        }
        else
            logged = false;
        try{
            Files.write(PATH, APPEND, Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }catch(IOException ioe){ioe.printStackTrace(); return false;}
        return logged;
    }
    public static boolean append(String... messages)
    {
        boolean logged;
        APPEND.clear();
        if(DATE == null)
        {
            DATE = new java.util.Date(System.currentTimeMillis());
            APPEND.add(BREAK);
        }
        else
        {
            DATE.setTime(System.currentTimeMillis());
        }
        if(messages != null)
        {
            APPEND.add(NULL);
            APPEND.add(DATE.toString());
            for(int i = 0; i < messages.length; i++)
            {
                if(messages[i] != null)
                    APPEND.add(INDENT + messages[i]);
            }
            logged = true;
        }
        else
            logged = false;
        try{
            Files.write(PATH, APPEND, Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }catch(IOException ioe){ioe.printStackTrace(); return false;}
        return logged;
    }
}
