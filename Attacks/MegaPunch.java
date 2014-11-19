/**
 * Write a description of class MegaPunch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MegaPunch extends Attack 
{
    /**
     * Constructor for objects of class MegaPunch
     */
    public MegaPunch()
    {
        //Attack(name, base, type, baseAccuracy);
        super("Mega Punch", 80.0, new PokeType("Normal"), .85);
    }
}
