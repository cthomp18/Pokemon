import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * 
 * @author Cameron Javier 
 * @version 11-18-14
*/
public class Jigglypuff extends Pokemon
{
   private PokeType type;
   private ArrayList<Stats> stats;
   
   private GreenfootImage JigglypuffImage;
   
    public Jigglypuff() {
        super(Arrays.asList(new Megapunch(), new SandAttack(), new Tackle(), new Slash()),
		 //TODO: Can we make evade and accuracy above 1.0?
         new PokeType("Normal"), new Stats(200, 75, 55, 1.2, 1.2), "Jigglypuff");
        JigglypuffImage = new GreenfootImage(getImage());
    }
}
   

