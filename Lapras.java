import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Lapras extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage LaprasImage;
   
    public Lapras() {
        super(Arrays.asList(new Watergun(), new AuroraBeam(), new Tackle(), new HydroPump()),
         new PokeType("Water"), new Stats(215, 115, 110, 1.0, 1.0), "Lapras");
        LaprasImage = new GreenfootImage(getImage());
    }
}
   

