import java.util.ArrayList;

/**
 * Write a description of class PokeType here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokeType  
{
    private ArrayList<PokeType> strongAgainst, weakAgainst; //just hardcode it. But the AI doesn't know
    private String name;
    /**
     * Constructor for objects of class PokeType
     */
    public PokeType(String name)
    {
        this.name = name;
        this.strongAgainst = new ArrayList<PokeType>(); //just hardcode it. But the AI doesn't know
        this.weakAgainst = new ArrayList<PokeType>(); 
    }

    public String getName() {
       return this.name;
    }
    public ArrayList<PokeType> getStrong() {
       return this.strongAgainst;
    }
    public ArrayList<PokeType> getWeak() {
       return this.weakAgainst;
    }
}
