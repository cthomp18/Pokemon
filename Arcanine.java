import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Arcanine extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage ArcanineImage;
   
    public Arcanine() {
        super(Arrays.asList(new Flamethrower(), new FireBlast(), new Tackle(), new Slash()),
         new PokeType("Fire"), new Stats(175, 140, 110, 1.0, 1.0), "Arcanine");
        ArcanineImage = new GreenfootImage(getImage());
    }
}
   

