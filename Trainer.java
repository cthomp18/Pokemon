import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Trainer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trainer extends Actor
{
    ArrayList<Pokemon> pokemon;
    int currentPokemon;
    String name;
    
    /**
     * Act - do whatever the Trainer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Trainer(ArrayList<Pokemon> pokemon, String name) {
        this.pokemon = pokemon;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Attack attack() {
        return getAttackChoice();
    }
    
    public String action() {
        return "Attack";
    }
    
    public Attack getAttackChoice() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        //if(mouse.getX() > 5)
        // GreenfootImage attack1 = new GreenfootImage(".jpg");
       return new Attack("Hydro", 1.0, new WaterType(), 1.0);
    }
    
    //Returns current Pokmeon
    public Pokemon getCurrentPokemon() {
        return pokemon.get(currentPokemon);
    }
    
    //Returns index of current Pokemon
    public int getCurrentPokemonIndex() {
        return currentPokemon;
    }
    
    //Returns the trainers list of Pokemon
    public ArrayList<Pokemon> getAllPokemon() {
        return pokemon;
    }
    
    //Switches the currentPokemon index
    public Pokemon changePokemon(int newPokemon) {
        Pokemon p = pokemon.get(newPokemon);
        
        if (!p.isAlive())
            return null;
        else
            this.currentPokemon = newPokemon;
            
        return getCurrentPokemon();
    }
    
    /**/
    public void act() {
        
    }
}
