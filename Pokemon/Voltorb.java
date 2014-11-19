import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Voltorb extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage VoltorbImage;
   
    public Voltorb() {
        super(Arrays.asList(new Thundershock(), new Thunder(), new Tackle(), new SandAttack()),
         new PokeType("Electric"), new Stats(125, 60, 80, 1.0, 1.0), "Voltorb");
        VoltorbImage = new GreenfootImage(getImage());
    }
}
   

