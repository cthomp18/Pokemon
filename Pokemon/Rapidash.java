import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Rapidash extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage RapidashImage;
   
    public Rapidash() {
        super(Arrays.asList(new Flamethrower(), new Fireblast(), new Tackle(), new SandAttack()),
         new PokeType("Fire"), new Stats(150, 130, 100, 1.0, 1.0), "Rapidash");
        RapidashImage = new GreenfootImage(getImage());
    }
}
   

