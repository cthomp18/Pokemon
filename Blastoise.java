import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Blastoise here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blastoise extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;

   private GreenfootImage blastoiseImage;
   
    public Blastoise() {
      super((ArrayList)Arrays.asList(new AuroraBeam(), new HydroPump(), new Slash(), new Tackle()),
       new PokeType("Water"), new Stats(165, 115, 130, 1.0, 1.0), "Blastoise"); 
      blastoiseImage = new GreenfootImage(getImage());
    } 
    
}
