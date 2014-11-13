import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Pokemon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Pokemon extends Actor
{
    String name;
    ArrayList<Attack> attacks; //its a list of 4
    PokeType type;
    Stats stats;
    
    public Pokemon(ArrayList<Attack> attacks, PokeType type, Stats stats, String name){
        this.attacks = new ArrayList<Attack>();
        for (int i = 0; i < 4; i++) {
            this.attacks.add(attacks.get(i));
        }
        this.type = type;
        this.stats = stats;
        this.name = name;
    }
    
    public PokeType getType() {
        return this.type;
    }
    public ArrayList<Attack> getAttacks() {
        return this.attacks;
    }
    public Stats getStats() {
        return this.stats;
    }
    
    public boolean isAlive() {
        if (this.stats.getHP() > 0)
            return true;
        else
            return false;
    }
}
