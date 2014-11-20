import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Venusaur extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage VenusaurImage;
   
    public Venusaur() {
        super(Arrays.asList(new RazorLeaf(), new SolarBeam(), new Tackle(), new SandAttack()),
         new PokeType("Grass"), new Stats(160, 115, 120, 1.0, 1.0), "Venusaur");
        VenusaurImage = new GreenfootImage(getImage());
    }
}
   

