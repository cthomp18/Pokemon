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
   
    public Mewtwo(ArrayList<Attack> att) {
        super(att, new PokeType("Psychic"), new Stats(190, 135, 120, 1.0, 1.0), "Mewtwo");
        MewtwoImage = new GreenfootImage(getImage());
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if(mouse != null && mouse.getX() > 300) {
            MewtwoImage.mirrorHorizontally();
        }
        
    }
    
}
