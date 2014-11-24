/**
 * Write a description of class Psychic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Psychic extends Attack 
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Psychic
     */
    public Psychic() {
        super("Psychic", 90, new PsychicType(), 1.0);
    }
}
