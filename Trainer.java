import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Trainer here.
 * * @author (your name) 
 * @version (a version number or a date)
 */
public class Trainer extends Actor
{
    ArrayList<Pokemon> pokemon;
    int currentPokemon;
    String name;
    
    public Trainer(ArrayList<Pokemon> pokemon, String name) {
        this.pokemon = pokemon;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
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

	//TODO: OVERWRITE
    public int nextMove() {
		return -1;
	}

	//TODO: OVERWRITE
	public int choosePokemon(Trainer Gary) {
		return -2;
	}
	//TODO: OVERWRITE
	public Attack getAttackChoice(Pokemon enemy, Pokemon Gary) {
		return new Attack("Hydro", 1.0, new WaterType(), 1.0);
	}
	public void saveOpponent(String opp) {}
    public void opponentChangedTo(Pokemon newPoke) {}
    public void superEffective(String pokemon, String typ) {}
    public void opponentNotEffective(String pokemon) {}
    public void notEffective(String pokemon, String typ) {}
    public void opponentSuperEffective(String pokemon, String attackType) {}
    
}
