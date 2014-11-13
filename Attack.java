/**
 * Write a description of class Attack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Attack  
{
    private String name;
    private double base;
    private PokeType type;
    private double baseAccuracy; //percentage
    
    /**
     * Constructor for objects of class Attack
     */
    public Attack(String name, double base, PokeType type, double baseAccuracy)
    {
        this.name = name;
        this.base = base;
        this.type = type;
        this.baseAccuracy = baseAccuracy; //percentage
    }
    public String getName() {
        return this.name;
    }
    public double getBase() {
        return this.base;
    }
    public PokeType getType() {
        return this.type;
    }
    public double getBaseAccuracy() {
        return this.baseAccuracy;
    }
}
