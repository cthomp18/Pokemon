import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Scyther extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage ScytherImage;
   
    public Scyther() {
        super(Arrays.asList(new RazorLeaf(), new SolarBeam(), new Tackle(), new SandAttack()),
         new PokeType("Grass"), new Stats(150, 140, 110, 1.0, 1.0), "Scyther");
        ScytherImage = new GreenfootImage(getImage());
    }
}
   

