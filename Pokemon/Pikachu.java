import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Pikachu extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage PikachuImage;
   
    public Pikachu() {
        super(Arrays.asList(new Thundershock(), new Thunder(), new MegaPunch(), new Tackle()),
         new PokeType("Electric"), new Stats(120, 75, 60, 1.2, 1.2), "Pikachu");
        PikachuImage = new GreenfootImage(getImage());
    }
}
   

