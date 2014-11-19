import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Dragonite extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage DragoniteImage;
   
    public Dragonite() {
        super(Arrays.asList(new Peck(), new DrillPeck(), new SandAttack(), new Tackle()),
         new PokeType("Flying"), new Stats(170, 165, 125, 1.0, 1.0), "Dragonite");
        DragoniteImage = new GreenfootImage(getImage());
    }
}
   

