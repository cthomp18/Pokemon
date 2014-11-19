import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Pidgeot extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage PidgeotImage;
   
    public Pidgeot() {
        super(Arrays.asList(new Peck(), new DrillPeck(), new SandAttack(), new Tackle()),
         new PokeType("Flying"), new Stats(165, 110, 105, 1.0, 1.0), "Pidgeot");
        PidgeotImage = new GreenfootImage(getImage());
    }
}
   

