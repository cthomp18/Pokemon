import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.lang.Math;

/**
 * Write a description of class Trainer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CameronTrainer extends Trainer 
{
	//TODO: First move is own type, make sure you know if you missed
    static final int WEAK_AGAINST = 1;
    static final int STRONG_AGAINST = 2;
    static final int WEAK_TOWARDS = 3;
    static final int STRONG_TOWARDS = 4;
    static final int FIGHT = 1;
    static final int NEXT_POKE= 2;
    boolean debug = true;
    
    int pokeChoice = -1;
    int choice = FIGHT;
    PokeInfo pokedex = new PokeInfo();

    private class PokeInfo { 
        ArrayList<Poke> allPokes;
        boolean updateFlag = false;
        public PokeInfo() {
            allPokes = new ArrayList<Poke>();
        }
        public void discover(String name, int weakStrongEnum, String typ) {
            for(Poke p : allPokes) {
                if(p.name == name) {
                    p.addInfo(typ, weakStrongEnum);
                    updateFlag = true;
                }
            }
            //we need to add new info
            if(updateFlag == false) {
                Poke newP = new Poke(name);
                allPokes.add(newP);
                newP.addInfo(typ, weakStrongEnum);
            }
			updateFlag = false;
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
                if(!s.getWeakAgainst().isEmpty())
                    return s.getWeakAgainst();
                else
                    debug("Weaknesses empty");
                    
            }
            else
                debug("SearchPoke came up with nothin");
            return null;
        }
        public ArrayList<String> getWeakTowards(String name) {
            Poke s = searchPoke(name);
            debug("Searching for weakensses for "+name);
            if(s != null) {
                if(!s.getWeakTowards().isEmpty())
                    return s.getWeakTowards();
                else
                    debug("Weaknesses empty");
                    
            }
            else
                debug("SearchPoke came up with nothin");
            return null;
        }
        public ArrayList<String> getStrongTowards(String name) {
            Poke s = searchPoke(name);
            debug("Searching for strengths of "+name);
            if(s != null) {
                if(!s.getStrongTowards().isEmpty())
                    return s.getStrongTowards();
                else
                    debug("Strengths empty");
                    
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
        private ArrayList<String> weakTowards;
        private ArrayList<String> strongTowards;
        public Poke(String name) {
            this.name = name;
            this.strongAgainst = new ArrayList<String>();
            this.weakAgainst = new ArrayList<String>();
            this.strongTowards = new ArrayList<String>();
            this.weakTowards = new ArrayList<String>();
        }
        public void addInfo(String t, int weakStrongEnum) {
            if(weakStrongEnum == WEAK_AGAINST) {
                if(!this.weakAgainst.contains(t))
                    debug("NEWSFLASH! - "+name+" is weak against "+t);
                    this.weakAgainst.add(t);
            }
            else if(weakStrongEnum == STRONG_AGAINST) {
                if(!this.strongAgainst.contains(t))
                    debug("NEWSFLASH! - "+name+" is strong against "+t);
                    this.strongAgainst.add(t);
            }
            else if(weakStrongEnum == WEAK_TOWARDS) {
                if(!this.weakTowards.contains(t))
                    debug("NEWSFLASH! - "+name+" is weak toward "+t);
                    this.weakTowards.add(t);
            }
            else {
                if(!this.strongTowards.contains(t))
                    debug("NEWSFLASH! - "+name+" is strong toward "+t);
                    this.strongTowards.add(t);
            }
        }
        public ArrayList<String> getWeakAgainst() {
            return this.weakAgainst;
        }
        public ArrayList<String> getStrongAgainst() {
            return this.strongAgainst;
        }
        public ArrayList<String> getStrongTowards() {
            return this.strongTowards;
        }
        public ArrayList<String> getWeakTowards() {
            return this.weakTowards;
        }
    }
    
    public CameronTrainer(ArrayList<Pokemon> pokemon, String name) {
		super(pokemon, name);
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
                percentageHealth -= 0.55;
            else
                percentageHealth -= 0.5;
            random = Math.random();
            //Eg - 70% health has .2 chance of sand attack
            if(random < percentageHealth) {
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
        int index, numOptions;
        double rand;

        for(Attack a : possibleAtts) {
            accList.add(a.getBaseAccuracy());
        }
        index = accList.indexOf(Collections.min(accList));

        //If they have a lot of health or I'm almost dead
        if(percentHealth > 0.25) {
            if (Math.random() < 0.5 || myHP < 25) {
                return possibleAtts.get(index);
            }
        } 
        debug("Number options initial="+accList.size());
        //Take out strong inaccurate move
        if(accList.size() != 1) {
            possibleAtts.remove(index);
        }
		//prefer own move
		for(Attack a : possibleAtts) {
			if(getCurrentPokemon().getType().getName() == a.getType().getName())
				return a;
		}
        rand = Math.random();
        numOptions = possibleAtts.size();
        debug("Meh, number options after best/sand eliminated = " + numOptions);
        index = (int) (rand * numOptions) % numOptions;
        return possibleAtts.get(index);
    }
    /*
	 * Towards = Attacking
	 * Against = Defending
	 */ 
    //Gary pokemon is strong against enemy
    public void superEffective(String enemy, String typ) {
		//we only know our type
		pokedex.discover(enemy, WEAK_AGAINST, typ); 
    }
    public void opponentNotEffective(String enemy) {
        String myType = getCurrentPokemon().getType().getName();
        pokedex.discover(enemy, WEAK_TOWARDS, myType);
    }
    public void notEffective(String enemy, String typ) {
        pokedex.discover(enemy, STRONG_AGAINST, typ);
        int otherOption = otherOption(enemy);
        if(otherOption != -1) {
            choice = NEXT_POKE;
            pokeChoice = otherOption;
        }
    }
    public void opponentSuperEffective(String enemy) {
        String myType = getCurrentPokemon().getType().getName();
        pokedex.discover(enemy, STRONG_TOWARDS, myType);
        int otherOption = otherOption(enemy);
        if(otherOption != -1) {
            choice = NEXT_POKE;
            pokeChoice = otherOption;
        }
    }
    public int nextMove() {
        int currHP = getCurrentPokemon().getStats().getHP();  
        int maxHP = getCurrentPokemon().getMaxHP();
        double percentageHealth = (double)currHP / (double)maxHP;
        if(choice == FIGHT || percentageHealth < .2) 
            return 1;
        else
            return 2;
    }
    //This is only code that chooses next poke
    public int choosePokemon(Trainer gary) {
        ArrayList<Pokemon> ps = getAllPokemon();
        Pokemon p;
        int r = -1;
        double rand;
        //switching due to non-effective
        if(pokeChoice != -1) {
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
        ArrayList<String> weakTowards = pokedex.getWeakTowards(enemy);
        ArrayList<String> strongTowards = pokedex.getStrongTowards(enemy);
		String myType;
		int i, j;
        //check for it's weakness
        if(weakAgainst != null) {
            for(String wa : weakAgainst) {
                for(i = 0; i < ps.size(); i++) {
                    if(ps.get(i).isAlive() && ps.get(i).getType().getName() == wa && getCurrentPokemonIndex() != i)
                        return i;
                }
            }
            debug("Have no weakness type");
        }
        if(weakTowards != null) {
            for(String wt : weakTowards) {
                for(i = 0; i < ps.size(); i++) {
                    if(ps.get(i).isAlive() && ps.get(i).getType().getName() == wt && getCurrentPokemonIndex() != i)
                        return i;
                }
            }
            debug("Have no types he's weak towards");
        }
        //At this point we either don't have weakness type or don't know it.
        //Bring out a different type if you have it
        for(j = 0; j < ps.size(); j++) {
            if(ps.get(j).isAlive() && getCurrentPokemonIndex() != j) {
				myType = ps.get(j).getType().getName();
                if(strongAgainst != null && strongTowards != null) {
                    if(!strongAgainst.contains(myType) && !strongTowards.contains(myType)) {
                        debug("This pokemon is alive and we don't know if "+myType+"'s weak against enemy");
                        return j;
                    }
                }
                else if(strongAgainst != null) {
                    if(!strongAgainst.contains(myType)) {
                        debug("This pokemon is alive and we don't know if "+myType+"'s weak against enemy");
                        return j;
                    }
                }
                else if(strongTowards != null) {
                    if(!strongTowards.contains(myType)) {
                        debug("This pokemon is alive and we don't know if "+myType+"'s weak against enemy");
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
    public void opponentChangedTo(Pokemon newPoke) {
        ArrayList<String> typeHeStrongToward = pokedex.getStrongTowards(newPoke.getName());
        ArrayList<String> typeImWeakAgainst = pokedex.getWeakAgainst(getCurrentPokemon().getName());
        ArrayList<String> typeImWeakTowards = pokedex.getWeakTowards(getCurrentPokemon().getName());
        String currType = getCurrentPokemon().getType().getName();
        String enemyType = newPoke.getType().getName();
        if(typeHeStrongToward != null) {
            for(String t : typeHeStrongToward) {
                if(currType == t) {
                    debug("Change! currType is "+currType+" and they are strong against us!");
                    pokeChoice = otherOption(newPoke.getName());
					//if we have another option
					if(pokeChoice != -1)
						choice = NEXT_POKE;
                }
            }
        }
        if(typeImWeakAgainst != null) {
			for(String wa : typeImWeakAgainst) {
				if(enemyType == wa) {
					debug("Change! enemy is "+enemyType+" and I am weak against them!");
					pokeChoice = otherOption(newPoke.getName());
					if(pokeChoice != -1)
						choice = NEXT_POKE;
				}
			}
        }
        if(typeImWeakTowards != null) {
			for(String wa : typeImWeakTowards) {
				if(enemyType == wa) {
					debug("Change! enemy is "+enemyType+" and I am weak toward them!");
					pokeChoice = otherOption(newPoke.getName());
					if(pokeChoice != -1)
						choice = NEXT_POKE;
				}
			}
        }
    }
	//Opponent killed us
	public void saveOpponent(String opp) {
		choice = NEXT_POKE;
		pokeChoice = otherOption(opp);
	}
    public void debug(String str) {
        if(this.getName() == "Gary" && debug == true)
            System.out.println(str);
    }
}
    
