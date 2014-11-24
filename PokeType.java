import java.util.*;

/**
 * Write a description of class PokeType here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokeType  
{
    private ArrayList<String> strongAgainst, weakAgainst; //just hardcode it. But the AI doesn't know
    private String name;
    /**
     * Constructor for objects of class PokeType
     */
    public PokeType(String name, List<String> s, List<String> w)
    {
        this.name = name;
        this.strongAgainst = new ArrayList<String>(); //just hardcode it. But the AI doesn't know
        for (int i = 0; i < s.size(); i++) {
            this.strongAgainst.add(s.get(i));
        }
        this.weakAgainst = new ArrayList<String>(); 
        for (int i = 0; i < w.size(); i++) {
            this.weakAgainst.add(w.get(i));
        }
    }

    public String getName() {
       return this.name;
    }
    public ArrayList<String> getStrong() {
       return this.strongAgainst;
    }
    public ArrayList<String> getWeak() {
       return this.weakAgainst;
    }
    public boolean isStrong(PokeType p) {
        boolean b = false;
        for (int i = 0; i < strongAgainst.size(); i++) {
            if (strongAgainst.get(i).equals(p.getName()))
                b = true;
        }
        return b;
    }
    public boolean isWeak(PokeType p) {
        boolean b = false;
        for (int i = 0; i < weakAgainst.size(); i++) {
            if (weakAgainst.get(i).equals(p.getName()))
                b = true;
        }
        return b;
    }
}
