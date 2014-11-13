import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Blastoise here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blastoise extends Pokemon
{
   private PokeType type;
   private ArrayList<Attack> attacks;
   private ArrayList<Stats> stats;
   
    public Blastoise(ArrayList<Attack> att) {
        super(att, new PokeType("Water"), new Stats(165, 115, 130, 1.0, 1.0), "Blastoise");
    }
}
