import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.lang.Math;

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
    static final int FIGHT = 1;
    static final int NEXT_POKE= 2;
	boolean debug = true;
	
	int pokeChoice = -1;
	int choice;
    ArrayList<Pokemon> pokemon;
    int currentPokemon;
    String name;
	PokeInfo pokedex = new PokeInfo();

	private class PokeInfo { 
		ArrayList<Poke> allPokes;
		boolean updateFlag = false;
		public PokeInfo() {
			allPokes = new ArrayList<Poke>();
			/*
			discover("Pikachu", WEAK_AGAINST, "Fire");
			discover("Mewtwo", WEAK_AGAINST, "Fire");
			discover("Lapras", WEAK_AGAINST, "Fire");
			discover("Arcanine", WEAK_AGAINST, "Fire");
			discover("Onix", WEAK_AGAINST, "Fire");
			discover("Scyther", WEAK_AGAINST, "Fire");
			discover("Alakazam", WEAK_AGAINST, "Fire");
			discover("Rapidash", WEAK_AGAINST, "Fire");
			discover("Dragonite", WEAK_AGAINST, "Fire");
			discover("Voltorb", WEAK_AGAINST, "Fire");
			discover("Blastoise", WEAK_AGAINST, "Fire");
			discover("Pidgeot", WEAK_AGAINST, "Fire");
			*/
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
		public ArrayList<String> getStrongAgainst(String name) {
			Poke s = searchPoke(name);
			debug("Searching for strengths of "+name);
			if(s != null) {
				debug("Found the poke");
				if(!s.getStrongAgainst().isEmpty())
					return s.getStrongAgainst();
				else
					debug("Strengths empty");
					
			}
			else
				debug("SearchPoke came up with nothin");
			return null;
		}
		public ArrayList<String> getWeakAgainst(String name) {
			Poke s = searchPoke(name);
			debug("Searching for weakensses for "+name);
			if(s != null) {
				debug("Found the poke");
				if(!s.getWeakAgainst().isEmpty())
					return s.getWeakAgainst();
				else
					debug("Weaknesses empty");
					
			}
			else
				debug("SearchPoke came up with nothin");
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
		this.choice = FIGHT;
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
		debug("percentage health - " + percentageHealth);

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
				debug("Sand!");
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
		debug("myHP is " + myHP);
		int index, numOptions;
		double rand;

		for(Attack a : possibleAtts) {
			accList.add(a.getBaseAccuracy());
		}
		index = accList.indexOf(Collections.min(accList));
		debug("Worst accuracy is "+accList.get(index));

		//If they have a lot of health or I'm almost dead
		if(percentHealth > 0.5) {
			if (Math.random() < 0.5) {
				debug("Go for it! Best Attack!!!");
				return possibleAtts.get(index);
			}
		} 
		else if(percentHealth > 0.2 && myHP < 25) {
			debug("I'm almost dead! Best Attack!!!");
			return possibleAtts.get(index);
		}
		debug("Number options initial="+accList.size());
		//Take out strong inaccurate move
		if(accList.size() != 1) {
			possibleAtts.remove(index);
		}
		rand = Math.random();
		numOptions = possibleAtts.size();
		debug("Meh, number options after best eliminated = " + numOptions);
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

	public void superEffective(String pokemon) {
		String myType = getCurrentPokemon().getType().getName();
		//TODO: change to be real logic
		pokedex.discover(pokemon, WEAK_AGAINST, myType);
	}

	//pokemon is strong against typ
	public void notEffective(String pokemon, String typ) {
		pokedex.discover(pokemon, STRONG_AGAINST, typ);
		int otherOption = otherOption(pokemon);
		debug(pokemon+" is strong against "+typ);
		if(otherOption != -1) {
			choice = NEXT_POKE;
			pokeChoice = otherOption;
		}
	}

	public void notEffective(String pokemon) {
		String myType = getCurrentPokemon().getType().getName();
		pokedex.discover(pokemon, STRONG_AGAINST, myType);
		int otherOption = otherOption(pokemon);
		debug(pokemon+" is strong against "+myType);
		if(otherOption != -1) {
			choice = NEXT_POKE;
			pokeChoice = otherOption;
		}
	}
	public int nextMove() {
		if(choice == FIGHT) 
			return 1;
		else
			return 2;
	}
	//THis is only code that chooses next poke
	public int choosePokemon(Trainer gary) {
		ArrayList<Pokemon> ps = getAllPokemon();
		Pokemon p;
		int r = -1;
		double rand;
		//switching due to non-effective
		if(pokeChoice != -1) {
			debug("We are switching due to non-effective");
			r = pokeChoice;
			//reset so you don't think you still know who to send out 
			pokeChoice = -1;
		}
		else {
			do {
				rand = Math.random();
				r = (int) (rand * 6) % 6;
				p = ps.get(r);
			} while (!p.isAlive() && r != getCurrentPokemonIndex());
		}
		choice = FIGHT;
		return r;
	} 
	//TODO: If we have other pokemon or theyre not all weak against him, dont pick same guy, dont bring back and take back out, instant release
	//If we know who's weak against, release.
	//Else: If it's not enemy's strength, they're alive, and it's not same guy on floor, release
	public int otherOption(String enemy) {
		ArrayList<Pokemon> ps = getAllPokemon();
		ArrayList<String> weakAgainst = pokedex.getWeakAgainst(enemy);
		ArrayList<String> strongAgainst = pokedex.getStrongAgainst(enemy);
		//check for it's weakness
		if(weakAgainst != null) {
			for(String wa : weakAgainst) {
				for(int i = 0; i < ps.size(); i++) {
					if(ps.get(i).isAlive() && ps.get(i).getType().getName() == wa && getCurrentPokemonIndex() != i)
						return i;
				}
			}
			debug("Have no weakness type");
		}
		//At this point we either don't have weakness type or don't know it.
		//Bring out a different type if you have it
		for(int j = 0; j < ps.size(); j++) {
			if(ps.get(j).isAlive() && getCurrentPokemonIndex() != j) {
				if(strongAgainst != null) {
					for(String s : strongAgainst)
						debug(s);
					if(!strongAgainst.contains(ps.get(j).getType().getName())) {
						debug("This pokemon is alive and we don't know if "+ps.get(j).getType().getName()+"'s weak against enemy");
						return j;
					}
				}
				else
					return j;
			}
		}
		
		debug("Unsure what it's weak against, or have none and no one else is alive, don't switch");
		return -1;
	}
	public void debug(String str) {
		if(name == "Gary" && debug == true)
			System.out.println(str);
	}
}
    
