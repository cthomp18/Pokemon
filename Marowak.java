import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Marowak extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage MarowakImage;
   
    public Marowak() {
        super(Arrays.asList(new RockThrow(), new Earthquake(), new SandAttack(), new Tackle()),
         new RockType(), new Stats(155, 110, 140, 1.0, 1.0), "Marowak");
        MarowakImage = new GreenfootImage(getImage());
    }
}
   

