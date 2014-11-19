import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Onix extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage OnixImage;
   
    public Onix() {
        super(Arrays.asList(new RockThrow(), new Earthquake(), new Tackle(), new SandAttack()),
         new PokeType("Rock"), new Stats(120, 75, 190, 1.0, 1.0), "Onix");
        OnixImage = new GreenfootImage(getImage());
    }
}
   

