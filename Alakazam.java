import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-17-14
*/
public class Alakazam extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage AlakazamImage;
   
    public Alakazam() {
        super(Arrays.asList(new Psychic(), new Psybeam(), new SandAttack(), new MegaPunch()),
         new PsychicType(), new Stats(140, 80, 75, 1.0, 1.0), "Alakazam");
        AlakazamImage = new GreenfootImage(getImage());
    }
}
   

