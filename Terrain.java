import java.util.ArrayList;
/**
 * Write a description of class Terrain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Terrain  
{
    // instance variables - replace the example below with your own
    private ArrayList<String> strongFor;
    private ArrayList<String> weakFor;

    /**
     * Constructor for objects of class Terrain
     */
    public Terrain(ArrayList<String> s, ArrayList<String> w)
    {
        this.strongFor = s;
        this.weakFor = w;
    }

    public boolean isStrong(String type) {
        boolean b = false;
        for (int i = 0; i < strongFor.size(); i++) {
            if (strongFor.get(i).equals(type))
                b = true;
        }
        return b;
    }
    public boolean isWeak(String type) {
        boolean b = false;
        for (int i = 0; i < weakFor.size(); i++) {
            if (weakFor.get(i).equals(type))
                b = true;
        }
        return b;
    }
}
