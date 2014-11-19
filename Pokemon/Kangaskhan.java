import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Kangaskhan extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage KangaskhanImage;
   
    public Kangaskhan() {
        super(Arrays.asList(new MegaPunch(), new Tackle(), new SandAttack(), new Slash()),
         new PokeType("Normal"),new Stats(190, 125, 110, 1.0, 1.0), "Kangaskhan");
        KangaskhanImage = new GreenfootImage(getImage());
    }
}
   

