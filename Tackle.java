/**
 * Write a description of class Tackle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tackle extends Attack 
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Tackle
     */
    public Tackle() {
        super("Tackle", 50, new PokeType("Normal"), 1.0);
    }
}
