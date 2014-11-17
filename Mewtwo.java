import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Metwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mewtwo extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage MewtwoImage;
   
    public Mewtwo() {
        super((ArrayList)Arrays.asList(new Psychic(), new Psybeam(), new SandAttack(), new Tackle()),
         new PokeType("Psychic"), new Stats(190, 135, 120, 1.0, 1.0), "Mewtwo");
        MewtwoImage = new GreenfootImage(getImage());
    }
}
   

