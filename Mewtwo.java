import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Metwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mewtwo extends Pokemon
{
   private PokeType type;
   private ArrayList<Attack> attacks;
   private ArrayList<Stats> stats;
   
   private GreenfootImage MewtwoImage;
   
    public Mewtwo() {
        MewtwoImage = new GreenfootImage(getImage());
        attacks = new ArrayList<Attack>(); 
        attacks.add(new Psychic());
        attacks.add(new Psybeam());
        attacks.add(new SandAttack());
        attacks.add(new Tackle());
        super(attacks, new PokeType("Psychic"), new Stats(190, 135, 120, 1.0, 1.0), "Mewtwo");
        
    }
    
}
