import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Trainer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trainer extends Actor
{
	static final int WEAK_AGAINST = 1;
	static final int STRONG_AGAINST = 2;
    ArrayList<Pokemon> pokemon;
    int currentPokemon;
    String name;
	PokeInfo pokedex = new PokeInfo();

	private class PokeInfo { 
		ArrayList<Poke> allPokes;
		boolean updateFlag = false;
		public PokeInfo() {
			allPokes = new ArrayList<Poke>();
		}
		public void discover(String name, int isWeakness, String typ) {
			for(Poke p : allPokes) {
				if(p.name == name) {
					p.addInfo(typ, isWeakness);
					updateFlag = true;
				}
			}
			//we need to add new info
			if(updateFlag == false) {
				Poke newP = new Poke(name);
				newP.addInfo(typ, isWeakness);
				allPokes.add(newP);
			}
		}
		public Poke searchPoke(String name) {
			for(Poke p : allPokes) {
				if(p.name == name) {
					return p;
				}
			}
			return null;
		}
	}

	private class Poke {
		public String name;
		private ArrayList<String> strongAgainst;
		private ArrayList<String> weakAgainst;
		public Poke(String name) {
			this.name = name;
			this.strongAgainst = new ArrayList<String>();
			this.weakAgainst = new ArrayList<String>();
		}
		public void addInfo(String t, int isWeakness) {
			if(isWeakness == WEAK_AGAINST) {
				if(!this.weakAgainst.contains(t))
					this.weakAgainst.add(t);
			}
			else {
				if(!this.strongAgainst.contains(t))
					this.strongAgainst.add(t);
			}
		}
		public ArrayList<String> getWeakAgainst() {
			return this.weakAgainst;
		}
		public ArrayList<String> getStrongAgainst() {
			return this.strongAgainst;
		}
	}
    
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
    
    public String action() {
        return "Attack";
    }
    
    public Attack getAttackChoice(Pokemon enemy, Pokemon local) {
		String enemyName = enemy.getName();
		Pokemon currPoke = this.getCurrentPokemon();
		Poke enemyPoke = pokedex.searchPoke(enemyName);
		ArrayList<Integer> outlawed = new ArrayList<Integer>();
		ArrayList<Attack> possibleAtts = new ArrayList<Attack>();
		int attIndex = 0;
		int sand = -1;
		int currHP = enemy.getStats().getHP();	
		int maxHP = enemy.getMaxHP();
		double percentageHealth, random;
		percentageHealth = (double)currHP / (double)maxHP;
		System.out.println("percentage health - " + percentageHealth);

		if(enemyPoke != null) {
			for(String weakness : enemyPoke.getWeakAgainst()) {
				//TODO: Change in accordance with enemy health
				for(Attack a : currPoke.getAttacks()) {
					if(a.getType().getName() == weakness)
						possibleAtts.add(a);
				}
			}
			//You know their weakness, use one of those
			if(!possibleAtts.isEmpty())
				return smartChoice(possibleAtts, percentageHealth, local);
			for(String strength : enemyPoke.getStrongAgainst()) {
				for(Attack a : currPoke.getAttacks()) {
					if(a.getType().getName() == strength)
						outlawed.add(attIndex);
					attIndex++;
				}
			}
		}
		attIndex = 0;
		for(Attack a : currPoke.getAttacks()) {
			if(a.getName() == "Sand Attack") {
				sand = attIndex;
			}
			attIndex++;
		}
		//They have sand attack
		if(sand != -1) {
			if(percentageHealth > .85)
				percentageHealth -= 0.6;
			else
				percentageHealth -= 0.5;
			random = Math.random();
			//Eg - 70% health has .2 chance of sand attack
			if(random < percentageHealth) {
				System.out.println("Sand!");
				return currPoke.getAttacks().get(sand);
			}
			else
				outlawed.add(sand);
		}
		for(int i = 0; i < 4; i++) {
			if(!outlawed.contains(i)) 
				possibleAtts.add(currPoke.getAttacks().get(i));
		}

        return smartChoice(possibleAtts, percentageHealth, local);
    }

	public Attack smartChoice(ArrayList<Attack> possibleAtts, double percentHealth, Pokemon local) {
		ArrayList<Double> accList = new ArrayList<Double>();
		int myHP = local.getStats().getHP();
		System.out.println("myHP is " + myHP);
		int index, numOptions;
		double rand;

		for(Attack a : possibleAtts) {
			accList.add(a.getBaseAccuracy());
		}
		index = accList.indexOf(Collections.min(accList));
		System.out.println("Worst accuracy is "+accList.get(index));

		//If they have a lot of health or I'm almost dead
		if(percentHealth > 0.5) {
			if (Math.random() < 0.5) {
				System.out.println("Go for it! Best Attack!!!");
				return possibleAtts.get(index);
			}
		} 
		else if(percentHealth > 0.2 && myHP < 25) {
			System.out.println("I'm almost dead! Best Attack!!!");
			return possibleAtts.get(index);
		}
		System.out.println("Number options initial="+accList.size());
		//Take out strong inaccurate move
		if(accList.size() != 1) {
			possibleAtts.remove(index);
		}
		rand = Math.random();
		numOptions = possibleAtts.size();
		System.out.println("Meh, number options after best eliminated = " + numOptions);
		index = (int) (rand * numOptions) % numOptions;
		return possibleAtts.get(index);
		
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
	
	public void superEffective(String pokemon, String typ) {
		pokedex.discover(pokemon, WEAK_AGAINST, typ);
	}

	public void notEffective(String pokemon, String typ) {
		pokedex.discover(pokemon, STRONG_AGAINST, typ);
	}
    
    /**/
    public void act() {
        
    }
}
